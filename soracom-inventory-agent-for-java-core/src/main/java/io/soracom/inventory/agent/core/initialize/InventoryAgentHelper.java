package io.soracom.inventory.agent.core.initialize;

import static org.eclipse.leshan.client.object.Security.noSec;
import static org.eclipse.leshan.client.object.Security.noSecBootstap;
import static org.eclipse.leshan.client.object.Security.psk;
import static org.eclipse.leshan.client.object.Security.pskBootstrap;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.object.Security;
import org.eclipse.leshan.client.object.Server;
import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.soracom.inventory.agent.core.credential.Credentials;

public class InventoryAgentHelper {

	private static final Logger log = LoggerFactory.getLogger(InventoryAgentHelper.class);

	public static String[] DEFAULT_LWM2M_MODELS = new String[] { "LWM2M_Access_Control-v1_0.xml",
			"LWM2M_Connectivity_Monitoring-v1_0.xml", "LWM2M_Connectivity_Statistics-v1_0.xml", "LWM2M_Device-v1_0.xml",
			"LWM2M_Firmware_Update-v1_0.xml", "LWM2M_Location-v1_0.xml", "LWM2M_APN_connection_profile-v1_0.xml",
			"LWM2M_Bearer_selection-v1_0.xml", "LWM2M_Cellular_connectivity-v1_0.xml", "LWM2M_DevCapMgmt-v1_0.xml",
			"LWM2M_Lock_and_Wipe-V1_0.xml", "LWM2M_Portfolio-v1_0.xml", "LWM2M_Software_Component-v1_0.xml",
			"LWM2M_Software_Management-v1_0.xml", "LWM2M_WLAN_connectivity4-v1_0.xml" };

	public static Security getSecurityInfo(Credentials c) {
		if (c.getPskId() != null && c.getPskKey() != null) {
			return psk(c.getServerURI(), c.getShortServerId(), c.getPskId(), c.getPskKey());
		} else {
			return noSec(c.getServerURI(), c.getShortServerId());
		}
	}

	public static Security getSecurityInfoForBootstrap(String bootstrapServerURI, byte[] bootstrapPskId,
			byte[] bootstrapPskKey) {

		if (bootstrapPskId != null && bootstrapPskKey != null) {
			return pskBootstrap(bootstrapServerURI, bootstrapPskId, bootstrapPskKey);
		} else {
			return noSecBootstap(bootstrapServerURI);
		}
	}

	public static Server getServerInfo(Credentials c) {
		if (c == null) {
			return null;
		} else {
			return new Server(c.getShortServerId(), c.getLifetime(), c.getBindingMode(), c.isNotifyWhenDisable());
		}
	}

	public static String generateEndpoint() {
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			String hardwareAddress = detectHardwareAddress();
			if (hardwareAddress == null) {
				hardwareAddress = "";
			}
			return localHost.getHostName() + "-" + hardwareAddress;
		} catch (Exception e) {
			return "SORACOMInventoryJavaAgent-" + UUID.randomUUID().toString();
		}
	}

	public static LwM2mModel createDefaultLwM2mModel() {
		List<ObjectModel> objectModelList = ObjectLoader.loadDefault();
		objectModelList.addAll(ObjectLoader.loadDdfResources("/models/", DEFAULT_LWM2M_MODELS));
		LwM2mModel model = new LwM2mModel(objectModelList);
		return model;
	}

	private static String detectHardwareAddress() {
		try {
			Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces();
			while (nics.hasMoreElements()) {
				NetworkInterface nic = nics.nextElement();
				String s = "";
				byte[] hardwareAddress = nic.getHardwareAddress();
				if (hardwareAddress != null) {
					for (byte b : hardwareAddress) {
						s += String.format("%02X", b);
					}
				}
				if (s.length() > 0) {
					log.info("detect hardware address:" + s + " from " + nic.getName());
					return s;
				}
			}
		} catch (Exception e) {
			// ignore
		}
		return null;
	}
	
	public static void addShutdownHoot(final LeshanClient client) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				client.destroy(true);
			}
		});
	}
}
