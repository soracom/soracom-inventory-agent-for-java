package io.soracom.inventory.agent.core.lwm2m.value_resolver;

import org.eclipse.leshan.core.response.ReadResponse;

import io.soracom.inventory.agent.core.lwm2m.ResourceContext;

public class LongValueResolver implements ValueResolver<Long> {
	@Override
	public Long resolveWriteParameter(ResourceContext rc) {
		Number writerValue = (Number) rc.getWriteValue().getValue();
		if (writerValue == null) {
			return null;
		}
		return writerValue.longValue();
	}

	@Override
	public ReadResponse toReadResponse(ResourceContext rc, Long returnValue) {
		return ReadResponse.success(rc.getResourceId(), returnValue);
	}

}