/*********************************************************************************************
*
* Copyright © 2020  Drut Technologies, Inc. All Rights Reserved. Proprietary and Confidential
*
* This confidential and proprietary software may be used only as expressly authorized by a
* licensing and specific agreement from DRUT.  In the event that DRUT authorizes any copying,
* distribution or publication of this software, then this notice must be reproduced in full
* on all such authorized copies.
*
**********************************************************************************************
*/

package com.locoupon.common.rest.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

@Component
public class StartupConnectivityService implements SmartLifecycle {

	private static final Logger LOGGER = LoggerFactory.getLogger(StartupConnectivityService.class);
	
	@Override
	public void start() {
		LOGGER.info("Application is now ready!");

	}

	@Override
	public void stop() {
		LOGGER.info("Application is now shutting down!");
	}

	@Override
	public boolean isRunning() {
		return false;
	}

	/**
	 * This is the most important method. Returning Integer.MAX_VALUE only suggests
	 * that we will be the first bean to shutdown and last bean to start
	 */
	@Override
	public int getPhase() {
		return Integer.MAX_VALUE;
	}
}

