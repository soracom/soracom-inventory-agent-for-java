package io.soracom.inventory.agent.core.lwm2m.base_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import org.eclipse.leshan.core.response.*;

/**
 * This object specifies resources to enable a device to connect to an APN.
 **/
@LWM2MObject(objectId = 11, name = "APN connection profile", multiple = true)
public abstract class APNConnectionProfileObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * Human-readable identifier. Multiple connection profiles can share the same APN value but e.g. have different credentials.
	 **/
	@Resource(resourceId = 0, operation = Operation.Read, type = "STRING")
	public abstract ReadResponse readProfileName(ResourceContext resourceContext)	;
	@Resource(resourceId = 0, operation = Operation.Write)
	public abstract WriteResponse writeProfileName(ResourceContext resourceContext)	;

	/**
	 * Presented to network during connection to PDN e.g. ‘internet.15.234’.
	 * This resource is not included in case ‘Auto select APN by device’ resource has the value TRUE.
	 * If the APN resource is present but contains an empty string, then the device shall not provide an APN in the connection request (invoking default APN procedures in the network).
	 **/
	@Resource(resourceId = 1, operation = Operation.Read, type = "STRING")
	public ReadResponse readAPN(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 1, operation = Operation.Write)
	public WriteResponse writeAPN(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * If this resource is present for a connection profile, it enables the device to choose an APN according to a device specific algorithm. It provides a fall-back mechanism e.g. for some MVNO SIMs the configured APN may not work.  Resource not included in case the ‘APN’ resource is specified.
	 **/
	@Resource(resourceId = 2, operation = Operation.Read, type = "BOOLEAN")
	public ReadResponse readAutoSelectAPNByDevice(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 2, operation = Operation.Write)
	public WriteResponse writeAutoSelectAPNByDevice(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * True: connection is activated
	 * False: connection is de-activated.
	 * Allows the profile to be remotely activated or deactivated.
	 **/
	@Resource(resourceId = 3, operation = Operation.Read, type = "BOOLEAN")
	public ReadResponse readEnableStatus(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 3, operation = Operation.Write)
	public WriteResponse writeEnableStatus(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Enumerated type:
	 * 0: PAP
	 * 1: CHAP
	 * 2: PAP or CHAP
	 * 3: None
	 **/
	@Resource(resourceId = 4, operation = Operation.Read, type = "INTEGER")
	public abstract ReadResponse readAuthenticationType(ResourceContext resourceContext)	;
	@Resource(resourceId = 4, operation = Operation.Write)
	public abstract WriteResponse writeAuthenticationType(ResourceContext resourceContext)	;

	/**
	 * Used with e.g. PAP.
	 **/
	@Resource(resourceId = 5, operation = Operation.Read, type = "STRING")
	public ReadResponse readUserName(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 5, operation = Operation.Write)
	public WriteResponse writeUserName(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Used with e.g. PAP.
	 **/
	@Resource(resourceId = 6, operation = Operation.Read, type = "STRING")
	public ReadResponse readSecret(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 6, operation = Operation.Write)
	public WriteResponse writeSecret(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Comma separated list of retry delay values in seconds to be used in case of unsuccessful connection establishment attempts. e.g. “10,60,600,3600,86400”
	 **/
	@Resource(resourceId = 7, operation = Operation.Read, type = "STRING")
	public ReadResponse readReconnectSchedule(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 7, operation = Operation.Write)
	public WriteResponse writeReconnectSchedule(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Coma separated mobile country code, then mobile network code – for which this APN is valid.
	 **/
	@Resource(resourceId = 8, operation = Operation.Read, multiple = true, type = "STRING")
	public ReadResponse readValidity_MCC_MNC(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 8, operation = Operation.Write, multiple = true)
	public WriteResponse writeValidity_MCC_MNC(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * UTC time of connection request. See note (1)
	 **/
	@Resource(resourceId = 9, operation = Operation.Read, multiple = true, type = "TIME")
	public ReadResponse readConnectionEstablishmentTime(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * 0 = accepted
	 * 1 = rejected
	 **/
	@Resource(resourceId = 10, operation = Operation.Read, multiple = true, type = "INTEGER")
	public ReadResponse readConnectionEstablishmentResult(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Reject cause, see [3GPP-TS_24.008, 3GPP-TS_24.301]
	 **/
	@Resource(resourceId = 11, operation = Operation.Read, multiple = true, type = "INTEGER")
	public ReadResponse readConnectionEstablishmentRejectCause(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * UTC time of connection end.
	 **/
	@Resource(resourceId = 12, operation = Operation.Read, multiple = true, type = "TIME")
	public ReadResponse readConnectionEndTime(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Rolling counter for total number of bytes sent via this interface since last device reset.
	 **/
	@Resource(resourceId = 13, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readTotalBytesSent(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Rolling counter for total number of bytes received via this interface since last device reset.
	 **/
	@Resource(resourceId = 14, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readTotalBytesReceived(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * May be IPv4 or IPv6 address.
	 **/
	@Resource(resourceId = 15, operation = Operation.Read, multiple = true, type = "STRING")
	public ReadResponse readIPAddress(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 15, operation = Operation.Write, multiple = true)
	public WriteResponse writeIPAddress(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Associated with IPv6 address.
	 **/
	@Resource(resourceId = 16, operation = Operation.Read, multiple = true, type = "STRING")
	public ReadResponse readPrefixLength(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 16, operation = Operation.Write, multiple = true)
	public WriteResponse writePrefixLength(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Subnet mask.
	 **/
	@Resource(resourceId = 17, operation = Operation.Read, multiple = true, type = "STRING")
	public ReadResponse readSubnetMask(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 17, operation = Operation.Write, multiple = true)
	public WriteResponse writeSubnetMask(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Gateway.
	 **/
	@Resource(resourceId = 18, operation = Operation.Read, multiple = true, type = "STRING")
	public ReadResponse readGateway(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 18, operation = Operation.Write, multiple = true)
	public WriteResponse writeGateway(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Primary DNS address.
	 **/
	@Resource(resourceId = 19, operation = Operation.Read, multiple = true, type = "STRING")
	public ReadResponse readPrimaryDNSAddress(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 19, operation = Operation.Write, multiple = true)
	public WriteResponse writePrimaryDNSAddress(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Secondary DNS address.
	 **/
	@Resource(resourceId = 20, operation = Operation.Read, multiple = true, type = "STRING")
	public ReadResponse readSecondaryDNSAddress(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 20, operation = Operation.Write, multiple = true)
	public WriteResponse writeSecondaryDNSAddress(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * For LTE and NB-IoT only
	 * QCI=Quality of service Class Identifier
	 * This resource enables the LWM2M server to signal the LWM2M client which QCI it shall request from the network.
	 * See [3GPP-TS_23.203, and 3GPP-TS_24.301] for a description of QCI values. See note (3).
	 **/
	@Resource(resourceId = 21, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readQCI(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Links to a vendor specific object.
	 **/
	@Resource(resourceId = 22, operation = Operation.Read, type = "OBJLNK")
	public ReadResponse readVendorSpecificExtensions(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Rolling counter for total number of packets sent via this interface since last device reset.
	 **/
	@Resource(resourceId = 23, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readTotalPacketsSent(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * 0=Non-IP
	 * 1=IPv4
	 * 2=IPv6
	 * 3=IPv4v6
	 **/
	@Resource(resourceId = 24, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readPDNType(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 24, operation = Operation.Write)
	public WriteResponse writePDNType(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Determines the number of allowed uplink PDU transmissions per time interval per APN.
	 * Rate Control information is part of the Protocol Configuration Options (PCO) according to [3GPP-TS_24.008 and 3GPP-TS_27.060]
	 **/
	@Resource(resourceId = 25, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readAPNRateControl(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
}
