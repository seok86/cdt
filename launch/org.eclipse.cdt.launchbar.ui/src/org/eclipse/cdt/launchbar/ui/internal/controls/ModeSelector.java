/*******************************************************************************
 * Copyright (c) 2014 QNX Software Systems and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Doug Schaefer
 *******************************************************************************/
package org.eclipse.cdt.launchbar.ui.internal.controls;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.launchbar.core.ILaunchBarManager;
import org.eclipse.cdt.launchbar.core.ILaunchConfigurationDescriptor;
import org.eclipse.cdt.launchbar.ui.internal.Activator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchMode;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.ILaunchGroup;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

@SuppressWarnings("restriction")
public class ModeSelector extends CSelector {

	private static final ISelection nullSelection = new StructuredSelection("---");

	public ModeSelector(Composite parent, int style) {
		super(parent, style);

		setContentProvider(new IStructuredContentProvider() {
			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
			@Override
			public void dispose() {
			}			
			@Override
			public Object[] getElements(Object inputElement) {
				try {
					return getManager().getLaunchModes();
				} catch (CoreException e) {
					Activator.log(e);
					return new Object[0];
				}
			}
		});

		setLabelProvider(new LabelProvider() {
			private Map<ImageDescriptor, Image> images = new HashMap<>();
			@Override
			public void dispose() {
				super.dispose();
				for (Image image : images.values()) {
					image.dispose();
				}
			}
			@Override
			public Image getImage(Object element) {
				ILaunchConfigurationDescriptor config = getManager().getActiveLaunchConfigurationDescriptor();
				if (config != null && element instanceof ILaunchMode) {
					ILaunchMode mode = (ILaunchMode) element;
					try {
						ILaunchGroup group = DebugUIPlugin.getDefault().getLaunchConfigurationManager()
								.getLaunchGroup(config.getLaunchConfigurationType(), mode.getIdentifier());
						if (group != null) {
							ImageDescriptor imageDesc = group.getImageDescriptor();
							Image image = images.get(imageDesc);
							if (image == null) {
								image = imageDesc.createImage();
								images.put(imageDesc, image);
							}
							return image;
						}
					} catch (CoreException e) {
						Activator.log(e);
					}
				}
				return super.getImage(element);
			}
			@Override
			public String getText(Object element) {
				ILaunchConfigurationDescriptor config = getManager().getActiveLaunchConfigurationDescriptor();
				if (config != null && element instanceof ILaunchMode) {
					ILaunchMode mode = (ILaunchMode) element;
					try {
						ILaunchGroup group = DebugUIPlugin.getDefault().getLaunchConfigurationManager()
								.getLaunchGroup(config.getLaunchConfigurationType(), mode.getIdentifier());
						if (group != null) {
							return group.getLabel().replace("&", "");
						}
					} catch (CoreException e) {
						Activator.log(e);
					}
				}
				return super.getText(element);
			}
		});

		setSorter(new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				if (o1 instanceof ILaunchMode && o2 instanceof ILaunchMode) {
					String mode1 = ((ILaunchMode)o1).getIdentifier();
					String mode2 = ((ILaunchMode)o2).getIdentifier();
					// run comes first, then debug, then the rest
					if (mode1.equals("run")) {
						if (mode2.equals("run"))
							return 0;
						else
							return -1;
					}
					if (mode2.equals("run"))
						return 1;
					if (mode1.equals("debug")) {
						if (mode2.equals("debug"))
							return 0;
						else
							return -1;
					}
					if (mode2.equals("debug"))
						return 1;
				}
				return 0;
			}
		});
		
		setToolTipText("Launch configuration");
		addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				Object selected = getSelection().getFirstElement();
				if (selected instanceof ILaunchMode) {
					ILaunchMode mode = (ILaunchMode) selected;
					getManager().setActiveLaunchMode(mode);
				}
			}
		});

	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		return super.computeSize(150, hHint, changed);
	}

	private ILaunchBarManager getManager() {
		return (ILaunchBarManager) getInput();
	}

	@Override
	public void setSelection(ISelection selection) {
		if (selection == null)
			super.setSelection(nullSelection);
		else
			super.setSelection(selection);
	}
	
}