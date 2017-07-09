package io.soracom.inventory.agent.core.lwm2m.base_object;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

import io.soracom.inventory.agent.core.lwm2m.AnnotatedLwM2mInstanceEnabler;
import io.soracom.inventory.agent.core.lwm2m.LWM2MObject;
import io.soracom.inventory.agent.core.lwm2m.Operation;
import io.soracom.inventory.agent.core.lwm2m.Resource;
import io.soracom.inventory.agent.core.lwm2m.ResourceContext;

/**
 * This object specifies resources to enable a device to connect to an APN.
 **/
@LWM2MObject(objectId = 11, name = "APN connection profile", multiple = true)
public abstract class APNConnectionProfileObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * Human-readable identifier. Multiple connection profiles can share the same APN value but e.g. have different credentials.
	 **/
	@Resource(resourceId = 0, operation = Operation.Read)
	public ReadResponse readProfileName(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 0, operation = Operation.Write)
	public WriteResponse writeProfileName(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 0, operation = Operation.Execute)
	public ExecuteResponse executeProfileName(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Presented to network during connection to PDN e.g. ‘internet.15.234’.
	 * This resource is not included in case ‘Auto select APN by device’ resource has the value TRUE.
	 * If the APN resource is present but contains an empty string, then the device shall not provide an APN in the connection request (invoking default APN procedures in the network).
	 **/
	@Resource(resourceId = 1, operation = Operation.Read)
	public ReadResponse readAPN(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 1, operation = Operation.Write)
	public WriteResponse writeAPN(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 1, operation = Operation.Execute)
	public ExecuteResponse executeAPN(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * If this resource is present for a connection profile, it enables the device to choose an APN according to a device specific algorithm. It provides a fall-back mechanism e.g. for some MVNO SIMs the configured APN may not work.  Resource not included in case the ‘APN’ resource is specified.
	 **/
	@Resource(resourceId = 2, operation = Operation.Read)
	public ReadResponse readAutoSelectAPNByDevice(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 2, operation = Operation.Write)
	public WriteResponse writeAutoSelectAPNByDevice(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 2, operation = Operation.Execute)
	public ExecuteResponse executeAutoSelectAPNByDevice(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * True: connection is activated
	 * False: connection is de-activated.
	 * Allows the profile to be remotely activated or deactivated.
	 **/
	@Resource(resourceId = 3, operation = Operation.Read)
	public ReadResponse readEnableStatus(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 3, operation = Operation.Write)
	public WriteResponse writeEnableStatus(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 3, operation = Operation.Execute)
	public ExecuteResponse executeEnableStatus(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Enumerated type:
	 * 0: PAP
	 * 1: CHAP
	 * 2: PAP or CHAP
	 * 3: None
	 **/
	@Resource(resourceId = 4, operation = Operation.Read)
	public ReadResponse readAuthenticationType(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 4, operation = Operation.Write)
	public WriteResponse writeAuthenticationType(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 4, operation = Operation.Execute)
	public ExecuteResponse executeAuthenticationType(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Used with e.g. PAP.
	 **/
	@Resource(resourceId = 5, operation = Operation.Read)
	public ReadResponse readUserName(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 5, operation = Operation.Write)
	public WriteResponse writeUserName(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 5, operation = Operation.Execute)
	public ExecuteResponse executeUserName(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Used with e.g. PAP.
	 **/
	@Resource(resourceId = 6, operation = Operation.Read)
	public ReadResponse readSecret(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 6, operation = Operation.Write)
	public WriteResponse writeSecret(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 6, operation = Operation.Execute)
	public ExecuteResponse executeSecret(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Comma separated list of retry delay values in seconds to be used in case of unsuccessful connection establishment attempts. e.g. “10,60,600,3600,86400”
	 **/
	@Resource(resourceId = 7, operation = Operation.Read)
	public ReadResponse readReconnectSchedule(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 7, operation = Operation.Write)
	public WriteResponse writeReconnectSchedule(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 7, operation = Operation.Execute)
	public ExecuteResponse executeReconnectSchedule(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Coma separated mobile country code, then mobile network code – for which this APN is valid.
	 **/
	@Resource(resourceId = 8, operation = Operation.Read, multiple = true)
	public ReadResponse readValidity_MCC_MNC(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 8, operation = Operation.Write, multiple = true)
	public WriteResponse writeValidity_MCC_MNC(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 8, operation = Operation.Execute, multiple = true)
	public ExecuteResponse executeValidity_MCC_MNC(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * UTC time of connection request. See note (1)
	 **/
	@Resource(resourceId = 9, operation = Operation.Read, multiple = true)
	public ReadResponse readConnectionEstablishmentTime(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 9, operation = Operation.Execute, multiple = true)
	public ExecuteResponse executeConnectionEstablishmentTime(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * 0 = accepted
	 * 1 = rejected
	 **/
	@Resource(resourceId = 10, operation = Operation.Read, multiple = true)
	public ReadResponse readConnectionEstablishmentResult(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 10, operation = Operation.Execute, multiple = true)
	public ExecuteResponse executeConnectionEstablishmentResult(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Reject cause, see [3GPP-TS_24.008, 3GPP-TS_24.301]
	 **/
	@Resource(resourceId = 11, operation = Operation.Read, multiple = true)
	public ReadResponse readConnectionEstablishmentRejectCause(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 11, operation = Operation.Execute, multiple = true)
	public ExecuteResponse executeConnectionEstablishmentRejectCause(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * UTC time of connection end.
	 **/
	@Resource(resourceId = 12, operation = Operation.Read, multiple = true)
	public ReadResponse readConnectionEndTime(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 12, operation = Operation.Execute, multiple = true)
	public ExecuteResponse executeConnectionEndTime(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Rolling counter for total number of bytes sent via this interface since last device reset.
	 **/
	@Resource(resourceId = 13, operation = Operation.Read)
	public ReadResponse readTotalBytesSent(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 13, operation = Operation.Execute)
	public ExecuteResponse executeTotalBytesSent(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Rolling counter for total number of bytes received via this interface since last device reset.
	 **/
	@Resource(resourceId = 14, operation = Operation.Read)
	public ReadResponse readTotalBytesReceived(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 14, operation = Operation.Execute)
	public ExecuteResponse executeTotalBytesReceived(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * May be IPv4 or IPv6 address.
	 **/
	@Resource(resourceId = 15, operation = Operation.Read, multiple = true)
	public ReadResponse readIPAddress(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 15, operation = Operation.Write, multiple = true)
	public WriteResponse writeIPAddress(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 15, operation = Operation.Execute, multiple = true)
	public ExecuteResponse executeIPAddress(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Associated with IPv6 address.
	 **/
	@Resource(resourceId = 16, operation = Operation.Read, multiple = true)
	public ReadResponse readPrefixLength(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 16, operation = Operation.Write, multiple = true)
	public WriteResponse writePrefixLength(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 16, operation = Operation.Execute, multiple = true)
	public ExecuteResponse executePrefixLength(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Subnet mask.
	 **/
	@Resource(resourceId = 17, operation = Operation.Read, multiple = true)
	public ReadResponse readSubnetMask(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 17, operation = Operation.Write, multiple = true)
	public WriteResponse writeSubnetMask(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 17, operation = Operation.Execute, multiple = true)
	public ExecuteResponse executeSubnetMask(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Gateway.
	 **/
	@Resource(resourceId = 18, operation = Operation.Read, multiple = true)
	public ReadResponse readGateway(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 18, operation = Operation.Write, multiple = true)
	public WriteResponse writeGateway(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 18, operation = Operation.Execute, multiple = true)
	public ExecuteResponse executeGateway(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Primary DNS address.
	 **/
	@Resource(resourceId = 19, operation = Operation.Read, multiple = true)
	public ReadResponse readPrimaryDNSAddress(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 19, operation = Operation.Write, multiple = true)
	public WriteResponse writePrimaryDNSAddress(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 19, operation = Operation.Execute, multiple = true)
	public ExecuteResponse executePrimaryDNSAddress(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Secondary DNS address.
	 **/
	@Resource(resourceId = 20, operation = Operation.Read, multiple = true)
	public ReadResponse readSecondaryDNSAddress(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 20, operation = Operation.Write, multiple = true)
	public WriteResponse writeSecondaryDNSAddress(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 20, operation = Operation.Execute, multiple = true)
	public ExecuteResponse executeSecondaryDNSAddress(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * For LTE and NB-IoT only
	 * QCI=Quality of service Class Identifier
	 * This resource enables the LWM2M server to signal the LWM2M client which QCI it shall request from the network.
	 * See [3GPP-TS_23.203, and 3GPP-TS_24.301] for a description of QCI values. See note (3).
	 **/
	@Resource(resourceId = 21, operation = Operation.Read)
	public ReadResponse readQCI(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 21, operation = Operation.Execute)
	public ExecuteResponse executeQCI(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Links to a vendor specific object.
	 **/
	@Resource(resourceId = 22, operation = Operation.Read)
	public ReadResponse readVendorSpecificExtensions(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 22, operation = Operation.Execute)
	public ExecuteResponse executeVendorSpecificExtensions(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Rolling counter for total number of packets sent via this interface since last device reset.
	 **/
	@Resource(resourceId = 23, operation = Operation.Read)
	public ReadResponse readTotalPacketsSent(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 23, operation = Operation.Execute)
	public ExecuteResponse executeTotalPacketsSent(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * 0=Non-IP
	 * 1=IPv4
	 * 2=IPv6
	 * 3=IPv4v6
	 **/
	@Resource(resourceId = 24, operation = Operation.Read)
	public ReadResponse readPDNType(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 24, operation = Operation.Write)
	public WriteResponse writePDNType(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 24, operation = Operation.Execute)
	public ExecuteResponse executePDNType(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Determines the number of allowed uplink PDU transmissions per time interval per APN.
	 * Rate Control information is part of the Protocol Configuration Options (PCO) according to [3GPP-TS_24.008 and 3GPP-TS_27.060]
	 **/
	@Resource(resourceId = 25, operation = Operation.Read)
	public ReadResponse readAPNRateControl(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 25, operation = Operation.Execute)
	public ExecuteResponse executeAPNRateControl(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}
}
