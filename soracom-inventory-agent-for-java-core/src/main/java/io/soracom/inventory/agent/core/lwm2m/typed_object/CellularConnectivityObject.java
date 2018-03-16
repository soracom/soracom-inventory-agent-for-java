package io.soracom.inventory.agent.core.lwm2m.typed_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import java.util.Date;
import org.eclipse.leshan.core.node.ObjectLink;

/**
 * This object specifies resources to enable a device to connect to a 3GPP or 3GPP2 bearer, including GPRS/EDGE, UMTS, LTE, NB-IoT, SMS. For cellular connectivity, this object focuses on Packet Switched (PS) connectivity and doesn’t aim to provide comprehensive Circuit Switched (CS) connectivity management.
 **/
@LWM2MObject(objectId = 10, name = "Cellular connectivity")
public abstract class CellularConnectivityObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * E.164 address of SMSC.   
	 * Applicable for 3GPP2 networks where SMSC is not available from a smart card, or for 3GPP/3GPP2 networks to provide the application with a customer specific SMSC.  The application decides how to use this parameter, e.g. precedence over UICC based SMSC address.
	 **/
	@Resource(resourceId = 0, operation = Operation.Read)
	public String readSMSCAddress()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 0, operation = Operation.Write)
	public void writeSMSCAddress(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Time period for which the device shall disconnect from cellular radio (PS detach, CS detach if applicable).
	 * Can be used to handle network overload situations. 
	 * The value is a positive integer (0 to 65535), duration can be from 1 minute to 65535 minutes (approximately 45 days).
	 * As soon the server writes a value which is >0 the device SHALL disconnect. When the period has elapsed the device MAY reconnect.
	 **/
	@Resource(resourceId = 1, operation = Operation.Read)
	public Integer readDisableRadioPeriod()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 1, operation = Operation.Write)
	public void writeDisableRadioPeriod(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Configurable in case the application needs to issue a code (e.g. via AT command) to activate the module. e.g. “*98”.
	 **/
	@Resource(resourceId = 2, operation = Operation.Read)
	public String readModuleActivationCode()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 2, operation = Operation.Write)
	public void writeModuleActivationCode(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Links to a vendor specific object.
	 **/
	@Resource(resourceId = 3, operation = Operation.Read)
	public ObjectLink readVendorSpecificExtensions()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Power saving mode (PSM) timer as defined in [3GPP-TS_23.682].
	 * PSM Timer = Extended T3412.
	 * Max interval between periodic TAU if there is no other transmission from the device. During most of this time the device is considered as unreachable and can therefore go into a deep sleep mode while keeping the PDN connection(s) active.
	 **/
	@Resource(resourceId = 4, operation = Operation.Read)
	public Integer readPSMTimer()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 4, operation = Operation.Write)
	public void writePSMTimer(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Active timer = T3324 as defined in [3GPP-TS_24.008].
	 * The time the UE has to remain reachable after transitioning to idle state in case there is pending data from the NW to send out. At the end of T3324 UE can go into a deep sleep mode while keeping the PDN connection(s) active.
	 **/
	@Resource(resourceId = 5, operation = Operation.Read)
	public Integer readActiveTimer()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 5, operation = Operation.Write)
	public void writeActiveTimer(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Only for when using Signalling Radio Bearers (c.f. Data over NAS), it indicates the maximum the number of allowed uplink PDU transmissions per 6 minute interval aggregated across all PDN connections. See [3GPP-TS_23.401], octet 3 to 4 of the Serving PLMN rate control IE.
	 **/
	@Resource(resourceId = 6, operation = Operation.Read)
	public Integer readServingPLMNRateControl()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Extended DRX parameters (Paging Time Window and eDRX value) for Iu mode which the UE can request from the network. This resource is encoded as octet 3 in [3GPP-TS_24.008, clause 10.5.5.32].
	 **/
	@Resource(resourceId = 7, operation = Operation.Read)
	public byte[] readEDRXParametersForIuMode()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 7, operation = Operation.Write)
	public void writeEDRXParametersForIuMode(byte[] writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Extended DRX parameters (Paging Time Window and eDRX value) for WB-S1 mode which the UE can request from the network. This resource is encoded as octet 3 in [3GPP-TS_24.008, clause 10.5.5.32].
	 **/
	@Resource(resourceId = 8, operation = Operation.Read)
	public byte[] readEDRXParametersForWB_S1Mode()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 8, operation = Operation.Write)
	public void writeEDRXParametersForWB_S1Mode(byte[] writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Extended DRX parameters (Paging Time Window and eDRX value) for NB-S1 mode which the UE can request from the network. This resource is encoded as octet 3 in [3GPP-TS_24.008, clause 10.5.5.32].
	 **/
	@Resource(resourceId = 9, operation = Operation.Read)
	public byte[] readEDRXParametersForNB_S1Mode()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 9, operation = Operation.Write)
	public void writeEDRXParametersForNB_S1Mode(byte[] writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Extended DRX parameters (Paging Time Window and eDRX value) for A/Gb mode which the UE can request from the network. This resource is encoded as octet 3 in [3GPP-TS_24.008, clause 10.5.5.32].
	 **/
	@Resource(resourceId = 10, operation = Operation.Read)
	public byte[] readEDRXParametersForA_GbMode()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 10, operation = Operation.Write)
	public void writeEDRXParametersForA_GbMode(byte[] writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Links to instances of the “APN connection profile” object representing every APN connection profile that has an activated connection to a PDN.
	 **/
	@Resource(resourceId = 11, operation = Operation.Read, multiple = true)
	public abstract String readActivatedProfileNames()	;
}
