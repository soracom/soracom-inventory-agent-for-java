package io.soracom.inventory.agent.core.lwm2m.value_resolver;

import static org.junit.Assert.assertEquals;

import org.eclipse.leshan.core.model.ResourceModel.Type;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ReadResponse;
import org.junit.Test;

import io.soracom.inventory.agent.core.lwm2m.ResourceContext;

public class IntegerValueResolverTest {

	private Type type = Type.INTEGER;
	private Object value = 1;
	private ValueResolver<Integer> resolver = new IntegerValueResolver();

	@Test
	public void testResolveWriteParamater() {
		LwM2mResource resource = new MockLwM2mResource(type, value);
		ResourceContext rc = ResourceContext.ofWriteContext(0, resource);
		assertEquals(value, resolver.resolveWriteParameter(rc));
	}

	@Test
	public void testToReadResponse() {
		ResourceContext rc = ResourceContext.ofReadContext(0);
		ReadResponse readResponse = resolver.toReadResponse(rc, (Integer) value);
		LwM2mResource resource = (LwM2mResource) readResponse.getContent();
		assertEquals(value, ((Long) resource.getValue()).intValue());
		assertEquals(type, resource.getType());
	}
}
