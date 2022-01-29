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
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.NonNull;

import java.io.IOException;
import java.util.Optional;

public class OptionalSerializer extends StdSerializer<Optional> {
	public static final long serialVersionUID = -5019224060053654064L;

	@SuppressWarnings("unchecked")
	public OptionalSerializer(Class clazz) {
		super(clazz);
	}

	@Override
	public void serialize(@NonNull Optional value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		// to skip a specific property (field name and value), use
		// @JsonInclude(JsonInclude.Inclusion.NON_DEFAULT) on this property
		if (value.isPresent()) {
			jgen.writeObject(value.get());
		} else {
			jgen.writeNull();
		}
	}
}
