# SORACOM Inventory agent for Java
SORACOM Inventory agent for Java は、SORACOM Inventory用のエージェント実装を提供します。
このエージェントには、SIM経由のブートストラップ、取得したキーの保存、基本的なLWM2Mのオブジェクト定義が入っています

## リポジトリの構成
このエージェントには、2つのプロジェクトがあります
- soracom-inventory-agent-core -- エージェントを実装するためのコアライブラリ
- soracom-inventory-agent-example -- コアライブラリを使用したエージェントの実装サンプル

soracom-inventory-agent-coreを利用して、自分の目的にあったInventoryエージェントを構築します。

エージェント実装の際には、soracom-inventory-agent-exampleが実装サンプルとして利用できます。

この実装サンプルには、コマンドラインからの起動、LWM2Mモデルのハンドリングおよびカスタムモデルの構築についての実装サンプルが入っています。

## プロジェクトのソースからのBuild
プロジェクトのビルドには、Gradleを使用します。プロジェクトをソースからビルドしたい場合は、チェックアウト後、コマンドを実行します。
 
```
./gradlew build
```

IDE用の設定ファイルは、以下のコマンドで生成できます。

```
//Eclipseの場合
./gradlew eclipse

//IntelliJの場合
./gradrew idea
```

また実装サンプルをアプリケーション配布するためのZipファイルは、以下のコマンドで生成できます。

```
./gradlew distZip
```

生成されたアーカイブは、build/distributionsに出力されます。

生成されたzip/tarを解凍したあとにできるbinディレクトリに、実行用のシェルが同梱されています。
実装サンプルをデバイス上で動作させる場合は、このシェルを利用してください。

### Inventoryエージェントの基本的な動作と設定
Inventoryエージェントは、初回通信時にSIM経由のブートストラップを実施します。一度bootstrapに成功すると、.soracom-inventory-credentials.datというファイルを生成し、キーをデバイス上に保管します。ブートストラップ成功後は、このファイルを使うことで、bootstrapなしでSORACOM Inventoryと通信できるため、wifiなどSIM以外経由でも通信を行うことが出来ます。

この保存先については、デフォルトではFileCredentialStoreが利用されますが、任意の方法で保存したい場合は、CredentialStoreインターフェースを実装して下さい。また再度ブートストラップしたい場合は、ファイルを消して再度実行するか、forceBootstrapオプションを有効にしてください。

Observeされたリソースについては、デフォルトで60秒に一度、対象リソースのreadメソッドの呼び出しが行われるようになっています。呼び出し間隔を変更したい場合は、InventoryAgentInitializer#setObservationTimerTaskIntervalSeconds で秒数を指定して下さい。

### ライブラリを使用したエージェントの実装方法
#### 環境構築
soracom-inventory-agent-coreエージェントをクラスパスに通した形で、javaのプロジェクトを作成します。以下Gradleがインストールされている前提のセットアップ手順となります。

1. プロジェクトの初期化
適当なフォルダを作成し、フォルダ内で以下のコマンドを実行します

```
gradle init --type java-application
```

2. build.gradleファイルの編集
初期化後に生成される build.gradleファイルを、以下の内容に置き換えます。

INVENTORY_AGENT_VERSIONの部分は、利用するInventoryエージェントのバージョンを指定して下さい。

```
apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'idea'
apply plugin: 'application'

repositories {
  jcenter()
  maven { url 'https://soracom.github.io/maven-repository/' }
}

def INVENTORY_AGENT_VERSION="0.0.5"

dependencies {
    compile "io.soracom:soracom-inventory-agent-for-java-core:$INVENTORY_AGENT_VERSION"
    testCompile 'junit:junit:4.12'
}

mainClassName = 'App'
```

3. IDE用ファイルの生成
IDEを使用する場合は、下記コマンドでIDE用ファイルを作成し、各IDEにインポートして下さい。

```
//Eclipseの場合
./gradlew eclipse

//IntelliJの場合
./gradrew idea
```

#### Inventoryエージェントの実装
Inventoryエージェント実行時のエントリポイントとなるmainメソッドを持ったクラスを作成します。上記手順で環境構築を行った場合、mainメソッドを持ったAppというjavaクラスが生成されています。もしなければ、適切な名前をつけたクラスを作成して下さい。

最もシンプルな実装は、以下のような形になります。

```
import org.eclipse.leshan.client.californium.LeshanClient;

import io.soracom.inventory.agent.core.initialize.InventoryAgentInitializer;
import io.soracom.inventory.agent.core.lwm2m.typed_object.impl.MockDeviceObjectImpl;

public class App {

	public static void main(String[] args) {
		InventoryAgentInitializer initializer = new InventoryAgentInitializer();
		initializer.addInstancesForObject(new MockDeviceObjectImpl());
		LeshanClient client = initializer.buildClient();
		client.start();
	}
}
```

この実装では、SIMを使ったブートストラップ（デバイスの新規作成）と、デバイスの日付/メモリ量を通知するだけのエージェント実装になります。

#### Inventoryエージェントの動作確認
Inventoryエージェントの動作確認には、SORACOMのSIMと通信機器(USBドングル/Wifiルーターなど)が必要になります。

SORACOMのユーザーコンソールから、SIMのグループ設定でInventoryの利用をonにした後、SORACOM SIMで通信できるように設定した後、Inventoryエージェントを実行します。
正常にブートストラップが動作すると、下記のようなログが出力されます

```
[RegistrationEngine#0] INFO io.soracom.inventory.agent.core.bootstrap.BootstrapObserver - Bootstrap success: deviceId:d-xxxxxxxxxxxxxxxxxxx
```

またユーザーコンソール上に、ログ出力されたdeviceIdでデバイスが登録されます。

#### Inventoryエージェントの拡張(基本的なモデル定義）
基本的なInventoryエージェントの拡張は、次のように行います。

io.soracom.inventory.agent.core.lwm2m.typed_object パッケージには、このパッケージには、デフォルトで定義されたLWM2Mのモデルのテンプレート実装が入っています。
上記のエージェント実装で利用した MockDeviceObjectImpl クラスも、このテンプレート実装からの拡張となります。

Inventroyエージェントが扱いたいリソースに対応したクラス/メソッドをオーバーライドし、その実装クラスをInventoryAgentInitializer#addInstancesForObjectで追加することで、Inventoryエージェントが扱うリソースを拡張することが出来ます。

実装サンプルとしては、 soracom-inventory-agent-exampleのプロジェクトでは、"DeviceObject","LocationObject", "LWM2MSoftwareComponent"を実装したサンプルが入っていますのでこれも参考にして下さい。

#### Inventoryエージェントの拡張(カスタムモデル定義）
規定のモデル定義ではなく、カスタムモデルを使用したい場合は、次の手順で実装を作成します。

#### 1.モデル定義XMLの作成

exampleプロジェクトには、src/main/resources以下に、カスタムオブジェクトの定義として 30000.xml が入っています。
このサンプルには基本的な定義が入っていますので、このファイルと下記URLの定義を、カスタムオブジェクト定義を作成します。

参考URL:
LwM2Mオブジェクト作成ガイドライン：http://www.openmobilealliance.org/documents/whitepapers/OMA-ORG-Guidelines_Creation_Registration_LwM2M_Objects_Resources-V1_0-20180209-A.pdf
XMLスキーマ : http://www.openmobilealliance.org/tech/profiles/LWM2M.xsd


#### 2.実装クラスの作成

sooracom-inventory-for-java-coreのio.soracom.inventory.agent.core.lwm2m.typed_objectパッケージ内のクラス、もしくはexampleプロジェクトのio.soracom.inventory.agent.example.object.CustomModelObject を参考に、実装クラスを作成します。

モデル定義XMLと対になるクラスは、AnnotatedLwM2mInstanceEnablerクラスを継承したクラスとして定義し、クラスには@LWM2MObject アノテーションを付与します。objectId属性は、モデル定義XMLのObjectIDと合わせます。

```
//クラス定義
@LWM2MObject(objectId = 30000, name = "Custom Model")
public class CustomModelObject extends AnnotatedLwM2mInstanceEnabler {

```

モデル定義XMLのResourcesに対応する部分は、メソッドとして定義を行います。

```
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
```

リソースを示すメソッドには、@Resourceアノテーションを付与します。resourceIdはモデル定義xml内のresourceIdに該当する値を、operationにはRead/Write/Executeの各操作を定義します。
Readオペレーションの場合は、メソッドの戻り値としてモデル定義xmlに定義されたデータ型を宣言します。同様にWriteオペレーションの場合は、引数でデータ型を宣言します。
Executeオペレーションの場合は、実行パラメータを受け取る場合はStringで変数を宣言します。
なお、デバイスが対応していないリソースの場合は、LwM2mInstanceResponseException.notFound() で生成される例外をスローして下さい。

なお、このモデル定義と対になるクラスは、その雛形をio.soracom.inventory.agent.core.util.TypedAnnotatedObjectTemplateClassGenerator を使用して、モデル定義XMLから
Javaソースを生成できます。以下が生成サンプルです。

```
import java.io.File;
import java.io.IOException;

import io.soracom.inventory.agent.core.util.TypedAnnotatedObjectTemplateClassGenerator;

public class GenerateCustomModelJavaSource {

	public static void main(String[] args) throws IOException {
		String javaPackage = "my.object";//生成するJavaソースのパッケージ名
		File sourceFileDir = new File("src/main/java");//ソース出力するルートディレクトリ 
		TypedAnnotatedObjectTemplateClassGenerator generator = new TypedAnnotatedObjectTemplateClassGenerator(
				javaPackage, sourceFileDir);
		File modelFile = new File("src/main/resources/custom-model.xml");//モデル定義XML
		generator.generateTemplateClassFromObjectModel(modelFile);//ソース生成実行
		System.out.println("generate Java source.");
		System.exit(0);
	}

}
```

Javaソースを生成後、適宜修正を加えることで効率よく実装を行ええます。

#### 3.Inventoryエージェントでの、モデル定義XMLと実装クラスの読み込み

実行時にカスタムモデルを利用する場合は、まずモデル定義XMLを、InventoryAgentInitializer#setLwM2mModel()で登録します。

また、実装したJavaクラスは、同じくInventoryAgentInitializer#addInstancesForObject()で設定します。

カスタムモデル定義およびカスタムモデル実装クラスを読みこんだInventoryエージェントの実装は以下のようになります。

```
import org.eclipse.leshan.client.californium.LeshanClient;

import io.soracom.inventory.agent.core.initialize.InventoryAgentInitializer;
import io.soracom.inventory.agent.core.lwm2m.typed_object.impl.MockDeviceObjectImpl;

public class App {

	public static void main(String[] args) {
		InventoryAgentInitializer initializer = new InventoryAgentInitializer();
		
		LwM2mModelBuilder lwM2mModelBuilder = new LwM2mModelBuilder();
		lwM2mModelBuilder.addPresetObjectModels(); // デフォルトのモデル定義の読み込み
		lwM2mModelBuilder.addObjectModelFromClassPath("/custom-model.xml");// カスタムモデル定義の読み込み
		initializer.setLwM2mModel(lwM2mModelBuilder.build());

		initializer.addInstancesForObject(new MockDeviceObjectImpl());
		initializer.addInstancesForObject(new CustomModelObject());

		final LeshanClient client = initializer.buildClient();
		client.start();
	}
}
```

#### 4.ユーザーコンソールでのモデル定義XMLの登録
上記Inventoryエージェント実行前に、モデル定義XMLをユーザーコンソールから登録して下さい。登録後エージェントを実行すると、カスタムモデル定義部分もユーザーコンソールに反映されます。




