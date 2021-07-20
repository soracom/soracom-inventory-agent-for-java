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

import static org.eclipse.leshan.client.object.Security.noSec;
import static org.eclipse.leshan.client.object.Security.noSecBootstap;
import static org.eclipse.leshan.client.object.Security.psk;
import static org.eclipse.leshan.client.object.Security.pskBootstrap;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.UUID;

import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.object.Security;
import org.eclipse.leshan.client.object.Server;
import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.request.BindingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.soracom.inventory.agent.core.credential.Credentials;
import io.soracom.inventory.agent.core.lwm2m.LWM2MObject;
import io.soracom.inventory.agent.core.lwm2m.Resource;

public class InventoryAgentHelper {

	private static final Logger log = LoggerFactory.getLogger(InventoryAgentHelper.class);

	public static String[] DEFAULT_LWM2M_MODELS = LwM2mModelBuilder.PRESET_LWM2M_MODELS;

	public static Security getSecurityInfo(Credentials c) {
		if (c.getPskId() != null && c.getPskKey() != null) {
			return psk(c.getServerURI(), c.getShortServerId(), c.getPskId(), c.getPskKey());
		} else {
			return noSec(c.getServerURI(), c.getShortServerId());
		}
	}

	public static Security getSecurityInfoForBootstrap(String bootstrapServerURI, byte[] bootstrapPskId,
			byte[] bootstrapPskKey) {

		if (bootstrapPskId != null && bootstrapPskKey != null) {
			return pskBootstrap(bootstrapServerURI, bootstrapPskId, bootstrapPskKey);
		} else {
			return noSecBootstap(bootstrapServerURI);
		}
	}

	public static Server getServerInfo(Credentials c) {
		if (c == null) {
			// set default server infor for Inventory's SIM based bootstrap
			// https://github.com/eclipse/leshan/blob/master/leshan-client-cf/src/main/java/org/eclipse/leshan/client/californium/LeshanClientBuilder.java#L273
			// TODO: even the default lifetime sec is set, it will be overwritten by the
			// server's response value.
			return new Server(12345, 5 * 60, BindingMode.U, false);
		} else {
			return new Server(c.getShortServerId(), c.getLifetime(), c.getBindingMode(), c.isNotifyWhenDisable());
		}
	}

	public static long calculateRecommendedLifetimeSec(long lifetimeSec) {
		return Math.max(lifetimeSec, calculateDefaultLifetimeSec());
	}

	private static long calculateDefaultLifetimeSec() {
		// according to
		// org.eclipse.leshan.client.californium.CaliforniumEndpointsManager.java#getMaxCommunicationPeriodFor
		return 247 + 30;
	}

	public static String generateEndpoint() {
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			String hardwareAddress = detectHardwareAddress();
			if (hardwareAddress == null) {
				hardwareAddress = "";
			} else {
				hardwareAddress = "-" + hardwareAddress;
			}
			return localHost.getHostName() + hardwareAddress;
		} catch (Exception e) {
			return "SORACOMInventoryJavaAgent-" + UUID.randomUUID().toString();
		}
	}

	public static LwM2mModel createDefaultLwM2mModel() {
		return new LwM2mModelBuilder().addPresetObjectModels().build();
	}

	private static String detectHardwareAddress() {
		try {
			Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces();
			while (nics.hasMoreElements()) {
				NetworkInterface nic = nics.nextElement();
				String s = "";
				byte[] hardwareAddress = nic.getHardwareAddress();
				if (hardwareAddress != null) {
					for (byte b : hardwareAddress) {
						s += String.format("%02X", b);
					}
				}
				if (s.length() > 0) {
					log.info("detect hardware address:" + s + " from " + nic.getName());
					return s;
				}
			}
		} catch (Exception e) {
			// ignore
		}
		return null;
	}

	public static Resource findResourceAnnotation(Class<?> clazz, Method m) {
		Resource[] annotation = m.getAnnotationsByType(Resource.class);
		if (annotation == null || annotation.length == 0) {
			clazz = clazz.getSuperclass();
			if (clazz == null || clazz == Object.class) {
				return null;
			}
			try {
				m = clazz.getMethod(m.getName(), m.getParameterTypes());
			} catch (NoSuchMethodException e) {
				return null;
			}
			return findResourceAnnotation(clazz, m);
		} else {
			return annotation[0];
		}
	}

	public static LWM2MObject findLWM2MObjectAnnotation(Class<?> clazz) {
		LWM2MObject[] lwm2mObjectAnnotation = clazz.getAnnotationsByType(LWM2MObject.class);
		if (lwm2mObjectAnnotation == null || lwm2mObjectAnnotation.length == 0) {
			clazz = clazz.getSuperclass();
			if (clazz == null) {
				return null;
			} else {
				return findLWM2MObjectAnnotation(clazz);
			}
		} else {
			return lwm2mObjectAnnotation[0];
		}
	}

	public static void addShutdownHoot(final LeshanClient client) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				client.destroy(true);
			}
		});
	}
}
