package io.soracom.inventory.agent.core.lwm2m.base_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import org.eclipse.leshan.core.response.*;

/**
 * This LwM2M Objects provide a range of device related information which can be queried by the LwM2M Server, and a device reboot and factory reset function.
 **/
@LWM2MObject(objectId = 6, name = "Location")
public abstract class LocationObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * The decimal notation of latitude, e.g., -43.5723 [World Geodetic System 1984].
	 **/
	@Resource(resourceId = 0, operation = Operation.Read, type = "FLOAT")
	public abstract ReadResponse readLatitude(ResourceContext resourceContext)	;

	/**
	 * The decimal notation of longitude, e.g., 153.21760 [World Geodetic System 1984].
	 **/
	@Resource(resourceId = 1, operation = Operation.Read, type = "FLOAT")
	public abstract ReadResponse readLongitude(ResourceContext resourceContext)	;

	/**
	 * The decimal notation of altitude in meters above sea level.
	 **/
	@Resource(resourceId = 2, operation = Operation.Read, type = "FLOAT")
	public ReadResponse readAltitude(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * The value in the Radius Resource indicates the size in meters of a circular area around a point of geometry.
	 **/
	@Resource(resourceId = 3, operation = Operation.Read, type = "FLOAT")
	public ReadResponse readRadius(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * The velocity in the LwM2M Client is defined in [3GPP-TS_23.032].
	 **/
	@Resource(resourceId = 4, operation = Operation.Read, type = "OPAQUE")
	public ReadResponse readVelocity(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * The timestamp of when the location measurement was performed.
	 **/
	@Resource(resourceId = 5, operation = Operation.Read, type = "TIME")
	public abstract ReadResponse readTimestamp(ResourceContext resourceContext)	;

	/**
	 * Speed is the time rate of change in position of a LwM2M Client without regard for direction: the scalar component of velocity.
	 **/
	@Resource(resourceId = 6, operation = Operation.Read, type = "FLOAT")
	public ReadResponse readSpeed(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
}
