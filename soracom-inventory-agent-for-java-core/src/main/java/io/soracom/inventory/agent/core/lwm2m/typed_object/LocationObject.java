package io.soracom.inventory.agent.core.lwm2m.typed_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import java.util.Date;
import org.eclipse.leshan.core.node.ObjectLink;

/**
 * This LwM2M Objects provide a range of device related information which can be queried by the LwM2M Server, and a device reboot and factory reset function.
 **/
@LWM2MObject(objectId = 6, name = "Location")
public abstract class LocationObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * The decimal notation of latitude, e.g., -43.5723 [World Geodetic System 1984].
	 **/
	@Resource(resourceId = 0, operation = Operation.Read)
	public abstract Float readLatitude()	;

	/**
	 * The decimal notation of longitude, e.g., 153.21760 [World Geodetic System 1984].
	 **/
	@Resource(resourceId = 1, operation = Operation.Read)
	public abstract Float readLongitude()	;

	/**
	 * The decimal notation of altitude in meters above sea level.
	 **/
	@Resource(resourceId = 2, operation = Operation.Read)
	public Float readAltitude()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * The value in the Radius Resource indicates the size in meters of a circular area around a point of geometry.
	 **/
	@Resource(resourceId = 3, operation = Operation.Read)
	public Float readRadius()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * The velocity in the LwM2M Client is defined in [3GPP-TS_23.032].
	 **/
	@Resource(resourceId = 4, operation = Operation.Read)
	public byte[] readVelocity()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * The timestamp of when the location measurement was performed.
	 **/
	@Resource(resourceId = 5, operation = Operation.Read)
	public abstract java.util.Date readTimestamp()	;

	/**
	 * Speed is the time rate of change in position of a LwM2M Client without regard for direction: the scalar component of velocity.
	 **/
	@Resource(resourceId = 6, operation = Operation.Read)
	public Float readSpeed()	{
		throw LwM2mInstanceResponseException.notFound();
	}
}