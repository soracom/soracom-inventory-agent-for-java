package io.soracom.inventory.agent.core.lwm2m.typed_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import java.util.Date;
import org.eclipse.leshan.core.node.ObjectLink;

/**
 * The Event Log Object is a single Instance Object defined for logging data in a straightforward and generic way.
 * The Resources of that Object are based on the OMA LwM2M set of reusable Resources dedicated to logging event activity.
 **/
@LWM2MObject(objectId = 20, name = "Event Log")
public abstract class EventLogObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 *  Define the Log Event Class: 0: generic (default)  1: system   2: security  3: event   4: trace   5: panic   6: charging [7-99]: reserved [100-255]: vendor specific 
	 **/
	@Resource(resourceId = 4010, operation = Operation.Read)
	public Integer readLogClass()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 4010, operation = Operation.Write)
	public void writeLogClass(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Actions:
	 * a) Start data collection(DC)
	 * b) LogStatus is set to 0 (running)
	 * c) DC is emptied (default) or extended according arg'0' value 
	 * Arguments definitions are described in the table below.
	 **/
	@Resource(resourceId = 4011, operation = Operation.Execute)
	public void executeLogStart(String executeParameter)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Actions: a) Stop data collection(DC) b)  1st LSB of LogStatus is set to "1"(stopped) c) DC is kept (default) or emptied according arg'0' value Arguments definitions are described in the table below.  
	 **/
	@Resource(resourceId = 4012, operation = Operation.Execute)
	public void executeLogStop(String executeParameter)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Data Collection process status: Each bit of this Resource Instance value defines specific status: 1st LSB 0=running, 1=stopped 2nd LSB 1=LogData contains Valid Data 0=LogData doesn't contain Valid Data 3rd LSB 1=Error occurred during Data Collection 0=No error [4th -7th ] LSB:reserved 8th LSB: vendor specific.
	 **/
	@Resource(resourceId = 4013, operation = Operation.Read)
	public Integer readLogStatus()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Read Access on that Resource returns the Data Collection associated to the current Object Instance.  
	 **/
	@Resource(resourceId = 4014, operation = Operation.Read)
	public abstract byte[] readLogData();

	/**
	 *  when set by the Server, this Resource indicates to the Client, what is the Server preferred data format to use when the LogData Resource is returned
	 * . when retrieved by the Server, this Resource indicates which specific data format is used when the LogData Resource is returned to the Server 
	 * 0  or Resource not present : no specific data format (sequence of bytes)
	 * 1 : OMA-LwM2M TLV format
	 * 2 : OMA-LwM2M JSON format
	 * 3:  OMA-LwM2M CBOR format
	 * [4..99] reserved
	 * [100..255] vendor specific data format
	 *  
	 **/
	@Resource(resourceId = 4015, operation = Operation.Read)
	public Integer readLogDataFormat()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 4015, operation = Operation.Write)
	public void writeLogDataFormat(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}
}
