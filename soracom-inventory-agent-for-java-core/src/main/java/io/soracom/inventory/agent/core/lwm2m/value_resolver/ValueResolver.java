package io.soracom.inventory.agent.core.lwm2m.value_resolver;

import org.eclipse.leshan.core.response.ReadResponse;

import io.soracom.inventory.agent.core.lwm2m.ResourceContext;

public interface ValueResolver<T> {
	T resolveWriteParameter(ResourceContext rc);

	ReadResponse toReadResponse(ResourceContext rc, T returnValue);
}