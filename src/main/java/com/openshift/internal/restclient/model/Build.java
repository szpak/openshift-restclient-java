/*******************************************************************************
 * Copyright (c) 2015 Red Hat, Inc. Distributed under license by Red Hat, Inc.
 * All rights reserved. This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Red Hat, Inc.
 ******************************************************************************/
package com.openshift.internal.restclient.model;

import java.util.Map;

import org.jboss.dmr.ModelNode;

import com.openshift.internal.restclient.capability.CapabilityInitializer;
import com.openshift.restclient.IClient;
import com.openshift.restclient.model.IBuild;

/**
 * @author Jeff Cantrill
 */
public class Build extends KubernetesResource implements IBuild{
	
	private static final String BUILD_MESSAGE = "status.message";
	private static final String BUILD_PODNAME = "podName";
	private static final String BUILD_STATUS = "status.phase";
	private static final String BUILD_STATUS_CANCELLED = "status.cancelled";
	
	private static final String COMPLETE = "Complete";
	private static final String FAILED = "Failed";
	private static final String CANCELLED = "Cancelled";


	public Build(ModelNode node, IClient client, Map<String, String []> propertyKeys) {
		super(node, client, propertyKeys);
		CapabilityInitializer.initializeCapabilities(getModifiableCapabilities(), this, client);
	}

	@Override
	public String getStatus() {
		return asString(BUILD_STATUS);
	}

	@Override
	public String getMessage() {
		return asString(BUILD_MESSAGE);
	}

	@Override
	public String getPodName() {
		return asString(BUILD_PODNAME);
	}

	@Override
	public boolean cancel() {
		String currentStatus = getStatus();
		if (!currentStatus.equalsIgnoreCase(COMPLETE) && !currentStatus.equalsIgnoreCase(FAILED) && !currentStatus.equalsIgnoreCase(CANCELLED)) {
			set(BUILD_STATUS_CANCELLED, true);
			return true;
		}
		return false;
	}

	
}
