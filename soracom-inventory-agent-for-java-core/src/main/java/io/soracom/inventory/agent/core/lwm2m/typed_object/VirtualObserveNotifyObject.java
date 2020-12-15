package io.soracom.inventory.agent.core.lwm2m.typed_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import java.util.Date;
import org.eclipse.leshan.core.node.ObjectLink;

/**
 * The Virtual Observe Notify Object provides a main function to notify multiple resources to a LwM2M Server in one notification message whether the resources are in one object/object instance or across multiple objects. The Virtual Observe Notify Object also provides some functions to implement more efficient multi-resource report.
 **/
@LWM2MObject(objectId = 22, name = "Virtual Observe Notify", multiple = true)
public abstract class VirtualObserveNotifyObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * Array of Core Links where each element includes a Resource ID or Object Instance ID to be observed and its observation criteria (lt, gt, st, pmin, pmax).
	 * 
	 * When the LwM2M Server write the resources or object instances to this Resource, the LwM2M Client starts to observe these resources.
	 * When the LwM2M Server write empty string to this Resource, the LwM2M Client stop to observe resources.
	 **/
	@Resource(resourceId = 0, operation = Operation.Read, multiple = true)
	public abstract String readObserveLinks();
	@Resource(resourceId = 0, operation = Operation.Write, multiple = true)
	public abstract void writeObserveLinks(String writeValue);

	/**
	 * This resource is populated by the LwM2M Client each time an observed resource from ObserveLinks meets its observation criteria, thereby resulting in a notification to a LwM2M Server that is observing Report resource. The type MUST be SenML JSON
	 **/
	@Resource(resourceId = 1, operation = Operation.Read)
	public abstract String readReport();

	/**
	 * Used by the LwM2M Server to indicate the LwM2M Client whether send all or changed resources. 
	 * 
	 * •false: report all Resources, 
	 * 
	 * •true: report only changed Resources.
	 * 
	 * When it is absent, the LwM2M Client shall report all Resources as default.
	 **/
	@Resource(resourceId = 2, operation = Operation.Read)
	public Boolean readResourceFilter()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 2, operation = Operation.Write)
	public void writeResourceFilter(Boolean writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Array of Core Links where each element includes the Resources ID to be included in the notification.
	 * 
	 * If ReportLinks Resource is absent, the notification sent to the LwM2M Server will only include the Resources in ObserveLinks  .
	 * 
	 * If the ReportLinks Resource is present, the notification sent to the LwM2M Server will only include the Resources that are listed in ReportLinks and not include the value of the Resource ObserveLinks.
	 * 
	 * To provide maximum flexibility to the server the Notifications will not comprise the union of resources in ObserveLinks and ReportLinks when both present.
	 **/
	@Resource(resourceId = 3, operation = Operation.Read, multiple = true)
	public String readReportLinks()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 3, operation = Operation.Write, multiple = true)
	public void writeReportLinks(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Used by the server to indicate the AND/OR logic relationship combination between or among resources included in ObserveLinks Resource.
	 * 
	 * •0: OR 
	 * 
	 * •1: AND 
	 * 
	 * •2: reserved value.
	 **/
	@Resource(resourceId = 4, operation = Operation.Read)
	public Integer readObserveRelation()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 4, operation = Operation.Write)
	public void writeObserveRelation(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}
}
