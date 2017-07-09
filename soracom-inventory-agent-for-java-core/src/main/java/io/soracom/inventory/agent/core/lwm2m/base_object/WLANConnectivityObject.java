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
 * This object specifies resources to enable a device to connect to a WLAN bearer.
 **/
@LWM2MObject(objectId = 12, name = "WLAN connectivity", multiple = true)
public abstract class WLANConnectivityObject extends AnnotatedLwM2mInstanceEnabler {

	/**
	 * Human-readable identifier
	 * eg. wlan0
	 **/
	@Resource(resourceId = 0, operation = Operation.Read)
	public ReadResponse readInterfaceName(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 0, operation = Operation.Write)
	public WriteResponse writeInterfaceName(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 0, operation = Operation.Execute)
	public ExecuteResponse executeInterfaceName(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * 0: Disabled
	 * 1: Enabled
	 * Enable / Disable interface
	 * When disabled radio must also be disabled
	 **/
	@Resource(resourceId = 1, operation = Operation.Read)
	public ReadResponse readEnable(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 1, operation = Operation.Write)
	public WriteResponse writeEnable(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 1, operation = Operation.Execute)
	public ExecuteResponse executeEnable(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

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
	public ReadResponse readRadioEnabled(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 2, operation = Operation.Write)
	public WriteResponse writeRadioEnabled(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 2, operation = Operation.Execute)
	public ExecuteResponse executeRadioEnabled(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * 0: Disabled
	 * 1: UP (OK)
	 * 2: Error
	 **/
	@Resource(resourceId = 3, operation = Operation.Read)
	public ReadResponse readStatus(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 3, operation = Operation.Execute)
	public ExecuteResponse executeStatus(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * The MAC address of the interface, in hexadecimal form.
	 **/
	@Resource(resourceId = 4, operation = Operation.Read)
	public ReadResponse readBSSID(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 4, operation = Operation.Execute)
	public ExecuteResponse executeBSSID(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * The Service Set Identifier for this interface.
	 **/
	@Resource(resourceId = 5, operation = Operation.Read)
	public ReadResponse readSSID(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 5, operation = Operation.Write)
	public WriteResponse writeSSID(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 5, operation = Operation.Execute)
	public ExecuteResponse executeSSID(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * 0: Do not broadcast SSID
	 * 1: Broadcast SSID
	 **/
	@Resource(resourceId = 6, operation = Operation.Read)
	public ReadResponse readBroadcastSSID(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 6, operation = Operation.Write)
	public WriteResponse writeBroadcastSSID(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 6, operation = Operation.Execute)
	public ExecuteResponse executeBroadcastSSID(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * 0: Do not broadcast beacons
	 * 1: Broadcast beacons
	 **/
	@Resource(resourceId = 7, operation = Operation.Read)
	public ReadResponse readBeaconEnabled(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 7, operation = Operation.Write)
	public WriteResponse writeBeaconEnabled(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 7, operation = Operation.Execute)
	public ExecuteResponse executeBeaconEnabled(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * 0: Access Point
	 * 1: Client (Station)
	 * 2: Bridge
	 * 3: Repeater
	 **/
	@Resource(resourceId = 8, operation = Operation.Read)
	public ReadResponse readMode(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 8, operation = Operation.Write)
	public WriteResponse writeMode(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 8, operation = Operation.Execute)
	public ExecuteResponse executeMode(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * The current radio channel in use by this interface.
	 * 0: Disabled
	 * 1: Enabled
	 **/
	@Resource(resourceId = 9, operation = Operation.Read)
	public ReadResponse readChannel(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 9, operation = Operation.Write)
	public WriteResponse writeChannel(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 9, operation = Operation.Execute)
	public ExecuteResponse executeChannel(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * 0: Disabled
	 * 1: Enabled
	 **/
	@Resource(resourceId = 10, operation = Operation.Read)
	public ReadResponse readAutoChannel(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 10, operation = Operation.Write)
	public WriteResponse writeAutoChannel(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 10, operation = Operation.Execute)
	public ExecuteResponse executeAutoChannel(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Array of supported radio channels.
	 **/
	@Resource(resourceId = 11, operation = Operation.Read, multiple = true)
	public ReadResponse readSupportedChannels(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 11, operation = Operation.Write, multiple = true)
	public WriteResponse writeSupportedChannels(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 11, operation = Operation.Execute, multiple = true)
	public ExecuteResponse executeSupportedChannels(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Array of channels which the access point has determined are ‘in use’.
	 * Including any channels in-use by access point itself.
	 **/
	@Resource(resourceId = 12, operation = Operation.Read, multiple = true)
	public ReadResponse readChannelsInUse(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 12, operation = Operation.Write, multiple = true)
	public WriteResponse writeChannelsInUse(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 12, operation = Operation.Execute, multiple = true)
	public ExecuteResponse executeChannelsInUse(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * 802.11d Regulatory Domain String. 
	 * First two octets are ISO/IEC 3166-1 two-character country code. 
	 * The third octet is either “ ” (all environments), “O” (outside) or “I” (inside).
	 **/
	@Resource(resourceId = 13, operation = Operation.Read)
	public ReadResponse readRegulatoryDomain(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 13, operation = Operation.Write)
	public WriteResponse writeRegulatoryDomain(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 13, operation = Operation.Execute)
	public ExecuteResponse executeRegulatoryDomain(ResourceContext resourceContext){
		return super.execute(resourceContext);
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
	public ReadResponse readStandard(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 14, operation = Operation.Write)
	public WriteResponse writeStandard(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 14, operation = Operation.Execute)
	public ExecuteResponse executeStandard(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * 0: None (Open)
	 * 1: PSK
	 * 2: EAP
	 * 3: EAP+PSK
	 * 4: EAPSIM
	 **/
	@Resource(resourceId = 15, operation = Operation.Read)
	public ReadResponse readAuthenticationMode(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 15, operation = Operation.Write)
	public WriteResponse writeAuthenticationMode(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 15, operation = Operation.Execute)
	public ExecuteResponse executeAuthenticationMode(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * 0: AES (WPA2)
	 * 1: TKIP (WPA)
	 * 2: WEP (1)
	 **/
	@Resource(resourceId = 16, operation = Operation.Read)
	public ReadResponse readEncryptionMode(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 16, operation = Operation.Write)
	public WriteResponse writeEncryptionMode(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 16, operation = Operation.Execute)
	public ExecuteResponse executeEncryptionMode(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * WPA/WPA2 Key expressed as a hex string.
	 * Write – Only.
	 **/
	@Resource(resourceId = 17, operation = Operation.Write)
	public WriteResponse writeWPAPreSharedKey(ResourceContext resourceContext){
		return super.write(resourceContext);
	}

	/**
	 * WPA/WPA2 Key Phrase.
	 * Write Only.
	 **/
	@Resource(resourceId = 18, operation = Operation.Write)
	public WriteResponse writeWPAKeyPhrase(ResourceContext resourceContext){
		return super.write(resourceContext);
	}

	/**
	 * Description.0: None
	 * 1: 40-bit
	 * 2: 104-bit
	 **/
	@Resource(resourceId = 19, operation = Operation.Read)
	public ReadResponse readWEPEncryptionType(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 19, operation = Operation.Write)
	public WriteResponse writeWEPEncryptionType(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 19, operation = Operation.Execute)
	public ExecuteResponse executeWEPEncryptionType(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Index of the default WEP key.
	 **/
	@Resource(resourceId = 20, operation = Operation.Read)
	public ReadResponse readWEPKeyIndex(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 20, operation = Operation.Write)
	public WriteResponse writeWEPKeyIndex(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 20, operation = Operation.Execute)
	public ExecuteResponse executeWEPKeyIndex(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * WPA/WPA2 Key Phrase.
	 * Write Only.
	 **/
	@Resource(resourceId = 21, operation = Operation.Write)
	public WriteResponse writeWEPKeyPhrase(ResourceContext resourceContext){
		return super.write(resourceContext);
	}

	/**
	 * WEP Key 1 expressed as a hexadecimal string.
	 * 10 Bytes for a 40 Bit key
	 * 26 Bytes for a 104 Bit key
	 **/
	@Resource(resourceId = 22, operation = Operation.Write)
	public WriteResponse writeWEPKey1(ResourceContext resourceContext){
		return super.write(resourceContext);
	}

	/**
	 * WEP Key 2 expressed as a hexadecimal string.
	 * 10 Bytes for a 40 Bit key
	 * 26 Bytes for a 104 Bit key
	 **/
	@Resource(resourceId = 23, operation = Operation.Write)
	public WriteResponse writeWEPKey2(ResourceContext resourceContext){
		return super.write(resourceContext);
	}

	/**
	 * WEP Key 3 expressed as a hexadecimal string.
	 * 10 Bytes for a 40 Bit key
	 * 26 Bytes for a 104 Bit key
	 **/
	@Resource(resourceId = 24, operation = Operation.Write)
	public WriteResponse writeWEPKey3(ResourceContext resourceContext){
		return super.write(resourceContext);
	}

	/**
	 * WEP Key 4 expressed as a hexadecimal string.
	 * 10 Bytes for a 40 Bit key
	 * 26 Bytes for a 104 Bit key
	 **/
	@Resource(resourceId = 25, operation = Operation.Write)
	public WriteResponse writeWEPKey4(ResourceContext resourceContext){
		return super.write(resourceContext);
	}

	/**
	 * RADIUS Authentication Server Address
	 **/
	@Resource(resourceId = 26, operation = Operation.Read)
	public ReadResponse readRADIUSServer(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 26, operation = Operation.Write)
	public WriteResponse writeRADIUSServer(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 26, operation = Operation.Execute)
	public ExecuteResponse executeRADIUSServer(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * RADIUS Authentication Server Port Number
	 **/
	@Resource(resourceId = 27, operation = Operation.Read)
	public ReadResponse readRADIUSServerPort(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 27, operation = Operation.Write)
	public WriteResponse writeRADIUSServerPort(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 27, operation = Operation.Execute)
	public ExecuteResponse executeRADIUSServerPort(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * RADIUS Shared Secret
	 **/
	@Resource(resourceId = 28, operation = Operation.Write)
	public WriteResponse writeRADIUSSecret(ResourceContext resourceContext){
		return super.write(resourceContext);
	}

	/**
	 * 0: WMM NOT Supported
	 * 1: WMM Wupported
	 **/
	@Resource(resourceId = 29, operation = Operation.Read)
	public ReadResponse readWMMSupported(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 29, operation = Operation.Execute)
	public ExecuteResponse executeWMMSupported(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * 0: Disabled
	 * 1: Enabled
	 **/
	@Resource(resourceId = 30, operation = Operation.Read)
	public ReadResponse readWMMEnabled(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 30, operation = Operation.Write)
	public WriteResponse writeWMMEnabled(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 30, operation = Operation.Execute)
	public ExecuteResponse executeWMMEnabled(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * 0: Any Client MAC Address accepted
	 * 1: Client MAC address must exist in MACAddressList
	 **/
	@Resource(resourceId = 31, operation = Operation.Read)
	public ReadResponse readMACControlEnabled(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 31, operation = Operation.Write)
	public WriteResponse writeMACControlEnabled(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 31, operation = Operation.Execute)
	public ExecuteResponse executeMACControlEnabled(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Array of allowed client MAC addresses, in hexadecimal form.
	 **/
	@Resource(resourceId = 32, operation = Operation.Read, multiple = true)
	public ReadResponse readMACAddressList(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 32, operation = Operation.Write, multiple = true)
	public WriteResponse writeMACAddressList(ResourceContext resourceContext){
		return super.write(resourceContext);
	}
	@Resource(resourceId = 32, operation = Operation.Execute, multiple = true)
	public ExecuteResponse executeMACAddressList(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Total number of bytes sent via this interface
	 **/
	@Resource(resourceId = 33, operation = Operation.Read)
	public ReadResponse readTotalBytesSent(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 33, operation = Operation.Execute)
	public ExecuteResponse executeTotalBytesSent(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Total number of bytes received via this interface
	 **/
	@Resource(resourceId = 34, operation = Operation.Read)
	public ReadResponse readTotalBytesReceived(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 34, operation = Operation.Execute)
	public ExecuteResponse executeTotalBytesReceived(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Total packets sent via this interface
	 **/
	@Resource(resourceId = 35, operation = Operation.Read)
	public ReadResponse readTotalPacketsSent(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 35, operation = Operation.Execute)
	public ExecuteResponse executeTotalPacketsSent(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Total packets received via this interface
	 **/
	@Resource(resourceId = 36, operation = Operation.Read)
	public ReadResponse readTotalPacketsReceived(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 36, operation = Operation.Execute)
	public ExecuteResponse executeTotalPacketsReceived(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Total number of packets which could not be transmitted because of errors.
	 **/
	@Resource(resourceId = 37, operation = Operation.Read)
	public ReadResponse readTransmitErrors(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 37, operation = Operation.Execute)
	public ExecuteResponse executeTransmitErrors(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Total number of packets received with errors which prevented those packets from being delivered.
	 **/
	@Resource(resourceId = 38, operation = Operation.Read)
	public ReadResponse readReceiveErrors(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 38, operation = Operation.Execute)
	public ExecuteResponse executeReceiveErrors(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Count of Unicast Packets Sent
	 **/
	@Resource(resourceId = 39, operation = Operation.Read)
	public ReadResponse readUnicastPacketsSent(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 39, operation = Operation.Execute)
	public ExecuteResponse executeUnicastPacketsSent(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Count of Unicast Packets Received
	 **/
	@Resource(resourceId = 40, operation = Operation.Read)
	public ReadResponse readUnicastPacketsReceived(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 40, operation = Operation.Execute)
	public ExecuteResponse executeUnicastPacketsReceived(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Count of Multicast Packets Sent
	 **/
	@Resource(resourceId = 41, operation = Operation.Read)
	public ReadResponse readMulticastPacketsSent(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 41, operation = Operation.Execute)
	public ExecuteResponse executeMulticastPacketsSent(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Count of Multicast Packets Received
	 **/
	@Resource(resourceId = 42, operation = Operation.Read)
	public ReadResponse readMulticastPacketsReceived(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 42, operation = Operation.Execute)
	public ExecuteResponse executeMulticastPacketsReceived(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Count of Broadcast Packets Sent
	 **/
	@Resource(resourceId = 43, operation = Operation.Read)
	public ReadResponse readBroadcastPacketsSent(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 43, operation = Operation.Execute)
	public ExecuteResponse executeBroadcastPacketsSent(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Count of Broadcast Packets Received
	 **/
	@Resource(resourceId = 44, operation = Operation.Read)
	public ReadResponse read44BroadcastPacketsReceived(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 44, operation = Operation.Execute)
	public ExecuteResponse execute44BroadcastPacketsReceived(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Count of valid outbound packets intentionally discarded without transmission, for example a packet may be discarded to manage buffer space.
	 **/
	@Resource(resourceId = 45, operation = Operation.Read)
	public ReadResponse readDiscardPacketsSent(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 45, operation = Operation.Execute)
	public ExecuteResponse executeDiscardPacketsSent(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Count of valid packets received and intentionally discarded without delivery to the system, for example a packet may be discarded to manage buffer space.
	 **/
	@Resource(resourceId = 46, operation = Operation.Read)
	public ReadResponse readDiscardPacketsReceived(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 46, operation = Operation.Execute)
	public ExecuteResponse executeDiscardPacketsReceived(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Count of Unknown Packets Received
	 **/
	@Resource(resourceId = 47, operation = Operation.Read)
	public ReadResponse readUnknownPacketsReceived(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 47, operation = Operation.Execute)
	public ExecuteResponse executeUnknownPacketsReceived(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}

	/**
	 * Links to a vendor specific object.
	 **/
	@Resource(resourceId = 48, operation = Operation.Read)
	public ReadResponse readVendorSpecificExtensions(ResourceContext resourceContext){
		return super.read(resourceContext);
	}
	@Resource(resourceId = 48, operation = Operation.Execute)
	public ExecuteResponse executeVendorSpecificExtensions(ResourceContext resourceContext){
		return super.execute(resourceContext);
	}
}
