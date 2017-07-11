/*******************************************************************************
 * Copyright (c) 2017 SORACOM, Inc. and others.
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.ResourceModel;
import org.eclipse.leshan.core.model.ResourceModel.Operations;

public class TemplateClassGenerator {

	protected String indentChar = "\t";

	protected PrintStream pw;

	public TemplateClassGenerator() {
		pw = System.out;
	}

	public void setOutput(File file) throws IOException {
		pw = new PrintStream(new FileOutputStream(file));
	}

	public void setIndentChar(String indentChar) {
		this.indentChar = indentChar;
	}

	public void generateTemplateClassFromObjectModel(String javaPackage, ObjectModel model) {
		println("package " + javaPackage + ";");
		println("import io.soracom.inventory.agent.core.lwm2m.*;");
		println("import org.eclipse.leshan.core.response.*;");
		println("");
		printComment(0, model.description);
		final String className = toJavaName(model.name + "Object");
		final String multipleObject = model.multiple ? ", multiple = true" : "";
		println(0, "@LWM2MObject(objectId = " + model.id + ", name = \"" + model.name + "\"" + multipleObject + ")");
		println(0, "public abstract class " + className + " extends AnnotatedLwM2mInstanceEnabler {");
		List<Integer> keyList = new ArrayList<Integer>(model.resources.keySet());
		Collections.sort(keyList);
		for (Integer resourceId : keyList) {
			println("");
			final ResourceModel resourceModel = model.resources.get(resourceId);
			final Operations operations = resourceModel.operations;
			final String multipleResource = resourceModel.multiple ? ", multiple = true" : "";
			final String type = resourceModel.type != null ? ", type = \"" + resourceModel.type.toString() + "\"" : "";
			printComment(1, resourceModel.description);
			if (operations.isReadable()) {
				println(1, "@Resource(resourceId = " + resourceId.intValue() + ", operation = Operation.Read"
						+ multipleResource + type + ")");
				println(1, "public ReadResponse read" + toJavaName(resourceModel.name)
						+ "(ResourceContext resourceContext){");
				println(2, "return super.read(resourceContext);");
				println(1, "}");
			}
			if (operations.isWritable()) {
				println(1, "@Resource(resourceId = " + resourceId.intValue() + ", operation = Operation.Write"
						+ multipleResource + ")");
				println(1, "public WriteResponse write" + toJavaName(resourceModel.name)
						+ "(ResourceContext resourceContext){");
				println(2, "return super.write(resourceContext);");
				println(1, "}");
			}
			if (operations.isReadable()) {
				println(1, "@Resource(resourceId = " + resourceId.intValue() + ", operation = Operation.Execute"
						+ multipleResource + ")");
				println(1, "public ExecuteResponse execute" + toJavaName(resourceModel.name)
						+ "(ResourceContext resourceContext){");
				println(2, "return super.execute(resourceContext);");
				println(1, "}");
			}
		}
		println(0, "}");
		pw.close();
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
