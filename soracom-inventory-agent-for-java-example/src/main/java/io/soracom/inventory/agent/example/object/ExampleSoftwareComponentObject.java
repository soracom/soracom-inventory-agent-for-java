package io.soracom.inventory.agent.example.object;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.soracom.inventory.agent.core.lwm2m.typed_object.LWM2MSoftwareComponentObject;
import io.soracom.inventory.agent.core.util.CommandExecutor;
import io.soracom.inventory.agent.core.util.CommandExecutor.CommandProcess;

public class ExampleSoftwareComponentObject extends LWM2MSoftwareComponentObject {

	private static final Logger log = LoggerFactory.getLogger(ExampleSoftwareComponentObject.class);

	protected CommandProcess runtimeCommand;
	protected boolean activated;

	@Override
	public String readComponentIdentity() {
		return "ping";
	}

	@Override
	public String readComponentVersion() {
		return "v1.0";
	}

	@Override
	public Boolean readActivationState() {
		return activated;
	}

	@Override
	public void executeActivate(String executeParameter) {
		if (runtimeCommand != null) {
			runtimeCommand.killProcess();
		}
		File scriptFile = new File("script/SoftwareComponent-Activate.sh");
		if (executeParameter == null) {
			executeParameter = "";
		}
		log.info("executeActivation command=" + scriptFile.getName() + " param=" + executeParameter);
		runtimeCommand = CommandExecutor.execute(new String[] { scriptFile.getAbsolutePath(), executeParameter });
		activated = true;
	}

	@Override
	public void executeDeactivate(String executeParameter) {
		if (runtimeCommand != null) {
			runtimeCommand.killProcess();
		}
		activated = false;
	}
}
