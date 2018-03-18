package io.soracom.inventory.agent.core.lwm2m.value_resolver;

import org.eclipse.leshan.core.response.ReadResponse;

import io.soracom.inventory.agent.core.lwm2m.ResourceContext;

public class StringValueResolver implements ValueResolver<String> {
	@Override
	public String resolveWriteParameter(ResourceContext rc) {
		return (String) rc.getWriteValue().getValue();
	}

	@Override
	public ReadResponse toReadResponse(ResourceContext rc, String returnValue) {
		return ReadResponse.success(rc.getResourceId(), returnValue);
	}
}