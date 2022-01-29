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

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.locoupon.common.types.enums.EnumeratedType;

public class EnumeratedTypeSerializer<T extends EnumeratedType> extends JsonSerializer<T> {
	@Override
	public void serialize(T value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.getValue());
	}
}
