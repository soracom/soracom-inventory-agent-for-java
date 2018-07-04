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
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.soracom.inventory.agent.core.exception.SORACOMInventoryAgentRuntimeException;

/**
 * File stored credntial store
 * @author c9katayama
 * @deprecated Recommended to use {@code JCEFileCredentialStore} instead of this class
 *
 */
@Deprecated
public class FileCredentialStore implements CredentialStore {

	private static final Logger log = LoggerFactory.getLogger(FileCredentialStore.class);

	protected static final String DEFAULT_CREDENTIALS_PATH = ".soracom-inventory-credentials.dat";

	private String credentialsPath = DEFAULT_CREDENTIALS_PATH;

	public void setCredentialsPath(String credentialsPath) {
		this.credentialsPath = credentialsPath;
	}

	@Override
	public void clearCredentials() {
		clearCredentials(credentialsPath);
	}

	protected void clearCredentials(String path) {
		File datFile = new File(path);
		try {
			Files.deleteIfExists(datFile.toPath());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public void saveCredentials(Credentials c) {
		File datFile = new File(credentialsPath);
		ObjectOutputStream objOut = null;
		try {
			objOut = new ObjectOutputStream(new FileOutputStream(datFile));
			objOut.writeObject(c);
			log.info("save credential to " + credentialsPath);
		} catch (IOException ioe) {
			throw new SORACOMInventoryAgentRuntimeException(ioe);
		} finally {
			close(objOut);
		}
	}

	@Override
	public Credentials loadCredentials() {
		return loadCredentials(credentialsPath);
	}

	protected Credentials loadCredentials(String path) {
		File datFile = new File(path);
		if (datFile.exists() == false) {
			return null;
		}
		ObjectInputStream objIn = null;
		try {
			objIn = new ObjectInputStream(new FileInputStream(datFile));
			Credentials credential = (Credentials) objIn.readObject();
			log.info("load credential from " + credentialsPath);
			return credential;
		} catch (Exception e) {
			throw new SORACOMInventoryAgentRuntimeException(e);
		} finally {
			close(objIn);
		}
	}

	protected void close(OutputStream os) {
		try {
			os.close();
		} catch (Exception e) {
		}
	}

	protected void close(InputStream is) {
		try {
			is.close();
		} catch (Exception e) {
		}
	}
}
