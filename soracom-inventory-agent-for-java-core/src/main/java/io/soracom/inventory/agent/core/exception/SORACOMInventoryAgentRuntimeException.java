package io.soracom.inventory.agent.core.exception;

@SuppressWarnings("serial")
public class SORACOMInventoryAgentRuntimeException extends RuntimeException {

	public SORACOMInventoryAgentRuntimeException(String message) {
		super(message);
	}

	public SORACOMInventoryAgentRuntimeException(Throwable cause) {
		super(cause);
	}

	public SORACOMInventoryAgentRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
}
