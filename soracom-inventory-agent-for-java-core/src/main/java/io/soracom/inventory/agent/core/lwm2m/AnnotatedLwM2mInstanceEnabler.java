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
package io.soracom.inventory.agent.core.lwm2m;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.client.resource.ResourceChangedListener;
import org.eclipse.leshan.client.servers.ServerIdentity;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.node.ObjectLink;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.soracom.inventory.agent.core.initialize.InventoryAgentHelper;
import io.soracom.inventory.agent.core.lwm2m.value_resolver.BooleanValueResolver;
import io.soracom.inventory.agent.core.lwm2m.value_resolver.ByteArrayValueResolver;
import io.soracom.inventory.agent.core.lwm2m.value_resolver.DateValueResolver;
import io.soracom.inventory.agent.core.lwm2m.value_resolver.DoubleValueResolver;
import io.soracom.inventory.agent.core.lwm2m.value_resolver.FloatValueResolver;
import io.soracom.inventory.agent.core.lwm2m.value_resolver.IntegerValueResolver;
import io.soracom.inventory.agent.core.lwm2m.value_resolver.LongValueResolver;
import io.soracom.inventory.agent.core.lwm2m.value_resolver.ObjectLinkValueResolver;
import io.soracom.inventory.agent.core.lwm2m.value_resolver.StringValueResolver;
import io.soracom.inventory.agent.core.lwm2m.value_resolver.ValueResolver;

public class AnnotatedLwM2mInstanceEnabler extends BaseInstanceEnabler {

	private static final Logger log = LoggerFactory.getLogger(AnnotatedLwM2mInstanceEnabler.class);

	List<ResourceChangedListener> listeners = Collections.synchronizedList(new ArrayList<ResourceChangedListener>());

	MetaDataHolder metaDataHolder;

	static class MetaDataHolder {
		Map<Integer, Method> readMethodMap = new HashMap<>();
		Map<Integer, Method> writeMethodMap = new HashMap<>();
		Map<Integer, Method> executeMethodMap = new HashMap<>();
		boolean initialized;
		static final Map<Class<?>, MetaDataHolder> metaDataHolderCaches = new HashMap<>();

		MetaDataHolder() {
		}

		static MetaDataHolder getMetaDataHolder(Class<?> clazz) {
			MetaDataHolder metaDataHolder = metaDataHolderCaches.get(clazz);
			if (metaDataHolder == null) {
				metaDataHolder = new MetaDataHolder();
				metaDataHolderCaches.put(clazz, metaDataHolder);
			}
			return metaDataHolder;
		}

		boolean isInitialized() {
			return initialized;
		}

		void setInitialized(boolean initialized) {
			this.initialized = initialized;
		}

		void addReadMethod(Integer resourceId, Method method) {
			readMethodMap.put(resourceId, method);
		}

		void addWriteMethod(Integer resourceId, Method method) {
			writeMethodMap.put(resourceId, method);
		}

		void addExecuteMethod(Integer resourceId, Method method) {
			executeMethodMap.put(resourceId, method);
		}

		Method findReadMethod(Integer resourceId) {
			return readMethodMap.get(resourceId);
		}

		Method findWriteMethod(Integer resourceId) {
			return writeMethodMap.get(resourceId);
		}

		Method findExecuteMethod(Integer resourceId) {
			return executeMethodMap.get(resourceId);
		}
	}

	protected static final Map<Class<?>, ValueResolver<?>> suppoortedTypes = new HashMap<>();
	static {
		// STRING, INTEGER, FLOAT, BOOLEAN, OPAQUE, TIME, OBJLNK, NONE;
		suppoortedTypes.put(String.class, new StringValueResolver());
		suppoortedTypes.put(Integer.class, new IntegerValueResolver());
		suppoortedTypes.put(Integer.TYPE, new IntegerValueResolver());
		suppoortedTypes.put(Long.class, new LongValueResolver());
		suppoortedTypes.put(Long.TYPE, new LongValueResolver());
		suppoortedTypes.put(Float.class, new FloatValueResolver());
		suppoortedTypes.put(Float.TYPE, new FloatValueResolver());
		suppoortedTypes.put(Double.class, new DoubleValueResolver());
		suppoortedTypes.put(Double.TYPE, new DoubleValueResolver());
		suppoortedTypes.put(Boolean.class, new BooleanValueResolver());
		suppoortedTypes.put(Boolean.TYPE, new BooleanValueResolver());
		suppoortedTypes.put(Date.class, new DateValueResolver());
		suppoortedTypes.put(byte[].class, new ByteArrayValueResolver());// OPAQUE
		suppoortedTypes.put(ObjectLink.class, new ObjectLinkValueResolver());// ObjLink
	}

	public AnnotatedLwM2mInstanceEnabler() {
		validateTypeAnnotation();
		initMetaDataHolders();
	}

	void validateTypeAnnotation() {
		LWM2MObject lwm2mObjectAnnotation = InventoryAgentHelper.findLWM2MObjectAnnotation(getClass());
		if (lwm2mObjectAnnotation == null) {
			throw new IllegalStateException("@LWM2MObject should be annotated to " + getClass().getSimpleName());
		}
	}

	void initMetaDataHolders() {
		final Class<?> clazz = getClass();
		metaDataHolder = MetaDataHolder.getMetaDataHolder(clazz);
		if (metaDataHolder.isInitialized()) {
			return;
		}
		final Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			Resource annotation = InventoryAgentHelper.findResourceAnnotation(clazz, method);
			if (annotation != null) {
				validateArgument(method, annotation);
				final int resourceId = annotation.resourceId();
				final Operation operation = annotation.operation();
				switch (operation) {
				case Read:
					validateReturnValue(method, ReadResponse.class, annotation);
					metaDataHolder.addReadMethod(resourceId, method);
					break;
				case Write:
					validateReturnValue(method, WriteResponse.class, annotation);
					metaDataHolder.addWriteMethod(resourceId, method);
					break;
				case Execute:
					validateReturnValue(method, ExecuteResponse.class, annotation);
					metaDataHolder.addExecuteMethod(resourceId, method);
					break;
				}
			}
		}
		metaDataHolder.setInitialized(true);
	}

	void validateArgument(Method m, Resource annotation) {
		Parameter[] parameters = m.getParameters();
		if (parameters == null || parameters.length == 0) {
			if (annotation.operation() == Operation.Write) {
				throw new IllegalStateException("Method:" + m.getName() + " should have 0 or 1 argument.");
			} else {
				return;
			}
		}
		boolean hasValueParam = false;
		for (int i = 0; i < parameters.length; i++) {
			Parameter param = parameters[i];
			if (param.getType().equals(ResourceContext.class)) {
				continue;
			}
			if (annotation.operation() == Operation.Read) {
				throw new IllegalStateException(
						"Read operatiodn does not support value parameter. Method:" + m.getName());
			}
			if (annotation.operation() == Operation.Execute && !param.getType().equals(String.class)) {
				throw new IllegalStateException(
						"Execute operation only suports String parameter. Method:" + m.getName());
			}
			// for Write operation
			if (suppoortedTypes.keySet().contains(param.getType()) == false) {
				throw new IllegalStateException(
						"Parameter type " + param.getType() + " is not supported. Method:" + m.getName());
			} else {
				if (!hasValueParam) {
					hasValueParam = true;
				} else {
					throw new IllegalStateException("Multiple value parametes is not supported. Method:" + m.getName());
				}
			}
		}
	}

	void validateReturnValue(Method m, Class<?> defaultReturnValueClass, Resource annotation) {
		final Class<?> returnType = m.getReturnType();
		if (returnType.isAssignableFrom(defaultReturnValueClass)) {
			return;
		}
		if (annotation.operation() == Operation.Read) {
			if (suppoortedTypes.keySet().contains(returnType) == false) {
				throw new IllegalStateException(
						"Return type " + returnType + " is not supported. Method:" + m.getName());
			}
		} else {
			// for Write and Execute operation
			if (!returnType.equals(Void.TYPE) && !returnType.equals(Void.class)) {
				throw new IllegalStateException("Method:" + m.getName() + " sould have void return value.");
			}
		}
	}

	Object[] prepareArgument(Method m, ResourceContext rc, Operation operation) {
		int paramCount = m.getParameterCount();
		if (paramCount == 0) {
			return new Object[] {};
		}
		final Parameter[] parameters = m.getParameters();
		final Object[] paramValues = new Object[paramCount];
		for (int i = 0; i < paramCount; i++) {
			Parameter param = parameters[i];
			Class<?> paramType = param.getType();
			if (paramType.isAssignableFrom(rc.getClass())) {
				paramValues[i] = rc;
			} else {
				if (operation == Operation.Read) {
					throw new IllegalStateException(
							"Read operatiodn does not support value parameter. Method:" + m.getName());
				} else if (operation == Operation.Execute) {
					if (!param.getType().equals(String.class)) {
						throw new IllegalStateException(
								"Execute operation only suports String parameter. Method:" + m.getName());
					} else {
						paramValues[i] = rc.getExecuteParameter();
					}
				} else {
					final ValueResolver<?> valueResolver = suppoortedTypes.get(param.getType());
					try {
						paramValues[i] = valueResolver.resolveWriteParameter(rc);
					} catch (Exception e) {
						throw new IllegalStateException(
								"Write parameter convert error.Expected type=" + param.getType().toGenericString()
										+ " write value=" + rc.getWriteValue().getValue() + " Method:" + m.getName());
					}
				}
			}
		}
		return paramValues;
	}

	@Override
	public void addResourceChangedListener(ResourceChangedListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeResourceChangedListener(ResourceChangedListener listener) {
		listeners.remove(listener);
	}

	public void fireResourcesChange(int... resourceIds) {
		for (ResourceChangedListener listener : listeners) {
			listener.resourcesChanged(resourceIds);
		}
	}

	@Deprecated
	protected ReadResponse read(ResourceContext resourceContext) {
		log.debug("read not found.resourceId:" + resourceContext.getResourceId());
		return ReadResponse.notFound();
	}

	@Deprecated
	protected WriteResponse write(ResourceContext resourceContext) {
		log.debug("write not found.resourceId:" + resourceContext.getResourceId());
		return WriteResponse.notFound();
	}

	@Deprecated
	protected ExecuteResponse execute(ResourceContext resourceContext) {
		log.debug("execute not found.resourceId:" + resourceContext.getResourceId());
		return ExecuteResponse.notFound();
	}

	@SuppressWarnings("unchecked")
	@Override
	public final ReadResponse read(ServerIdentity identity, int resourceId) {
		final Method m = metaDataHolder.findReadMethod(resourceId);
		if (m == null) {
			return ReadResponse.notFound();
		}
		final ResourceContext rc = ResourceContext.ofReadContext(resourceId);
		final Object[] params = prepareArgument(m, rc, Operation.Read);
		try {
			log.debug("invoke read method.method:" + m.getName() + " resourceId:" + resourceId);
			final Object returnValue = m.invoke(this, params);
			if (returnValue == null) {
				return ReadResponse.notFound();
			}
			if (returnValue instanceof ReadResponse) {
				return (ReadResponse) returnValue;
			}
			@SuppressWarnings("rawtypes")
			final ValueResolver valueResolver = suppoortedTypes.get(returnValue.getClass());
			return valueResolver.toReadResponse(rc, returnValue);
		} catch (Throwable e) {
			e = stripInvocationTargetException(e);
			if (e instanceof LwM2mInstanceResponseException) {
				LwM2mInstanceResponseException le = (LwM2mInstanceResponseException) e;
				log.debug(le.getResponseCode().getName() + le.getMessage());
				return new ReadResponse(le.getResponseCode(), null, le.getMessage());
			}
			log.error(e.getMessage(), e);
			return ReadResponse.internalServerError(e.getMessage());
		}
	}

	@Override
	public final WriteResponse write(ServerIdentity identity, int resourceId, LwM2mResource value) {
		final Method m = metaDataHolder.findWriteMethod(resourceId);
		if (m == null) {
			return WriteResponse.notFound();
		}
		final ResourceContext rc = ResourceContext.ofWriteContext(resourceId, value);
		final Object[] params = prepareArgument(m, rc, Operation.Write);
		try {
			log.debug("invoke write method.method:" + m.getName() + " resourceId:" + resourceId);
			final Object returnValue = m.invoke(this, params);
			if (returnValue == null || returnValue.getClass().equals(Void.TYPE)
					|| returnValue.getClass().equals(Void.class)) {
				return WriteResponse.success();
			}
			if (returnValue instanceof WriteResponse) {
				return (WriteResponse) returnValue;
			}
			throw new IllegalStateException("Invalid return value for write operation.method=" + m.getName());
		} catch (Throwable e) {
			e = stripInvocationTargetException(e);
			if (e instanceof LwM2mInstanceResponseException) {
				LwM2mInstanceResponseException le = (LwM2mInstanceResponseException) e;
				log.debug(le.getResponseCode().getName() + le.getMessage());
				return new WriteResponse(le.getResponseCode(), null, le.getMessage());
			}
			log.error(e.getMessage(), e);
			return WriteResponse.internalServerError(e.getMessage());
		}
	}

	@Override
	public final ExecuteResponse execute(ServerIdentity identity, int resourceId, String executeParams) {
		final Method m = metaDataHolder.findExecuteMethod(resourceId);
		if (m == null) {
			return ExecuteResponse.notFound();
		}
		final ResourceContext rc = ResourceContext.ofExecuteContext(resourceId, executeParams);
		final Object[] params = prepareArgument(m, rc, Operation.Execute);
		try {
			log.debug("invoke execute method.method:" + m.getName() + " resourceId:" + resourceId);
			final Object returnValue = m.invoke(this, params);
			if (returnValue == null || returnValue.getClass().equals(Void.TYPE)
					|| returnValue.getClass().equals(Void.class)) {
				return ExecuteResponse.success();
			}
			if (returnValue instanceof ExecuteResponse) {
				return (ExecuteResponse) returnValue;
			}
			throw new IllegalStateException("Invalid return value for execute operatioon. method=" + m.getName());
		} catch (Throwable e) {
			e = stripInvocationTargetException(e);
			if (e instanceof LwM2mInstanceResponseException) {
				LwM2mInstanceResponseException le = (LwM2mInstanceResponseException) e;
				log.debug(le.getResponseCode().getName() + le.getMessage());
				return new ExecuteResponse(le.getResponseCode(), null, le.getMessage());
			}
			log.error(e.getMessage(), e);
			return ExecuteResponse.internalServerError(e.getMessage());
		}
	}

	Throwable stripInvocationTargetException(Throwable e) {
		while (e instanceof InvocationTargetException) {
			e = e.getCause();
		}
		return e;
	}
}
