package io.soracom.inventory.agent.core.lwm2m.value_resolver;

import org.eclipse.leshan.core.response.ReadResponse;

import io.soracom.inventory.agent.core.lwm2m.ResourceContext;

public class ByteArrayValueResolver implements ValueResolver<byte[]> {
	@Override
	public byte[] resolveWriteParameter(ResourceContext rc) {
		return (byte[]) rc.getWriteValue().getValue();
	}

	@Override
	public ReadResponse toReadResponse(ResourceContext rc, byte[] returnValue) {
		return ReadResponse.success(rc.getResourceId(), returnValue);
	}
}