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
package io.soracom.inventory.agent.core.initialize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.californium.core.server.resources.Resource;
import org.eclipse.leshan.LwM2mId;
import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.eclipse.leshan.client.object.Security;
import org.eclipse.leshan.client.object.Server;
import org.eclipse.leshan.client.resource.LwM2mInstanceEnabler;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.request.BindingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.soracom.inventory.agent.core.bootstrap.BootstrapObserver;
import io.soracom.inventory.agent.core.credential.CredentialStore;
import io.soracom.inventory.agent.core.credential.Credentials;
import io.soracom.inventory.agent.core.credential.PreSharedKey;
import io.soracom.inventory.agent.core.lwm2m.AnnotatedLwM2mInstanceEnabler;
import io.soracom.inventory.agent.core.lwm2m.LWM2MObject;

public class InventoryAgentInitializer {

	private static final Logger log = LoggerFactory.getLogger(InventoryAgentInitializer.class);

	protected String serverUri;
	protected PreSharedKey preSharedKey;
	protected String endpoint;

	protected CredentialStore credentialStore;
	protected boolean forceBootstrap;
	protected LwM2mModel lwM2mModel;
	protected Map<Integer, List<AnnotatedLwM2mInstanceEnabler>> objectInstanceMap = new HashMap<>();
	protected LwM2mInstanceEnabler serverInstance;
	private int shortServerId = 123;

	public InventoryAgentInitializer() {

	}

	public void setServerUri(String serverUri) {
		this.serverUri = serverUri;
	}

	public String getServerUri() {
		return serverUri;
	}

	public void setPreSharedKey(PreSharedKey preSharedKey) {
		this.preSharedKey = preSharedKey;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public void setCredentialStore(CredentialStore credentialStore) {
		this.credentialStore = credentialStore;
	}

	public void setForceBootstrap(boolean forceBootstrap) {
		this.forceBootstrap = forceBootstrap;
	}

	public void setLwM2mModel(LwM2mModel lwM2mModel) {
		this.lwM2mModel = lwM2mModel;
	}

	public void addInstancesForObject(AnnotatedLwM2mInstanceEnabler objectInstance) {
		final LWM2MObject lwm2mObjectAnnotation = InventoryAgentHelper
				.findLWM2MObjectAnnotation(objectInstance.getClass());
		final int objectId = lwm2mObjectAnnotation.objectId();
		List<AnnotatedLwM2mInstanceEnabler> instanceList = objectInstanceMap.get(objectId);
		if (instanceList == null) {
			instanceList = new ArrayList<>();
		}
		instanceList.add(objectInstance);
		if (lwm2mObjectAnnotation.multiple() == false && instanceList.size() > 1) {
			throw new IllegalStateException(
					"This model is not supported multiple instances.Model:" + lwm2mObjectAnnotation.name());
		}
		objectInstanceMap.put(objectId, instanceList);
	}

	public LeshanClient buildClient() {
		final LwM2mModel lwM2mModel = initLwM2mModel();
		final ObjectsInitializer initializer = new ObjectsInitializer(lwM2mModel);
		final Credentials credentials = loadCredentials();
		initSecurity(initializer, credentials);
		initServer(initializer, credentials);
		initObjects(initializer);
		
		final Map<Integer, LwM2mObjectEnabler> objectEnablerMap = initObjectEnablers(initializer);
		// Create client
		final String endpoint = initEndpoint();
		final LeshanClientBuilder builder = new LeshanClientBuilder(endpoint);
		builder.setObjects(new ArrayList<>(objectEnablerMap.values()));
		final LeshanClient client = builder.build();
		client.addObserver(new BootstrapObserver(objectEnablerMap, credentialStore));
		final InventoryResourceObserver resourceObserver = new InventoryResourceObserver();
		for (Resource resource : client.getCoapServer().getRoot().getChildren()) {
			resource.addObserver(resourceObserver);
		}
		InventoryAgentHelper.addShutdownHoot(client);
		return client;
	}

	protected LwM2mModel initLwM2mModel() {
		return this.lwM2mModel == null ? InventoryAgentHelper.createDefaultLwM2mModel() : this.lwM2mModel;
	}

	private Credentials loadCredentials() {
		if (preSharedKey != null) {
			log.info("load credentials from psk.");
			Credentials credentials = new Credentials();
			credentials.setServerURI(serverUri);
			credentials.setShortServerId(shortServerId);
			credentials.setPskId(preSharedKey.getPskIdentityBytes());
			credentials.setPskKey(preSharedKey.getPskKey());
			credentials.setBindingMode(BindingMode.U);
			credentials.setLifetime(60L);
			return credentials;
		} else {
			if (forceBootstrap) {
				credentialStore.clearCredentials();
			}
			final Credentials credentials = credentialStore.loadCredentials();
			if (credentials == null) {
				log.info("credentials does not exist.");
			} else {
				log.info("load credentials from credential store.");
			}
			return credentials;
		}
	}

	private Security securityObject;
	private Server serverObject;

	private void initSecurity(ObjectsInitializer initializer, Credentials credentials) {
		if (objectInstanceMap.containsKey(LwM2mId.SECURITY)) {
			return;
		}
		if (preSharedKey != null) {
			log.info("set bootstrap security object from psk.");
			securityObject = InventoryAgentHelper.getSecurityInfo(credentials);
		} else {
			if (credentials == null) {
				log.info("set bootstrap security object.");
				securityObject = InventoryAgentHelper.getSecurityInfoForBootstrap(serverUri, null, null);
			} else {
				log.info("set security object from credential store.");
				securityObject = InventoryAgentHelper.getSecurityInfo(credentials);
			}
		}
		initializer.setInstancesForObject(LwM2mId.SECURITY, securityObject);
	}

	private void initServer(ObjectsInitializer initializer, Credentials credentials) {
		if (objectInstanceMap.containsKey(LwM2mId.SERVER)) {
			return;
		}
		if (credentials != null) {
			serverObject = InventoryAgentHelper.getServerInfo(credentials);
			initializer.setInstancesForObject(LwM2mId.SERVER, serverObject);
		}
	}

	private void initObjects(ObjectsInitializer initializer) {
		
		for (Entry<Integer, List<AnnotatedLwM2mInstanceEnabler>> entry : objectInstanceMap.entrySet()) {
			final int objectId = entry.getKey();
			final List<AnnotatedLwM2mInstanceEnabler> instances = entry.getValue();
			log.info("set instance to initializer.objectId:" + objectId + " instance num:" + instances.size());
			initializer.setInstancesForObject(objectId, instances.toArray(new AnnotatedLwM2mInstanceEnabler[] {}));
		}
	}

	private Map<Integer, LwM2mObjectEnabler> initObjectEnablers(ObjectsInitializer initializer) {
		final Map<Integer, LwM2mObjectEnabler> enablerMap = new HashMap<>();
		final List<Integer> keySet = new ArrayList<>();	
		keySet.add(LwM2mId.SERVER);
		keySet.add(LwM2mId.SECURITY);
		keySet.addAll(objectInstanceMap.keySet());
		for (Integer objectId : keySet) {
			final LwM2mObjectEnabler objectEnabler = initializer.create(objectId);
			log.debug("create LwM2mObjectEnabler. objectId:" + objectId);
			enablerMap.put(objectId, objectEnabler);
		}
		return enablerMap;
	}

	private String initEndpoint() {
		String endpoint = this.endpoint;
		if (endpoint == null) {
			endpoint = InventoryAgentHelper.generateEndpoint();
		}
		return endpoint;
	}

}
