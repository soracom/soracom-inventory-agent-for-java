package io.soracom.inventory.agent.core.lwm2m.typed_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import java.util.Date;
import org.eclipse.leshan.core.node.ObjectLink;

/**
 * This LwM2M Object enables monitoring of parameters related to network connectivity.
 * In this general connectivity Object, the Resources are limited to the most general cases common to most network bearers. It is recommended to read the description, which refers to relevant standard development organizations (e.g. 3GPP, IEEE).
 * The goal of the Connectivity Monitoring Object is to carry information reflecting the more up to date values of the current connection for monitoring purposes. Resources such as Link Quality, Radio Signal Strength, Cell ID are retrieved during connected mode at least for cellular networks.
 **/
@LWM2MObject(objectId = 4, name = "Connectivity Monitoring")
public abstract class ConnectivityMonitoringObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * Indicates the network bearer used for the current LwM2M communication session from the network bearer list below. 
	 * The number range is split into three categories:
	 * 0 - 20 are Cellular Bearers 
	 * 21 - 40 are Wireless Bearers 
	 * 41 - 50 are Wireline Bearers
	 * 
	 * More specifically: 
	 * 0: GSM cellular network
	 * 1: TD-SCDMA cellular network
	 * 2: WCDMA cellular network
	 * 3: CDMA2000 cellular network
	 * 4: WiMAX cellular network
	 * 5: LTE-TDD cellular network
	 * 6: LTE-FDD cellular network
	 * 7: NB-IoT
	 * 8 - 20: Reserved for other types of cellular network
	 * 21: WLAN network
	 * 22: Bluetooth network
	 * 23: IEEE 802.15.4 network
	 * 24 - 40: Reserved for other types of local wireless network
	 * 41: Ethernet
	 * 42: DSL
	 * 43: PLC
	 * 44 - 50: reserved for other types of wireline networks.
	 **/
	@Resource(resourceId = 0, operation = Operation.Read)
	public abstract Integer readNetworkBearer();

	/**
	 * Indicates a list of current available network bearer. Each Resource Instance has a value from the network bearer list.
	 **/
	@Resource(resourceId = 1, operation = Operation.Read, multiple = true)
	public abstract Integer readAvailableNetworkBearer();

	/**
	 * Indicates the average value of the received signal strength indication used in the
	 * current network bearer (as indicated by Resource 0 of this Object). For the following network bearers the signal strength parameters indicated below are represented by this resource:
	 * GSM:    RSSI
	 * UMTS:   RSCP
	 * LTE:    RSRP
	 * NB-IoT: NRSRP
	 * 
	 * For more details on Network Measurement Report, refer to the appropriate Cellular or Wireless Network standards, (e.g. for LTE Cellular Network
	 * refer to 3GPP TS 36.133 specification).
	 **/
	@Resource(resourceId = 2, operation = Operation.Read)
	public abstract Integer readRadioSignalStrength();

	/**
	 * This contains received link quality  e.g. LQI for IEEE 802.15.4 (range 0...255), RxQual Downlink for GSM (range 0...7, refer to [3GPP 44.018] for more details on Network Measurement Report encoding), RSRQ for LTE, (refer to [3GPP 36.214]), NRSRQ for NB-IoT (refer to [3GPP 36.214]).
	 **/
	@Resource(resourceId = 3, operation = Operation.Read)
	public Integer readLinkQuality()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * The IP addresses assigned to the connectivity interface. (e.g. IPv4, IPv6, etc.)
	 **/
	@Resource(resourceId = 4, operation = Operation.Read, multiple = true)
	public abstract String readIPAddresses();

	/**
	 * The IP address of the next-hop IP router, on each of the interfaces specified in resource 4 (IP Addresses).
	 * Note: This IP Address doesn’t indicate the Server IP address.
	 **/
	@Resource(resourceId = 5, operation = Operation.Read, multiple = true)
	public String readRouterIPAddresses()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * The percentage indicating the average utilization of the link to the next-hop IP router.
	 **/
	@Resource(resourceId = 6, operation = Operation.Read)
	public Integer readLinkUtilization()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Access Point Name in case Network Bearer Resource is a Cellular Network.
	 **/
	@Resource(resourceId = 7, operation = Operation.Read, multiple = true)
	public String readAPN()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Serving Cell ID in case Network Bearer Resource is a Cellular Network.
	 * As specified in TS [3GPP 23.003] and in [3GPP. 24.008]. Range (0...65535) in GSM/EDGE
	 * UTRAN Cell ID has a length of 28 bits.
	 * Cell Identity in WCDMA/TD-SCDMA. Range: (0...268435455).
	 * LTE Cell ID has a length of 28 bits.
	 * Parameter definitions in [3GPP 25.331].
	 **/
	@Resource(resourceId = 8, operation = Operation.Read)
	public Integer readCellID()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Serving Mobile Network Code. This is applicable when the Network Bearer Resource value is referring to a cellular network.
	 * As specified in TS [3GPP 23.003].
	 **/
	@Resource(resourceId = 9, operation = Operation.Read)
	public Integer readSMNC()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Serving Mobile Country Code. This is applicable when the Network Bearer Resource value is referring to a cellular network.
	 * As specified in TS [3GPP 23.003].
	 **/
	@Resource(resourceId = 10, operation = Operation.Read)
	public Integer readSMCC()	{
		throw LwM2mInstanceResponseException.notFound();
	}
}
