package io.soracom.inventory.agent.core.credential;

public interface CredentialStore {

	void clearCredentials();
	
	void saveCredentials(Credentials credentials);

	Credentials loadCredentials();
}
