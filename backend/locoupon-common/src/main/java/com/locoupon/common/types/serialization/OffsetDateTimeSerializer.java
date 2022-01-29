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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializes OffsetDateTime class to JSON String class with date format
 * "yyyy-MM-dd'T'HH:mm:ssxxx" Example: "2015-04-09T11:00:00+00:00"
 */
public class OffsetDateTimeSerializer extends JsonSerializer<OffsetDateTime> {
	public static final OffsetDateTimeSerializer INSTANCE = new OffsetDateTimeSerializer();

	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssxxx";

	@Override
	public void serialize(OffsetDateTime time, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException {
		jsonGenerator.writeString(time.format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
	}
}
