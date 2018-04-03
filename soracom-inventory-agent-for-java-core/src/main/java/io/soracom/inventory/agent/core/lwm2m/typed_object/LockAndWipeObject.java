package io.soracom.inventory.agent.core.lwm2m.typed_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import java.util.Date;
import org.eclipse.leshan.core.node.ObjectLink;

/**
 * This LWM2M objects provides the resources needed to perform the lock and wipe operations.
 **/
@LWM2MObject(objectId = 8, name = "Lock and Wipe")
public abstract class LockAndWipeObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * State of the device:
	 * 0: unlocked state Normal operation.
	 * 1: partially locked state
	 * To render the device inoperable the device has been partially locked. The “lock target” resource allows specifying the target(s) for this operation.
	 * 2: fully locked state
	 * To render the device fully inoperable the device has been fully locked.
	 **/
	@Resource(resourceId = 0, operation = Operation.Read)
	public abstract Integer readState();
	@Resource(resourceId = 0, operation = Operation.Write)
	public abstract void writeState(Integer writeValue);

	/**
	 * To specify one or several targets for the lock operation. This allows partially locking the device by selecting specific components or interfaces to be locked.
	 **/
	@Resource(resourceId = 1, operation = Operation.Write, multiple = true)
	public abstract void writeLockTarget(String writeValue);

	/**
	 * Indicates which data can be wiped from the device. This resource could be e.g. representing a directory.
	 **/
	@Resource(resourceId = 2, operation = Operation.Read, multiple = true)
	public String readWipeItem()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * To permanently erase data from the device.
	 **/
	@Resource(resourceId = 3, operation = Operation.Execute)
	public abstract void executeWipe(String executeParameter);

	/**
	 * To specify one or several targets for the wipe operation. This allows selecting specific data, or, memory areas for the wipe operation.
	 **/
	@Resource(resourceId = 4, operation = Operation.Write, multiple = true)
	public abstract void writeWipeTarget(String writeValue);

	/**
	 * Contains the result of a lock and wipe operation
	 * 0: Default
	 * 1: Partially Lock operation successful
	 * 2: Fully Lock operation successful
	 * 3: Unlock operation successful
	 * 4: Wipe operation successful
	 * 5: Partially Lock operation failed
	 * 6: Fully Lock operation failed
	 * 7: Unlock operation failed 8: Wipe operation failed
	 * This Resource MAY be reported by sending Observe operation.
	 **/
	@Resource(resourceId = 5, operation = Operation.Read)
	public abstract Integer readLockOrWipeOperationResult();
}
