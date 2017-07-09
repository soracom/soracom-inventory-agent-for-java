package io.soracom.inventory.agent.core.credential;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.soracom.inventory.agent.core.exception.SORACOMInventoryAgentRuntimeException;

public class FileCredentialStore implements CredentialStore {

	private static final Logger log = LoggerFactory.getLogger(FileCredentialStore.class);

	private static final String DEFAULT_CREDENTIALS_PATH = ".soracom-inventory-credentials.dat";
	
	private String credentialsPath = DEFAULT_CREDENTIALS_PATH;
	
	public void setCredentialsPath(String credentialsPath) {
		this.credentialsPath = credentialsPath;
	}

	@Override
	public void clearCredentials() {
		File datFile = new File(credentialsPath);
		if (datFile.exists()) {
			datFile.delete();
		}
	}

	public void saveCredentials(Credentials c) {
		File datFile = new File(credentialsPath);
		try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(datFile))) {
			objOut.writeObject(c);
			log.info("save credential to " + credentialsPath);
		} catch (IOException ioe) {
			throw new SORACOMInventoryAgentRuntimeException(ioe);
		}
	}

	@Override
	public Credentials loadCredentials() {
		File datFile = new File(credentialsPath);
		if (datFile.exists() == false) {
			return null;
		}
		try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(datFile))) {
			Credentials credential = (Credentials) objIn.readObject();
			log.info("load credential from " + credentialsPath);
			return credential;
		} catch (Exception e) {
			throw new SORACOMInventoryAgentRuntimeException(e);
		}
	}
}
