package io.soracom.inventory.agent.core.lwm2m.base_object;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;

import io.soracom.inventory.agent.core.lwm2m.AnnotatedLwM2mInstanceEnabler;
import io.soracom.inventory.agent.core.lwm2m.LWM2MObject;
import io.soracom.inventory.agent.core.lwm2m.Operation;
import io.soracom.inventory.agent.core.lwm2m.Resource;
import io.soracom.inventory.agent.core.lwm2m.ResourceContext;

/**
 * This LwM2M Object enables monitoring of parameters related to network connectivity.
 * In this general connectivity Object, the Resources are limited to the most general cases common to most network bearers. It is recommended to read the description, which refers to relevant standard development organizations (e.g., 3GPP, IEEE).
 * The goal of the Connectivity Monitoring Object is to carry information reflecting the more up to date values of the current connection for monitoring purposes. Resources such as Link Quality, Radio Signal Strength, Cell ID are retrieved during connected mode at least for cellular networks.
 **/
@LWM2MObject(objectId = 4, name = "Connectivity Monitoring")
public abstract class ConnectivityMonitoringObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * Indicates the network bearer used for the current LwM2M communication session from the below network bearer list.
	 * 0~20 are Cellular Bearers
	 * 0: GSM cellular network
	 * 1: TD-SCDMA cellular network
	 * 2: WCDMA cellular network
	 * 3: CDMA2000 cellular network
	 * 4: WiMAX cellular network
	 * 5: LTE-TDD cellular network
	 * 6: LTE-FDD cellular network
	 * 7~20: Reserved for other type cellular network
	 * 21~40 are Wireless Bearers
	 * 21: WLAN network
	 * 22: Bluetooth network
	 * 23: IEEE 802.15.4 network
	 * 24~40: Reserved for other type local wireless network
	 * 41~50 are Wireline Bearers
	 * 41: Ethernet
	 * 42: DSL
	 * 43: PLC
	 * 44~50: reserved for others type wireline networks.
	 **/
	@Resource(resourceId = 0, operation = Operation.Read)
	public ReadResponse readNetworkBearer(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 0, operation = Operation.Execute)
	public ExecuteResponse executeNetworkBearer(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Indicates list of current available network bearer. Each Resource Instance has a value from the network bearer list.
	 **/
	@Resource(resourceId = 1, operation = Operation.Read, multiple = true)
	public ReadResponse readAvailableNetworkBearer(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 1, operation = Operation.Execute, multiple = true)
	public ExecuteResponse executeAvailableNetworkBearer(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * This node contains the average value of the
	 * received signal strength indication used in the
	 * current network bearer (as indicated by
	 * Resource 0 of this Object). For more details
	 * on Network Measurement Report, refer to the
	 * appropriate Cellular or Wireless Network
	 * standards. (e.g., for LTE Cellular Network
	 * refer to ETSI TS 36.133 specification).
	 **/
	@Resource(resourceId = 2, operation = Operation.Read)
	public ReadResponse readRadioSignalStrength(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 2, operation = Operation.Execute)
	public ExecuteResponse executeRadioSignalStrength(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * This contains received link quality  e.g., LQI for IEEE 802.15.4, (Range (0..255)), RxQual Downlink (for GSM range is 0…7).
	 * Refer to [3GPP 44.018] for more details on Network Measurement Report encoding.
	 **/
	@Resource(resourceId = 3, operation = Operation.Read)
	public ReadResponse readLinkQuality(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 3, operation = Operation.Execute)
	public ExecuteResponse executeLinkQuality(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * The IP addresses assigned to the connectivity interface. (e.g., IPv4, IPv6, etc.)
	 **/
	@Resource(resourceId = 4, operation = Operation.Read, multiple = true)
	public ReadResponse readIPAddresses(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 4, operation = Operation.Execute, multiple = true)
	public ExecuteResponse executeIPAddresses(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * The IP address of the next-hop IP router, on each of the interfaces specified in resource 4 (IP Addresses).
	 * Note: This IP Address doesn’t indicate the Server IP address.
	 **/
	@Resource(resourceId = 5, operation = Operation.Read, multiple = true)
	public ReadResponse readRouterIPAddresses(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 5, operation = Operation.Execute, multiple = true)
	public ExecuteResponse executeRouterIPAddresses(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * The average utilization of the link to the next-hop IP router in %.
	 **/
	@Resource(resourceId = 6, operation = Operation.Read)
	public ReadResponse readLinkUtilization(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 6, operation = Operation.Execute)
	public ExecuteResponse executeLinkUtilization(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Access Point Name in case Network Bearer Resource is a Cellular Network.
	 **/
	@Resource(resourceId = 7, operation = Operation.Read, multiple = true)
	public ReadResponse readAPN(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 7, operation = Operation.Execute, multiple = true)
	public ExecuteResponse executeAPN(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Serving Cell ID in case Network Bearer Resource is a Cellular Network.
	 * As specified in TS [3GPP 23.003] and in [3GPP. 24.008]. Range (0…65535) in GSM/EDGE
	 * UTRAN Cell ID has a length of 28 bits.
	 * Cell Identity in WCDMA/TD-SCDMA. Range: (0..268435455).
	 * LTE Cell ID has a length of 28 bits.
	 * Parameter definitions in [3GPP 25.331].
	 **/
	@Resource(resourceId = 8, operation = Operation.Read)
	public ReadResponse readCellID(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 8, operation = Operation.Execute)
	public ExecuteResponse executeCellID(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Serving Mobile Network Code. In case Network Bearer Resource has 0(cellular network). Range (0…999).
	 * As specified in TS [3GPP 23.003].
	 **/
	@Resource(resourceId = 9, operation = Operation.Read)
	public ReadResponse readSMNC(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 9, operation = Operation.Execute)
	public ExecuteResponse executeSMNC(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Serving Mobile Country Code. In case Network Bearer Resource has 0 (cellular network). Range (0…999).
	 * As specified in TS [3GPP 23.003].
	 **/
	@Resource(resourceId = 10, operation = Operation.Read)
	public ReadResponse readSMCC(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 10, operation = Operation.Execute)
	public ExecuteResponse executeSMCC(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}
}
