/*******************************************************************************
 * Copyright (c) 2011, 2016 Ericsson and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ericsson - initial API and implementation
 *     Marc Khouzam (Ericsson) - Added support for the different GDBControl versions (Bug 324101)
 *******************************************************************************/
package org.eclipse.cdt.debug.gdbjtag.core.dsf.gdb.service;

import org.eclipse.cdt.dsf.debug.service.command.ICommandControl;
import org.eclipse.cdt.dsf.gdb.service.GdbDebugServicesFactory;
import org.eclipse.cdt.dsf.gdb.service.command.CommandFactory_6_8;
import org.eclipse.cdt.dsf.mi.service.command.CommandFactory;
import org.eclipse.cdt.dsf.service.DsfSession;
import org.eclipse.debug.core.ILaunchConfiguration;

/**
 * @since 8.4
 */
public class GdbJtagDebugServicesFactory extends GdbDebugServicesFactory {

	/** @since 9.0 */
	public GdbJtagDebugServicesFactory(String version, ILaunchConfiguration config) {
		super(version, config);
	}	

	@Override
	protected ICommandControl createCommandControl(DsfSession session, ILaunchConfiguration config) {
		if (compareVersionWith(GDB_7_7_VERSION) >= 0) {
			return new GDBJtagControl_7_7(session, config, new CommandFactory_6_8());
		}
		if (compareVersionWith(GDB_7_4_VERSION) >= 0) {
			return new GDBJtagControl_7_4(session, config, new CommandFactory_6_8());
		}
		if (compareVersionWith(GDB_7_2_VERSION) >= 0) {
			return new GDBJtagControl_7_2(session, config, new CommandFactory_6_8());
		}
		if (compareVersionWith(GDB_7_0_VERSION) >= 0) {
			return new GDBJtagControl_7_0(session, config, new CommandFactory_6_8());
		}
		if (compareVersionWith(GDB_6_8_VERSION) >= 0) {
			return new GDBJtagControl(session, config, new CommandFactory_6_8());
		}
		return new GDBJtagControl(session, config, new CommandFactory());
	}
}
