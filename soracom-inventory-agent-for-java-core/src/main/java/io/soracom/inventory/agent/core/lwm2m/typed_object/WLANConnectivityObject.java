package io.soracom.inventory.agent.core.lwm2m.typed_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import java.util.Date;
import org.eclipse.leshan.core.node.ObjectLink;

/**
 * This object specifies resources to enable a device to connect to a WLAN bearer.
 **/
@LWM2MObject(objectId = 12, name = "WLAN connectivity", multiple = true)
public abstract class WLANConnectivityObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * Human-readable identifier
	 * eg. wlan0
	 **/
	@Resource(resourceId = 0, operation = Operation.Read)
	public abstract String readInterfaceName()	;
	@Resource(resourceId = 0, operation = Operation.Write)
	public abstract void writeInterfaceName(String writeValue)	;

	/**
	 * 0: Disabled
	 * 1: Enabled
	 * Enable / Disable interface
	 * When disabled radio must also be disabled
	 **/
	@Resource(resourceId = 1, operation = Operation.Read)
	public abstract Boolean readEnable()	;
	@Resource(resourceId = 1, operation = Operation.Write)
	public abstract void writeEnable(Boolean writeValue)	;

	/**
	 * 0: Disabled
	 * 1: 2.4 GHz
	 * 2: 5 GHz
	 * 3: 0.9 GHz
	 * 4: 3.7 GHz
	 * 5: 45 GHz
	 * 6: 60 GHz
	 **/
	@Resource(resourceId = 2, operation = Operation.Read)
	public Integer readRadioEnabled()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 2, operation = Operation.Write)
	public void writeRadioEnabled(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * 0: Disabled
	 * 1: UP (OK)
	 * 2: Error
	 **/
	@Resource(resourceId = 3, operation = Operation.Read)
	public abstract Integer readStatus()	;

	/**
	 * The MAC address of the interface, in hexadecimal form.
	 **/
	@Resource(resourceId = 4, operation = Operation.Read)
	public abstract String readBSSID()	;

	/**
	 * The Service Set Identifier for this interface.
	 **/
	@Resource(resourceId = 5, operation = Operation.Read)
	public abstract String readSSID()	;
	@Resource(resourceId = 5, operation = Operation.Write)
	public abstract void writeSSID(String writeValue)	;

	/**
	 * 0: Do not broadcast SSID
	 * 1: Broadcast SSID
	 **/
	@Resource(resourceId = 6, operation = Operation.Read)
	public Boolean readBroadcastSSID()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 6, operation = Operation.Write)
	public void writeBroadcastSSID(Boolean writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * 0: Do not broadcast beacons
	 * 1: Broadcast beacons
	 **/
	@Resource(resourceId = 7, operation = Operation.Read)
	public Boolean readBeaconEnabled()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 7, operation = Operation.Write)
	public void writeBeaconEnabled(Boolean writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * 0: Access Point
	 * 1: Client (Station)
	 * 2: Bridge
	 * 3: Repeater
	 **/
	@Resource(resourceId = 8, operation = Operation.Read)
	public abstract Integer readMode()	;
	@Resource(resourceId = 8, operation = Operation.Write)
	public abstract void writeMode(Integer writeValue)	;

	/**
	 * The current radio channel in use by this interface.
	 * 0: Disabled
	 * 1: Enabled
	 **/
	@Resource(resourceId = 9, operation = Operation.Read)
	public abstract Integer readChannel()	;
	@Resource(resourceId = 9, operation = Operation.Write)
	public abstract void writeChannel(Integer writeValue)	;

	/**
	 * 0: Disabled
	 * 1: Enabled
	 **/
	@Resource(resourceId = 10, operation = Operation.Read)
	public Boolean readAutoChannel()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 10, operation = Operation.Write)
	public void writeAutoChannel(Boolean writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Array of supported radio channels.
	 **/
	@Resource(resourceId = 11, operation = Operation.Read, multiple = true)
	public Integer readSupportedChannels()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 11, operation = Operation.Write, multiple = true)
	public void writeSupportedChannels(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Array of channels which the access point has determined are ‘in use’.
	 * Including any channels in-use by access point itself.
	 **/
	@Resource(resourceId = 12, operation = Operation.Read, multiple = true)
	public Integer readChannelsInUse()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 12, operation = Operation.Write, multiple = true)
	public void writeChannelsInUse(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * 802.11d Regulatory Domain String. 
	 * First two octets are ISO/IEC 3166-1 two-character country code. 
	 * The third octet is either “ ” (all environments), “O” (outside) or “I” (inside).
	 **/
	@Resource(resourceId = 13, operation = Operation.Read)
	public String readRegulatoryDomain()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 13, operation = Operation.Write)
	public void writeRegulatoryDomain(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * 0: 802.11a
	 * 1: 802.11b
	 * 2: 802.11bg 
	 * 3: 802.11g
	 * 4: 802.11n
	 * 5: 802.11bgn
	 * 6: 802.11ac
	 * 7: 802.11ah
	 **/
	@Resource(resourceId = 14, operation = Operation.Read)
	public abstract Integer readStandard()	;
	@Resource(resourceId = 14, operation = Operation.Write)
	public abstract void writeStandard(Integer writeValue)	;

	/**
	 * 0: None (Open)
	 * 1: PSK
	 * 2: EAP
	 * 3: EAP+PSK
	 * 4: EAPSIM
	 **/
	@Resource(resourceId = 15, operation = Operation.Read)
	public abstract Integer readAuthenticationMode()	;
	@Resource(resourceId = 15, operation = Operation.Write)
	public abstract void writeAuthenticationMode(Integer writeValue)	;

	/**
	 * 0: AES (WPA2)
	 * 1: TKIP (WPA)
	 * 2: WEP (1)
	 **/
	@Resource(resourceId = 16, operation = Operation.Read)
	public Integer readEncryptionMode()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 16, operation = Operation.Write)
	public void writeEncryptionMode(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * WPA/WPA2 Key expressed as a hex string.
	 * Write – Only.
	 **/
	@Resource(resourceId = 17, operation = Operation.Write)
	public void writeWPAPreSharedKey(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * WPA/WPA2 Key Phrase.
	 * Write Only.
	 **/
	@Resource(resourceId = 18, operation = Operation.Write)
	public void writeWPAKeyPhrase(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Description.0: None
	 * 1: 40-bit
	 * 2: 104-bit
	 **/
	@Resource(resourceId = 19, operation = Operation.Read)
	public Integer readWEPEncryptionType()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 19, operation = Operation.Write)
	public void writeWEPEncryptionType(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Index of the default WEP key.
	 **/
	@Resource(resourceId = 20, operation = Operation.Read)
	public Integer readWEPKeyIndex()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 20, operation = Operation.Write)
	public void writeWEPKeyIndex(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * WPA/WPA2 Key Phrase.
	 * Write Only.
	 **/
	@Resource(resourceId = 21, operation = Operation.Write)
	public void writeWEPKeyPhrase(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * WEP Key 1 expressed as a hexadecimal string.
	 * 10 Bytes for a 40 Bit key
	 * 26 Bytes for a 104 Bit key
	 **/
	@Resource(resourceId = 22, operation = Operation.Write)
	public void writeWEPKey1(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * WEP Key 2 expressed as a hexadecimal string.
	 * 10 Bytes for a 40 Bit key
	 * 26 Bytes for a 104 Bit key
	 **/
	@Resource(resourceId = 23, operation = Operation.Write)
	public void writeWEPKey2(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * WEP Key 3 expressed as a hexadecimal string.
	 * 10 Bytes for a 40 Bit key
	 * 26 Bytes for a 104 Bit key
	 **/
	@Resource(resourceId = 24, operation = Operation.Write)
	public void writeWEPKey3(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * WEP Key 4 expressed as a hexadecimal string.
	 * 10 Bytes for a 40 Bit key
	 * 26 Bytes for a 104 Bit key
	 **/
	@Resource(resourceId = 25, operation = Operation.Write)
	public void writeWEPKey4(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * RADIUS Authentication Server Address
	 **/
	@Resource(resourceId = 26, operation = Operation.Read)
	public String readRADIUSServer()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 26, operation = Operation.Write)
	public void writeRADIUSServer(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * RADIUS Authentication Server Port Number
	 **/
	@Resource(resourceId = 27, operation = Operation.Read)
	public Integer readRADIUSServerPort()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 27, operation = Operation.Write)
	public void writeRADIUSServerPort(Integer writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * RADIUS Shared Secret
	 **/
	@Resource(resourceId = 28, operation = Operation.Write)
	public void writeRADIUSSecret(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * 0: WMM NOT Supported
	 * 1: WMM Wupported
	 **/
	@Resource(resourceId = 29, operation = Operation.Read)
	public Boolean readWMMSupported()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * 0: Disabled
	 * 1: Enabled
	 **/
	@Resource(resourceId = 30, operation = Operation.Read)
	public Boolean readWMMEnabled()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 30, operation = Operation.Write)
	public void writeWMMEnabled(Boolean writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * 0: Any Client MAC Address accepted
	 * 1: Client MAC address must exist in MACAddressList
	 **/
	@Resource(resourceId = 31, operation = Operation.Read)
	public Boolean readMACControlEnabled()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 31, operation = Operation.Write)
	public void writeMACControlEnabled(Boolean writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Array of allowed client MAC addresses, in hexadecimal form.
	 **/
	@Resource(resourceId = 32, operation = Operation.Read, multiple = true)
	public String readMACAddressList()	{
		throw LwM2mInstanceResponseException.notFound();
	}
	@Resource(resourceId = 32, operation = Operation.Write, multiple = true)
	public void writeMACAddressList(String writeValue)	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Total number of bytes sent via this interface
	 **/
	@Resource(resourceId = 33, operation = Operation.Read)
	public Integer readTotalBytesSent()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Total number of bytes received via this interface
	 **/
	@Resource(resourceId = 34, operation = Operation.Read)
	public Integer readTotalBytesReceived()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Total packets sent via this interface
	 **/
	@Resource(resourceId = 35, operation = Operation.Read)
	public Integer readTotalPacketsSent()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Total packets received via this interface
	 **/
	@Resource(resourceId = 36, operation = Operation.Read)
	public Integer readTotalPacketsReceived()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Total number of packets which could not be transmitted because of errors.
	 **/
	@Resource(resourceId = 37, operation = Operation.Read)
	public Integer readTransmitErrors()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Total number of packets received with errors which prevented those packets from being delivered.
	 **/
	@Resource(resourceId = 38, operation = Operation.Read)
	public Integer readReceiveErrors()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Count of Unicast Packets Sent
	 **/
	@Resource(resourceId = 39, operation = Operation.Read)
	public Integer readUnicastPacketsSent()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Count of Unicast Packets Received
	 **/
	@Resource(resourceId = 40, operation = Operation.Read)
	public Integer readUnicastPacketsReceived()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Count of Multicast Packets Sent
	 **/
	@Resource(resourceId = 41, operation = Operation.Read)
	public Integer readMulticastPacketsSent()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Count of Multicast Packets Received
	 **/
	@Resource(resourceId = 42, operation = Operation.Read)
	public Integer readMulticastPacketsReceived()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Count of Broadcast Packets Sent
	 **/
	@Resource(resourceId = 43, operation = Operation.Read)
	public Integer readBroadcastPacketsSent()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Count of Broadcast Packets Received
	 **/
	@Resource(resourceId = 44, operation = Operation.Read)
	public Integer read44BroadcastPacketsReceived()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Count of valid outbound packets intentionally discarded without transmission, for example a packet may be discarded to manage buffer space.
	 **/
	@Resource(resourceId = 45, operation = Operation.Read)
	public Integer readDiscardPacketsSent()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Count of valid packets received and intentionally discarded without delivery to the system, for example a packet may be discarded to manage buffer space.
	 **/
	@Resource(resourceId = 46, operation = Operation.Read)
	public Integer readDiscardPacketsReceived()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Count of Unknown Packets Received
	 **/
	@Resource(resourceId = 47, operation = Operation.Read)
	public Integer readUnknownPacketsReceived()	{
		throw LwM2mInstanceResponseException.notFound();
	}

	/**
	 * Links to a vendor specific object.
	 **/
	@Resource(resourceId = 48, operation = Operation.Read)
	public ObjectLink readVendorSpecificExtensions()	{
		throw LwM2mInstanceResponseException.notFound();
	}
}
