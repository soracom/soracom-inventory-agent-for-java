package io.soracom.inventory.agent.core.lwm2m.value_resolver;

import static org.junit.Assert.assertEquals;

import org.eclipse.leshan.core.model.ResourceModel.Type;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ReadResponse;
import org.junit.Test;

import io.soracom.inventory.agent.core.lwm2m.ResourceContext;

public class ByteArrayValueResolverTest {

	private Type type = Type.OPAQUE;
	private Object value = new byte[] { 1 };
	private ValueResolver<byte[]> resolver = new ByteArrayValueResolver();

	@Test
	public void testResolveWriteParamater() {
		LwM2mResource resource = new MockLwM2mResource(type, value);
		ResourceContext rc = ResourceContext.ofWriteContext(0, resource);
		assertEquals(value, resolver.resolveWriteParameter(rc));
	}

	@Test
	public void testToReadResponse() {
		ResourceContext rc = ResourceContext.ofReadContext(0);
		ReadResponse readResponse = resolver.toReadResponse(rc, (byte[]) value);
		LwM2mResource resource = (LwM2mResource) readResponse.getContent();
		assertEquals(value, resource.getValue());
		assertEquals(type, resource.getType());
	}
}
