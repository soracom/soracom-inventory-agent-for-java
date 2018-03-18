package io.soracom.inventory.agent.example.object;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.soracom.inventory.agent.core.lwm2m.AnnotatedLwM2mInstanceEnabler;
import io.soracom.inventory.agent.core.lwm2m.LWM2MObject;
import io.soracom.inventory.agent.core.lwm2m.Operation;
import io.soracom.inventory.agent.core.lwm2m.Resource;

/**
 * Custom model
 **/
@LWM2MObject(objectId = 30000, name = "Custom Model")
public class CustomModelObject extends AnnotatedLwM2mInstanceEnabler {

	private Logger log = LoggerFactory.getLogger(CustomModelObject.class);

	private float currentX = 10;
	private float targetX = 10;

	/**
	 * Current location in X.
	 **/
	@Resource(resourceId = 0, operation = Operation.Read)
	public Float readCurrentX() {
		log.info("readCurrentX currentX=" + currentX);
		return currentX;
	}

	/**
	 * Target location in X.
	 **/
	@Resource(resourceId = 1, operation = Operation.Read)
	public Float readTargetX() {
		log.info("readTargetX targetX=" + targetX);
		return targetX;
	}

	@Resource(resourceId = 1, operation = Operation.Write)
	public void writeTargetX(Float writeValue) {
		log.info("writeTargetX targetX=" + writeValue);
		this.targetX = writeValue;
	}

	/**
	 * Execute command.
	 **/
	@Resource(resourceId = 2, operation = Operation.Execute)
	public void executeExecuteCommand(String executeParameter) {
		log.info("executeExecuteCommand param=" + executeParameter);
	}
}
