package io.soracom.inventory.agent.example.object;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

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
	public ReadResponse readTimezone(ResourceContext resourceContext) {
		String timeZone = TimeZone.getDefault().getID();
		return ReadResponse.success(resourceContext.getResourceId(), timeZone);
	}

	@Override
	public ReadResponse readCurrentTime(ResourceContext resourceContext) {
		return ReadResponse.success(resourceContext.getResourceId(), new Date());
	}

	@Override
	public ReadResponse readMemoryFree(ResourceContext resourceContext) {
		int freeMemory = (int) Runtime.getRuntime().freeMemory();
		return ReadResponse.success(resourceContext.getResourceId(), freeMemory);
	}

	@Override
	public ReadResponse readMemoryTotal(ResourceContext resourceContext) {
		int totalMemory = (int) Runtime.getRuntime().totalMemory();
		return ReadResponse.success(resourceContext.getResourceId(), totalMemory);
	}

	@Override
	public ReadResponse readBatteryLevel(ResourceContext resourceContext) {
		return ReadResponse.success(resourceContext.getResourceId(), 100);
	}

	@Override
	public ReadResponse readManufacturer(ResourceContext resourceContext) {
		return ReadResponse.success(resourceContext.getResourceId(), "SORACOM");
	}

	@Override
	public ReadResponse readSerialNumber(ResourceContext resourceContext) {
		return ReadResponse.success(resourceContext.getResourceId(),
				"T-" + UUID.randomUUID().toString().substring(0, 8));
	}

	@Override
	public ReadResponse readFirmwareVersion(ResourceContext resourceContext) {
		return ReadResponse.success(resourceContext.getResourceId(), "1.0.0");
	}
}
