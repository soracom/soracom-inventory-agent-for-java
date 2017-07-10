package io.soracom.inventory.agent.example;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.leshan.client.californium.LeshanClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.soracom.inventory.agent.core.bootstrap.BootstrapConstants;
import io.soracom.inventory.agent.core.credential.FileCredentialStore;
import io.soracom.inventory.agent.core.credential.PreSharedKey;
import io.soracom.inventory.agent.core.initialize.InventoryAgentHelper;
import io.soracom.inventory.agent.core.initialize.InventoryAgentInitializer;
import io.soracom.inventory.agent.example.object.ExampleDeviceObject;

public class SORACOMInventoryAgentExample {

	private static final Logger log = LoggerFactory.getLogger(SORACOMInventoryAgentExample.class);

	public static void main(String[] args) {

		Options options = new Options();
		options.addOption("h", "help", false, "Display help information.");
		options.addOption("e", "endpoint", true,
				String.format("Set the endpoint name of the Client.\nDefault: generate from network interface."));
		options.addOption("b", "bootstrap", false, "Use bootstrap even credential exists if present this option.");
		options.addOption("u", true, "Set the LWM2M or Bootstrap server URL.\nDefault: "
				+ BootstrapConstants.DEFAULT_BOOTSTRAP_SERVER_ADDRESS);
		options.addOption("i", true,
				"Set the LWM2M or Bootstrap server PSK identity in ascii.\nSIM bootstrap is executed if this value is not set.");
		options.addOption("p", true,
				"Set the LWM2M or Bootstrap server Pre-Shared-Key in hexa.\nSIM bootstrap is executed if this value is not set.");

		HelpFormatter formatter = new HelpFormatter();
		formatter.setOptionComparator(null);
		final String USAGE = "java -jar soracom-inventory-agent-example.jar [OPTION]";

		CommandLine cl = null;
		try {
			cl = new DefaultParser().parse(options, args);
		} catch (ParseException e) {
			log.error("Parsing failed.  Reason: " + e.getMessage());
			formatter.printHelp(USAGE, options);
			return;
		}

		if (cl.hasOption("help")) {
			formatter.printHelp(USAGE, options);
			return;
		}

		// Abort if we have not identity and key for psk.
		if ((cl.hasOption("i") && !cl.hasOption("p")) || !cl.hasOption("i") && cl.hasOption("p")) {
			log.error("You should precise identity and Pre-Shared-Key if you want to connect in PSK");
			formatter.printHelp(USAGE, options);
			return;
		}

		// Get endpoint
		String endpoint;
		if (cl.hasOption("e")) {
			endpoint = cl.getOptionValue("e");
		} else {
			endpoint = InventoryAgentHelper.generateEndpoint();
		}
		log.info("using endpoint [" + endpoint + "]");

		// Get server URI
		String serverURI = cl.hasOption("i") ? "coaps://" : "coap://";
		if (cl.hasOption("u")) {
			serverURI += cl.getOptionValue("u");
		} else {
			if (cl.hasOption("i")) {
				serverURI += BootstrapConstants.DEFAULT_BOOTSTRAP_COAPS_SERVER_ADDRESS;
			} else {
				serverURI += BootstrapConstants.DEFAULT_BOOTSTRAP_SERVER_ADDRESS;
			}
		}

		// get security info
		PreSharedKey psk = null;
		if (cl.hasOption("i") && cl.hasOption("p")) {
			psk = new PreSharedKey(cl.getOptionValue("i"), cl.getOptionValue("p"));
		}

		final boolean forceBootstrap = cl.hasOption("b");

		InventoryAgentInitializer initializer = new InventoryAgentInitializer();
		initializer.setEndpoint(endpoint);
		initializer.setServerUri(serverURI);
		initializer.setForceBootstrap(forceBootstrap);
		initializer.setPreSharedKey(psk);
		initializer.setCredentialStore(new FileCredentialStore());

		initializer.addInstancesForObject(new ExampleDeviceObject());

		final LeshanClient client = initializer.buildClient();
		client.start();
	}
}
