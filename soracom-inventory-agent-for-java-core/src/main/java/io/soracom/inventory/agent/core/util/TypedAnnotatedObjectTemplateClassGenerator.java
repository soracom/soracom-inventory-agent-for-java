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
package io.soracom.inventory.agent.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.ResourceModel;
import org.eclipse.leshan.core.model.ResourceModel.Operations;
import org.eclipse.leshan.core.model.ResourceModel.Type;
import org.eclipse.leshan.core.node.ObjectLink;

/**
 * <pre>
 * Utility class to generate template implementation of java class from LWM2M model definition model file.
 * 
 * Usage:
 * String javaPackage = "my.models"; //Java package for generated java source
 * File sourceOutputDir = new File("source"); // root of source dir
 * TypedAnnotatedObjectTemplateClassGenerator generator = new TypedAnnotatedObjectTemplateClassGenerator(javaPackage,outputDir);
 * File modelFile = new File("30000.xml");
 * generator.generateTemplateClassFromObjectModel(modelFile);
 * </pre>
 * 
 * @author c9katayama
 *
 */
public class TypedAnnotatedObjectTemplateClassGenerator {

	protected String indentChar = "\t";

	protected PrintStream pw;

	protected boolean abstractTemplate = false;

	protected String javaPackage;

	protected File sourceOutputDir;

	public TypedAnnotatedObjectTemplateClassGenerator() {
	}

	public TypedAnnotatedObjectTemplateClassGenerator(String javaPackage) {
		this.javaPackage = javaPackage;
	}

	public TypedAnnotatedObjectTemplateClassGenerator(String javaPackage, File sourceOutputDir) {
		this.javaPackage = javaPackage;
		setSourceOutputDir(sourceOutputDir);
	}

	public void setSourceOutputDir(File dir) {
		sourceOutputDir = dir;
	}

	public void setIndentChar(String indentChar) {
		this.indentChar = indentChar;
	}

	public void setJavaPackage(String javaPackage) {
		this.javaPackage = javaPackage;
	}

	public void setGenerateAbstractTemplate(boolean abstractTemplate) {
		this.abstractTemplate = abstractTemplate;
	}

	private Map<ResourceModel.Type, String> resourceModel2TypeMap = new HashMap<>();
	{
		resourceModel2TypeMap.put(ResourceModel.Type.BOOLEAN, Boolean.class.getSimpleName());
		resourceModel2TypeMap.put(ResourceModel.Type.FLOAT, Float.class.getSimpleName());
		resourceModel2TypeMap.put(ResourceModel.Type.INTEGER, Integer.class.getSimpleName());
		resourceModel2TypeMap.put(ResourceModel.Type.OBJLNK, ObjectLink.class.getSimpleName());
		resourceModel2TypeMap.put(ResourceModel.Type.OPAQUE, "byte[]");
		resourceModel2TypeMap.put(ResourceModel.Type.STRING, String.class.getSimpleName());
		resourceModel2TypeMap.put(ResourceModel.Type.TIME, Date.class.getName());
	}

	public void generateTemplateClassFromObjectModel(File modelDefinitionFile) {
		try {
			generateTemplateClassFromObjectModel(new FileInputStream(modelDefinitionFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	protected FileOutputStream prepareSourceFileOutputStream(String className) {
		File sourceDir = javaPackage == null ? sourceOutputDir
				: new File(sourceOutputDir, javaPackage.replaceAll("\\.", "\\/"));
		sourceDir.mkdirs();
		try {
			return new FileOutputStream(new File(sourceDir, className + ".java"));
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void generateTemplateClassFromObjectModel(InputStream modelDefinitionInputStream) {
		final List<ObjectModel> models = ObjectLoader.loadDdfFile(modelDefinitionInputStream, "");
		for(ObjectModel model:models) {
			generateTemplateClassFromObjectModel(model);
		}
	}
	private void generateTemplateClassFromObjectModel(ObjectModel model) {
		final String className = toJavaName(model.name + "Object");

		FileOutputStream fout = null;
		if (sourceOutputDir != null) {
			fout = prepareSourceFileOutputStream(className);
			pw = new PrintStream(fout);
		} else {
			pw = System.out;
		}

		if (javaPackage != null) {
			println("package " + javaPackage + ";");
		}
		println("import io.soracom.inventory.agent.core.lwm2m.*;");
		println("import java.util.Date;");
		println("import " + ObjectLink.class.getName() + ";");
		println("");
		printComment(0, model.description);

		final String multipleObject = model.multiple ? ", multiple = true" : "";
		println(0, "@LWM2MObject(objectId = " + model.id + ", name = \"" + model.name + "\"" + multipleObject + ")");
		final String defaultModifier = getModifier();
		println(0, "public" + defaultModifier + " class " + className + " extends AnnotatedLwM2mInstanceEnabler {");
		List<Integer> keyList = new ArrayList<Integer>(model.resources.keySet());
		Collections.sort(keyList);
		for (Integer resourceId : keyList) {
			println("");
			final ResourceModel resourceModel = model.resources.get(resourceId);
			final Operations operations = resourceModel.operations;
			final String multipleResource = resourceModel.multiple ? ", multiple = true" : "";
			final boolean mondatory = (abstractTemplate) ? resourceModel.mandatory : false;
			final String modifire = (mondatory == true) ? "public" + defaultModifier : "public";
			final Type type = resourceModel.type;
			printComment(1, resourceModel.description);
			if (operations.isReadable()) {
				final String responseValue = resourceModel2TypeMap.get(type);
				println(1, "@Resource(resourceId = " + resourceId.intValue() + ", operation = Operation.Read"
						+ multipleResource + ")");
				print(1, modifire + " " + responseValue + " read" + toJavaName(resourceModel.name) + "()");
				if (mondatory) {
					println(0, ";");
				} else {
					println(1, "{");
					println(2, "throw LwM2mInstanceResponseException.notFound();");
					println(1, "}");
				}
			}
			if (operations.isWritable()) {
				final String paramValue = resourceModel2TypeMap.get(type);
				println(1, "@Resource(resourceId = " + resourceId.intValue() + ", operation = Operation.Write"
						+ multipleResource + ")");
				print(1, modifire + " void write" + toJavaName(resourceModel.name) + "(" + paramValue + " writeValue)");
				if (mondatory) {
					println(0, ";");
				} else {
					println(1, "{");
					println(2, "throw LwM2mInstanceResponseException.notFound();");
					println(1, "}");
				}
			}
			if (operations.isExecutable()) {
				println(1, "@Resource(resourceId = " + resourceId.intValue() + ", operation = Operation.Execute"
						+ multipleResource + ")");
				print(1, modifire + " void execute" + toJavaName(resourceModel.name) + "(String executeParameter)");
				if (mondatory) {
					println(0, ";");
				} else {
					println(1, "{");
					println(2, "throw LwM2mInstanceResponseException.notFound();");
					println(1, "}");
				}
			}
		}
		println(0, "}");
		pw.close();
		if (fout != null) {
			try {
				fout.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}

	public String toJavaClassName(ObjectModel model) {
		return toJavaName(model.name + "Object");
	}

	protected String toJavaName(String value) {
		value = value.trim();
		value = value.replaceAll("\\([0-9]*\\)", "");// remove reference to note
		String[] replacedChars = { "-", ",", "\\/", "\\[", "\\]", "\\(", "\\)" };
		for (int i = 0; i < replacedChars.length; i++) {
			value = value.replaceAll(replacedChars[i], "_");
		}
		if (value.endsWith("_")) {
			value = value.substring(0, value.length() - 1);
		}
		StringBuilder className = new StringBuilder();
		boolean toUpperCase = true;
		for (int i = 0; i < value.length(); i++) {
			String c = value.substring(i, i + 1);
			if (c.equals(" ")) {
				toUpperCase = true;
			} else {
				if (toUpperCase) {
					className.append(c.toUpperCase());
					toUpperCase = false;
				} else {
					className.append(c);
				}
			}
		}
		return className.toString();
	}

	protected String getModifier() {
		return abstractTemplate ? " abstract" : "";
	}

	protected void printComment(int indentNum, String value) {
		pw.println(indent(indentNum) + "/**");
		String[] lines = value.split("\n");
		for (int i = 0; i < lines.length; i++) {
			pw.println(indent(indentNum) + " * " + lines[i]);
		}
		pw.println(indent(indentNum) + " **/");
	}

	protected String indent(int indentNum) {
		String value = "";
		for (int i = 0; i < indentNum; i++) {
			value += indentChar;
		}
		return value;
	}

	protected void print(String source) {
		print(0, source);
	}

	protected void println(String source) {
		println(0, source);
	}

	protected void print(int indentNum, String source) {
		pw.print(indent(indentNum) + source);
	}

	protected void println(int indentNum, String source) {
		pw.println(indent(indentNum) + source);
	}
}
