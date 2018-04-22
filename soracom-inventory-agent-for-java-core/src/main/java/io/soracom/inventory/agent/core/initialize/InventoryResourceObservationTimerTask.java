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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.californium.core.observe.ObserveRelation;
import org.eclipse.californium.core.server.resources.Resource;
import org.eclipse.californium.core.server.resources.ResourceObserver;
import org.eclipse.leshan.ResponseCode;
import org.eclipse.leshan.client.observer.LwM2mClientObserverAdapter;
import org.eclipse.leshan.client.resource.NotifySender;
import org.eclipse.leshan.client.servers.DmServerInfo;
import org.eclipse.leshan.core.node.LwM2mPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handle resource observe and remove observe request, and invoke observed
 * resource continuously.
 * 
 * <pre>
 * This class provides continuous read method invocation for observed resources.
 * Once an observation request comes from server, this class add the observed
 * resource to observed map, then the resource's read method is invoked at the
 * interval of observationIntervalSeconds property. Also this class listens
 * registration status through
 * {@link org.eclipse.leshan.client.observer.LwM2mClientObserver}. Observation
 * is started when registration is succeeded, and it is stopped registration is
 * failed.
 * 
 * <pre>
 * 
 * @author c9katayama
 *
 */
public class InventoryResourceObservationTimerTask extends LwM2mClientObserverAdapter implements ResourceObserver {

	private static final Logger log = LoggerFactory.getLogger(InventoryResourceObservationTimerTask.class);

	private Map<LwM2mPath, NotifySender> observedResourceMap = Collections
			.synchronizedMap(new HashMap<LwM2mPath, NotifySender>());

	protected int timerTaskStartDelaySeconds = 10;
	protected int timerTaskIntervalSeconds = 60;
	protected boolean enable = true;

	protected Timer timer;

	public InventoryResourceObservationTimerTask() {
	}

	/**
	 * The time to delay start observation
	 * 
	 * @param observationStartDelaySeconds
	 */
	public void setTimerTaskStartDelaySeconds(int value) {
		this.timerTaskStartDelaySeconds = value;
		startObservationIfRunning();
	}

	/**
	 * The period between observations
	 * 
	 * @param observationIntervalSeconds
	 */
	public void setTimerTaskIntervalSeconds(int value) {
		if (value < 1) {
			throw new IllegalArgumentException("TimerTaskIntervalSeconds must be at least 1.");
		}
		this.timerTaskIntervalSeconds = value;
		startObservationIfRunning();
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
		if (enable == false) {
			stopObservation();
		}
	}

	/**
	 * Remove observation.
	 */
	@Override
	public void removedObserveRelation(ObserveRelation relation) {
		LwM2mPath path = new LwM2mPath(relation.getExchange().getCurrentRequest().getOptions().getUriPathString());
		NotifySender removed = observedResourceMap.remove(path);
		if (removed != null) {
			log.info("removed Observe Relation path=" + path.toString());
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void removedChild(Resource child) {
		List<LwM2mPath> removeKeyList = new ArrayList<>();
		for (Entry<LwM2mPath, NotifySender> entry : observedResourceMap.entrySet()) {
			if (entry.getValue() == child || entry.getValue().equals(child)) {
				removeKeyList.add(entry.getKey());
			}
		}
		for (LwM2mPath path : removeKeyList) {
			observedResourceMap.remove(path);
			log.info("removed child path=" + path.toString());
		}
	}

	@Override
	public void addedChild(Resource child) {
	}

	@Override
	public void changedPath(String old) {
	}

	@Override
	public void changedName(String old) {
	}

	@Override
	public void addedObserveRelation(ObserveRelation relation) {
		LwM2mPath path = new LwM2mPath(relation.getExchange().getCurrentRequest().getOptions().getUriPathString());
		Resource resource = relation.getResource();
		if (resource instanceof NotifySender) {
			NotifySender notifySender = (NotifySender) resource;
			observedResourceMap.put(path, notifySender);
			log.info("added ObserveRelation path=" + path.toString());
		}
	}

	@Override
	public void onRegistrationSuccess(DmServerInfo server, String registrationID) {
		startObservation();
	}

	@Override
	public void onRegistrationFailure(DmServerInfo server, ResponseCode responseCode, String errorMessage) {
		stopObservation();
	}

	@Override
	public void onRegistrationTimeout(DmServerInfo server) {
		stopObservation();
	}

	protected void startObservation() {
		if (timer != null) {
			timer.cancel();
		}
		if (enable == false) {
			log.debug("InventoryResourceObservationTimerTask is disabled.");
			return;
		}
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				log.info("fire resource change for observation.");
				fireResourcesChange();
			}
		}, timerTaskStartDelaySeconds * 1000, timerTaskIntervalSeconds * 1000);
		log.info("start observation. observationStartDelaySeconds:" + timerTaskStartDelaySeconds
				+ " observationIntervalSeconds:" + timerTaskIntervalSeconds);
	}

	protected void startObservationIfRunning() {
		if (timer != null) {
			startObservation();
		}
	}

	protected void stopObservation() {
		if (timer != null) {
			timer.cancel();
			timer = null;
			log.info("stop observation.");
		}
	}

	protected void fireResourcesChange() {
		for (LwM2mPath lwm2mPath : observedResourceMap.keySet()) {
			final NotifySender sender = observedResourceMap.get(lwm2mPath);
			final String relationURI = toRelationURI(lwm2mPath);
			log.debug("send notify to " + relationURI);
			sender.sendNotify(relationURI);
		}
	}

	protected String toRelationURI(LwM2mPath lwm2mPath) {
		String relationURI = lwm2mPath.toString();
		// relationURI should not begin with /
		if (relationURI.startsWith("/")) {
			relationURI = relationURI.substring(1, relationURI.length());
		}
		return relationURI;
	}
}
