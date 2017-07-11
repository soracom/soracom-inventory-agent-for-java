package io.soracom.inventory.agent.core.lwm2m.base_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import org.eclipse.leshan.core.response.*;

/**
 * This object specifies resources to enable a device to connect to a 3GPP or 3GPP2 bearer, including GPRS/EDGE, UMTS, LTE, NB-IoT, SMS. For cellular connectivity, this object focuses on Packet Switched (PS) connectivity and doesn’t aim to provide comprehensive Circuit Switched (CS) connectivity management.
 **/
@LWM2MObject(objectId = 10, name = "Cellular connectivity")
public abstract class CellularConnectivityObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * E.164 address of SMSC.   
	 * Applicable for 3GPP2 networks where SMSC is not available from a smart card, or for 3GPP/3GPP2 networks to provide the application with a customer specific SMSC.  The application decides how to use this parameter, e.g. precedence over UICC based SMSC address.
	 **/
	@Resource(resourceId = 0, operation = Operation.Read, type = "STRING")
	public ReadResponse readSMSCAddress(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 0, operation = Operation.Write)
	public WriteResponse writeSMSCAddress(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Time period for which the device shall disconnect from cellular radio (PS detach, CS detach if applicable).
	 * Can be used to handle network overload situations. 
	 * The value is a positive integer (0 to 65535), duration can be from 1 minute to 65535 minutes (approximately 45 days).
	 * As soon the server writes a value which is >0 the device SHALL disconnect. When the period has elapsed the device MAY reconnect.
	 **/
	@Resource(resourceId = 1, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readDisableRadioPeriod(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 1, operation = Operation.Write)
	public WriteResponse writeDisableRadioPeriod(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Configurable in case the application needs to issue a code (e.g. via AT command) to activate the module. e.g. “*98”.
	 **/
	@Resource(resourceId = 2, operation = Operation.Read, type = "STRING")
	public ReadResponse readModuleActivationCode(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 2, operation = Operation.Write)
	public WriteResponse writeModuleActivationCode(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Links to a vendor specific object.
	 **/
	@Resource(resourceId = 3, operation = Operation.Read, type = "OBJLNK")
	public ReadResponse readVendorSpecificExtensions(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Power saving mode (PSM) timer as defined in [3GPP-TS_23.682].
	 * PSM Timer = Extended T3412.
	 * Max interval between periodic TAU if there is no other transmission from the device. During most of this time the device is considered as unreachable and can therefore go into a deep sleep mode while keeping the PDN connection(s) active.
	 **/
	@Resource(resourceId = 4, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readPSMTimer(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 4, operation = Operation.Write)
	public WriteResponse writePSMTimer(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Active timer = T3324 as defined in [3GPP-TS_24.008].
	 * The time the UE has to remain reachable after transitioning to idle state in case there is pending data from the NW to send out. At the end of T3324 UE can go into a deep sleep mode while keeping the PDN connection(s) active.
	 **/
	@Resource(resourceId = 5, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readActiveTimer(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 5, operation = Operation.Write)
	public WriteResponse writeActiveTimer(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Only for when using Signalling Radio Bearers (c.f. Data over NAS), it indicates the maximum the number of allowed uplink PDU transmissions per 6 minute interval aggregated across all PDN connections. See [3GPP-TS_23.401], octet 3 to 4 of the Serving PLMN rate control IE.
	 **/
	@Resource(resourceId = 6, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readServingPLMNRateControl(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Extended DRX parameters (Paging Time Window and eDRX value) for Iu mode which the UE can request from the network. This resource is encoded as octet 3 in [3GPP-TS_24.008, clause 10.5.5.32].
	 **/
	@Resource(resourceId = 7, operation = Operation.Read, type = "OPAQUE")
	public ReadResponse readEDRXParametersForIuMode(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 7, operation = Operation.Write)
	public WriteResponse writeEDRXParametersForIuMode(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Extended DRX parameters (Paging Time Window and eDRX value) for WB-S1 mode which the UE can request from the network. This resource is encoded as octet 3 in [3GPP-TS_24.008, clause 10.5.5.32].
	 **/
	@Resource(resourceId = 8, operation = Operation.Read, type = "OPAQUE")
	public ReadResponse readEDRXParametersForWB_S1Mode(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 8, operation = Operation.Write)
	public WriteResponse writeEDRXParametersForWB_S1Mode(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Extended DRX parameters (Paging Time Window and eDRX value) for NB-S1 mode which the UE can request from the network. This resource is encoded as octet 3 in [3GPP-TS_24.008, clause 10.5.5.32].
	 **/
	@Resource(resourceId = 9, operation = Operation.Read, type = "OPAQUE")
	public ReadResponse readEDRXParametersForNB_S1Mode(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 9, operation = Operation.Write)
	public WriteResponse writeEDRXParametersForNB_S1Mode(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Extended DRX parameters (Paging Time Window and eDRX value) for A/Gb mode which the UE can request from the network. This resource is encoded as octet 3 in [3GPP-TS_24.008, clause 10.5.5.32].
	 **/
	@Resource(resourceId = 10, operation = Operation.Read, type = "OPAQUE")
	public ReadResponse readEDRXParametersForA_GbMode(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 10, operation = Operation.Write)
	public WriteResponse writeEDRXParametersForA_GbMode(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Links to instances of the “APN connection profile” object representing every APN connection profile that has an activated connection to a PDN.
	 **/
	@Resource(resourceId = 11, operation = Operation.Read, multiple = true, type = "STRING")
	public abstract ReadResponse readActivatedProfileNames(ResourceContext resourceContext)	;
}
