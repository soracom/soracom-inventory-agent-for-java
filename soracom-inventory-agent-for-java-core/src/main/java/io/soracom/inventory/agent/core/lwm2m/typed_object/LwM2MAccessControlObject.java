package io.soracom.inventory.agent.core.lwm2m.typed_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import java.util.Date;
import org.eclipse.leshan.core.node.ObjectLink;

/**
 * Access Control Object is used to check whether the LwM2M Server has access right for performing an operation.
 **/
@LWM2MObject(objectId = 2, name = "LwM2M Access Control", multiple = true)
public abstract class LwM2MAccessControlObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * The Object ID and The Object Instance ID are applied for.
	 **/
	@Resource(resourceId = 0, operation = Operation.Read)
	public abstract Integer readObjectID();

	/**
	 * See Table 20: LwM2M Identifiers.
	 **/
	@Resource(resourceId = 1, operation = Operation.Read)
	public abstract Integer readObjectInstanceID();

	/**
	 * The Resource Instance ID MUST be the Short Server ID of a certain LwM2M Server for which associated access rights are contained in the Resource Instance value.
	 * The Resource Instance ID 0 is a specific ID, determining the ACL Instance which contains the default access rights.
	 * Each bit set in the Resource Instance value, grants an access right to the LwM2M Server to the corresponding operation.
	 * The bit order is specified as below.
	 * 1st LSB: R(Read, Observe, Discover, Write-Attributes)
	 * 2nd LSB: W(Write)
	 * 3rd LSB: E(Execute)
	 * 4th LSB: D(Delete)
	 * 5th LSB: C(Create)
	 * Other bits are reserved for future use.
	 **/
	@Resource(resourceId = 2, operation = Operation.Read, multiple = true)
	public Integer readACL()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 2, operation = Operation.Write, multiple = true)
	public void writeACL(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Short Server ID of a certain LwM2M Server; only such an LwM2M Server can manage the Resources of this Object Instance. 
	 * The specific value MAX_ID=65535 means this Access Control Object Instance is created and modified during a Bootstrap phase only.
	 **/
	@Resource(resourceId = 3, operation = Operation.Read)
	public abstract Integer readAccessControlOwner();
	@Resource(resourceId = 3, operation = Operation.Write)
	public abstract void writeAccessControlOwner(Integer writeValue);
}
