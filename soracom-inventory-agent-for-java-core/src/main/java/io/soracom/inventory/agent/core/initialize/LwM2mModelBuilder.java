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
package io.soracom.inventory.agent.core.initialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.StaticModel;

public class LwM2mModelBuilder {

	private List<ObjectModel> objectModelList;

	static String[] PRESET_LWM2M_MODELS = new String[] { "LWM2M_Access_Control-v1_0.xml",
			"LWM2M_Connectivity_Monitoring-v1_0.xml", "LWM2M_Connectivity_Statistics-v1_0.xml", "LWM2M_Device-v1_0.xml",
			"LWM2M_Firmware_Update-v1_0.xml", "LWM2M_Location-v1_0.xml", "LWM2M_APN_connection_profile-v1_0.xml",
			"LWM2M_Bearer_selection-v1_0.xml", "LWM2M_Cellular_connectivity-v1_0.xml", "LWM2M_DevCapMgmt-v1_0.xml",
			"LWM2M_Lock_and_Wipe-V1_0.xml", "LWM2M_Portfolio-v1_0.xml", "LWM2M_Software_Component-v1_0.xml",
			"LWM2M_Software_Management-v1_0.xml", "LWM2M_WLAN_connectivity4-v1_0.xml" };

	public LwM2mModelBuilder() {
		objectModelList = ObjectLoader.loadDefault();
	}

	public LwM2mModelBuilder addPresetObjectModels() {
		objectModelList.addAll(ObjectLoader.loadDdfResources("/models/", InventoryAgentHelper.DEFAULT_LWM2M_MODELS));
		return this;
	}

	public LwM2mModelBuilder addObjectModel(ObjectModel objectModel) {
		objectModelList.add(objectModel);
		return this;
	}

	public LwM2mModelBuilder addObjectModels(List<ObjectModel> objectModels) {
		objectModelList.addAll(objectModels);
		return this;
	}

	public LwM2mModelBuilder addObjectModelsFromDir(File dir) {
		final List<ObjectModel> loadObjectsFromDir = ObjectLoader.loadObjectsFromDir(dir);
		if (loadObjectsFromDir.size() > 0) {
			objectModelList.addAll(loadObjectsFromDir);
		} else {
			throw new IllegalArgumentException("Model files not found. dir=" + dir.getAbsolutePath());
		}
		return this;
	}

	public LwM2mModelBuilder addObjectModelFromClassPath(String file) {
		InputStream is = LwM2mModelBuilder.class.getResourceAsStream(file);
		if (is == null) {
			throw new IllegalArgumentException("Resource not found.resource file=" + file);
		}
		addObjectModel(is);
		return this;
	}

	public LwM2mModelBuilder addObjectModelFromFile(File file) {
		try (FileInputStream input = new FileInputStream(file)) {
			List<ObjectModel> objectModels = ObjectLoader.loadDdfFile(input, file.getName());
			if (objectModels != null) {
				addObjectModels(objectModels);
			} else {
				throw new IllegalArgumentException("Invalid model definition.file=" + file.getAbsolutePath());
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return this;
	}

	public LwM2mModelBuilder addObjectModel(InputStream is) {
		addObjectModels(ObjectLoader.loadDdfFile(is, ""));
		return this;
	}

	public LwM2mModel build() {
		return new StaticModel(objectModelList);
	}
}
