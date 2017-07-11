package io.soracom.inventory.agent.core.lwm2m.base_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import org.eclipse.leshan.core.response.*;

/**
 * This LwM2M Object provides a range of device related information which can be queried by the LwM2M Server, and a device reboot and factory reset function.
 **/
@LWM2MObject(objectId = 3, name = "Device")
public abstract class DeviceObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * Human readable manufacturer name
	 **/
	@Resource(resourceId = 0, operation = Operation.Read, type = "STRING")
	public ReadResponse readManufacturer(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * A model identifier (manufacturer specified string)
	 **/
	@Resource(resourceId = 1, operation = Operation.Read, type = "STRING")
	public ReadResponse readModelNumber(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Serial Number
	 **/
	@Resource(resourceId = 2, operation = Operation.Read, type = "STRING")
	public ReadResponse readSerialNumber(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Current firmware version of the Device.The Firmware Management function could rely on this resource.
	 **/
	@Resource(resourceId = 3, operation = Operation.Read, type = "STRING")
	public ReadResponse readFirmwareVersion(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Reboot the LwM2M Device to restore the Device from unexpected firmware failure.
	 **/
	@Resource(resourceId = 4, operation = Operation.Execute)
	public abstract ExecuteResponse executeReboot(ResourceContext resourceContext)	;

	/**
	 * Perform factory reset of the LwM2M Device to make the LwM2M Device to go through initial deployment sequence where provisioning and bootstrap sequence is performed. This requires client ensuring post factory reset to have minimal information to allow it to carry out one of the bootstrap methods specified in section 5.2.3. 
	 * When this Resource is executed, “De-register” operation MAY be sent to the LwM2M Server(s) before factory reset of the LwM2M Device.
	 **/
	@Resource(resourceId = 5, operation = Operation.Execute)
	public ExecuteResponse executeFactoryReset(ResourceContext resourceContext)	{
		return super.execute(resourceContext);
	}

	/**
	 * 0 – DC power
	 * 1 – Internal Battery
	 * 2 – External Battery
	 * 4 – Power over Ethernet
	 * 5 – USB
	 * 6 – AC (Mains) power
	 * 7 – Solar
	 * The same Resource Instance ID MUST be used to associate a given Power Source (Resource ID:6) with its Present Voltage (Resource ID:7) and its Present Current (Resource ID:8)
	 **/
	@Resource(resourceId = 6, operation = Operation.Read, multiple = true, type = "INTEGER")
	public ReadResponse readAvailablePowerSources(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Present voltage for each Available Power Sources Resource Instance.
	 **/
	@Resource(resourceId = 7, operation = Operation.Read, multiple = true, type = "INTEGER")
	public ReadResponse readPowerSourceVoltage(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Present current for each Available Power Source.
	 **/
	@Resource(resourceId = 8, operation = Operation.Read, multiple = true, type = "INTEGER")
	public ReadResponse readPowerSourceCurrent(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Contains the current battery level as a percentage (with a range from 0 to 100). This value is only valid for the Device internal Battery if present (one Available Power Sources Resource Instance is 1).
	 **/
	@Resource(resourceId = 9, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readBatteryLevel(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Estimated current available amount of storage space which can store data and software in the LwM2M Device (expressed in kilobytes).
	 **/
	@Resource(resourceId = 10, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readMemoryFree(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * 0=No error
	 * 1=Low battery power
	 * 2=External power supply off
	 * 3=GPS module failure
	 * 4=Low received signal strength
	 * 5=Out of memory
	 * 6=SMS failure
	 * 7=IP connectivity failure
	 * 8=Peripheral malfunction
	 * 
	 * When the single Device Object Instance is initiated, there is only one error code Resource Instance whose value is equal to 0 that means no error. When the first error happens, the LwM2M Client changes error code Resource Instance to any non-zero value to indicate the error type. When any other error happens, a new error code Resource Instance is created.
	 * This error code Resource MAY be observed by the LwM2M Server. How to deal with LwM2M Client’s error report depends on the policy of the LwM2M Server.
	 **/
	@Resource(resourceId = 11, operation = Operation.Read, multiple = true, type = "INTEGER")
	public abstract ReadResponse readErrorCode(ResourceContext resourceContext)	;

	/**
	 * Delete all error code Resource Instances and create only one zero-value error code that implies no error.
	 **/
	@Resource(resourceId = 12, operation = Operation.Execute)
	public ExecuteResponse executeResetErrorCode(ResourceContext resourceContext)	{
		return super.execute(resourceContext);
	}

	/**
	 * Current UNIX time of the LwM2M Client.
	 * The LwM2M Client should be responsible to increase this time value as every second elapses.
	 * The LwM2M Server is able to write this Resource to make the LwM2M Client synchronized with the LwM2M Server.
	 **/
	@Resource(resourceId = 13, operation = Operation.Read, type = "TIME")
	public ReadResponse readCurrentTime(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 13, operation = Operation.Write)
	public WriteResponse writeCurrentTime(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Indicates the UTC offset currently in effect for this LwM2M Device. UTC+X [ISO 8601].
	 **/
	@Resource(resourceId = 14, operation = Operation.Read, type = "STRING")
	public ReadResponse readUTCOffset(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 14, operation = Operation.Write)
	public WriteResponse writeUTCOffset(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Indicates in which time zone the LwM2M Device is located, in IANA Timezone (TZ) database format.
	 **/
	@Resource(resourceId = 15, operation = Operation.Read, type = "STRING")
	public ReadResponse readTimezone(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 15, operation = Operation.Write)
	public WriteResponse writeTimezone(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Indicates which bindings and modes are supported in the LwM2M Client. The possible values of Resource are combination of "U" or "UQ" and "S" or "SQ".
	 **/
	@Resource(resourceId = 16, operation = Operation.Read, type = "STRING")
	public abstract ReadResponse readSupportedBindingAndModes(ResourceContext resourceContext)	;

	/**
	 * Type of the device (manufacturer specified string: e.g., smart meters / dev Class…)
	 **/
	@Resource(resourceId = 17, operation = Operation.Read, type = "STRING")
	public ReadResponse readDeviceType(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Current hardware version of the device
	 **/
	@Resource(resourceId = 18, operation = Operation.Read, type = "STRING")
	public ReadResponse readHardwareVersion(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Current software version of the device (manufacturer specified string). On elaborated LwM2M device, SW could be split in 2 parts: a firmware one and a higher level software on top.
	 * Both pieces of Software are together managed by LwM2M Firmware Update Object (Object ID 5)
	 **/
	@Resource(resourceId = 19, operation = Operation.Read, type = "STRING")
	public ReadResponse readSoftwareVersion(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * This value is only valid for the Device Internal Battery if present (one Available Power Sources Resource Instance value is 1).
	 * Battery
	 * Status	Meaning	Description
	 * 0	Normal	The battery is operating normally and not on power.
	 * 1	Charging	The battery is currently charging.
	 * 2	Charge Complete	The battery is fully charged and still on power.
	 * 3	Damaged	The battery has some problem.
	 * 4	Low Battery	The battery is low on charge.
	 * 5	Not Installed	The battery is not installed.
	 * 6	Unknown	The battery information is not available.
	 **/
	@Resource(resourceId = 20, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readBatteryStatus(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Total amount of storage space which can store data and software in the LwM2M Device (expressed in kilobytes).
	 **/
	@Resource(resourceId = 21, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readMemoryTotal(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Reference to external “Device” object instance containing information. For example, such an external device can be a Host Device, which is a device into which the Device containing the LwM2M client is embedded. This Resource may be used to retrieve information about the Host Device.
	 **/
	@Resource(resourceId = 22, operation = Operation.Read, multiple = true, type = "OBJLNK")
	public ReadResponse readExtDevInfo(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
}