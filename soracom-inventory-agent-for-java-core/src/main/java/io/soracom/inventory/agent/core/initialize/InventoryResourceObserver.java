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
import org.eclipse.leshan.client.resource.NotifySender;
import org.eclipse.leshan.core.node.LwM2mPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryResourceObserver implements ResourceObserver {

	private static final Logger log = LoggerFactory.getLogger(InventoryResourceObserver.class);

	private Map<LwM2mPath, NotifySender> observedResourceMap = Collections
			.synchronizedMap(new HashMap<LwM2mPath, NotifySender>());

	private int observeStartDelayMillis = 10000;
	private int observeInternalMillis = 60000;

	protected Timer timer;

	public InventoryResourceObserver() {
		initObserve();
	}

	public void setObserveInternalMillis(int observeInternalMillis) {
		this.observeInternalMillis = observeInternalMillis;
		initObserve();
	}

	public void setObserveStartDelayMillis(int observeStartDelayMillis) {
		this.observeStartDelayMillis = observeStartDelayMillis;
		initObserve();
	}

	@Override
	public void removedObserveRelation(ObserveRelation relation) {
		LwM2mPath path = new LwM2mPath(relation.getExchange().getCurrentRequest().getOptions().getUriPathString());
		NotifySender removed = observedResourceMap.remove(path);
		if (removed != null) {
			log.info("removedObserveRelation path=" + path.toString());
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
			log.info("removedChild path=" + path.toString());
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
			log.info("addedObserveRelation path=" + path.toString());
		}
	}

	protected void initObserve() {
		if (timer != null) {
			timer.cancel();
		}
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				log.info("fire resource change for observation.");
				fireResourcesChange();
			}
		}, observeStartDelayMillis, observeInternalMillis);
		log.info("Observe start. observeStartDelayMillis:" + observeStartDelayMillis + " observeInternalMillis:"
				+ observeInternalMillis);
	}

	protected void fireResourcesChange() {
		for (LwM2mPath lwm2mPath : observedResourceMap.keySet()) {
			final NotifySender sender = observedResourceMap.get(lwm2mPath);
			final String relationURI = toRelationURI(lwm2mPath);
			log.debug("sendNotify " + relationURI);
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
