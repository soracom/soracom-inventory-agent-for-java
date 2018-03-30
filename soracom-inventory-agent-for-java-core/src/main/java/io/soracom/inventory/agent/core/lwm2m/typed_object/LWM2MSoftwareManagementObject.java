package io.soracom.inventory.agent.core.lwm2m.typed_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import java.util.Date;
import org.eclipse.leshan.core.node.ObjectLink;

/**
 * This LwM2M objects provides the resources needed to perform software management on the device. Each software component is managed via a dedicated Software Management Object instance.
 **/
@LWM2MObject(objectId = 9, name = "LWM2M Software Management", multiple = true)
public abstract class LWM2MSoftwareManagementObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * Name of the software package
	 **/
	@Resource(resourceId = 0, operation = Operation.Read)
	public abstract String readPkgName();

	/**
	 * Version of the software package
	 **/
	@Resource(resourceId = 1, operation = Operation.Read)
	public abstract String readPkgVersion();

	/**
	 * Software package
	 * The package can be in one single software component, or any delivery material used by the Device to determine the software component(s) and proceed to their installation.
	 * Can be archive file, executable, manifest. This resource to be used when it is single block of delivery.
	 **/
	@Resource(resourceId = 2, operation = Operation.Write)
	public void writePackage(byte[] writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * URI from where the device can download the software package by an alternative mechanism. As soon the device has received the Package URI it performs the download at the next practical opportunity.
	 * Can be direct link to a single software component or link to archive file, executable, or manifest, used by the Device to determine, then access to the software component(s). This resource to be used when it is single block of delivery.
	 **/
	@Resource(resourceId = 3, operation = Operation.Write)
	public void writePackageURI(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Installs software from the package either stored in Package resource, or, downloaded from the Package URI. This Resource is only executable when the value of the State Resource is DELIVERED.
	 **/
	@Resource(resourceId = 4, operation = Operation.Execute)
	public abstract void executeInstall(String executeParameter);

	/**
	 * Link to a Checkpoint“ object which allows to specify conditions/dependencies for a software update. E.g. power connected, sufficient memory, target system.
	 **/
	@Resource(resourceId = 5, operation = Operation.Read)
	public ObjectLink readCheckpoint()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Uninstalls the software package.
	 * This executable resource may have one argument.
	 * If used with no argument or argument is 0, the Package is removed i from the Device. If the argument is 1 (“ForUpdate”), the Client MUST prepare itself for receiving a Package used to upgrade the Software already in place. Update State is set back to INITIAL state.
	 **/
	@Resource(resourceId = 6, operation = Operation.Execute)
	public abstract void executeUninstall(String executeParameter);

	/**
	 * Indicates current state with respect to this software update. This value is set by the LwM2M Client.
	 * 0: INITIAL
	 * Before downloading.
	 * (see 5.1.2.1)
	 * 1: DOWNLOAD STARTED
	 * The downloading process has started and is on-going.
	 * (see 5.1.2.2)
	 * 2: DOWNLOADED
	 * The package has been completely downloaded 
	 * (see 5.1.2.3)
	 * 3: DELIVERED
	 * In that state, the package has been correctly downloaded and is ready to be installed. 
	 * (see 5.1.2.4)
	 * If executing the Install Resource failed, the state remains at DELIVERED.
	 * If executing the Install Resource was successful, the state changes from DELIVERED to INSTALLED.
	 * After executing the UnInstall Resource, the state changes to INITIAL.
	 * 4: INSTALLED
	 * In that state the software is correctly installed and can be activated or deactivated according to the Activation State Machine.
	 * (see 5.1.2.5)
	 **/
	@Resource(resourceId = 7, operation = Operation.Read)
	public abstract Integer readUpdateState();

	/**
	 * If this value is true, the LwM2M Client MUST inform the registered LwM2M Servers of Objects and Object Instances parameter by sending an Update or Registration message after the software update operation at the next practical opportunity if supported Objects in the LwM2M Client have changed, in order for the LwM2M Servers to promptly manage newly installed Objects. 
	 * If false, Objects and Object Instances parameter MUST be reported at the next periodic Update message.
	 * The default value is false.
	 **/
	@Resource(resourceId = 8, operation = Operation.Read)
	public Boolean readUpdateSupportedObjects()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 8, operation = Operation.Write)
	public void writeUpdateSupportedObjects(Boolean writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Contains the result of downloading or installing/uninstalling the software
	 * 0: Initial value. Prior to download any new package in the Device, Update Result MUST be reset to this initial value. One side effect of executing the Uninstall resource is to reset Update Result to this initial value “0”.
	 * 1: Downloading. The package downloading process is on-going.
	 * 2: Software successfully installed.
	 * 3: Successfully Downloaded and package integrity verified
	 * (( 4-49, for expansion, of other scenarios))
	 * 50: Not enough storage for the new software package.
	 * 51: Out of memory during downloading process.
	 * 52: Connection lost during downloading process.
	 * 53: Package integrity check failure.
	 * 54: Unsupported package type.
	 * 56: Invalid URI
	 * 57: Device defined update error
	 * 58: Software installation failure
	 * 59: Uninstallation Failure during forUpdate(arg=0)
	 * 60-200 : (for expansion, selection to be in blocks depending on new introduction of features)
	 * This Resource MAY be reported by sending Observe operation.
	 **/
	@Resource(resourceId = 9, operation = Operation.Read)
	public abstract Integer readUpdateResult();

	/**
	 * This action activates the software previously successfully installed (the Package Installation State Machine is currently in the INSTALLED state)
	 **/
	@Resource(resourceId = 10, operation = Operation.Execute)
	public abstract void executeActivate(String executeParameter);

	/**
	 * This action deactivates softwareif the Package Installation State Machine is currently in the INSTALLED state.
	 **/
	@Resource(resourceId = 11, operation = Operation.Execute)
	public abstract void executeDeactivate(String executeParameter);

	/**
	 * Indicates the current activation state of this software:
	 * 0: DISABLED
	 * Activation State is DISABLED if the Software Activation State Machine is in the INACTIVE state or not alive.
	 * 1: ENABLED
	 * Activation State is ENABLED only if the Software Activation State Machine is in the ACTIVE state
	 **/
	@Resource(resourceId = 12, operation = Operation.Read)
	public abstract Boolean readActivationState();

	/**
	 * Link to “Package Settings” object which allows to modify at any time software configuration settings. This is an application specific object. 
	 * Note: OMA might provide a template for a Package Settings object in a future release of this specification.
	 **/
	@Resource(resourceId = 13, operation = Operation.Read)
	public ObjectLink readPackageSettings()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 13, operation = Operation.Write)
	public void writePackageSettings(ObjectLink writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * User Name for access to SW Update Package in pull mode.
	 * Key based mechanism can alternatively use for talking to the component server instead of user name and password combination.
	 **/
	@Resource(resourceId = 14, operation = Operation.Write)
	public void writeUserName(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Password for access to SW Update Package in pull mode.
	 **/
	@Resource(resourceId = 15, operation = Operation.Write)
	public void writePassword(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}
}
