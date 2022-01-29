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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

import static java.util.Objects.nonNull;

public class PositiveIntegerDeserializer extends JsonDeserializer<Integer> {
	@Override
	public Integer deserialize(JsonParser parser, DeserializationContext context) throws IOException {
		Integer value = parser.readValueAs(Integer.class);
		if (nonNull(value) && value <= 0) {
			throw new JsonParseException("Only positive integer value is expected for " + parser.getCurrentName(),
					parser.getCurrentLocation());
		}

		return value;
	}
}
