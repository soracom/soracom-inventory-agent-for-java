package io.soracom.inventory.agent.core.lwm2m.value_resolver;

import java.util.Map;

import org.eclipse.leshan.core.model.ResourceModel.Type;
import org.eclipse.leshan.core.node.LwM2mNodeVisitor;
import org.eclipse.leshan.core.node.LwM2mResource;

public class MockLwM2mResource implements LwM2mResource {

	private Type type;
	private Object value;

	public MockLwM2mResource(Type type, Object value) {
		this.type = type;
		this.value = value;
	}

	@Override
	public int getId() {
		return 0;
	}

	@Override
	public void accept(LwM2mNodeVisitor visitor) {

	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public boolean isMultiInstances() {
		return false;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public Map<Integer, ?> getValues() {
		return null;
	}

	@Override
	public Object getValue(int id) {
		return null;
	}

}
