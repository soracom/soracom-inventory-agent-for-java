package io.soracom.inventory.agent.core.util;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class TypedAnnotatedLwM2mInstanceTemplateClassGeneratorMain {

	public static void main(String[] args) throws Exception {

		List<String> modelDefinitionFileNames = Arrays.asList("LWM2M_Access_Control-v1_0.xml",
				"LWM2M_Connectivity_Monitoring-v1_0.xml", "LWM2M_Connectivity_Statistics-v1_0.xml",
				"LWM2M_Device-v1_0.xml", "LWM2M_Firmware_Update-v1_0.xml", "LWM2M_Location-v1_0.xml",
				"LWM2M_APN_connection_profile-v1_0.xml", "LWM2M_Bearer_selection-v1_0.xml",
				"LWM2M_Cellular_connectivity-v1_0.xml", "LWM2M_DevCapMgmt-v1_0.xml", "LWM2M_Lock_and_Wipe-V1_0.xml",
				"LWM2M_Portfolio-v1_0.xml", "LWM2M_Software_Component-v1_0.xml", "LWM2M_Software_Management-v1_0.xml",
				"LWM2M_WLAN_connectivity4-v1_0.xml");

		String javaPackage = "io.soracom.inventory.agent.core.lwm2m.typed_object";
		File outputDir = new File("src/main/java");

		for (String fileName : modelDefinitionFileNames) {
			TypedAnnotatedObjectTemplateClassGenerator generator = new TypedAnnotatedObjectTemplateClassGenerator(
					javaPackage,outputDir);
			generator.setGenerateAbstractTemplate(true);
			InputStream inputStream = TypedAnnotatedLwM2mInstanceTemplateClassGeneratorMain.class
					.getResourceAsStream("/models/" + fileName);
			generator.generateTemplateClassFromObjectModel(inputStream);
		}
	}

}
