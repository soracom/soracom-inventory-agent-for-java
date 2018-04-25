package io.soracom.inventory.agent.example;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.soracom.inventory.agent.core.bootstrap.BootstrapConstants;
import io.soracom.inventory.agent.core.credential.PreSharedKey;
import io.soracom.inventory.agent.core.initialize.InventoryAgentHelper;

public class CommandLineParser {

	private static final Logger log = LoggerFactory.getLogger(CommandLineParser.class);
	private Options options;

	String endpoint;
	String serverURI;
	PreSharedKey psk;
	boolean forceBootstrap;

	public void parseArguments(String[] args) {

		// command line option
		options = new Options();
		options.addOption("h", "help", false, "Display help information.");
		options.addOption("e", "endpoint", true,
				String.format("Set the endpoint name of the Client.\nDefault: generate from network interface."));
		options.addOption("b", "bootstrap", false, "If this option is enabled, bootstrap is executed even if there is a cedential in local.");
		options.addOption("u", true, "Set the LWM2M or Bootstrap server URL.\nDefault: "
				+ BootstrapConstants.DEFAULT_BOOTSTRAP_SERVER_ADDRESS);
		options.addOption("i", true,
				"Set the PSK identity (deviceId) in ascii.\nBootstrap sequence is not executed if the value is set.");
		options.addOption("p", true,
				"Set the Pre-Shared-Key (secret key) in hexa.\nBootstrap sequence is not executed if the value is set.");

		CommandLine cl = null;
		try {
			cl = new DefaultParser().parse(options, args);
		} catch (ParseException e) {
			log.error("Parsing failed.  Reason: " + e.getMessage());
			printHelp();
			return;
		}

		if (cl.hasOption("help")) {
			printHelp();
			return;
		}

		// Abort if we have not identity and key for psk.
		if ((cl.hasOption("i") && !cl.hasOption("p")) || !cl.hasOption("i") && cl.hasOption("p")) {
			log.error("You should precise identity and Pre-Shared-Key if you want to connect in PSK");
			printHelp();
			return;
		}

		// Get endpoint
		if (cl.hasOption("e")) {
			endpoint = cl.getOptionValue("e");
		} else {
			endpoint = InventoryAgentHelper.generateEndpoint();
		}
		log.info("using endpoint [" + endpoint + "]");

		// Get server URI
		serverURI = cl.hasOption("i") ? "coaps://" : "coap://";
		if (cl.hasOption("u")) {
			serverURI += cl.getOptionValue("u");
		} else {
			if (cl.hasOption("i")) {
				throw new IllegalStateException("Server uri is necessary when using PSK. ["
						+ BootstrapConstants.DEFAULT_JP_DM_SERVER_ADDRESS + "] is for Japan coverage, and ["
						+ BootstrapConstants.DEFAULT_GLOBAL_DM_SERVER_ADDRESS + "] is for Global coverage.");
			} else {
				serverURI += BootstrapConstants.DEFAULT_BOOTSTRAP_SERVER_ADDRESS;
			}
		}

		// get security info
		if (cl.hasOption("i") && cl.hasOption("p")) {
			psk = new PreSharedKey(cl.getOptionValue("i"), cl.getOptionValue("p"));
		}

		forceBootstrap = cl.hasOption("b");
	}

	protected void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.setOptionComparator(null);
		final String USAGE = "java -jar soracom-inventory-agent-example.jar [OPTION]";
		formatter.printHelp(USAGE, options);
		System.exit(0);
	}

}
