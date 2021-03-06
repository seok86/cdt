/*******************************************************************************
 * Copyright (c) 2011, 2015 Anton Gorenkov 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Anton Gorenkov - initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.testsrunner.internal.tap;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;


/**
 * The activator class controls the plug-in life cycle
 */
public class TAPTestsRunnerPlugin extends Plugin {

	/** The plug-in ID .*/
	public static final String PLUGIN_ID = "org.eclipse.cdt.testsrunner.tap"; //$NON-NLS-1$

	/** Plug-in instance. */
	private static TAPTestsRunnerPlugin plugin;


	public TAPTestsRunnerPlugin() {
		super();
		plugin = this;
	}

	/**
	 * Returns the TAP Tests Runner provider plug-in instance.
	 * 
	 * @return the plug-in instance
	 */
	public static TAPTestsRunnerPlugin getDefault() {
		return plugin;
	}

	/** Convenience method which returns the unique identifier of this plugin. */
	public static String getUniqueIdentifier() {
		return PLUGIN_ID;
	}

	/**
	 * Logs the specified status with this plug-in's log.
	 * 
	 * @param status status to log
	 */
	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}
	
	/**
	 * Logs an internal error with the specified throwable
	 * 
	 * @param e the exception to be logged
	 */
	public static void log(Throwable e) {
		log(new Status(IStatus.ERROR, getUniqueIdentifier(), IStatus.ERROR, e.getMessage(), e));
	}

}
