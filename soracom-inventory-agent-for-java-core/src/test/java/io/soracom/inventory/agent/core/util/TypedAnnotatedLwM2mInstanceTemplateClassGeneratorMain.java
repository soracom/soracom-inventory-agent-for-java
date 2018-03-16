package io.soracom.inventory.agent.core.util;

import java.io.File;
import java.util.List;

import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;

public class TypedAnnotatedLwM2mInstanceTemplateClassGeneratorMain {

	public static void main(String[] args) throws Exception {

		List<ObjectModel> rdfResource = ObjectLoader.loadDdfResources("/models/",
				new String[] { "LWM2M_Access_Control-v1_0.xml", "LWM2M_Connectivity_Monitoring-v1_0.xml",
						"LWM2M_Connectivity_Statistics-v1_0.xml", "LWM2M_Device-v1_0.xml",
						"LWM2M_Firmware_Update-v1_0.xml", "LWM2M_Location-v1_0.xml",
						"LWM2M_APN_connection_profile-v1_0.xml", "LWM2M_Bearer_selection-v1_0.xml",
						"LWM2M_Cellular_connectivity-v1_0.xml", "LWM2M_DevCapMgmt-v1_0.xml",
						"LWM2M_Lock_and_Wipe-V1_0.xml", "LWM2M_Portfolio-v1_0.xml", "LWM2M_Software_Component-v1_0.xml",
						"LWM2M_Software_Management-v1_0.xml", "LWM2M_WLAN_connectivity4-v1_0.xml" });

		String javaPackage = "io.soracom.inventory.agent.core.lwm2m.typed_object";
		File outputDir = new File("src/main/java/" + javaPackage.replaceAll("\\.", "\\/"));
		outputDir.mkdirs();
		for (ObjectModel model : rdfResource) {
			TypedAnnotatedObjectTemplateClassGenerator generator = new TypedAnnotatedObjectTemplateClassGenerator();
			String fileName = generator.toJavaClassName(model);
			File outputFile = new File(outputDir, fileName + ".java");
			generator.setOutput(outputFile);
			generator.generateTemplateClassFromObjectModel(javaPackage, model);
		}
	}

}
