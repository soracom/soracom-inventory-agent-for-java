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
package io.soracom.inventory.agent.core.lwm2m;

import org.eclipse.leshan.core.node.LwM2mResource;

public class ResourceContext {

	private int resourceId;

	private Operation operation;
	private String executeParameter;
	private LwM2mResource writeValue;

	private ResourceContext(int resourceId, Operation operation, LwM2mResource writeValue, String executeParameter) {
		this.resourceId = resourceId;
		this.operation = operation;
		this.writeValue = writeValue;
		this.executeParameter = executeParameter;
	}

	public static ResourceContext ofReadContext(int resourceId) {
		return new ResourceContext(resourceId, Operation.Read, null, null);
	}

	public static ResourceContext ofWriteContext(int resourceId, LwM2mResource writeValue) {
		return new ResourceContext(resourceId, Operation.Write, writeValue, null);
	}

	public static ResourceContext ofExecuteContext(int resourceId, String executeParameter) {
		return new ResourceContext(resourceId, Operation.Execute, null, executeParameter);
	}

	public Operation getOperation() {
		return operation;
	}

	public int getResourceId() {
		return resourceId;
	}

	public String getExecuteParameter() {
		return executeParameter;
	}

	public LwM2mResource getWriteValue() {
		return writeValue;
	}
}
