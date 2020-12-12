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
import org.eclipse.leshan.core.LwM2mId;
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

import io.soracom.inventory.agent.core.bootstrap.BootstrapConstants;
import io.soracom.inventory.agent.core.bootstrap.BootstrapObserver;
import io.soracom.inventory.agent.core.bootstrap.krypton.KryptonClientConfigForInventory;
import io.soracom.inventory.agent.core.bootstrap.krypton.KryptonLogListener;
import io.soracom.inventory.agent.core.credential.CredentialStore;
import io.soracom.inventory.agent.core.credential.Credentials;
import io.soracom.inventory.agent.core.credential.JCEFileCredentialStore;
import io.soracom.inventory.agent.core.credential.PreSharedKey;
import io.soracom.inventory.agent.core.lwm2m.AnnotatedLwM2mInstanceEnabler;
import io.soracom.inventory.agent.core.lwm2m.LWM2MObject;
import io.soracom.krypton.SORACOMKryptonClient;
import io.soracom.krypton.SORACOMKryptonClientConfig;
import io.soracom.krypton.beans.BootstrapInventoryDeviceParams;
import io.soracom.krypton.beans.BootstrapInventoryDeviceResult;

/**
 * Helper class to initialize LeshanClient instance
 * 
 * @author c9katayama
 *
 */
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
	protected Integer observationTimerTaskStartDelaySeconds;
	protected Integer observationTimerTaskIntervalSeconds;
	protected boolean enableObservationTimerTask = true;

	private SORACOMKryptonClientConfig kryptonClientConfig;

	public InventoryAgentInitializer() {

	}

	/**
	 * Server URI to use bootstrap and registration
	 * 
	 * @param serverUri
	 */
	public void setServerUri(String serverUri) {
		this.serverUri = serverUri;
	}

	public void setPreSharedKey(PreSharedKey preSharedKey) {
		this.preSharedKey = preSharedKey;
	}

	/**
	 * Endpoint name to identify client device
	 * 
	 * @param endpoint
	 */
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

	/**
	 * Enable bootstrap by SORACOM Krypton with default parameters.
	 * 
	 * <pre>
	 * - Using GLOVAL_COVERAGE for api endpoint url
	 * - Automatic detection of UICC interface
	 * </pre>
	 * 
	 * @param kryptonClientConfig
	 */
	public void enableKryptonBootstrap() {
		this.kryptonClientConfig = new KryptonClientConfigForInventory();
	}

	/**
	 * Enable bootstrap by SORACOM Krypton
	 * 
	 * @param kryptonClientConfig
	 */
	public void enableKryptonBootstrap(KryptonClientConfigForInventory kryptonClientConfig) {
		this.kryptonClientConfig = kryptonClientConfig;
	}

	/**
	 * Observation interval calling read method continuously
	 * 
	 * @param observationIntervalSeconds
	 */
	public void setObservationTimerTaskIntervalSeconds(Integer observationTimerTaskIntervalSeconds) {
		this.observationTimerTaskIntervalSeconds = observationTimerTaskIntervalSeconds;
	}

	/**
	 * Enable timer task to invoke observed resources continuously.
	 * 
	 * @param enableObservationTimerTasks
	 *            defaul value is true
	 */
	public void setEnableObservationTimerTasks(boolean enableObservationTimerTasks) {
		this.enableObservationTimerTask = enableObservationTimerTasks;
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
		initCredentialStore();
		initEndpoint();
		initServerUri();
		initLwM2mModel();
		// psk > credential store > krypton > bootstrap
		Credentials credentials = loadCredentials();
		if (credentials == null) {
			executeKryptonBootstrap();
			credentials = loadCredentials();
		}
		final ObjectsInitializer initializer = new ObjectsInitializer(lwM2mModel);
		initSecurity(initializer, credentials);
		initServer(initializer, credentials);
		initObjects(initializer);

		final Map<Integer, LwM2mObjectEnabler> objectEnablerMap = initObjectEnablers(initializer);
		// Create client
		final LeshanClientBuilder builder = new LeshanClientBuilder(endpoint);
		builder.setObjects(new ArrayList<>(objectEnablerMap.values()));
		final LeshanClient client = builder.build();
		client.addObserver(new BootstrapObserver(objectEnablerMap, credentialStore));
		final InventoryResourceObservationTimerTask resourceObserver = buildInventoryResourceObserverTimerTask();
		client.addObserver(resourceObserver);
		for (Resource resource : client.coap().getServer().getRoot().getChildren()) {
			resource.addObserver(resourceObserver);
		}
		InventoryAgentHelper.addShutdownHoot(client);
		return client;
	}

	InventoryResourceObservationTimerTask buildInventoryResourceObserverTimerTask() {
		final InventoryResourceObservationTimerTask resourceObserver = new InventoryResourceObservationTimerTask();
		if (observationTimerTaskIntervalSeconds != null) {
			resourceObserver.setTimerTaskIntervalSeconds(observationTimerTaskIntervalSeconds);
		}
		if (observationTimerTaskStartDelaySeconds != null) {
			resourceObserver.setTimerTaskStartDelaySeconds(observationTimerTaskStartDelaySeconds);
		}
		resourceObserver.setEnable(this.enableObservationTimerTask);
		return resourceObserver;
	}

	protected void executeKryptonBootstrap() {
		if (kryptonClientConfig == null) {
			return;
		}
		log.info("trying to retrieve bootstrap parameters from Krypton.");
		final SORACOMKryptonClient kryptonClient = new SORACOMKryptonClient(kryptonClientConfig,
				new KryptonLogListener());
		final BootstrapInventoryDeviceParams bootstrapParam = new BootstrapInventoryDeviceParams();
		bootstrapParam.setEndpoint(endpoint);
		BootstrapInventoryDeviceResult bootstrapResult = kryptonClient.bootstrapInventoryDevice(bootstrapParam);

		this.serverUri = bootstrapResult.getServerUri();
		final String pskId = bootstrapResult.getPskId();
		final String appKey = bootstrapResult.getApplicationKey();
		PreSharedKey psk = new PreSharedKey(pskId, appKey);
		this.preSharedKey = psk;
		log.info("succeded to retrieve bootstrap parameters from Krypton.pskId:" + pskId + " serverUri:" + serverUri);
	}

	protected void initServerUri() {
		if (serverUri == null) {
			if (preSharedKey != null) {
				// need to set serverUri when using PSK
				throw new IllegalStateException("Server uri is necessary when using PSK. ["
						+ BootstrapConstants.DEFAULT_JP_DM_SERVER_ADDRESS + "] is for Japan coverage, and ["
						+ BootstrapConstants.DEFAULT_GLOBAL_DM_SERVER_ADDRESS + "] is for Global coverage.");
			} else {
				serverUri = "coap://" + BootstrapConstants.DEFAULT_BOOTSTRAP_SERVER_ADDRESS;
			}
		}
	}

	protected void initLwM2mModel() {
		if (this.lwM2mModel == null) {
			this.lwM2mModel = InventoryAgentHelper.createDefaultLwM2mModel();
		}
	}

	protected void initCredentialStore() {
		if (this.credentialStore == null) {
			this.credentialStore = new JCEFileCredentialStore();
		}
		if (forceBootstrap) {
			credentialStore.clearCredentials();
		}
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
			final Credentials credentials = credentialStore.loadCredentials();
			if (credentials != null) {
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

	private void initEndpoint() {
		if (endpoint == null) {
			endpoint = InventoryAgentHelper.generateEndpoint();
		}
	}

}
