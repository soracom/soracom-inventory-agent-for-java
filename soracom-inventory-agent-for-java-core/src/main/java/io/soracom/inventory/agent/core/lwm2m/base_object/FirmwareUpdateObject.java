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
 * This LwM2M Object enables management of firmware which is to be updated. This Object includes installing firmware package, updating firmware, and performing actions after updating firmware. The firmware update MAY require to reboot the device; it will depend on a number of factors, such as the operating system architecture and the extent of the updated software.
 * The envisioned functionality with LwM2M version 1.0 is to allow a LwM2M Client to connect to any LwM2M version 1.0 compliant Server to obtain a firmware imagine using the object and resource structure defined in this section experiencing communication security protection using DTLS. There are, however, other design decisions that need to be taken into account to allow a manufacturer of a device to securely install firmware on a device. Examples for such design decisions are how to manage the firmware update repository at the server side (which may include user interface considerations), the techniques to provide additional application layer security protection of the firmware image, how many versions of firmware imagines to store on the device, and how to execute the firmware update process considering the hardware specific details of a given IoT hardware product. These aspects are considered to be outside the scope of the LwM2M version 1.0 specification.
 * A LwM2M Server may also instruct a LwM2M Client to fetch a firmware image from a dedicated server (instead of pushing firmware imagines to the LwM2M Client). The Package URI resource is contained in the Firmware object and can be used for this purpose.
 * A LwM2M Client MUST support block-wise transfer [CoAP_Blockwise] if it implements the Firmware Update object.
 * A LwM2M Server MUST support block-wise transfer. Other protocols, such as HTTP/HTTPs, MAY also be used for downloading firmware updates (via the Package URI resource). For constrained devices it is, however, RECOMMENDED to use CoAP for firmware downloads to avoid the need for additional protocol implementations.
 **/
@LWM2MObject(objectId = 5, name = "Firmware Update")
public abstract class FirmwareUpdateObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * Firmware package
	 **/
	@Resource(resourceId = 0, operation = Operation.Write)
	public WriteResponse writePackage(ResourceContext resourceContext){
		return super.write(resourceContext);
	}

	/**
	 * URI from where the device can download the firmware package by an alternative mechanism. As soon the device has received the Package URI it performs the download at the next practical opportunity. 
	 * The URI format is defined in RFC 3986. For example, coaps://example.org/firmware is a syntactically valid URI. The URI scheme determines the protocol to be used. For CoAP this endpoint MAY be a LwM2M Server but does not necessarily need to be. A CoAP server implementing block-wise transfer is sufficient as a server hosting a firmware repository and the expectation is that this server merely serves as a separate file server making firmware images available to LwM2M Clients.
	 **/
	@Resource(resourceId = 1, operation = Operation.Read)
	public ReadResponse readPackageURI(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 1, operation = Operation.Write)
	public WriteResponse writePackageURI(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 1, operation = Operation.Execute)
	public ExecuteResponse executePackageURI(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Updates firmware by using the firmware package stored in Package, or, by using the firmware downloaded from the Package URI.
	 * This Resource is only executable when the value of the State Resource is Downloaded.
	 **/

	/**
	 * Indicates current state with respect to this firmware update. This value is set by the LwM2M Client.
	 * 0: Idle (before downloading or after successful updating)
	 * 1: Downloading (The data sequence is on the way)
	 * 2: Downloaded
	 * 3: Updating
	 * If writing the firmware package to Package Resource is done, or, if the device has downloaded the firmware package from the Package URI the state changes to Downloaded.
	 * Writing an empty string to Package Resource or to Package URI Resource, resets the Firmware Update State Machine: the State Resource value is set to Idle and the Update Result Resource value is set to 0.
	 * When in Downloaded state, and the executable Resource Update is triggered, the state changes to Updating.
	 * If the Update Resource failed, the state returns at Downloaded.
	 * If performing the Update Resource was successful, the state changes from Updating to Idle. 
	 * Firmware Update mechanisms are illustrated below in Figure 29 of the LwM2M version 1.0 specification.
	 **/
	@Resource(resourceId = 3, operation = Operation.Read)
	public ReadResponse readState(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 3, operation = Operation.Execute)
	public ExecuteResponse executeState(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Contains the result of downloading or updating the firmware
	 * 0: Initial value. Once the updating process is initiated (Download /Update), this Resource MUST be reset to Initial value.
	 * 1: Firmware updated successfully,
	 * 2: Not enough flash memory for the new firmware package.
	 * 3. Out of RAM during downloading process.
	 * 4: Connection lost during downloading process.
	 * 5: Integrity check failure for new downloaded package.
	 * 6: Unsupported package type.
	 * 7: Invalid URI
	 * 8: Firmware update failed
	 * 9: Unsupported protocol. A LwM2M client indicates the failure to retrieve the firmware imagine using the URI provided in the Package URI resource by writing the value 9 to the /5/0/5 (Update Result resource) when the URI contained a URI scheme unsupported by the client. Consequently, the LwM2M Client is unable to retrieve the firmware image using the URI provided by the LwM2M Server in the Package URI when it refers to an unsupported protocol.
	 **/
	@Resource(resourceId = 5, operation = Operation.Read)
	public ReadResponse readUpdateResult(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 5, operation = Operation.Execute)
	public ExecuteResponse executeUpdateResult(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Name of the Firmware Package
	 **/
	@Resource(resourceId = 6, operation = Operation.Read)
	public ReadResponse readPkgName(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 6, operation = Operation.Execute)
	public ExecuteResponse executePkgName(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Version of the Firmware package
	 **/
	@Resource(resourceId = 7, operation = Operation.Read)
	public ReadResponse readPkgVersion(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 7, operation = Operation.Execute)
	public ExecuteResponse executePkgVersion(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * This resource indicates what protocols the LwM2M Client implements to retrieve firmware images. The LwM2M server uses this information to decide what URI to include in the Package URI. A LwM2M Server MUST NOT include a URI in the Package URI object that uses a protocol that is unsupported by the LwM2M client.
	 * For example, if a LwM2M client indicates that it supports CoAP and CoAPS then a LwM2M Server must not provide an HTTP URI in the Packet URI.
	 * The following values are defined by this version of the specification:
	 * 0 – CoAP (as defined in RFC 7252) with the additional support for block-wise transfer. CoAP is the default setting.
	 * 1 – CoAPS (as defined in RFC 7252) with the additional support for block-wise transfer
	 * 2 – HTTP 1.1 (as defined in RFC 7230)
	 * 3 – HTTPS 1.1 (as defined in RFC 7230)
	 * Additional values MAY be defined in the future. Any value not understood by the LwM2M Server MUST be ignored.
	 **/
	@Resource(resourceId = 8, operation = Operation.Read, multiple = true)
	public ReadResponse readFirmwareUpdateProtocolSupport(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 8, operation = Operation.Execute, multiple = true)
	public ExecuteResponse executeFirmwareUpdateProtocolSupport(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * The LwM2M Client uses this resource to indicate its support for transferring firmware images to the client either via the Package Resource (=push) or via the Package URI Resource (=pull) mechanism.
	 * 0 – Pull only
	 * 1 – Push only
	 * 2 – Both. In this case the LwM2M Server MAY choose the preferred mechanism for conveying the firmware image to the LwM2M Client.
	 **/
	@Resource(resourceId = 9, operation = Operation.Read)
	public ReadResponse readFirmwareUpdateDeliveryMethod(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 9, operation = Operation.Execute)
	public ExecuteResponse executeFirmwareUpdateDeliveryMethod(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}
}
