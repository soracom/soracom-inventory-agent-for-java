package io.soracom.inventory.agent.core.lwm2m.value_resolver;

import org.eclipse.leshan.core.response.ReadResponse;

import io.soracom.inventory.agent.core.lwm2m.ResourceContext;

public class IntegerValueResolver implements ValueResolver<Integer> {
	@Override
	public Integer resolveWriteParameter(ResourceContext rc) {
		Number writerValue = (Number) rc.getWriteValue().getValue();
		if (writerValue == null) {
			return null;
		}
		return writerValue.intValue();
	}

	@Override
	public ReadResponse toReadResponse(ResourceContext rc, Integer returnValue) {
		return ReadResponse.success(rc.getResourceId(), returnValue);
	}
}