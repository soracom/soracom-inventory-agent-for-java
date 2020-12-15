package io.soracom.inventory.agent.example;

import org.eclipse.leshan.client.californium.LeshanClient;

import io.soracom.inventory.agent.core.bootstrap.krypton.KryptonClientConfigForInventory;
import io.soracom.inventory.agent.core.initialize.InventoryAgentInitializer;
import io.soracom.inventory.agent.core.initialize.LwM2mModelBuilder;
import io.soracom.inventory.agent.example.object.CustomModelObject;
import io.soracom.inventory.agent.example.object.ExampleDeviceObject;
import io.soracom.inventory.agent.example.object.ExampleLocationObject;
import io.soracom.inventory.agent.example.object.ExampleSoftwareComponentObject;

/**
 * Example Agent implementation for SORACOM Inventory
 * 
 * @author c9katayama
 *
 */
public class SORACOMInventoryAgentExample {

	public static void main(String[] args) {
		final CommandLineParser parser = new CommandLineParser();
		parser.parseArguments(args);

		// initialize inventory agent
		final InventoryAgentInitializer initializer = new InventoryAgentInitializer();
		initializer.setEndpoint(parser.endpoint);
		initializer.setServerUri(parser.serverURI);
		initializer.setForceBootstrap(parser.forceBootstrap);
		initializer.setPreSharedKey(parser.psk);
		initializer.setObservationTimerTaskIntervalSeconds(parser.observationTimerTaskIntervalSeconds);

		// enable bootstrap by SORACOM Krypton
		if (parser.enableKryptonBootstrap) {
			KryptonClientConfigForInventory kryptonClientConfig = new KryptonClientConfigForInventory();
			initializer.enableKryptonBootstrap(kryptonClientConfig);
		}

		final LwM2mModelBuilder lwM2mModelBuilder = new LwM2mModelBuilder();
		lwM2mModelBuilder.addPresetObjectModels();// set default lwm2m object models
		lwM2mModelBuilder.addObjectModelFromClassPath("/30000.xml");// add custom model
		initializer.setLwM2mModel(lwM2mModelBuilder.build());

		// add object instances here
		initializer.addInstancesForObject(new ExampleDeviceObject());
		initializer.addInstancesForObject(new ExampleLocationObject());
		initializer.addInstancesForObject(new ExampleSoftwareComponentObject());
		initializer.addInstancesForObject(new CustomModelObject());

		final LeshanClient client = initializer.buildClient();
		client.start();
	}
}
