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
(注:LWM2MSoftwareComponentを実装した ExampleSoftwareComponentObject は、release-0.0.1で利用していた実装方式で作られています。
release-0.0.2以降は、ExampleDeviceObjectやExampleLocationObjectの実装方法が推奨です）

実装後、InventoryAgentInitializer#addInstancesForObjectメソッドを使用して、登録を行います。

また外部シェルを実行する場合のサンプル実装が、DeviceObjectのRebootとSoftwareComponentObjectのActivate/Deactivateに
実装されています。
これらのExecuteをサーバ側から呼ぶと、scriptフォルダ内のシェルが実行されます。

実行するシェルスクリプトを変えたり、種類を増やす場合はこれらを参考にしてください。

##カスタムオブジェクトの利用
規定のオブジェクト以外は、次の手順で実装を作成します。

1.モデル定義XMLの作成

exampleプロジェクトには、src/main/resources以下に、カスタムオブジェクトの定義として 30000.xml が入っています。
これを参考に、カスタムオブジェクト定義を作成します。

2.実装クラスの作成

io.soracom.inventory.agent.example.object.CustomModelObject を参考に、実装クラスを作成します。
なお、io.soracom.inventory.agent.core.util.TypedAnnotatedObjectTemplateClassGenerator を使用すると、モデル定義XMLから
雛形となるJavaソースを生成できます。

````
//雛形のソースコード生成のサンプル
String javaPackage = "my.models"; //生成するJavaソースのパッケージ
File sourceOutputDir = new File("source"); //ソース出力するルートディレクトリ 
TypedAnnotatedObjectTemplateClassGenerator generator 
   = new TypedAnnotatedObjectTemplateClassGenerator(javaPackage,outputDir);
File modelFile = new File("src/main/resources/30000.xml"); //モデル定義
generator.generateTemplateClassFromObjectModel(modelFile); //ソース生成
````

3.Main関数でモデル定義XMLと実装クラスの読み込み

モデル定義XMLを、InventoryAgentInitializer#setLwM2mModel()で登録します。
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
