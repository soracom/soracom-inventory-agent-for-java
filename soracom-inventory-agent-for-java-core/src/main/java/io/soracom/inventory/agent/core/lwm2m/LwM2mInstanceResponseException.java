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
package io.soracom.inventory.agent.core.lwm2m;

import org.eclipse.leshan.ResponseCode;

public class LwM2mInstanceResponseException extends RuntimeException {

	protected ResponseCode responseCode;

	public LwM2mInstanceResponseException(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}

	public LwM2mInstanceResponseException(ResponseCode responseCode, String errorMessage) {
		super(errorMessage);
		this.responseCode = responseCode;
	}

	public ResponseCode getResponseCode() {
		return responseCode;
	}

	public static LwM2mInstanceResponseException notFound() {
		return new LwM2mInstanceResponseException(ResponseCode.NOT_FOUND);
	}
}
