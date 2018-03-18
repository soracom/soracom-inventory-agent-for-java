package io.soracom.inventory.agent.core.lwm2m.value_resolver;

import org.eclipse.leshan.core.response.ReadResponse;

import io.soracom.inventory.agent.core.lwm2m.ResourceContext;

public class DoubleValueResolver implements ValueResolver<Double> {
	@Override
	public Double resolveWriteParameter(ResourceContext rc) {
		Number writerValue = (Number) rc.getWriteValue().getValue();
		if (writerValue == null) {
			return null;
		}
		return writerValue.doubleValue();
	}

	@Override
	public ReadResponse toReadResponse(ResourceContext rc, Double returnValue) {
		return ReadResponse.success(rc.getResourceId(), returnValue);
	}
}