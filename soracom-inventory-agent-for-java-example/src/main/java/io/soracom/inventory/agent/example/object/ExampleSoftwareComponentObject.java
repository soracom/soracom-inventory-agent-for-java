package io.soracom.inventory.agent.example.object;

import java.io.File;

import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.soracom.inventory.agent.core.lwm2m.ResourceContext;
import io.soracom.inventory.agent.core.lwm2m.base_object.LWM2MSoftwareComponentObject;
import io.soracom.inventory.agent.core.util.CommandExecutor;
import io.soracom.inventory.agent.core.util.CommandExecutor.CommandProcess;

public class ExampleSoftwareComponentObject extends LWM2MSoftwareComponentObject {

	private static final Logger log = LoggerFactory.getLogger(ExampleSoftwareComponentObject.class);

	protected CommandProcess runtimeCommand;
	protected boolean activated;

	@Override
	public ReadResponse readComponentIdentity(ResourceContext resourceContext) {
		return ReadResponse.success(resourceContext.getResourceId(), "ping");
	}

	@Override
	public ReadResponse readComponentVersion(ResourceContext resourceContext) {
		return ReadResponse.success(resourceContext.getResourceId(), "v1.0");
	}

	@Override
	public ReadResponse readActivationState(ResourceContext resourceContext) {
		return ReadResponse.success(resourceContext.getResourceId(), activated);
	}

	@Override
	public ExecuteResponse executeActivate(ResourceContext resourceContext) {
		if (runtimeCommand != null) {
			runtimeCommand.killProcess();
		}
		File scriptFile = new File("script/SoftwareComponent-Activate.sh");
		String param = resourceContext.getExecuteParameter();
		if (param == null) {
			param = "";
		}
		log.info("executeActivation command=" + scriptFile.getName() + " param=" + param);
		runtimeCommand = CommandExecutor.execute(new String[] { scriptFile.getAbsolutePath(), param });
		activated = true;
		return ExecuteResponse.success();
	}

	@Override
	public ExecuteResponse executeDeactivate(ResourceContext resourceContext) {
		if (runtimeCommand != null) {
			runtimeCommand.killProcess();
		}
		activated = false;
		return ExecuteResponse.success();

	}
}
