package io.soracom.inventory.agent.core.lwm2m.value_resolver;

import org.eclipse.leshan.core.response.ReadResponse;

import io.soracom.inventory.agent.core.lwm2m.ResourceContext;

public class BooleanValueResolver implements ValueResolver<Boolean> {
	@Override
	public Boolean resolveWriteParameter(ResourceContext rc) {
		return (Boolean) rc.getWriteValue().getValue();
	}

	@Override
	public ReadResponse toReadResponse(ResourceContext rc, Boolean returnValue) {
		return ReadResponse.success(rc.getResourceId(), returnValue);
	}
}