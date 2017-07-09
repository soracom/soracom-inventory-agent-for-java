package io.soracom.inventory.agent.core.lwm2m.base_object;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

import io.soracom.inventory.agent.core.lwm2m.AnnotatedLwM2mInstanceEnabler;
import io.soracom.inventory.agent.core.lwm2m.LWM2MObject;
import io.soracom.inventory.agent.core.lwm2m.Operation;
import io.soracom.inventory.agent.core.lwm2m.Resource;
import io.soracom.inventory.agent.core.lwm2m.ResourceContext;

/**
 * This LwM2M Objects enables client to collect statistical information and enables the LwM2M Server to retrieve these information, set the collection duration and reset the statistical parameters.
 **/
@LWM2MObject(objectId = 7, name = "Connectivity Statistics")
public abstract class ConnectivityStatisticsObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * Indicate the total number of SMS successfully transmitted during the collection period.
	 **/
	@Resource(resourceId = 0, operation = Operation.Read)
	public ReadResponse readSMSTxCounter(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 0, operation = Operation.Execute)
	public ExecuteResponse executeSMSTxCounter(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Indicate the total number of SMS successfully received during the collection period.
	 **/
	@Resource(resourceId = 1, operation = Operation.Read)
	public ReadResponse readSMSRxCounter(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 1, operation = Operation.Execute)
	public ExecuteResponse executeSMSRxCounter(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Indicate the total amount of data transmitted during the collection period.
	 **/
	@Resource(resourceId = 2, operation = Operation.Read)
	public ReadResponse readTxData(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 2, operation = Operation.Execute)
	public ExecuteResponse executeTxData(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Indicate the total amount of data received during the collection period.
	 **/
	@Resource(resourceId = 3, operation = Operation.Read)
	public ReadResponse readRxData(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 3, operation = Operation.Execute)
	public ExecuteResponse executeRxData(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * The maximum message size that is used during the collection period.
	 **/
	@Resource(resourceId = 4, operation = Operation.Read)
	public ReadResponse readMaxMessageSize(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 4, operation = Operation.Execute)
	public ExecuteResponse executeMaxMessageSize(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * The average message size that is used during the collection period.
	 **/
	@Resource(resourceId = 5, operation = Operation.Read)
	public ReadResponse readAverageMessageSize(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 5, operation = Operation.Execute)
	public ExecuteResponse executeAverageMessageSize(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Reset resources 0-5 to 0 and start to collect information, If resource 8 (Collection Period) value is 0, the client will keep collecting information until resource 7 (Stop) is executed, otherwise the client will stop collecting information after specified period ended.
	 **/

	/**
	 * Stop collecting information, but do not reset resources 0-5.
	 **/

	/**
	 * The default collection period in seconds. The value 0 indicates that the collection period is not set.
	 **/
	@Resource(resourceId = 8, operation = Operation.Read)
	public ReadResponse readCollectionPeriod(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 8, operation = Operation.Write)
	public WriteResponse writeCollectionPeriod(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 8, operation = Operation.Execute)
	public ExecuteResponse executeCollectionPeriod(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}
}
