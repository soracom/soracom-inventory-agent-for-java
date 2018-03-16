package io.soracom.inventory.agent.core.lwm2m.typed_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import java.util.Date;
import org.eclipse.leshan.core.node.ObjectLink;

/**
 * This object specifies resources to enable a device to choose a PLMN/network on which to attach/register and what type of bearer to then connect. 
 * This object allows via remote bearer and network configuration to overwrite automatic network and bearer selection e.g. as supported by the UICC. An equivalent example for overwriting automatic selection is a user doing manual network and bearer selection on a smart phone.
 **/
@LWM2MObject(objectId = 13, name = "Bearer selection")
public abstract class BearerSelectionObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * Used in network selection and, if applicable, in subsequent mobility management procedures:
	 * 0: auto connect
	 * 1: 3GPP PS preferred
	 * 2: 3GPP PS GSM (GPRS) preferred (including EC-GSM-IoT)
	 * 3: 3GPP PS UMTS preferred
	 * 4: 3GPP PS LTE preferred
	 * 5: 1xEV-DO preferred (1)
	 * 6: 3GPP CS preferred (1)
	 * 7: WLAN preferred
	 * 8: Ethernet preferred (1)
	 * 9: DSL preferred (1)
	 * 10: Bluetooth preferred (1)
	 * 11: WIMAX preferred (1)
	 * 12: 3GPP PS LTE with CIoT EPS optimisations, User Plane preferred (2)
	 * 13: 3GPP PS LTE with CIoT EPS optimisations, Control Plane preferred (2)14: 3GPP PS NB-IoT Control Plane optimisations preferred (2)
	 * 15: 3GPP PS NB-IoT User Plane optimisations preferred (2) 
	 * 16-100: Reserved for future use
	 * The Preferred Communications Bearer resource specifies the preferred communications bearer that the LWM2M Client is requested to use for connecting to the LWM2M Server. If multiple preferred communications bearers are specified, the priority order is reflected by the resource instance order. E.g. the bearer which appears first in the list of resource instances is to have higher priority over the rest of available bearers. The LWM2M Client SHOULD use the preferred bearers with higher priority first if they are available. If none of indicated preferred bearers is available, the LWM2M Client SHOULD wait until one of them becomes available. 
	 **/
	@Resource(resourceId = 0, operation = Operation.Read, multiple = true)
	public Integer readPreferredCommunicationsBearer()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 0, operation = Operation.Write, multiple = true)
	public void writePreferredCommunicationsBearer(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Provides guide to the application when performing manual network selection.
	 **/
	@Resource(resourceId = 1, operation = Operation.Read)
	public Integer readAcceptableRSSI_GSM()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 1, operation = Operation.Write)
	public void writeAcceptableRSSI_GSM(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Provides guide to the application when performing manual network selection.
	 **/
	@Resource(resourceId = 2, operation = Operation.Read)
	public Integer readAcceptableRSCP_UMTS()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 2, operation = Operation.Write)
	public void writeAcceptableRSCP_UMTS(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Provides guide to the application when performing manual network selection.
	 **/
	@Resource(resourceId = 3, operation = Operation.Read)
	public Integer readAcceptableRSRP_LTE()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 3, operation = Operation.Write)
	public void writeAcceptableRSRP_LTE(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Provides guide to the application when performing manual network selection.
	 **/
	@Resource(resourceId = 4, operation = Operation.Read)
	public Integer readAcceptableRSSI_1xEV_DO()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 4, operation = Operation.Write)
	public void writeAcceptableRSSI_1xEV_DO(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Comma separated list of allowed Global Cell Identities.
	 **/
	@Resource(resourceId = 5, operation = Operation.Read)
	public String readCellLockList()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 5, operation = Operation.Write)
	public void writeCellLockList(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Comma separated list of MCC+MNC of operators, in priority order.
	 * Resource “operator list mode” indicates how to process this list.
	 **/
	@Resource(resourceId = 6, operation = Operation.Read)
	public String readOperatorList()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 6, operation = Operation.Write)
	public void writeOperatorList(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Indicates whether resource “operator list” represents the allowed operator list (white list), or, the preferred operator list.
	 * 0=preferred
	 * 1=allowed
	 **/
	@Resource(resourceId = 7, operation = Operation.Read)
	public Boolean readOperatorListMode()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 7, operation = Operation.Write)
	public void writeOperatorListMode(Boolean writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Allows server to see results of network scan (e.g. result of AT+COPS=?)
	 **/
	@Resource(resourceId = 8, operation = Operation.Read)
	public String readListOfAvailablePLMNs()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Links to a vendor specific object.
	 **/
	@Resource(resourceId = 9, operation = Operation.Read)
	public ObjectLink readVendorSpecificExtensions()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Provides guide to the application when performing manual network selection.
	 **/
	@Resource(resourceId = 10, operation = Operation.Read)
	public Integer readAcceptableRSRP_NB_IoT()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 10, operation = Operation.Write)
	public void writeAcceptableRSRP_NB_IoT(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Interval between periodic searches for higher priority PLMNs of the same country when camped on a visited PLMN, i.e. roaming scenario; based on SIM configuration, EFHPPLMN [3GPP-TS_31.102, section 4.2.6]
	 **/
	@Resource(resourceId = 11, operation = Operation.Read)
	public Integer readHigherPriorityPLMNSearchTimer()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 11, operation = Operation.Write)
	public void writeHigherPriorityPLMNSearchTimer(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * 0=attach with PDN connection
	 * 1=attach without PDN connection
	 **/
	@Resource(resourceId = 12, operation = Operation.Read)
	public Boolean readAttachWithoutPDNConnection()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 12, operation = Operation.Write)
	public void writeAttachWithoutPDNConnection(Boolean writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}
}
