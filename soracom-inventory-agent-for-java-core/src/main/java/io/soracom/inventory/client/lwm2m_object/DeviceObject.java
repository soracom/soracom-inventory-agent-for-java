package io.soracom.inventory.client.lwm2m_object;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.model.ResourceModel.Type;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.request.BindingMode;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeviceObject extends BaseInstanceEnabler {

	private static final Logger LOG = LoggerFactory.getLogger(DeviceObject.class);

	public DeviceObject() {
		// notify new date each 5 second
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				fireResourcesChange(13);
			}
		}, 5000, 5000);
	}

	@Override
	public ReadResponse read(int resourceid) {
		LOG.info("Read on Device Resource " + resourceid);
		switch (resourceid) {
		case 0:
			return ReadResponse.success(resourceid, getManufacturer());
		case 1:
			return ReadResponse.success(resourceid, getModelNumber());
		case 2:
			return ReadResponse.success(resourceid, getSerialNumber());
		case 3:
			return ReadResponse.success(resourceid, getFirmwareVersion());
		case 9:
			return ReadResponse.success(resourceid, getBatteryLevel());
		case 10:
			return ReadResponse.success(resourceid, getMemoryFree());
		case 11:
			Map<Integer, Long> errorCodes = new HashMap<>();
			errorCodes.put(0, getErrorCode());
			return ReadResponse.success(resourceid, errorCodes, Type.INTEGER);
		case 13:
			return ReadResponse.success(resourceid, getCurrentTime());
		case 14:
			return ReadResponse.success(resourceid, getUtcOffset());
		case 15:
			return ReadResponse.success(resourceid, getTimezone());
		case 16:
			return ReadResponse.success(resourceid, getSupportedBinding());
		default:
			return super.read(resourceid);
		}
	}

	@Override
	public ExecuteResponse execute(int resourceid, String params) {
		LOG.info("Execute on Device resource " + resourceid);
		if (params != null && params.length() != 0)
			System.out.println("\t params " + params);
		return ExecuteResponse.success();
	}

	@Override
	public WriteResponse write(int resourceid, LwM2mResource value) {
		LOG.info("Write on Device Resource " + resourceid + " value " + value);
		switch (resourceid) {
		case 13:
			return WriteResponse.notFound();
		case 14:
			setUtcOffset((String) value.getValue());
			fireResourcesChange(resourceid);
			return WriteResponse.success();
		case 15:
			setTimezone((String) value.getValue());
			fireResourcesChange(resourceid);
			return WriteResponse.success();
		default:
			return super.write(resourceid, value);
		}
	}

	private String getManufacturer() {
		return "SORACOM";
	}

	private String getModelNumber() {
		return "SRCM-001";
	}

	private String getSerialNumber() {
		return "T-"+UUID.randomUUID().randomUUID().toString().substring(0,8);
	}

	private String getFirmwareVersion() {
		return "1.0.0";
	}

	private long getErrorCode() {
		return 0;
	}

	private int getBatteryLevel() {
		return 100;
	}

	private int getMemoryFree() {
		return (int) Runtime.getRuntime().freeMemory();
	}

	private Date getCurrentTime() {
		return new Date();
	}

	private String utcOffset = new SimpleDateFormat("X").format(Calendar.getInstance().getTime());;

	private String getUtcOffset() {
		return utcOffset;
	}

	private void setUtcOffset(String t) {
		utcOffset = t;
	}

	private String timeZone = TimeZone.getDefault().getID();

	private String getTimezone() {
		return timeZone;
	}

	private void setTimezone(String t) {
		timeZone = t;
	}

	private String getSupportedBinding() {
		return BindingMode.U.toString();
	}
}