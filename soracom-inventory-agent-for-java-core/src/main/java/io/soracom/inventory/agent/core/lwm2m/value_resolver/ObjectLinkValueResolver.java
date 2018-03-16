package io.soracom.inventory.agent.core.lwm2m.value_resolver;

import org.eclipse.leshan.core.node.ObjectLink;
import org.eclipse.leshan.core.response.ReadResponse;

import io.soracom.inventory.agent.core.lwm2m.ResourceContext;

public class ObjectLinkValueResolver implements ValueResolver<ObjectLink> {
	@Override
	public ObjectLink resolveWriteParameter(ResourceContext rc) {
		return (ObjectLink) rc.getWriteValue().getValue();
	}

	@Override
	public ReadResponse toReadResponse(ResourceContext rc, ObjectLink returnValue) {
		return ReadResponse.success(rc.getResourceId(), returnValue);
	}
}