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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.soracom.endorse.common.ITextLogListener;
import io.soracom.endorse.common.TextLogItem;

public class KryptonLogListener implements ITextLogListener {

	private static final Logger log = LoggerFactory.getLogger(KryptonLogListener.class);

	@Override
	public void itemAdded(TextLogItem item) {
		switch (item.getType()) {
		case DEBUG:
			log.debug(item.getDescription());
			break;
		case LOG:
			log.info(item.getDescription());
			break;
		case WARN:
			log.warn(item.getDescription());
			break;
		case ERR:
			log.error(item.getDescription());
			break;
		}
	}
}
