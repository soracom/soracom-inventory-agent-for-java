package io.soracom.inventory.agent.core.lwm2m.value_resolver;

import java.util.Date;

import org.eclipse.leshan.core.response.ReadResponse;

import io.soracom.inventory.agent.core.lwm2m.ResourceContext;

public class DateValueResolver implements ValueResolver<Date> {
	@Override
	public Date resolveWriteParameter(ResourceContext rc) {
		return (Date) rc.getWriteValue().getValue();
	}

	@Override
	public ReadResponse toReadResponse(ResourceContext rc, Date returnValue) {
		return ReadResponse.success(rc.getResourceId(), returnValue);
	}
}