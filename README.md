[Japanese README is here](README_ja.md)

This library contains bootstrap via SIM, store credential, basic LWM2M object definition. SORACOM Inventory user can implements  an Inventory agent to match own device.

## Repository structure
This repository includes 2 projects.
- soracom-inventory-agent-core -- core library to implement Inventory agent
- soracom-inventory-agent-example -- sample implementation of Inventory agent using core library

## How to build SORACOM Inventory agent for Java
User can use Gradle to build the project. If you want to build the project from source code, execute following command after checkout.
 
```sh
./gradlew build
```

You can generate configuration file for IDEs with following command.

```sh
// for Eclipse
./gradlew eclipse

// for IntelliJ
./gradrew idea
```

Also you can generate application zip archive with following command.

```sh
./gradlew distZip
```

### How to implement Inventory agent with core library
#### Environment setup
Following is the setup procedure to create java project with jar file of soracom-inventory-agent-core. 

1. Initializing new project
Install Gradle, then create new directory. Execute following command in the directory.

```sh
gradle init --type java-application
```

2. Edit build.gradle
Replace build.gradle that is generated after initialize with following contents.

For INVENTORY_AGENT_VERSION part, please specify the version of Inventory core library to be used.

```java
apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'idea'
apply plugin: 'application'

repositories {
  jcenter()
  maven { url 'https://soracom.github.io/maven-repository/' }
}

def INVENTORY_AGENT_VERSION="0.1.1"

dependencies {
    compile "io.soracom:soracom-inventory-agent-for-java-core:$INVENTORY_AGENT_VERSION"
    testCompile 'junit:junit:4.12'
}

mainClassName = 'App'
```

3. Generate configuration files for IDE
if you want to use IDE, generate configuration files for IIDE with following command, then import to IDE.

```sh
//for Eclipse
./gradlew eclipse

//for IntelliJ
./gradrew idea
```

#### How to implement Inventory agent
Create a class with a main method that is an entry point when executing the Inventory agent. When building the environment in the above procedure, a java class named App with the main method is generated.

The simplest implementation is as follows.

```java
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

It is an agent implementation that just bootstrap (new device creation) using SIM and send date / memory amount of the device.

#### How to run Inventory agent
You need to prepare SORACOM SIM and communication devices such as USB dongle, Wifi route to run Inventory agent.

Turn on the use of Inventory in the group setting from SORACOM user console. Then run the Inventory agent through a communication device with SORACOM SIM.

If the bootstrap works fine, the following log will be output.

```
[RegistrationEngine#0] INFO io.soracom.inventory.agent.core.bootstrap.BootstrapObserver - Bootstrap success: deviceId:d-xxxxxxxxxxxxxxxxxxx
```

Also you can see a registered device with devicdId on user console.

#### Extending the Inventory agent
io.soracom.inventory.agent.core.lwm2m.typed_object package contains template implementation of the LWM2M models defined by OMA.

MockDeviceObjectImpl class used in the above agent implementation is also as extension from this template implementation class.

Inherit the template class for the resource you want to handle, and implement methods. You can extend the resources handled by the Inventory agent by adding that class's instance with InventoryAgentInitializer#addInstancesForObject().


#For committer
## How to publish new artifact to Maven repository
To publish a new artifact to SORACOM Maven repository, please follow the steps below.

1. Set your Github token to environment variable

```sh
export GRGIT_USER=${YOUR_GITHUB_API_TOKEN}
```

2. Run Gradle command 

```sh
 ./gradlew gitPublishPush
 ```

Note: Current tool chain does not support PGP code signing for git commit, so please disable it if you are using it. 

## How to release new version of the library
To releaser the library with a new version. you can use the tool "Gren".

1 Setup Gren by npm

```sh
npm install github-release-notes -g
```

2 Set GitHub credential to your environment variable

```sh
export GREN_GITHUB_TOKEN=xxxxxx
```

3 Create new release with new version of tag(e.g 0.0.9 ) on GitHub
 You can create the release with blank title and details.
 
4 run Gren release --override" command.


```sh
gren release --override
```






