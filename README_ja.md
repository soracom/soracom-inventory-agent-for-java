## SORACOM Inventory agent for Java
SORACOM Inventory agent for Java は、SORACOM Inventory用のエージェント実装を提供します。
このクライアントには、SIM経由のブートストラップ、取得したキーの保存、基本的なLWM2Mのオブジェクト定義が入っています

##セットアップ
Eclipseの場合、以下のコマンドでEclipse用のプロジェクトファイルを生成できます。
```
./gradrew eclipse
```
実行後、Eclipseにプロジェクトをインポートします。
IntelliJの場合は、"./gradrew idea"で同様にファイルを生成できます。

##ビルド
ビルドは以下のコマンドで実行できます。 
```
./gradrew build
```

##配布
アプリケーション配布用のZipファイルは、以下のコマンドで生成できます。
生成されたアーカイブは、build/distributionsに出力されます。
```
./gradrew distZip
```

##動作
初回通信時は、SIM経由のブートストラップを実施します。一度bootstrapに成功すると、.soracom-inventory-credentials.datというファイルが作成されます。
次回以降はこのファイルを使ってbootstrapなしでSORACOM Inventoryと通信できるため、wifiなどSIM以外経由でも通信を行うことが出来ます。
再度ブートストラップしたい場合は、ファイルを消して再度実行するか、実行時に -b オプションをつけてください。

##実装
SORACOMInventoryAgentExampleが、mainクラスとなります。このクラスの中で、Inventoryとやりとりをする
オブジェクト(io.soracom.inventory.client.lwm2m_objectパッケージ以下のオブジェクト）を
登録しています。
任意のオブジェクト実装を使いたい場合は、上記パッケージを参考にして、import org.eclipse.leshan.client.resource.BaseInstanceEnabler
インターフェースを実装したクラスをつくり、mainクラスに登録します。

また外部シェルを実行する場合のサンプル実装が、SoftwareComponentObjectとSoftwareManagementObjectのActivate/Deactivateに
入ってます。これらのExecuteをサーバ側から呼ぶと、scriptフォルダ内のシェルが実行されます。
各Objectクラスとシェルスクリプトのひも付けは、object-config.propertiesに記載しています。
実行するシェルスクリプトを変えたり、種類を増やす場合はこれらを参考にしてください。