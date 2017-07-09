package io.soracom.inventory.agent.core.lwm2m.base_object;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

import io.soracom.inventory.agent.core.lwm2m.AnnotatedLwM2mInstanceEnabler;
import io.soracom.inventory.agent.core.lwm2m.LWM2MObject;
import io.soracom.inventory.agent.core.lwm2m.Operation;
import io.soracom.inventory.agent.core.lwm2m.Resource;
import io.soracom.inventory.agent.core.lwm2m.ResourceContext;

/**
 * This LWM2M Object is dedicated to manage the device capabilities of a device e.g. sensors, communication, etc.
 **/
@LWM2MObject(objectId = 15, name = "DevCapMgmt", multiple = true)
public abstract class DevCapMgmtObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * List of Device Capabilities inside a given Group.
	 * The format is a free list ASCII-represented integers separated by a semi colon. (e.g. 0;1;10)
	 * The list of capabilities per Group is given in Appendix B: Device Capabilities Vocabulary
	 * executable Resource can work with.
	 **/
	@Resource(resourceId = 0, operation = Operation.Read)
	public ReadResponse readProperty(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 0, operation = Operation.Execute)
	public ExecuteResponse executeProperty(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Group name of Device Capabilities
	 * 0: SENSOR: luminosity, presence,temp,humidity
	 * 1: CONTROL: Light, Power, Sound
	 * 2: CONNECTIVITY: Bluetooth, wifi, …
	 * 3: NAVIGATION: gps, galieo
	 * 4: STORAGE: external memory,
	 * 5: VISION: cam, video-cam, night_cam.
	 * 6: SOUND: speaker, buzzer
	 * 7: ANALOG_INPUT: generic input
	 * 8: ANALOG_OUTPUT: generic output
	 * 9-15: reserved
	 **/
	@Resource(resourceId = 1, operation = Operation.Read)
	public ReadResponse readGroup(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 1, operation = Operation.Execute)
	public ExecuteResponse executeGroup(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Device Capability Description
	 * (manufacturer specified string)
	 **/
	@Resource(resourceId = 2, operation = Operation.Read)
	public ReadResponse readDescription(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 2, operation = Operation.Execute)
	public ExecuteResponse executeDescription(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * When the resource doesn’t exist, it means the associated Device Capability is not removable.
	 * When this resource is “False”, it means the associated Device Capability is removable and is currently not attached to the device.
	 * When this resource is “True”, it means the associated Device Capability – if removable – is currently attached to the Device.
	 * When a Device Capability is not removable, and the “Attached” Resource is present, the “Attached” value but be set to “True”.
	 **/
	@Resource(resourceId = 3, operation = Operation.Read)
	public ReadResponse readAttached(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 3, operation = Operation.Execute)
	public ExecuteResponse executeAttached(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * This resource indicates whether the Device Capability is enabled regardless whether the Device Capability is attached or not. If the value of this resource is “True” the Device Capability is in Enabled State. If the value is “False” the Device Capability is in Disabled State;
	 * The ‘Attached’ and ‘Enabled’ resources are independent. A Device Capability MAY have ‘True’ as value for ‘Enabled’ Resource while having ‘False’ as value for the ‘Attached’ Resource. That means the Device Capability is still not available and can’t be used until it is attached to the Device, but will be useable once the Device Capability is attached.
	 **/
	@Resource(resourceId = 4, operation = Operation.Read)
	public ReadResponse readEnabled(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 4, operation = Operation.Execute)
	public ExecuteResponse executeEnabled(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * This command is used to enable the Device Capability to transfer the Device Capability from Disabled State to Enabled state.
	 * In Enabled State, the Device Capability is allowed to work when it is attached to the Device.
	 **/

	/**
	 * This command is used to disable the Device Capability to transfer the Device Capability from Enabled State to Disabled State.
	 * In Disabled state the Device Capability is not allowed to work.
	 **/

	/**
	 * When the Resources “Enabled” or “Attached” are under “Observation”, this resource specifies whether the LWM2M Server MUST be notified when the value of the Resource on “Observation” changed. If the Resource “NotifyEn” is not present or the value is ‘False’, the LWM2M Server will be not notified about this change. If the “NotifyEn” Resource is present and the value is ‘True’, the LWM2M Server will be notified.
	 **/
	@Resource(resourceId = 7, operation = Operation.Read)
	public ReadResponse readNotifyEn(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 7, operation = Operation.Write)
	public WriteResponse writeNotifyEn(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 7, operation = Operation.Execute)
	public ExecuteResponse executeNotifyEn(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}
}
