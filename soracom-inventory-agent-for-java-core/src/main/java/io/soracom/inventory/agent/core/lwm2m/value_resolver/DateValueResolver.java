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
package io.soracom.inventory.agent.core.lwm2m.value_resolver;

import java.util.Date;

import org.eclipse.leshan.core.response.ReadResponse;

import io.soracom.inventory.agent.core.lwm2m.ResourceContext;

public class DateValueResolver implements ValueResolver<Date> {
	@Override
	public Date resolveWriteParameter(ResourceContext rc) {
		return (Date) rc.getWriteValue().getValue();
	}

	@Override
	public ReadResponse toReadResponse(ResourceContext rc, Date returnValue) {
		return ReadResponse.success(rc.getResourceId(), returnValue);
	}
}