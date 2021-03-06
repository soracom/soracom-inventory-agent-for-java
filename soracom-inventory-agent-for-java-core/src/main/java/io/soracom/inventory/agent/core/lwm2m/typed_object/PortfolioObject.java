package io.soracom.inventory.agent.core.lwm2m.typed_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import java.util.Date;
import org.eclipse.leshan.core.node.ObjectLink;

/**
 * The Portfolio Object allows to extend the data storage capability of other Object Instances in the LwM2M system, as well as the services which may be used to authenticate and to protect privacy of data contained in those extensions. In addition, a service of data encryption is also defined
 **/
@LWM2MObject(objectId = 16, name = "Portfolio", multiple = true)
public abstract class PortfolioObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * Data Storage extension for other Object Instances. 
	 *  e.g  for [GSMA]  : 
	 * 0 : Host Device ID, 
	 * 1:  Host Device Manufacturer
	 * 2:  Host Device Model
	 * 3:  Host Device Software Version,
	 * 
	 * This Resource contains data that the GetAuthData executable Resource can work with.
	 **/
	@Resource(resourceId = 0, operation = Operation.Read, multiple = true)
	public abstract String readIdentity();
	@Resource(resourceId = 0, operation = Operation.Write, multiple = true)
	public abstract void writeIdentity(String writeValue);

	/**
	 * Executable resource to trigger Services described in Section 5.2.2 
	 * Arguments definitions are described in Section 5.2.1 as well as in table 2 of this document
	 **/
	@Resource(resourceId = 1, operation = Operation.Execute)
	public void executeGetAuthData(String executeParameter)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Buffer which contains the data generated by the  process triggered by a GetAuthData request
	 **/
	@Resource(resourceId = 2, operation = Operation.Read, multiple = true)
	public byte[] readAuthData()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * This Resource contains the state related to the process triggered by GetAuthData  request.
	 * 0 :  IDLE_STATE :  AuthData doesn’t contain any valid data
	 * 1 :  DATA_AVAIL_STATE : AuthData  contains a valid data 
	 * 2 :  ERROR_STATE :  an error occurred  
	 * This state is reset to IDLE_STATE, when the executable resource “GetAuthData” is triggered or when the AuthData resource has been returned to the LwM2M Server (READ / NOTIFY) .
	 **/
	@Resource(resourceId = 3, operation = Operation.Read)
	public Integer readAuthStatus()	{
		throw LwM2mInstanceResponseException.notFound();
	}
}
