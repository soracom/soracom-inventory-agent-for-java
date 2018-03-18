package io.soracom.inventory.agent.core.lwm2m.base_object;
import io.soracom.inventory.agent.core.lwm2m.*;
import org.eclipse.leshan.core.response.*;

/**
 * This object specifies resources to enable a device to connect to a WLAN bearer.
 **/
//typed_object package is recommended.
@Deprecated
@LWM2MObject(objectId = 12, name = "WLAN connectivity", multiple = true)
public abstract class WLANConnectivityObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * Human-readable identifier
	 * eg. wlan0
	 **/
	@Resource(resourceId = 0, operation = Operation.Read, type = "STRING")
	public abstract ReadResponse readInterfaceName(ResourceContext resourceContext)	;
	@Resource(resourceId = 0, operation = Operation.Write)
	public abstract WriteResponse writeInterfaceName(ResourceContext resourceContext)	;

	/**
	 * 0: Disabled
	 * 1: Enabled
	 * Enable / Disable interface
	 * When disabled radio must also be disabled
	 **/
	@Resource(resourceId = 1, operation = Operation.Read, type = "BOOLEAN")
	public abstract ReadResponse readEnable(ResourceContext resourceContext)	;
	@Resource(resourceId = 1, operation = Operation.Write)
	public abstract WriteResponse writeEnable(ResourceContext resourceContext)	;

	/**
	 * 0: Disabled
	 * 1: 2.4 GHz
	 * 2: 5 GHz
	 * 3: 0.9 GHz
	 * 4: 3.7 GHz
	 * 5: 45 GHz
	 * 6: 60 GHz
	 **/
	@Resource(resourceId = 2, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readRadioEnabled(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 2, operation = Operation.Write)
	public WriteResponse writeRadioEnabled(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * 0: Disabled
	 * 1: UP (OK)
	 * 2: Error
	 **/
	@Resource(resourceId = 3, operation = Operation.Read, type = "INTEGER")
	public abstract ReadResponse readStatus(ResourceContext resourceContext)	;

	/**
	 * The MAC address of the interface, in hexadecimal form.
	 **/
	@Resource(resourceId = 4, operation = Operation.Read, type = "STRING")
	public abstract ReadResponse readBSSID(ResourceContext resourceContext)	;

	/**
	 * The Service Set Identifier for this interface.
	 **/
	@Resource(resourceId = 5, operation = Operation.Read, type = "STRING")
	public abstract ReadResponse readSSID(ResourceContext resourceContext)	;
	@Resource(resourceId = 5, operation = Operation.Write)
	public abstract WriteResponse writeSSID(ResourceContext resourceContext)	;

	/**
	 * 0: Do not broadcast SSID
	 * 1: Broadcast SSID
	 **/
	@Resource(resourceId = 6, operation = Operation.Read, type = "BOOLEAN")
	public ReadResponse readBroadcastSSID(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 6, operation = Operation.Write)
	public WriteResponse writeBroadcastSSID(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * 0: Do not broadcast beacons
	 * 1: Broadcast beacons
	 **/
	@Resource(resourceId = 7, operation = Operation.Read, type = "BOOLEAN")
	public ReadResponse readBeaconEnabled(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 7, operation = Operation.Write)
	public WriteResponse writeBeaconEnabled(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * 0: Access Point
	 * 1: Client (Station)
	 * 2: Bridge
	 * 3: Repeater
	 **/
	@Resource(resourceId = 8, operation = Operation.Read, type = "INTEGER")
	public abstract ReadResponse readMode(ResourceContext resourceContext)	;
	@Resource(resourceId = 8, operation = Operation.Write)
	public abstract WriteResponse writeMode(ResourceContext resourceContext)	;

	/**
	 * The current radio channel in use by this interface.
	 * 0: Disabled
	 * 1: Enabled
	 **/
	@Resource(resourceId = 9, operation = Operation.Read, type = "INTEGER")
	public abstract ReadResponse readChannel(ResourceContext resourceContext)	;
	@Resource(resourceId = 9, operation = Operation.Write)
	public abstract WriteResponse writeChannel(ResourceContext resourceContext)	;

	/**
	 * 0: Disabled
	 * 1: Enabled
	 **/
	@Resource(resourceId = 10, operation = Operation.Read, type = "BOOLEAN")
	public ReadResponse readAutoChannel(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 10, operation = Operation.Write)
	public WriteResponse writeAutoChannel(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Array of supported radio channels.
	 **/
	@Resource(resourceId = 11, operation = Operation.Read, multiple = true, type = "INTEGER")
	public ReadResponse readSupportedChannels(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 11, operation = Operation.Write, multiple = true)
	public WriteResponse writeSupportedChannels(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Array of channels which the access point has determined are ‘in use’.
	 * Including any channels in-use by access point itself.
	 **/
	@Resource(resourceId = 12, operation = Operation.Read, multiple = true, type = "INTEGER")
	public ReadResponse readChannelsInUse(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 12, operation = Operation.Write, multiple = true)
	public WriteResponse writeChannelsInUse(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * 802.11d Regulatory Domain String. 
	 * First two octets are ISO/IEC 3166-1 two-character country code. 
	 * The third octet is either “ ” (all environments), “O” (outside) or “I” (inside).
	 **/
	@Resource(resourceId = 13, operation = Operation.Read, type = "STRING")
	public ReadResponse readRegulatoryDomain(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 13, operation = Operation.Write)
	public WriteResponse writeRegulatoryDomain(ResourceContext resourceContext)	{
		return super.write(resourceContext);
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
	@Resource(resourceId = 14, operation = Operation.Read, type = "INTEGER")
	public abstract ReadResponse readStandard(ResourceContext resourceContext)	;
	@Resource(resourceId = 14, operation = Operation.Write)
	public abstract WriteResponse writeStandard(ResourceContext resourceContext)	;

	/**
	 * 0: None (Open)
	 * 1: PSK
	 * 2: EAP
	 * 3: EAP+PSK
	 * 4: EAPSIM
	 **/
	@Resource(resourceId = 15, operation = Operation.Read, type = "INTEGER")
	public abstract ReadResponse readAuthenticationMode(ResourceContext resourceContext)	;
	@Resource(resourceId = 15, operation = Operation.Write)
	public abstract WriteResponse writeAuthenticationMode(ResourceContext resourceContext)	;

	/**
	 * 0: AES (WPA2)
	 * 1: TKIP (WPA)
	 * 2: WEP (1)
	 **/
	@Resource(resourceId = 16, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readEncryptionMode(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 16, operation = Operation.Write)
	public WriteResponse writeEncryptionMode(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * WPA/WPA2 Key expressed as a hex string.
	 * Write – Only.
	 **/
	@Resource(resourceId = 17, operation = Operation.Write)
	public WriteResponse writeWPAPreSharedKey(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * WPA/WPA2 Key Phrase.
	 * Write Only.
	 **/
	@Resource(resourceId = 18, operation = Operation.Write)
	public WriteResponse writeWPAKeyPhrase(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Description.0: None
	 * 1: 40-bit
	 * 2: 104-bit
	 **/
	@Resource(resourceId = 19, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readWEPEncryptionType(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 19, operation = Operation.Write)
	public WriteResponse writeWEPEncryptionType(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Index of the default WEP key.
	 **/
	@Resource(resourceId = 20, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readWEPKeyIndex(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 20, operation = Operation.Write)
	public WriteResponse writeWEPKeyIndex(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * WPA/WPA2 Key Phrase.
	 * Write Only.
	 **/
	@Resource(resourceId = 21, operation = Operation.Write)
	public WriteResponse writeWEPKeyPhrase(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * WEP Key 1 expressed as a hexadecimal string.
	 * 10 Bytes for a 40 Bit key
	 * 26 Bytes for a 104 Bit key
	 **/
	@Resource(resourceId = 22, operation = Operation.Write)
	public WriteResponse writeWEPKey1(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * WEP Key 2 expressed as a hexadecimal string.
	 * 10 Bytes for a 40 Bit key
	 * 26 Bytes for a 104 Bit key
	 **/
	@Resource(resourceId = 23, operation = Operation.Write)
	public WriteResponse writeWEPKey2(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * WEP Key 3 expressed as a hexadecimal string.
	 * 10 Bytes for a 40 Bit key
	 * 26 Bytes for a 104 Bit key
	 **/
	@Resource(resourceId = 24, operation = Operation.Write)
	public WriteResponse writeWEPKey3(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * WEP Key 4 expressed as a hexadecimal string.
	 * 10 Bytes for a 40 Bit key
	 * 26 Bytes for a 104 Bit key
	 **/
	@Resource(resourceId = 25, operation = Operation.Write)
	public WriteResponse writeWEPKey4(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * RADIUS Authentication Server Address
	 **/
	@Resource(resourceId = 26, operation = Operation.Read, type = "STRING")
	public ReadResponse readRADIUSServer(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 26, operation = Operation.Write)
	public WriteResponse writeRADIUSServer(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * RADIUS Authentication Server Port Number
	 **/
	@Resource(resourceId = 27, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readRADIUSServerPort(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 27, operation = Operation.Write)
	public WriteResponse writeRADIUSServerPort(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * RADIUS Shared Secret
	 **/
	@Resource(resourceId = 28, operation = Operation.Write)
	public WriteResponse writeRADIUSSecret(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * 0: WMM NOT Supported
	 * 1: WMM Wupported
	 **/
	@Resource(resourceId = 29, operation = Operation.Read, type = "BOOLEAN")
	public ReadResponse readWMMSupported(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * 0: Disabled
	 * 1: Enabled
	 **/
	@Resource(resourceId = 30, operation = Operation.Read, type = "BOOLEAN")
	public ReadResponse readWMMEnabled(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 30, operation = Operation.Write)
	public WriteResponse writeWMMEnabled(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * 0: Any Client MAC Address accepted
	 * 1: Client MAC address must exist in MACAddressList
	 **/
	@Resource(resourceId = 31, operation = Operation.Read, type = "BOOLEAN")
	public ReadResponse readMACControlEnabled(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 31, operation = Operation.Write)
	public WriteResponse writeMACControlEnabled(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Array of allowed client MAC addresses, in hexadecimal form.
	 **/
	@Resource(resourceId = 32, operation = Operation.Read, multiple = true, type = "STRING")
	public ReadResponse readMACAddressList(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
	@Resource(resourceId = 32, operation = Operation.Write, multiple = true)
	public WriteResponse writeMACAddressList(ResourceContext resourceContext)	{
		return super.write(resourceContext);
	}

	/**
	 * Total number of bytes sent via this interface
	 **/
	@Resource(resourceId = 33, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readTotalBytesSent(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Total number of bytes received via this interface
	 **/
	@Resource(resourceId = 34, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readTotalBytesReceived(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Total packets sent via this interface
	 **/
	@Resource(resourceId = 35, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readTotalPacketsSent(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Total packets received via this interface
	 **/
	@Resource(resourceId = 36, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readTotalPacketsReceived(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Total number of packets which could not be transmitted because of errors.
	 **/
	@Resource(resourceId = 37, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readTransmitErrors(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Total number of packets received with errors which prevented those packets from being delivered.
	 **/
	@Resource(resourceId = 38, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readReceiveErrors(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Count of Unicast Packets Sent
	 **/
	@Resource(resourceId = 39, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readUnicastPacketsSent(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Count of Unicast Packets Received
	 **/
	@Resource(resourceId = 40, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readUnicastPacketsReceived(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Count of Multicast Packets Sent
	 **/
	@Resource(resourceId = 41, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readMulticastPacketsSent(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Count of Multicast Packets Received
	 **/
	@Resource(resourceId = 42, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readMulticastPacketsReceived(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Count of Broadcast Packets Sent
	 **/
	@Resource(resourceId = 43, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readBroadcastPacketsSent(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Count of Broadcast Packets Received
	 **/
	@Resource(resourceId = 44, operation = Operation.Read, type = "INTEGER")
	public ReadResponse read44BroadcastPacketsReceived(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Count of valid outbound packets intentionally discarded without transmission, for example a packet may be discarded to manage buffer space.
	 **/
	@Resource(resourceId = 45, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readDiscardPacketsSent(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Count of valid packets received and intentionally discarded without delivery to the system, for example a packet may be discarded to manage buffer space.
	 **/
	@Resource(resourceId = 46, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readDiscardPacketsReceived(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Count of Unknown Packets Received
	 **/
	@Resource(resourceId = 47, operation = Operation.Read, type = "INTEGER")
	public ReadResponse readUnknownPacketsReceived(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}

	/**
	 * Links to a vendor specific object.
	 **/
	@Resource(resourceId = 48, operation = Operation.Read, type = "OBJLNK")
	public ReadResponse readVendorSpecificExtensions(ResourceContext resourceContext)	{
		return super.read(resourceContext);
	}
}
