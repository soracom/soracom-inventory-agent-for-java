package io.soracom.inventory.agent.core.util;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import io.soracom.inventory.agent.core.initialize.InventoryAgentHelper;

public class TypedAnnotatedLwM2mInstanceTemplateClassGeneratorMain {

	public static void main(String[] args) throws Exception {

		List<String> modelDefinitionFileNames = Arrays.asList(InventoryAgentHelper.DEFAULT_LWM2M_MODELS);


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
