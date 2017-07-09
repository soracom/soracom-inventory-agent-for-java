package io.soracom.inventory.agent.example.object;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.eclipse.leshan.core.response.ReadResponse;

import io.soracom.inventory.agent.core.lwm2m.ResourceContext;
import io.soracom.inventory.agent.core.lwm2m.base_object.DeviceObject;

public class ExampleDeviceObject extends DeviceObject {

	@Override
	public ReadResponse readUTCOffset(ResourceContext resourceContext) {
		String utcOffset = new SimpleDateFormat("X").format(Calendar.getInstance().getTime());
		return ReadResponse.success(resourceContext.getResourceId(), utcOffset);
	}

	@Override
	public ReadResponse readBatteryLevel(ResourceContext resourceContext) {
		return ReadResponse.success(resourceContext.getResourceId(), "100%");
	}
}
