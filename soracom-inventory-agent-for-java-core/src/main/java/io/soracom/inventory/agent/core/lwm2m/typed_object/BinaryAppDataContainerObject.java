package io.soracom.inventory.agent.core.lwm2m.typed_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import java.util.Date;
import org.eclipse.leshan.core.node.ObjectLink;

/**
 * This LwM2M Objects provides the application service data related to a LwM2M Server, eg. Water meter data. 
 * There are several methods to create instance to indicate the message direction based on the negotiation between Application and LwM2M. The Client and Server should negotiate the instance(s) used to exchange the data. For example:
 *  - Using a single instance for both directions communication, from Client to Server and from Server to Client.
 *  - Using an instance for communication from Client to Server and another one for communication from Server to Client
 *  - Using several instances
 **/
@LWM2MObject(objectId = 19, name = "BinaryAppDataContainer", multiple = true)
public abstract class BinaryAppDataContainerObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * Indicates the application data content.
	 **/
	@Resource(resourceId = 0, operation = Operation.Read, multiple = true)
	public abstract byte[] readData();
	@Resource(resourceId = 0, operation = Operation.Write, multiple = true)
	public abstract void writeData(byte[] writeValue);

	/**
	 * Indicates the Application data priority:
	 * 0:Immediate
	 * 1:BestEffort
	 * 2:Latest
	 * 3-100: Reserved for future use.
	 * 101-254: Proprietary mode.
	 **/
	@Resource(resourceId = 1, operation = Operation.Read)
	public Integer readDataPriority()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 1, operation = Operation.Write)
	public void writeDataPriority(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Indicates the Data instance creation timestamp.
	 **/
	@Resource(resourceId = 2, operation = Operation.Read)
	public java.util.Date readDataCreationTime()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 2, operation = Operation.Write)
	public void writeDataCreationTime(java.util.Date writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Indicates the data description.
	 * e.g. "meter reading".
	 **/
	@Resource(resourceId = 3, operation = Operation.Read)
	public String readDataDescription()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 3, operation = Operation.Write)
	public void writeDataDescription(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Indicates the format of the Application Data.
	 * e.g. YG-Meter-Water-Reading
	 * UTF8-string
	 **/
	@Resource(resourceId = 4, operation = Operation.Read)
	public String readDataFormat()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 4, operation = Operation.Write)
	public void writeDataFormat(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Indicates the destination Application ID.
	 **/
	@Resource(resourceId = 5, operation = Operation.Read)
	public Integer readAppID()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 5, operation = Operation.Write)
	public void writeAppID(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}
}
