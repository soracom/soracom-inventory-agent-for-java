package io.soracom.inventory.agent.example.object;
import io.soracom.inventory.agent.core.lwm2m.*;
import java.util.Date;
import org.eclipse.leshan.core.node.ObjectLink;

/**
 * Custom model
 **/
@LWM2MObject(objectId = 30000, name = "Custom Model")
public class CustomModelObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * Current location in X.
	 **/
	@Resource(resourceId = 0, operation = Operation.Read)
	public Float readCurrentX()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Target location in X.
	 **/
	@Resource(resourceId = 1, operation = Operation.Read)
	public Float readTargetX()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 1, operation = Operation.Write)
	public void writeTargetX(Float writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Execute command.
	 **/
	@Resource(resourceId = 2, operation = Operation.Execute)
	public void executeExecuteCommand(String executeParameter)	{
		throw LwM2mInstanceResponseException.notFound();
	}
}
