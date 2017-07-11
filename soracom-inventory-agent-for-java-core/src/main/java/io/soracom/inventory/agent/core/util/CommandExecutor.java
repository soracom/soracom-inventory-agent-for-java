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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.soracom.inventory.agent.core.exception.SORACOMInventoryAgentRuntimeException;

/**
 * Execute external command.
 * 
 * @author c9katayama
 *
 */
public class CommandExecutor {

	private static final Logger log = LoggerFactory.getLogger(CommandExecutor.class);

	public static class CommandProcess {
		private Process process;
		private Thread processThread;

		public void execCmd(String[] cmd) throws IOException {
			process = new ProcessBuilder().command(cmd).start();
			log.info("command process is running.");
			processThread = new Thread(new Runnable() {
				@Override
				public void run() {
					execCmd(process);
				}
			});
			processThread.start();
		}

		private void execCmd(Process process) {

			try (OutputStream out = process.getOutputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
					BufferedReader ebr = new BufferedReader(new InputStreamReader(process.getErrorStream()));) {
				ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
				newFixedThreadPool.submit(new Runnable() {
					public void run() {
						try {
							String line = null;
							while ((line = br.readLine()) != null) {
								log.info(line);
							}
						} catch (Exception e) {
						}
					}
				});
				newFixedThreadPool.submit(new Runnable() {
					public void run() {
						try {
							String errorLine = null;
							while ((errorLine = ebr.readLine()) != null) {
								log.error(errorLine);
							}
						} catch (Exception e) {
						}
					}
				});
				newFixedThreadPool.shutdown();
				int result = process.waitFor();
				log.info("command process is finished. result=" + result);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			killProcess();
		}

		public void killProcess() {
			try {
				if (process != null) {
					process.destroy();
					log.info("command process is killed.");
					process = null;
				} else {
					log.info("command process was killed.");
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public static CommandProcess execute(String[] commands) {
		try {
			CommandProcess cmd = new CommandProcess();
			cmd.execCmd(commands);
			return cmd;
		} catch (IOException ioe) {
			throw new SORACOMInventoryAgentRuntimeException(ioe);
		}

	}

	public static CommandProcess execute(List<String> commands) {
		return execute(commands.toArray(new String[] {}));
	}
}
