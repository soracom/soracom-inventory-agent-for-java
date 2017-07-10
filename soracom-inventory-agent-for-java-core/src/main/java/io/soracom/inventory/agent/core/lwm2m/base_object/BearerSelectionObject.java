package io.soracom.inventory.agent.core.lwm2m.base_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import org.eclipse.leshan.core.response.*;

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
	@Resource(resourceId = 0, operation = Operation.Read, multiple = true, type = "INTEGER")
	public ReadResponse readPreferredCommunicationsBearer(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 0, operation = Operation.Write, multiple = true)
	public WriteResponse writePreferredCommunicationsBearer(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 0, operation = Operation.Execute, multiple = true)
	public ExecuteResponse executePreferredCommunicationsBearer(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Provides guide to the application when performing manual network selection.
	 **/
	@Resource(resourceId = 1, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readAcceptableRSSI_GSM(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 1, operation = Operation.Write)
	public WriteResponse writeAcceptableRSSI_GSM(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 1, operation = Operation.Execute)
	public ExecuteResponse executeAcceptableRSSI_GSM(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Provides guide to the application when performing manual network selection.
	 **/
	@Resource(resourceId = 2, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readAcceptableRSCP_UMTS(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 2, operation = Operation.Write)
	public WriteResponse writeAcceptableRSCP_UMTS(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 2, operation = Operation.Execute)
	public ExecuteResponse executeAcceptableRSCP_UMTS(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Provides guide to the application when performing manual network selection.
	 **/
	@Resource(resourceId = 3, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readAcceptableRSRP_LTE(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 3, operation = Operation.Write)
	public WriteResponse writeAcceptableRSRP_LTE(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 3, operation = Operation.Execute)
	public ExecuteResponse executeAcceptableRSRP_LTE(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Provides guide to the application when performing manual network selection.
	 **/
	@Resource(resourceId = 4, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readAcceptableRSSI_1xEV_DO(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 4, operation = Operation.Write)
	public WriteResponse writeAcceptableRSSI_1xEV_DO(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 4, operation = Operation.Execute)
	public ExecuteResponse executeAcceptableRSSI_1xEV_DO(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Comma separated list of allowed Global Cell Identities.
	 **/
	@Resource(resourceId = 5, operation = Operation.Read, type = "STRING")
	public ReadResponse readCellLockList(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 5, operation = Operation.Write)
	public WriteResponse writeCellLockList(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 5, operation = Operation.Execute)
	public ExecuteResponse executeCellLockList(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Comma separated list of MCC+MNC of operators, in priority order.
	 * Resource “operator list mode” indicates how to process this list.
	 **/
	@Resource(resourceId = 6, operation = Operation.Read, type = "STRING")
	public ReadResponse readOperatorList(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 6, operation = Operation.Write)
	public WriteResponse writeOperatorList(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 6, operation = Operation.Execute)
	public ExecuteResponse executeOperatorList(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Indicates whether resource “operator list” represents the allowed operator list (white list), or, the preferred operator list.
	 * 0=preferred
	 * 1=allowed
	 **/
	@Resource(resourceId = 7, operation = Operation.Read, type = "BOOLEAN")
	public ReadResponse readOperatorListMode(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 7, operation = Operation.Write)
	public WriteResponse writeOperatorListMode(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 7, operation = Operation.Execute)
	public ExecuteResponse executeOperatorListMode(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Allows server to see results of network scan (e.g. result of AT+COPS=?)
	 **/
	@Resource(resourceId = 8, operation = Operation.Read, type = "STRING")
	public ReadResponse readListOfAvailablePLMNs(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 8, operation = Operation.Execute)
	public ExecuteResponse executeListOfAvailablePLMNs(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Links to a vendor specific object.
	 **/
	@Resource(resourceId = 9, operation = Operation.Read, type = "OBJLNK")
	public ReadResponse readVendorSpecificExtensions(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 9, operation = Operation.Execute)
	public ExecuteResponse executeVendorSpecificExtensions(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Provides guide to the application when performing manual network selection.
	 **/
	@Resource(resourceId = 10, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readAcceptableRSRP_NB_IoT(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 10, operation = Operation.Write)
	public WriteResponse writeAcceptableRSRP_NB_IoT(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 10, operation = Operation.Execute)
	public ExecuteResponse executeAcceptableRSRP_NB_IoT(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Interval between periodic searches for higher priority PLMNs of the same country when camped on a visited PLMN, i.e. roaming scenario; based on SIM configuration, EFHPPLMN [3GPP-TS_31.102, section 4.2.6]
	 **/
	@Resource(resourceId = 11, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readHigherPriorityPLMNSearchTimer(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 11, operation = Operation.Write)
	public WriteResponse writeHigherPriorityPLMNSearchTimer(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 11, operation = Operation.Execute)
	public ExecuteResponse executeHigherPriorityPLMNSearchTimer(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * 0=attach with PDN connection
	 * 1=attach without PDN connection
	 **/
	@Resource(resourceId = 12, operation = Operation.Read, type = "BOOLEAN")
	public ReadResponse readAttachWithoutPDNConnection(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 12, operation = Operation.Write)
	public WriteResponse writeAttachWithoutPDNConnection(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 12, operation = Operation.Execute)
	public ExecuteResponse executeAttachWithoutPDNConnection(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}
}
