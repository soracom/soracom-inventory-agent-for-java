package io.soracom.inventory.agent.core.lwm2m.typed_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import java.util.Date;
import org.eclipse.leshan.core.node.ObjectLink;

/**
 * This object specifies resources to enable a device to connect to an APN.
 **/
@LWM2MObject(objectId = 11, name = "APN connection profile", multiple = true)
public abstract class APNConnectionProfileObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * Human-readable identifier. Multiple connection profiles can share the same APN value but e.g. have different credentials.
	 **/
	@Resource(resourceId = 0, operation = Operation.Read)
	public abstract String readProfileName()	;
	@Resource(resourceId = 0, operation = Operation.Write)
	public abstract void writeProfileName(String writeValue)	;

	/**
	 * Presented to network during connection to PDN e.g. ‘internet.15.234’.
	 * This resource is not included in case ‘Auto select APN by device’ resource has the value TRUE.
	 * If the APN resource is present but contains an empty string, then the device shall not provide an APN in the connection request (invoking default APN procedures in the network).
	 **/
	@Resource(resourceId = 1, operation = Operation.Read)
	public String readAPN()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 1, operation = Operation.Write)
	public void writeAPN(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * If this resource is present for a connection profile, it enables the device to choose an APN according to a device specific algorithm. It provides a fall-back mechanism e.g. for some MVNO SIMs the configured APN may not work.  Resource not included in case the ‘APN’ resource is specified.
	 **/
	@Resource(resourceId = 2, operation = Operation.Read)
	public Boolean readAutoSelectAPNByDevice()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 2, operation = Operation.Write)
	public void writeAutoSelectAPNByDevice(Boolean writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * True: connection is activated
	 * False: connection is de-activated.
	 * Allows the profile to be remotely activated or deactivated.
	 **/
	@Resource(resourceId = 3, operation = Operation.Read)
	public Boolean readEnableStatus()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 3, operation = Operation.Write)
	public void writeEnableStatus(Boolean writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Enumerated type:
	 * 0: PAP
	 * 1: CHAP
	 * 2: PAP or CHAP
	 * 3: None
	 **/
	@Resource(resourceId = 4, operation = Operation.Read)
	public abstract Integer readAuthenticationType()	;
	@Resource(resourceId = 4, operation = Operation.Write)
	public abstract void writeAuthenticationType(Integer writeValue)	;

	/**
	 * Used with e.g. PAP.
	 **/
	@Resource(resourceId = 5, operation = Operation.Read)
	public String readUserName()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 5, operation = Operation.Write)
	public void writeUserName(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Used with e.g. PAP.
	 **/
	@Resource(resourceId = 6, operation = Operation.Read)
	public String readSecret()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 6, operation = Operation.Write)
	public void writeSecret(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Comma separated list of retry delay values in seconds to be used in case of unsuccessful connection establishment attempts. e.g. “10,60,600,3600,86400”
	 **/
	@Resource(resourceId = 7, operation = Operation.Read)
	public String readReconnectSchedule()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 7, operation = Operation.Write)
	public void writeReconnectSchedule(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Coma separated mobile country code, then mobile network code – for which this APN is valid.
	 **/
	@Resource(resourceId = 8, operation = Operation.Read, multiple = true)
	public String readValidity_MCC_MNC()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 8, operation = Operation.Write, multiple = true)
	public void writeValidity_MCC_MNC(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * UTC time of connection request. See note (1)
	 **/
	@Resource(resourceId = 9, operation = Operation.Read, multiple = true)
	public java.util.Date readConnectionEstablishmentTime()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * 0 = accepted
	 * 1 = rejected
	 **/
	@Resource(resourceId = 10, operation = Operation.Read, multiple = true)
	public Integer readConnectionEstablishmentResult()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Reject cause, see [3GPP-TS_24.008, 3GPP-TS_24.301]
	 **/
	@Resource(resourceId = 11, operation = Operation.Read, multiple = true)
	public Integer readConnectionEstablishmentRejectCause()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * UTC time of connection end.
	 **/
	@Resource(resourceId = 12, operation = Operation.Read, multiple = true)
	public java.util.Date readConnectionEndTime()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Rolling counter for total number of bytes sent via this interface since last device reset.
	 **/
	@Resource(resourceId = 13, operation = Operation.Read)
	public Integer readTotalBytesSent()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Rolling counter for total number of bytes received via this interface since last device reset.
	 **/
	@Resource(resourceId = 14, operation = Operation.Read)
	public Integer readTotalBytesReceived()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * May be IPv4 or IPv6 address.
	 **/
	@Resource(resourceId = 15, operation = Operation.Read, multiple = true)
	public String readIPAddress()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 15, operation = Operation.Write, multiple = true)
	public void writeIPAddress(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Associated with IPv6 address.
	 **/
	@Resource(resourceId = 16, operation = Operation.Read, multiple = true)
	public String readPrefixLength()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 16, operation = Operation.Write, multiple = true)
	public void writePrefixLength(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Subnet mask.
	 **/
	@Resource(resourceId = 17, operation = Operation.Read, multiple = true)
	public String readSubnetMask()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 17, operation = Operation.Write, multiple = true)
	public void writeSubnetMask(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Gateway.
	 **/
	@Resource(resourceId = 18, operation = Operation.Read, multiple = true)
	public String readGateway()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 18, operation = Operation.Write, multiple = true)
	public void writeGateway(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Primary DNS address.
	 **/
	@Resource(resourceId = 19, operation = Operation.Read, multiple = true)
	public String readPrimaryDNSAddress()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 19, operation = Operation.Write, multiple = true)
	public void writePrimaryDNSAddress(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Secondary DNS address.
	 **/
	@Resource(resourceId = 20, operation = Operation.Read, multiple = true)
	public String readSecondaryDNSAddress()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 20, operation = Operation.Write, multiple = true)
	public void writeSecondaryDNSAddress(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * For LTE and NB-IoT only
	 * QCI=Quality of service Class Identifier
	 * This resource enables the LWM2M server to signal the LWM2M client which QCI it shall request from the network.
	 * See [3GPP-TS_23.203, and 3GPP-TS_24.301] for a description of QCI values. See note (3).
	 **/
	@Resource(resourceId = 21, operation = Operation.Read)
	public Integer readQCI()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Links to a vendor specific object.
	 **/
	@Resource(resourceId = 22, operation = Operation.Read)
	public ObjectLink readVendorSpecificExtensions()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Rolling counter for total number of packets sent via this interface since last device reset.
	 **/
	@Resource(resourceId = 23, operation = Operation.Read)
	public Integer readTotalPacketsSent()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * 0=Non-IP
	 * 1=IPv4
	 * 2=IPv6
	 * 3=IPv4v6
	 **/
	@Resource(resourceId = 24, operation = Operation.Read)
	public Integer readPDNType()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 24, operation = Operation.Write)
	public void writePDNType(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Determines the number of allowed uplink PDU transmissions per time interval per APN.
	 * Rate Control information is part of the Protocol Configuration Options (PCO) according to [3GPP-TS_24.008 and 3GPP-TS_27.060]
	 **/
	@Resource(resourceId = 25, operation = Operation.Read)
	public Integer readAPNRateControl()	{
		throw LwM2mInstanceResponseException.notFound();
	}
}
