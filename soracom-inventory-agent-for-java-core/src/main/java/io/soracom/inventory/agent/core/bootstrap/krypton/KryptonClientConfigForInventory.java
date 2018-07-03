/*******************************************************************************
 * Copyright (c) 2018 SORACOM, Inc. and others.
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 and Eclipse
 * Distribution License v1.0 which accompany this distribution.
 * 
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html and the Eclipse Distribution
 * License is available at http://www.eclipse.org/org/documents/edl-v10.html.
 * 
 * Contributors: SORACOM,Inc. - initial API and implementation
 *******************************************************************************/
package io.soracom.inventory.agent.core.bootstrap.krypton;

import io.soracom.endorse.SORACOMEndorseClientConfig;
import io.soracom.krypton.SORACOMKryptonClientConfig;

public class KryptonClientConfigForInventory extends SORACOMKryptonClientConfig {
	private static final int KEY_LENGTH = 16;

	public KryptonClientConfigForInventory() {
		setDefaultValue();
	}

	private void setDefaultValue() {
		SORACOMEndorseClientConfig endorseClientConfig = new SORACOMEndorseClientConfig();
		endorseClientConfig.setKeyLength(KEY_LENGTH);
		setEndorseClientConfig(endorseClientConfig);
	}
}
