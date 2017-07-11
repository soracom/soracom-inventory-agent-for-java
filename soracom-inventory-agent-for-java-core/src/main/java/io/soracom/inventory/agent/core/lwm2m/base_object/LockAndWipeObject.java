package io.soracom.inventory.agent.core.lwm2m.base_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import org.eclipse.leshan.core.response.*;

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
	@Resource(resourceId = 0, operation = Operation.Read, type = "INTEGER")
	public abstract ReadResponse readState(ResourceContext resourceContext)	;
	@Resource(resourceId = 0, operation = Operation.Write)
	public abstract WriteResponse writeState(ResourceContext resourceContext)	;

	/**
	 * To specify one or several targets for the lock operation. This allows partially locking the device by selecting specific components or interfaces to be locked.
	 **/
	@Resource(resourceId = 1, operation = Operation.Write, multiple = true)
	public abstract WriteResponse writeLockTarget(ResourceContext resourceContext)	;

	/**
	 * Indicates which data can be wiped from the device. This resource could be e.g. representing a directory.
	 **/
	@Resource(resourceId = 2, operation = Operation.Read, multiple = true, type = "STRING")
	public ReadResponse readWipeItem(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * To permanently erase data from the device.
	 **/
	@Resource(resourceId = 3, operation = Operation.Execute)
	public abstract ExecuteResponse executeWipe(ResourceContext resourceContext)	;

	/**
	 * To specify one or several targets for the wipe operation. This allows selecting specific data, or, memory areas for the wipe operation.
	 **/
	@Resource(resourceId = 4, operation = Operation.Write, multiple = true)
	public abstract WriteResponse writeWipeTarget(ResourceContext resourceContext)	;

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
	@Resource(resourceId = 5, operation = Operation.Read, type = "INTEGER")
	public abstract ReadResponse readLockOrWipeOperationResult(ResourceContext resourceContext)	;
}