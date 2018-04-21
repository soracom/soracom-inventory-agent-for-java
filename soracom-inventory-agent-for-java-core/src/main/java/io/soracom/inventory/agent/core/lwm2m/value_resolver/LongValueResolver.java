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

import org.eclipse.leshan.core.response.ReadResponse;

import io.soracom.inventory.agent.core.lwm2m.ResourceContext;

public class LongValueResolver implements ValueResolver<Long> {
	@Override
	public Long resolveWriteParameter(ResourceContext rc) {
		Number writerValue = (Number) rc.getWriteValue().getValue();
		if (writerValue == null) {
			return null;
		}
		return writerValue.longValue();
	}

	@Override
	public ReadResponse toReadResponse(ResourceContext rc, Long returnValue) {
		return ReadResponse.success(rc.getResourceId(), returnValue);
	}

}