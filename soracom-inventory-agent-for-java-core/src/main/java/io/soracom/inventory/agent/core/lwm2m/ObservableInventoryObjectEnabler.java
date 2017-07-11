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
package io.soracom.inventory.agent.core.lwm2m;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.leshan.client.request.ServerIdentity;
import org.eclipse.leshan.client.resource.LwM2mInstanceEnabler;
import org.eclipse.leshan.client.resource.LwM2mInstanceEnablerFactory;
import org.eclipse.leshan.client.resource.NotifySender;
import org.eclipse.leshan.client.resource.ObjectEnabler;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.node.LwM2mPath;
import org.eclipse.leshan.core.request.ObserveRequest;
import org.eclipse.leshan.core.response.ObserveResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.util.internal.ConcurrentSet;

public class ObservableInventoryObjectEnabler extends ObjectEnabler {

	private static final Logger log = LoggerFactory.getLogger(ObservableInventoryObjectEnabler.class);

	private int observeStartDelayMillis = 10000;
	private int observeInternalMillis = 60000;

	protected Timer timer;

	public void setObserveInternalMillis(int observeInternalMillis) {
		this.observeInternalMillis = observeInternalMillis;
		initObserve();
	}

	public void setObserveStartDelayMillis(int observeStartDelayMillis) {
		this.observeStartDelayMillis = observeStartDelayMillis;
		initObserve();
	}

	private Set<LwM2mPath> observePathSet = new ConcurrentSet<>();

	public ObservableInventoryObjectEnabler(int id, ObjectModel objectModel,
			Map<Integer, LwM2mInstanceEnabler> instances, LwM2mInstanceEnablerFactory instanceFactory) {
		super(id, objectModel, instances, instanceFactory);
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
		log.info("observe start. observeStartDelayMillis:" + observeStartDelayMillis + " observeInternalMillis:"
				+ observeInternalMillis);
	}

	@Override
	public synchronized ObserveResponse observe(ServerIdentity identity, ObserveRequest request) {
		observePathSet.add(request.getPath());
		if (timer == null) {
			initObserve();
		}
		return super.observe(identity, request);
	}

	protected void fireResourcesChange() {
		NotifySender sender = getNotifySender();
		if (null != sender) {
			final Set<String> sentPathSet = new HashSet<>();
			for (LwM2mPath path : observePathSet) {
				final String objPath = getId() + "";
				sendNotify(sender, sentPathSet, objPath);
				final Integer instanceId = path.getObjectInstanceId();
				if (instanceId != null) {
					sendNotify(sender, sentPathSet, objPath + "/" + instanceId);
					final Integer resourceId = path.getResourceId();
					if (resourceId != null) {
						sendNotify(sender, sentPathSet, objPath + "/" + instanceId + "/" + resourceId);
					}
				}
			}
		}
	}

	private void sendNotify(NotifySender sender, Set<String> sentPathSet, String path) {
		if (sentPathSet.contains(path) == false) {
			sender.sendNotify(path);
			sentPathSet.add(path);
		}
	}
}