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

package com.locoupon.common.types.enums;

import static com.locoupon.common.util.Contracts.requires;
import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;

/**
 * All enums which implement this interface will be automatically deserialized to String value got from method getValue();
 */
//@AutoDeserializing
public interface EnumeratedType {
    Logger LOGGER = getLogger(EnumeratedType.class);
    // Priority defines order of enums. Default value is 1 - this is the highest priority enum can have.
    // Priority is mainly used in allocation algorithms to specify default selection of resources.
    Integer DEFAULT_PRIORITY = 1;

    static <T extends EnumeratedType> T stringToEnum(Class<T> enumType, String text) {
        T foundEnumeration = findEnum(enumType, text);
        if (foundEnumeration == null) {
            LOGGER.warn("No constant with text [{}] found for enum [{}]", text, enumType.getSimpleName());
        }

        return foundEnumeration;
    }

    static <T extends EnumeratedType> T stringToEnumStrictly(Class<T> enumType, String text) {
        T foundEnumeration = findEnum(enumType, text);
        if (foundEnumeration == null) {
            throw new IllegalArgumentException("No constant with text [" + text + "] found for enum ["
                + enumType.getSimpleName() + "]");
        }

        return foundEnumeration;
    }

    static <T extends EnumeratedType> T findEnum(Class<T> enumType, String text) {
        requires(enumType != null, "Type of enum (class) should not be null");
        requires(text != null, "Constant with null test for enum [" + enumType.getSimpleName() + "] is not allowed");

        for (T extension : enumType.getEnumConstants()) {
            if (text.equalsIgnoreCase(extension.getValue()) || extension.getAliases().contains(text)) {
                return extension;
            }
        }
        return null;
    }

    static <T extends EnumeratedType> Comparator<T> byPriority() {
        return comparing(T::getPriority);
    }

    String getValue();
    default List<String> getAliases() {
        return emptyList();
    }
    default Integer getPriority() {
        return DEFAULT_PRIORITY;
    }
}
