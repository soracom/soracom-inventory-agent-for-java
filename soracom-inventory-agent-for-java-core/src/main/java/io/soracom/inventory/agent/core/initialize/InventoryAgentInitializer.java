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
package io.soracom.inventory.agent.core.initialize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.leshan.LwM2mId;
import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.eclipse.leshan.client.object.Security;
import org.eclipse.leshan.client.resource.LwM2mInstanceEnabler;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.model.LwM2mModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.soracom.inventory.agent.core.bootstrap.BootstrapObserver;
import io.soracom.inventory.agent.core.credential.CredentialStore;
import io.soracom.inventory.agent.core.credential.Credentials;
import io.soracom.inventory.agent.core.credential.PreSharedKey;
import io.soracom.inventory.agent.core.lwm2m.AnnotatedLwM2mInstanceEnabler;
import io.soracom.inventory.agent.core.lwm2m.LWM2MObject;
import io.soracom.inventory.agent.core.lwm2m.ObservableInventoryObjectEnabler;

public class InventoryAgentInitializer {

	private static final Logger log = LoggerFactory.getLogger(InventoryAgentInitializer.class);

	protected String serverUri;
	protected PreSharedKey preSharedKey;
	protected String endpoint;

	protected CredentialStore credentialStore;
	protected boolean forceBootstrap;
	protected LwM2mModel lwM2mModel;
	protected Map<Integer, List<AnnotatedLwM2mInstanceEnabler>> objectInstanceMap = new HashMap<>();

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
		final ObjectsInitializer initializer = new ObjectsInitializer(lwM2mModel) {
			protected org.eclipse.leshan.client.resource.ObjectEnabler createNodeEnabler(
					org.eclipse.leshan.core.model.ObjectModel objectModel) {
				Map<Integer, LwM2mInstanceEnabler> instances = new HashMap<>();
				LwM2mInstanceEnabler[] newInstances = createInstances(objectModel);
				for (int i = 0; i < newInstances.length; i++) {
					instances.put(i, newInstances[i]);
				}
				return new ObservableInventoryObjectEnabler(objectModel.id, objectModel, instances,
						getFactoryFor(objectModel));
			};
		};
		initSecurity(initializer);
		initServer(initializer);
		initObjects(initializer);
		final List<LwM2mObjectEnabler> enablers = initObjectEnablers(initializer);
		// Create client
		final String endpoint = initEndpoint();
		final LeshanClientBuilder builder = new LeshanClientBuilder(endpoint);
		builder.setObjects(enablers);
		final LeshanClient client = builder.build();
		client.addObserver(new BootstrapObserver(enablers, credentialStore));
		InventoryAgentHelper.addShutdownHoot(client);
		return client;
	}

	protected LwM2mModel initLwM2mModel() {
		return this.lwM2mModel == null ? InventoryAgentHelper.createDefaultLwM2mModel() : this.lwM2mModel;
	}

	protected void initSecurity(ObjectsInitializer initializer) {
		if (objectInstanceMap.containsKey(LwM2mId.SECURITY)) {
			return;
		}
		if (forceBootstrap) {
			credentialStore.clearCredentials();
		}
		Security securityInfo = null;
		if (preSharedKey != null) {
			log.info("set bootstrap security object from psk.");
			Credentials credential = new Credentials();
			credential.setServerURI(serverUri);
			credential.setShortServerId(123);
			credential.setPskId(preSharedKey.getPskIdentityBytes());
			credential.setPskKey(preSharedKey.getPskKey());
			securityInfo = InventoryAgentHelper.getSecurityInfo(credential);
		} else {
			final Credentials credential = credentialStore.loadCredentials();
			if (credential == null) {
				log.info("set bootstrap security object.");
				securityInfo = InventoryAgentHelper.getSecurityInfoForBootstrap(serverUri, null, null);
			} else {
				log.info("set security object from credential.");
				securityInfo = InventoryAgentHelper.getSecurityInfo(credential);
			}
		}
		initializer.setInstancesForObject(LwM2mId.SECURITY, securityInfo);
	}

	protected void initServer(ObjectsInitializer initializer) {
		if (objectInstanceMap.containsKey(LwM2mId.SERVER)) {
			return;
		}
		final Credentials credential = credentialStore.loadCredentials();
		if (credential != null) {
			initializer.setInstancesForObject(LwM2mId.SERVER, InventoryAgentHelper.getServerInfo(credential));
		}
	}

	protected void initObjects(ObjectsInitializer initializer) {
		for (Entry<Integer, List<AnnotatedLwM2mInstanceEnabler>> entry : objectInstanceMap.entrySet()) {
			final int objectId = entry.getKey();
			final List<AnnotatedLwM2mInstanceEnabler> instances = entry.getValue();
			log.info("set instance to initializer.objectId:" + objectId + " instance num:" + instances.size());
			initializer.setInstancesForObject(objectId, instances.toArray(new AnnotatedLwM2mInstanceEnabler[] {}));
		}
	}

	protected List<LwM2mObjectEnabler> initObjectEnablers(ObjectsInitializer initializer) {
		Set<Integer> keySet = new HashSet<>();
		keySet.addAll(objectInstanceMap.keySet());
		keySet.add(LwM2mId.SERVER);
		keySet.add(LwM2mId.SECURITY);
		List<LwM2mObjectEnabler> enablers = new ArrayList<>();
		for (Integer objectId : keySet) {
			LwM2mObjectEnabler objectEnabler = initializer.create(objectId);
			log.debug("create LwM2mObjectEnabler. objectId:" + objectId);
			enablers.add(objectEnabler);
		}
		return enablers;
	}

	protected String initEndpoint() {
		String endpoint = this.endpoint;
		if (endpoint == null) {
			endpoint = InventoryAgentHelper.generateEndpoint();
		}
		return endpoint;
	}

}
