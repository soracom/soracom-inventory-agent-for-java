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
package io.soracom.inventory.agent.core.bootstrap;

import static org.eclipse.leshan.client.servers.ServerIdentity.SYSTEM;
import static org.eclipse.leshan.core.LwM2mId.SECURITY;
import static org.eclipse.leshan.core.model.ResourceModel.Type.INTEGER;
import static org.eclipse.leshan.core.model.ResourceModel.Type.OPAQUE;
import static org.eclipse.leshan.core.model.ResourceModel.Type.STRING;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.leshan.client.observer.LwM2mClientObserverAdapter;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.servers.DmServerInfo;
import org.eclipse.leshan.client.servers.ServerIdentity;
import org.eclipse.leshan.client.servers.ServersInfo;
import org.eclipse.leshan.client.servers.ServersInfoExtractor;
import org.eclipse.leshan.core.node.LwM2mObject;
import org.eclipse.leshan.core.node.LwM2mObjectInstance;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.request.BootstrapRequest;
import org.eclipse.leshan.core.request.ReadRequest;
import org.eclipse.leshan.core.request.RegisterRequest;
import org.eclipse.leshan.core.request.UpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.soracom.inventory.agent.core.credential.CredentialStore;
import io.soracom.inventory.agent.core.credential.Credentials;

/**
 * Observe bootstrap sequence to retrieve and store credential from Security
 * object.
 * 
 * @author c9katayama
 *
 */
public class BootstrapObserver extends LwM2mClientObserverAdapter {

	private static final Logger log = LoggerFactory.getLogger(BootstrapObserver.class);

	private Map<Integer, LwM2mObjectEnabler> objectEnablerMap;

	private CredentialStore credentialStore;

	public BootstrapObserver(Map<Integer, LwM2mObjectEnabler> enablerMap, CredentialStore credentialStore) {
		objectEnablerMap = enablerMap;
		this.credentialStore = credentialStore;
	}

	@Override
	public void onBootstrapSuccess(ServerIdentity bsserver, BootstrapRequest request) {
		Credentials c = extractCredentialsInfo();
		log.info("Bootstrap success: deviceId:" + new String(c.getPskId()) + " server:" + bsserver.toString());
		credentialStore.saveCredentials(c);
	}

	@Override
	public void onRegistrationSuccess(ServerIdentity server, RegisterRequest request, String registrationID) {
		Credentials c = extractCredentialsInfo();
		log.info("Registration success: deviceId:" + new String(c.getPskId()) + " registrationID:" + registrationID
				+ " server:" + server.toString());
		credentialStore.saveCredentials(c);
	}

	@Override
	public void onBootstrapTimeout(ServerIdentity bsserver, BootstrapRequest request) {
		log.info("Bootstrap timeout: " + bsserver.toString());
	}

	@Override
	public void onRegistrationTimeout(ServerIdentity server, RegisterRequest request) {
		log.info("Registration timeout: " + server.toString());
	}

	@Override
	public void onUpdateTimeout(ServerIdentity server, UpdateRequest request) {
		log.info("Update timeout: " + server.toString());
	}

	private Credentials extractCredentialsInfo() {
		try {
			final ServersInfo si = ServersInfoExtractor.getInfo(objectEnablerMap);
			if (si.bootstrap != null) {
				log.info("Bootstrap server: " + si.bootstrap.toString());
			}
			StringBuilder deviceManagementServersLog = new StringBuilder();
			for (Entry<Long, DmServerInfo> entry : si.deviceManagements.entrySet()) {
				deviceManagementServersLog.append("\n" + entry.getKey() + " " + entry.toString());
			}
			log.info("Device management servers: " + deviceManagementServersLog);

			final LwM2mObjectEnabler oe = objectEnablerMap.get(SECURITY);
			final LwM2mObject object = (LwM2mObject) oe.read(SYSTEM, new ReadRequest(0)).getContent();
			if (object == null) {
				log.warn("no security object found");
				return null;
			}
			final LwM2mObjectInstance deviceManagementServer = findDeviceManagementServerInstance(object);
			if (deviceManagementServer == null) {
				log.warn("no device management server instance found");
				return null;
			}
			final Long shortServerId = getResourceInteger(deviceManagementServer, 10);
			final DmServerInfo dm = si.deviceManagements.get(shortServerId);

			Credentials c = new Credentials();
			c.setServerURI(getResourceString(deviceManagementServer, 0));
			c.setShortServerId(shortServerId.intValue());
			c.setPskId(getResourceOpaque(deviceManagementServer, 3));
			c.setPskKey(getResourceOpaque(deviceManagementServer, 5));

			c.setLifetime(dm.lifetime);
			c.setBindingMode(dm.binding);
			c.setNotifyWhenDisable(false);
			return c;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	private LwM2mObjectInstance findDeviceManagementServerInstance(LwM2mObject object) {
		for (LwM2mObjectInstance i : object.getInstances().values()) {
			LwM2mResource r = i.getResource(1);
			if (!((Boolean) r.getValue())) {
				return i;
			}
		}
		return null;
	}

	private String getResourceString(LwM2mObjectInstance instance, int resourceId) {
		LwM2mResource r = instance.getResource(resourceId);
		if (r == null) {
			log.warn("no resource found for resourceId:" + resourceId);
			return "null";
		}
		if (r.getType() != STRING) {
			log.warn("wrong type specified for resourceId: " + resourceId);
			return "wrong type";
		}
		return (String) r.getValue();
	}

	private Long getResourceInteger(LwM2mObjectInstance instance, int resourceId) {
		LwM2mResource r = instance.getResource(resourceId);
		if (r == null) {
			log.warn("no resource found for resourceId:" + resourceId);
			return -1L;
		}
		if (r.getType() != INTEGER) {
			log.warn("wrong type specified for resourceId: " + resourceId);
			return -2L;
		}
		return (Long) r.getValue();
	}

	private byte[] getResourceOpaque(LwM2mObjectInstance instance, int resourceId) {
		LwM2mResource r = instance.getResource(resourceId);
		if (r == null) {
			log.warn("no resource found for resourceId:" + resourceId);
			return null;
		}
		if (r.getType() != OPAQUE) {
			log.warn("wrong type specified for resourceId: " + resourceId);
			return null;
		}
		return (byte[]) r.getValue();
	}
}
