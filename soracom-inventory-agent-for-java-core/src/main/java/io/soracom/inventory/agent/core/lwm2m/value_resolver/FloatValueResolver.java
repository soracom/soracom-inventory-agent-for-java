package io.soracom.inventory.agent.core.lwm2m.value_resolver;

import org.eclipse.leshan.core.response.ReadResponse;

import io.soracom.inventory.agent.core.lwm2m.ResourceContext;

public class FloatValueResolver implements ValueResolver<Float> {
	@Override
	public Float resolveWriteParameter(ResourceContext rc) {
		Number writerValue = (Number) rc.getWriteValue().getValue();
		if (writerValue == null) {
			return null;
		}
		return writerValue.floatValue();
	}

	@Override
	public ReadResponse toReadResponse(ResourceContext rc, Float returnValue) {
		return ReadResponse.success(rc.getResourceId(), returnValue);
	}
}