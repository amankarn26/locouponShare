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
package com.locoupon.common.types.serialization;

import static java.lang.reflect.Modifier.isAbstract;
import static java.util.stream.Collectors.toSet;

import java.util.Set;

import org.springframework.data.util.AnnotatedTypeScanner;

import com.locoupon.common.types.enums.AutoDeserializing;
import com.locoupon.common.types.enums.EnumeratedType;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EnumeratedTypeProvider {
	private static Set<Class<? extends EnumeratedType>> subTypes = scanForSubTypes();

	private static Set<Class<? extends EnumeratedType>> scanForSubTypes() {
		AnnotatedTypeScanner scanner = new AnnotatedTypeScanner(AutoDeserializing.class);
		String scannedPackageName = EnumeratedType.class.getPackage().getName();

		return scanner.findTypes(scannedPackageName).stream().filter(clazz -> !isAbstract(clazz.getModifiers()))
				.filter(EnumeratedType.class::isAssignableFrom).map(clazz -> (Class<? extends EnumeratedType>) clazz)
				.collect(toSet());
	}

	public static Set<Class<? extends EnumeratedType>> getSubTypes() {
		return subTypes;
	}
}
