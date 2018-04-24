## SORACOM Inventory agent for Java
SORACOM Inventory agent for Java は、SORACOM Inventory用のエージェント実装を提供します。
このエージェントには、SIM経由のブートストラップ、取得したキーの保存、基本的なLWM2Mのオブジェクト定義が入っています

##セットアップ
Eclipseの場合、以下のコマンドでEclipse用のプロジェクトファイルを生成できます。
```
./gradlew eclipse
```
なお、IntelliJの場合は、"./gradrew idea"で同様にファイルを生成できます。

実行後、Eclipseにプロジェクトをインポートします。
soracom-inventory-agent-for-java-coreプロジェクトは、Inventoryエージェントを作成するのに便利なクラス群を提供する
ためのライブラリプロジェクトです。
soracom-inventory-agent-for-java-exampleプロジェクトは、Inventoryエージェントを作成するためのサンプルが入った
プロジェクトです。基本的にこちらに変更をおこなって頂き、デバイスに合ったエージェントを作成します。

##ビルド
ビルドは以下のコマンドで実行できます。 
```
./gradlew build
```

##配布
アプリケーション配布用のZipファイルは、以下のコマンドで生成できます。
生成されたアーカイブは、build/distributionsに出力されます。
```
./gradlew distZip
```
生成されたzip/tarを解凍したあとにできるbinディレクトリに、実行用のシェルが同梱されています。
デバイス上で動作させる場合は、このシェルを利用してください。

##動作
初回通信時は、SIM経由のブートストラップを実施します。一度bootstrapに成功すると、.soracom-inventory-credentials.datというファイルが作成されます。
次回以降はこのファイルを使ってbootstrapなしでSORACOM Inventoryと通信できるため、wifiなどSIM以外経由でも通信を行うことが出来ます。
再度ブートストラップしたい場合は、ファイルを消して再度実行するか、実行時に -b オプションをつけてください。

##実装方法
SORACOMInventoryAgentExampleが、mainクラスとなります。このクラスが実行のメインクラスとなります。
環境に応じて、適宜変更してください。

実装する必要があるクラスは、io.soracom.inventory.agent.core.lwm2m.typed_object パッケージに入っているオブジェクト群です。
(release-0.0.1で実装されたio.soracom.inventory.agent.core.lwm2m.base_objectは非推奨になっています）
このオブジェクトは、LWM2M v1.0で規定された定義XMLから生成されたクラスとなっています。

実際にサーバとやりとりをするためには、このクラスを継承したクラスを作成して、実装したいメソッドをオーバーライドして
実装を行ってください。

exampleのプロジェクトでは、"DeviceObject","LocationObject", "LWM2MSoftwareComponent"を実装したサンプルが入っています。

実装後、InventoryAgentInitializer#addInstancesForObjectメソッドを使用して、登録を行います。

また外部シェルを実行する場合のサンプル実装が、DeviceObjectのRebootとSoftwareComponentObjectのActivate/Deactivateに
実装されています。
これらのExecuteをサーバ側から呼ぶと、scriptフォルダ内のシェルが実行されます。

実行するシェルスクリプトを変えたり、種類を増やす場合はこれらを参考にしてください。

##カスタムオブジェクトの利用
規定のオブジェクト以外は、次の手順で実装を作成します。

### 1.モデル定義XMLの作成

exampleプロジェクトには、src/main/resources以下に、カスタムオブジェクトの定義として 30000.xml が入っています。
このサンプルには基本的な定義が入っていますので、このファイルと下記URLの定義を、カスタムオブジェクト定義を作成します。

参考URL:
LwM2Mオブジェクト作成ガイドライン：http://www.openmobilealliance.org/documents/whitepapers/OMA-ORG-Guidelines_Creation_Registration_LwM2M_Objects_Resources-V1_0-20180209-A.pdf
XMLスキーマ : http://www.openmobilealliance.org/tech/profiles/LWM2M.xsd


### 2.実装クラスの作成

sooracom-inventory-for-java-coreのio.soracom.inventory.agent.core.lwm2m.typed_objectパッケージ内のクラス、もしくはexampleプロジェクトのio.soracom.inventory.agent.example.object.CustomModelObject を参考に、実装クラスを作成します。

モデル定義XMLと対になるクラスは、AnnotatedLwM2mInstanceEnablerクラスを継承したクラスとして定義し、クラスには@LWM2MObject アノテーションを付与します。objectId属性は、モデル定義XMLのObjectIDと合わせます。

````
//クラス定義
@LWM2MObject(objectId = 30000, name = "Custom Model")
public class CustomModelObject extends AnnotatedLwM2mInstanceEnabler {

````

モデル定義XMLのResourcesに対応する部分は、メソッドとして定義を行います。
````
//メソッド定義例
@Resource(resourceId = 0, operation = Operation.Read)
public Float readCurrentX()	{
	return 0;
}

@Resource(resourceId = 1, operation = Operation.Write)
public void writeTargetX(Float writeValue)	{
	this.value = writeValue;
}

@Resource(resourceId = 2, operation = Operation.Execute)
public void executeExecuteCommand(String executeParameter)	{
	//execute shell or batch 
}
````
リソースを示すメソッドには、@Resourceアノテーションを付与します。resourceIdはモデル定義xml内のresourceIdに該当する値を、operationにはRead/Write/Executeの各操作を定義します。
Readオペレーションの場合は、メソッドの戻り値としてモデル定義xmlに定義されたデータ型を宣言します。同様にWriteオペレーションの場合は、引数でデータ型を宣言します。
Executeオペレーションの場合は、実行パラメータを受け取る場合はStringで変数を宣言します。
なお、デバイスが対応していないリソースの場合は、LwM2mInstanceResponseException.notFound() で生成される例外をスローして下さい。

なお、このモデル定義と対になるクラスは、その雛形をio.soracom.inventory.agent.core.util.TypedAnnotatedObjectTemplateClassGenerator を使用して、モデル定義XMLから
Javaソースを生成できます。以下が生成サンプルです。

````
//雛形のソースコード生成のサンプル
String javaPackage = "my.models"; //生成するJavaソースのパッケージ名
File sourceOutputDir = new File("source"); //ソース出力するルートディレクトリ 
TypedAnnotatedObjectTemplateClassGenerator generator 
   = new TypedAnnotatedObjectTemplateClassGenerator(javaPackage,outputDir);
File modelFile = new File("src/main/resources/30000.xml"); //モデル定義
generator.generateTemplateClassFromObjectModel(modelFile); //ソース生成
````

Javaソースを生成後、適宜修正を加えることで効率よく実装を行ええます。

### 3.Main関数でモデル定義XMLと実装クラスの読み込み

実行時にカスタムモデルを利用する場合は、まずモデル定義XMLを、InventoryAgentInitializer#setLwM2mModel()で登録します。
以下が読み込みの例となります。

````
InventoryAgentInitializer initializer = new InventoryAgentInitializer();
initializer.setLwM2mModel(
	new LwM2mModelBuilder().addPresetObjectModels()
	.addObjectModelFromClassPath("/30000.xml") //モデル定義xmlファイルの指定
	.build());
````

また、実装したJavaクラスは、同じくInventoryAgentInitializer#addInstancesForObject()で設定します。

````
//CustomModelObjectは、モデル定義XMLを元に実装したJavaソース
initializer.addInstancesForObject(new CustomModelObject());
````
