package com.locoupon.common.enums;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OSCheck {

	private static final Logger logger = LoggerFactory.getLogger(OSCheck.class);
	/**
	 * types of Operating Systems
	 */
	public enum OSType {
		Windows, MacOS, Linux, Other
	};

	// cached result of OS detection
	protected static OSType detectedOS;

	/**
	 * detect the operating system from the os.name System property and cache
	 * the result
	 * 
	 * @returns - the operating system detected
	 */
	public static OSType getOperatingSystemType() {
		if (detectedOS == null) {
			String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
			logger.debug("OS:"+OS);
			if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
				detectedOS = OSType.MacOS;
			} else if (OS.indexOf("win") >= 0) {
				detectedOS = OSType.Windows;
			} else if (OS.indexOf("nux") >= 0 || OS.indexOf("cent") >= 0) {
				detectedOS = OSType.Linux;
			} else {
				detectedOS = OSType.Other;
			}
		}
		return detectedOS;
	}
}
