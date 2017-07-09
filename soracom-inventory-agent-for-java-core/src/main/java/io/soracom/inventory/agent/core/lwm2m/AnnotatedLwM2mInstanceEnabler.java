package io.soracom.inventory.agent.core.lwm2m;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.leshan.client.resource.LwM2mInstanceEnabler;
import org.eclipse.leshan.client.resource.ResourceChangedListener;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotatedLwM2mInstanceEnabler implements LwM2mInstanceEnabler {

	private static final Logger log = LoggerFactory.getLogger(AnnotatedLwM2mInstanceEnabler.class);

	private List<ResourceChangedListener> listeners = new ArrayList<>();

	protected Map<Integer, Method> readMethodMap = new HashMap<>();
	protected Map<Integer, Method> writeMethodMap = new HashMap<>();
	protected Map<Integer, Method> executeMethodMap = new HashMap<>();

	public AnnotatedLwM2mInstanceEnabler() {
		validateTypeAnnotation();
		initAnnotationCache();
	}

	protected void initAnnotationCache() {
		Method[] methods = getClass().getMethods();
		for (Method method : methods) {
			Resource[] resourceAnnotation = method.getAnnotationsByType(Resource.class);
			if (resourceAnnotation != null) {
				validateArgument(method);
				Resource annotation = resourceAnnotation[0];
				final int resourceId = annotation.resourceId();
				final Operation operation = annotation.operation();
				switch (operation) {
				case Read:
					validateReturnValue(method, ReadResponse.class);
					readMethodMap.put(resourceId, method);
					break;
				case Write:
					validateReturnValue(method, WriteResponse.class);
					writeMethodMap.put(resourceId, method);
					break;
				case Execute:
					validateReturnValue(method, ExecuteResponse.class);
					executeMethodMap.put(resourceId, method);
					break;
				}
			}
		}
	}

	protected void validateTypeAnnotation() {
		LWM2MObject[] lwm2mObjectAnnotation = getClass().getAnnotationsByType(LWM2MObject.class);
		if (lwm2mObjectAnnotation == null || lwm2mObjectAnnotation.length == 0) {
			throw new IllegalStateException("@LWM2MObject should be annotated to " + getClass().getSimpleName());
		}
	}

	protected void validateArgument(Method m) {
		Parameter[] parameters = m.getParameters();
		if (parameters == null || parameters.length != 0) {
			throw new IllegalStateException("Method:" + m.getName() + " should have 0 or 1 argument.");
		}
		if (parameters[0].getType().equals(ResourceContext.class) == false) {
			throw new IllegalStateException("Method:" + m.getName() + " has unsupported argument.");
		}
	}

	protected void validateReturnValue(Method m, Class<?> returnValueClass) {
		if (m.getReturnType().isAssignableFrom(returnValueClass) == false) {
			throw new IllegalStateException("Method:" + m.getName() + " has unsupported return value.");
		}
	}

	protected Object[] prepareArgument(Method m, ResourceContext rc) {
		int paramCount = m.getParameterCount();
		if (paramCount == 0) {
			return new Object[] {};
		}
		if (paramCount == 1) {
			return new Object[] { rc };
		}
		throw new IllegalStateException("invalid parameter count.method=" + m.getName());
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

	public ReadResponse read(ResourceContext resourceContext) {
		log.debug("read not found.resourceId:" + resourceContext.getResourceId());
		return ReadResponse.notFound();
	}

	public WriteResponse write(ResourceContext resourceContext) {
		log.debug("write not found.resourceId:" + resourceContext.getResourceId());
		return WriteResponse.notFound();
	}

	public ExecuteResponse execute(ResourceContext resourceContext) {
		log.debug("execute not found.resourceId:" + resourceContext.getResourceId());
		return ExecuteResponse.notFound();
	}

	@Override
	public final ReadResponse read(int resourceId) {
		final Method m = readMethodMap.get(resourceId);
		if (m == null) {
			return ReadResponse.notFound();
		}
		final Object[] params = prepareArgument(m, ResourceContext.ofReadContext(resourceId));
		try {
			log.debug("invoke read method.method:" + m.getName() + " resourceId:" + resourceId);
			return (ReadResponse) m.invoke(this, params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ReadResponse.internalServerError(e.getMessage());
		}
	}

	@Override
	public final WriteResponse write(int resourceId, LwM2mResource value) {
		final Method m = writeMethodMap.get(resourceId);
		if (m == null) {
			return WriteResponse.notFound();
		}
		final Object[] params = prepareArgument(m, ResourceContext.ofWriteContext(resourceId, value));
		try {
			log.debug("invoke write method.method:" + m.getName() + " resourceId:" + resourceId);
			return (WriteResponse) m.invoke(this, params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return WriteResponse.internalServerError(e.getMessage());
		}
	}

	@Override
	public final ExecuteResponse execute(int resourceId, String executeParams) {
		final Method m = executeMethodMap.get(resourceId);
		if (m == null) {
			return ExecuteResponse.notFound();
		}
		final Object[] params = prepareArgument(m, ResourceContext.ofExecuteContext(resourceId, executeParams));
		try {
			log.debug("invoke execute method.method:" + m.getName() + " resourceId:" + resourceId);
			return (ExecuteResponse) m.invoke(this, params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ExecuteResponse.internalServerError(e.getMessage());
		}
	}

	@Override
	public void reset(int resourceid) {
	}
}
