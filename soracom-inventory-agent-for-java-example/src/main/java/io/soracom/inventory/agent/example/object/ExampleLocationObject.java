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

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.soracom.inventory.agent.core.lwm2m.typed_object.LocationObject;

public class ExampleLocationObject extends LocationObject {

	private static final Logger log = LoggerFactory.getLogger(ExampleSoftwareComponentObject.class);

	Float latitude = 0f;
	Float longitude = 0f;

	@Override
	public Float readLatitude() {
		latitude += 2.1f;
		log.info("latitude=" + latitude);
		return latitude;
	}

	@Override
	public Float readLongitude() {
		longitude += 1.1f;
		log.info("longitude=" + longitude);
		return longitude;
	}

	@Override
	public Date readTimestamp() {
		return new Date();
	}

}
