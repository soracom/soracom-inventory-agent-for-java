package io.soracom.inventory.agent.core.lwm2m.typed_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import java.util.Date;
import org.eclipse.leshan.core.node.ObjectLink;

/**
 * If some Objects are not supported after software update, the LwM2M Client MUST delete all the Object Instances of the Objects that are not supported.
 **/
@LWM2MObject(objectId = 14, name = "LWM2M Software Component", multiple = true)
public abstract class LWM2MSoftwareComponentObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * Name or identifier of the software component
	 **/
	@Resource(resourceId = 0, operation = Operation.Read)
	public String readComponentIdentity()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Software components is stored in this resource
	 **/
	@Resource(resourceId = 1, operation = Operation.Read)
	public byte[] readComponentPack()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Version of the software component.
	 **/
	@Resource(resourceId = 2, operation = Operation.Read)
	public String readComponentVersion()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * This action activates the software previously successfully installed (the SW Update Package Installation State Machine is currently in the INSTALLED state).
	 **/
	@Resource(resourceId = 3, operation = Operation.Execute)
	public void executeActivate(String executeParameter)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * This action deactivates software if the SW Update Package Installation State Machine is currently in the INSTALLED state.
	 **/
	@Resource(resourceId = 4, operation = Operation.Execute)
	public void executeDeactivate(String executeParameter)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Indicates the current activation state of this software component:
	 * 0: DISABLED
	 * Activation State is DISABLED if the Software Component Activation State Machine is in the INACTIVE state or not alive.
	 * 1: ENABLED
	 * Activation State is ENABLED only if the Software Component Activation State Machine is in the ACTIVE state.
	 **/
	@Resource(resourceId = 5, operation = Operation.Read)
	public Boolean readActivationState()	{
		throw LwM2mInstanceResponseException.notFound();
	}
}
