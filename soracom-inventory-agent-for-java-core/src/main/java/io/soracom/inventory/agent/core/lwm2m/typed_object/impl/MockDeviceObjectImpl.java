/*******************************************************************************
 * Copyright (c) 2018 SORACOM, Inc. and others.
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
package io.soracom.inventory.agent.core.lwm2m.typed_object.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.eclipse.leshan.core.request.BindingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.soracom.inventory.agent.core.lwm2m.typed_object.DeviceObject;

/**
 * Mock implementation for Device object
 * 
 * @author c9katayama
 *
 */
public class MockDeviceObjectImpl extends DeviceObject {

	private static final Logger log = LoggerFactory.getLogger(MockDeviceObjectImpl.class);

	@Override
	public String readUTCOffset() {
		String utcOffset = new SimpleDateFormat("X").format(Calendar.getInstance().getTime());
		return utcOffset;
	}

	@Override
	public String readTimezone() {
		String timeZone = TimeZone.getDefault().getID();
		return timeZone;
	}

	@Override
	public Date readCurrentTime() {
		return new Date();
	}

	@Override
	public Integer readMemoryFree() {
		int freeMemory = (int) Runtime.getRuntime().freeMemory() / 1024;
		return freeMemory;
	}

	@Override
	public Integer readMemoryTotal() {
		int totalMemory = (int) Runtime.getRuntime().totalMemory() / 1024;
		return totalMemory;
	}

	@Override
	public Integer readBatteryLevel() {
		return 100;
	}

	@Override
	public String readManufacturer() {
		return "manufacturer";
	}

	@Override
	public String readSerialNumber() {
		return "serial-number";
	}

	@Override
	public String readFirmwareVersion() {
		return System.getProperty("os.version");
	}

	@Override
	public Integer readErrorCode() {
		return 0;
	}

	@Override
	public String readSupportedBindingAndModes() {
		return BindingMode.U.toString();
	}

	@Override
	public void executeReboot(String executeParameter) {
		log.info("Reboot is executed. executeParameter=" + executeParameter);
	}
}
