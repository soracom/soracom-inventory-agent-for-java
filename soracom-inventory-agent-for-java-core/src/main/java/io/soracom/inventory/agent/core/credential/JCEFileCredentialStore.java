/*******************************************************************************
 * Copyright (c) 2018 SORACOM, Inc. and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 * 
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *    http://www.eclipse.org/org/documents/edl-v10.html.
 * 
 * Contributors:
 *     SORACOM,Inc. - initial API and implementation
 *******************************************************************************/
package io.soracom.inventory.agent.core.credential;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * Java Cryptography Extension (JCE) credential store
 * 
 * @author c9katayama
 *
 */
public class JCEFileCredentialStore extends FileCredentialStore {

	private static final Logger log = LoggerFactory.getLogger(JCEFileCredentialStore.class);
	public static final String ENV_NAME_INVENTORY_CREDENTIAL_STORE_KEY = "INVENTORY_CREDENTIAL_STORE_KEY";
	private String credentialsPath = System.getProperty("user.home") + File.separator + ".soracom-inventory-jce";
	private static final String ALIAS = "soracom-inventory-credential";
	private KeyStore keyStore;
	private char[] keyStoreKey;

	@Override
	public void clearCredentials() {
		if (keyStore == null) {
			initKeyStore();
		}
		try {
			keyStore.deleteEntry(ALIAS);
		} catch (KeyStoreException e) {
			throw new RuntimeException(e);
		}
		clearCredentials(credentialsPath);
		clearOldDatFile();
	}

	protected void clearOldDatFile() {
		// remove old version of credential
		if (credentialsPath.equals(FileCredentialStore.DEFAULT_CREDENTIALS_PATH) == false) {
			if (new File(FileCredentialStore.DEFAULT_CREDENTIALS_PATH).exists()) {
				clearCredentials(FileCredentialStore.DEFAULT_CREDENTIALS_PATH);
				log.info("delete old type of credential store file : "
						+ FileCredentialStore.DEFAULT_CREDENTIALS_PATH);
			}
		}
	}

	@Override
	public void setCredentialsPath(String credentialsPath) {
		this.credentialsPath = credentialsPath;
	}

	@Override
	public void saveCredentials(Credentials credentials) {
		if (keyStore == null) {
			initKeyStore();
		}
		byte[] value = new Gson().toJson(credentials).getBytes();
		Key key = new SecretKeySpec(value, "AES");
		FileOutputStream fout = null;
		try {
			keyStore.setKeyEntry(ALIAS, key, keyStoreKey, null);
			fout = new FileOutputStream(new File(credentialsPath));
			keyStore.store(fout, keyStoreKey);
			log.info("save credential to " + credentialsPath);
		} catch (NoSuchAlgorithmException | CertificateException | IOException | KeyStoreException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(fout);
		}
		clearOldDatFile();
	}

	@Override
	public Credentials loadCredentials() {
		if (keyStore == null) {
			initKeyStore();
		}
		try {
			Key key = keyStore.getKey(ALIAS, keyStoreKey);
			if (key != null) {
				byte[] value = key.getEncoded();
				Credentials credentials = new Gson().fromJson(new String(value), Credentials.class);
				log.info("load credential from " + credentialsPath);
				return credentials;
			}
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		// fallback
		return super.loadCredentials(DEFAULT_CREDENTIALS_PATH);
	}

	protected void initKeyStore() {
		loadKeyStoreKey();
		File file = new File(credentialsPath);

		try {
			keyStore = KeyStore.getInstance("JCEKS");
		} catch (KeyStoreException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		FileOutputStream fout = null;
		FileInputStream fin = null;
		try {
			if (file.exists()) {
				fin = new FileInputStream(file);
				keyStore.load(fin, keyStoreKey);
			} else {
				// if not exists, create
				keyStore.load(null, null);
				fout = new FileOutputStream(file);
				keyStore.store(fout, keyStoreKey);
			}
		} catch (NoSuchAlgorithmException | IOException nsa) {
			throw new RuntimeException(nsa.getMessage(), nsa);
		} catch (CertificateException | KeyStoreException e) {
			log.error("Failed to load key store. Please check credential store key. credential store file="
					+ credentialsPath);
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(fin);
			close(fout);
		}
	}

	protected void loadKeyStoreKey() {
		if (keyStoreKey == null) {
			String key = System.getenv(ENV_NAME_INVENTORY_CREDENTIAL_STORE_KEY);
			if (key != null) {
				log.info("load credential store key from env");
			} else {
				key = System.getProperty(ENV_NAME_INVENTORY_CREDENTIAL_STORE_KEY);
				if (key != null) {
					log.info("load credential store key from system property");
				} else {
					key = "!_S0r4C0m_&";
					log.info("use default credential store key");
				}
			}
			keyStoreKey = key.toCharArray();
		}
	}
}
