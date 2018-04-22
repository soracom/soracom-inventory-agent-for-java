package io.soracom.inventory.agent.core.lwm2m;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.eclipse.leshan.core.model.ResourceModel.Type;
import org.junit.Test;

import io.soracom.inventory.agent.core.lwm2m.value_resolver.MockLwM2mResource;

public class AnnotatedLwM2mInstanceEnablerTest {

	@LWM2MObject(objectId = 10, name = "Test")
	public static class TestAnnotatedLwM2minstanceEnabler extends AnnotatedLwM2mInstanceEnabler {
		private String value;
		private String executeParam;

		@Resource(resourceId = 0, operation = Operation.Write)
		public void writeString0(String writeValue) {
			this.value = writeValue;
		}

		@Resource(resourceId = 0, operation = Operation.Read)
		public String readString0() {
			return value;
		}

		@Resource(resourceId = 1, operation = Operation.Write)
		public void writeString1(String writeValue, ResourceContext rc) {
			this.value = writeValue;
		}

		@Resource(resourceId = 1, operation = Operation.Read)
		public String readString1(ResourceContext rc) {
			return value;
		}

		@Resource(resourceId = 2, operation = Operation.Execute)
		public void execute2(String executeParameter) {
		}

		@Resource(resourceId = 3, operation = Operation.Execute)
		public void execute3(String executeParameter, ResourceContext rc) {
		}
	}

	@Test
	public void testMetadata() {
		TestAnnotatedLwM2minstanceEnabler enabler = new TestAnnotatedLwM2minstanceEnabler();
		assertTrue(enabler.metaDataHolder.initialized);
		assertEquals(enabler.metaDataHolder.writeMethodMap.size(), 2);
		{
			Method method = enabler.metaDataHolder.writeMethodMap.get(0);
			assertEquals(method.getName(), "writeString0");
		}
		assertEquals(enabler.metaDataHolder.readMethodMap.size(), 2);
		{
			Method method = enabler.metaDataHolder.readMethodMap.get(1);
			assertEquals(method.getName(), "readString1");
		}
		assertEquals(enabler.metaDataHolder.executeMethodMap.size(), 2);
		{
			Method method = enabler.metaDataHolder.executeMethodMap.get(2);
			assertEquals(method.getName(), "execute2");
		}
		{
			Method method = enabler.metaDataHolder.executeMethodMap.get(3);
			assertEquals(method.getName(), "execute3");
		}
	}

	@Test
	public void testPrepareArgumentForRead() {
		{
			TestAnnotatedLwM2minstanceEnabler enabler = new TestAnnotatedLwM2minstanceEnabler();
			Method m = findMethod("readString0");
			ResourceContext rc = ResourceContext.ofReadContext(0);
			Object[] prepareArgument = enabler.prepareArgument(m, rc, Operation.Read);
			assertEquals(prepareArgument.length, 0);
		}
		{
			TestAnnotatedLwM2minstanceEnabler enabler = new TestAnnotatedLwM2minstanceEnabler();
			Method m = findMethod("readString1");
			ResourceContext rc = ResourceContext.ofReadContext(0);
			Object[] prepareArgument = enabler.prepareArgument(m, rc, Operation.Read);
			assertEquals(prepareArgument.length, 1);
			assertEquals(prepareArgument[0], rc);
		}
	}

	@Test
	public void testPrepareArgumentForWrite() {
		{
			TestAnnotatedLwM2minstanceEnabler enabler = new TestAnnotatedLwM2minstanceEnabler();
			Method m = findMethod("writeString0");
			ResourceContext rc = ResourceContext.ofWriteContext(0, new MockLwM2mResource(Type.STRING, "hoge"));
			Object[] prepareArgument = enabler.prepareArgument(m, rc, Operation.Write);
			assertEquals(prepareArgument.length, 1);
			assertEquals(prepareArgument[0], "hoge");
		}
		{
			TestAnnotatedLwM2minstanceEnabler enabler = new TestAnnotatedLwM2minstanceEnabler();
			Method m = findMethod("writeString1");
			ResourceContext rc = ResourceContext.ofWriteContext(0, new MockLwM2mResource(Type.STRING, "hoge"));
			Object[] prepareArgument = enabler.prepareArgument(m, rc, Operation.Write);
			assertEquals(prepareArgument.length, 2);
			assertEquals(prepareArgument[0], "hoge");
			assertEquals(prepareArgument[1], rc);
		}
	}

	@Test
	public void testPrepareArgumentForExecute() {
		{
			TestAnnotatedLwM2minstanceEnabler enabler = new TestAnnotatedLwM2minstanceEnabler();
			Method m = findMethod("execute2");
			ResourceContext rc = ResourceContext.ofExecuteContext(0, "hoge");
			Object[] prepareArgument = enabler.prepareArgument(m, rc, Operation.Execute);
			assertEquals(prepareArgument.length, 1);
			assertEquals(prepareArgument[0], "hoge");
		}
		{
			TestAnnotatedLwM2minstanceEnabler enabler = new TestAnnotatedLwM2minstanceEnabler();
			Method m = findMethod("execute3");
			ResourceContext rc = ResourceContext.ofExecuteContext(0, "hoge");
			Object[] prepareArgument = enabler.prepareArgument(m, rc, Operation.Execute);
			assertEquals(prepareArgument.length, 2);
			assertEquals(prepareArgument[0], "hoge");
			assertEquals(prepareArgument[1], rc);
		}
	}

	private Method findMethod(String name) {
		for (Method m : TestAnnotatedLwM2minstanceEnabler.class.getMethods()) {
			if (m.getName().equals(name)) {
				return m;
			}
		}
		throw new IllegalArgumentException(name);
	}
}
