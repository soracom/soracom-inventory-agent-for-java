package io.soracom.inventory.agent.example.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;

import io.soracom.inventory.agent.core.util.TypedAnnotatedObjectTemplateClassGenerator;

public class GenerateCustomModelJavaSource {

	public static void main(String[] args) throws IOException {
		String javaPackage = "io.soracom.inventory.agent.example.object";
		File outputDir = new File("src/main/java/" + javaPackage.replaceAll("\\.", "\\/"));
		List<ObjectModel> models = ObjectLoader.loadDdfResources("/", new String[] { "30000.xml" });

		for (ObjectModel objectModel : models) {
			TypedAnnotatedObjectTemplateClassGenerator generator = new TypedAnnotatedObjectTemplateClassGenerator();
			String fileName = generator.toJavaClassName(objectModel);
			File outputFile = new File(outputDir, fileName + ".java");
			generator.setOutput(outputFile);
			generator.generateTemplateClassFromObjectModel(javaPackage, objectModel);
			System.out.println("generate Java source.file=" + outputFile.getAbsolutePath());
		}
		System.exit(0);
	}

}
