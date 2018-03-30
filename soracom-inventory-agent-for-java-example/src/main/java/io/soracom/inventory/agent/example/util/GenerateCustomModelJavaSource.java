package io.soracom.inventory.agent.example.util;

import java.io.File;
import java.io.IOException;

import io.soracom.inventory.agent.core.util.TypedAnnotatedObjectTemplateClassGenerator;

public class GenerateCustomModelJavaSource {

	public static void main(String[] args) throws IOException {
		String javaPackage = "io.soracom.inventory.agent.example.object";
		File sourceFileDir = new File("src/main/java");
		TypedAnnotatedObjectTemplateClassGenerator generator = new TypedAnnotatedObjectTemplateClassGenerator(
				javaPackage, sourceFileDir);

		File modelFile = new File("src/main/resources/30000.xml");

		generator.generateTemplateClassFromObjectModel(modelFile);
		System.out.println("generate Java source.");

		System.exit(0);
	}

}
