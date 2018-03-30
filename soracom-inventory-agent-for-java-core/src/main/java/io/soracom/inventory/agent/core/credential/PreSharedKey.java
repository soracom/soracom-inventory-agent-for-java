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
package io.soracom.inventory.agent.core.credential;

import java.util.Base64;

import org.eclipse.leshan.util.Hex;

public class PreSharedKey {

	protected String pskIdentity;
	protected byte[] pskKey;

	public PreSharedKey(String keyId, byte[] psk) {
		pskIdentity = keyId;
		pskKey = psk;
	}

	public PreSharedKey(String keyId, String pskBase64OrHex) {
		pskIdentity = keyId;
		try {
			pskKey = Base64.getDecoder().decode(pskBase64OrHex);
		} catch (Exception e) {
			pskKey = Hex.decodeHex(pskBase64OrHex.toCharArray());
		}
	}

	public String getPskIdentity() {
		return pskIdentity;
	}

	public byte[] getPskIdentityBytes() {
		return pskIdentity.getBytes();
	}

	public byte[] getPskKey() {
		return pskKey;
	}
}
