/*******************************************************************************
 * Copyright (c) 2007, 2010 Intel Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Intel Corporation - initial API and implementation
 *     LSI Corporation	 - added symmetric project clean action
 *******************************************************************************/
package org.eclipse.cdt.managedbuilder.ui.actions;

import org.eclipse.cdt.managedbuilder.core.IConfiguration;
import org.eclipse.cdt.managedbuilder.core.ManagedBuildManager;
import org.eclipse.cdt.managedbuilder.internal.ui.actions.CommonBuildCleanAllAction;
import org.eclipse.cdt.managedbuilder.ui.properties.Messages;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Action which changes active build configuration of the current project to
 * the given one.
 *
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public class BuildAllAction extends CommonBuildCleanAllAction {
	/** @since 7.0 */
	@Override
	protected String getTIP_ALL() { return Messages.getString("BuildAllAction.0");}//$NON-NLS-1$
	/** @since 7.0 */
	@Override
	protected String getLBL_ALL() { return Messages.getString("BuildAllAction.1");}//$NON-NLS-1$
	/** @since 7.0 */
	@Override
	protected String getJOB_MSG() { return Messages.getString("BuildAllAction.2");}//$NON-NLS-1$
	/** @since 7.0 */
	@Override
	protected String getERR_MSG() { return Messages.getString("BuildAllAction.3");}//$NON-NLS-1$
	/** @since 7.0 */
	@Override
	protected String getLBL_SEL() { return Messages.getString("BuildAllAction.4");}//$NON-NLS-1$
	/** @since 7.0 */
	@Override
	protected String getTIP_SEL() {	return Messages.getString("BuildAllAction.5");}//$NON-NLS-1$
	/** @since 7.0 */
	@Override
	protected String getDLG_TEXT(){ return Messages.getString("BuildAllAction.6"); }//$NON-NLS-1$
	/** @since 7.0 */
	@Override
	protected String getDLG_TITLE(){ return Messages.getString("BuildAllAction.7");}//$NON-NLS-1$
	/** @since 7.0 */
	@Override
	protected void performAction(IConfiguration[] configs,
			IProgressMonitor monitor) throws CoreException {
		ManagedBuildManager.buildConfigurations(configs, monitor);
	}
}

