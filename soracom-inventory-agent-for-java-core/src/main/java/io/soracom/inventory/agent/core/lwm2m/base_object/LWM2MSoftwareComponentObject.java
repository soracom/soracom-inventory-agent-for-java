package io.soracom.inventory.agent.core.lwm2m.base_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import org.eclipse.leshan.core.response.*;

/**
 * If some Objects are not supported after software update, the LwM2M Client MUST delete all the Object Instances of the Objects that are not supported.
 **/
@LWM2MObject(objectId = 14, name = "LWM2M Software Component", multiple = true)
public abstract class LWM2MSoftwareComponentObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * Name or identifier of the software component
	 **/
	@Resource(resourceId = 0, operation = Operation.Read, type = "STRING")
	public ReadResponse readComponentIdentity(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Software components is stored in this resource
	 **/
	@Resource(resourceId = 1, operation = Operation.Read, type = "OPAQUE")
	public ReadResponse readComponentPack(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Version of the software component.
	 **/
	@Resource(resourceId = 2, operation = Operation.Read, type = "STRING")
	public ReadResponse readComponentVersion(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * This action activates the software previously successfully installed (the SW Update Package Installation State Machine is currently in the INSTALLED state).
	 **/
	@Resource(resourceId = 3, operation = Operation.Execute)
	public ExecuteResponse executeActivate(ResourceContext resourceContext)	{
		return super.execute(resourceContext);
	}

	/**
	 * This action deactivates software if the SW Update Package Installation State Machine is currently in the INSTALLED state.
	 **/
	@Resource(resourceId = 4, operation = Operation.Execute)
	public ExecuteResponse executeDeactivate(ResourceContext resourceContext)	{
		return super.execute(resourceContext);
	}

	/**
	 * Indicates the current activation state of this software component:
	 * 0: DISABLED
	 * Activation State is DISABLED if the Software Component Activation State Machine is in the INACTIVE state or not alive.
	 * 1: ENABLED
	 * Activation State is ENABLED only if the Software Component Activation State Machine is in the ACTIVE state.
	 **/
	@Resource(resourceId = 5, operation = Operation.Read, type = "BOOLEAN")
	public ReadResponse readActivationState(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
}
