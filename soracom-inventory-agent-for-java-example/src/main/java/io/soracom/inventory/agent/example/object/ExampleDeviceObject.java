/*******************************************************************************
 * Copyright (c) 2017 SORACOM, Inc. and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 * 
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *    http://www.eclipse.org/org/documents/edl-v10.html.
 * 
 * Contributors:
 *     SORACOM,Inc. - initial API and implementation
 *******************************************************************************/
package io.soracom.inventory.agent.example.object;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import org.eclipse.leshan.core.request.BindingMode;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.soracom.inventory.agent.core.lwm2m.ResourceContext;
import io.soracom.inventory.agent.core.lwm2m.base_object.DeviceObject;
import io.soracom.inventory.agent.core.util.CommandExecutor;

public class ExampleDeviceObject extends DeviceObject {

	private static final Logger log = LoggerFactory.getLogger(ExampleSoftwareComponentObject.class);

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
		int freeMemory = (int) Runtime.getRuntime().freeMemory() / 1024;
		return ReadResponse.success(resourceContext.getResourceId(), freeMemory);
	}

	@Override
	public ReadResponse readMemoryTotal(ResourceContext resourceContext) {
		int totalMemory = (int) Runtime.getRuntime().totalMemory() / 1024;
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

	@Override
	public ReadResponse readErrorCode(ResourceContext resourceContext) {
		return ReadResponse.success(resourceContext.getResourceId(), 0);
	}

	@Override
	public ReadResponse readSupportedBindingAndModes(ResourceContext resourceContext) {
		return ReadResponse.success(resourceContext.getResourceId(), BindingMode.U.toString());
	}

	@Override
	public ExecuteResponse executeReboot(ResourceContext resourceContext) {

		File scriptFile = new File("script/DeviceObject-Reboot.sh");
		String param = resourceContext.getExecuteParameter();
		if (param == null) {
			param = "";
		}
		log.info("executeReboot command=" + scriptFile.getName() + " param=" + param);
		CommandExecutor.execute(new String[] { scriptFile.getAbsolutePath(), param });
		return ExecuteResponse.success();
	}
}
