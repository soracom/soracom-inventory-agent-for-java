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
package io.soracom.inventory.agent.core.credential;

import java.io.Serializable;

import org.eclipse.leshan.core.request.BindingMode;

public class Credentials implements Serializable {
	private static final long serialVersionUID = 1L;
	private String serverURI;
	private int shortServerId;
	private long lifetime;
	private byte[] pskId;
	private byte[] pskKey;
	private BindingMode bindingMode;
	private boolean notifyWhenDisable;

	public String getServerURI() {
		return serverURI;
	}

	public void setServerURI(String serverURI) {
		this.serverURI = serverURI;
	}

	public int getShortServerId() {
		return shortServerId;
	}

	public void setShortServerId(int shortServerId) {
		this.shortServerId = shortServerId;
	}

	public long getLifetime() {
		return lifetime;
	}

	public void setLifetime(long lifetime) {
		this.lifetime = lifetime;
	}

	public byte[] getPskId() {
		return pskId;
	}

	public void setPskId(byte[] pskId) {
		this.pskId = pskId;
	}

	public byte[] getPskKey() {
		return pskKey;
	}

	public void setPskKey(byte[] pskKey) {
		this.pskKey = pskKey;
	}

	public BindingMode getBindingMode() {
		return bindingMode;
	}

	public void setBindingMode(BindingMode bindingMode) {
		this.bindingMode = bindingMode;
	}

	public boolean isNotifyWhenDisable() {
		return notifyWhenDisable;
	}

	public void setNotifyWhenDisable(boolean notifyWhenDisable) {
		this.notifyWhenDisable = notifyWhenDisable;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
