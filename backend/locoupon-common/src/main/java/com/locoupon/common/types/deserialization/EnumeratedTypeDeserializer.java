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

package com.locoupon.common.types.deserialization;

import static com.locoupon.common.types.enums.EnumeratedType.stringToEnum;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.locoupon.common.types.enums.EnumeratedType;

public class EnumeratedTypeDeserializer<T extends EnumeratedType> extends JsonDeserializer<T> {
    private final Class<T> enumType;

    public EnumeratedTypeDeserializer(Class<T> enumType) {
        this.enumType = enumType;
    }

    @Override
    public T deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String text = parser.getValueAsString();
        // Allow null as value
        if (text == null) {
            return null;
        }

        return stringToEnum(enumType, text);
    }
}
