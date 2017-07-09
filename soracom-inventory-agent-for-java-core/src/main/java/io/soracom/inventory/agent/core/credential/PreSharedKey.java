package io.soracom.inventory.agent.core.credential;

import org.eclipse.leshan.util.Hex;

public class PreSharedKey {

	protected String pskIdentity;
	protected byte[] pskKey;

	public PreSharedKey(String keyId, byte[] psk) {
		pskIdentity = keyId;
		pskKey = psk;
	}

	public PreSharedKey(String keyId, String pskHex) {
		pskIdentity = keyId;
		pskKey = Hex.decodeHex(pskHex.toCharArray());
	}

	public String getPskIdentity() {
		return pskIdentity;
	}

	public byte[] getPskIdentityBytes() {
		return pskIdentity.getBytes();
	}

	public byte[] getPskKey() {
		return pskKey;
	}
}
