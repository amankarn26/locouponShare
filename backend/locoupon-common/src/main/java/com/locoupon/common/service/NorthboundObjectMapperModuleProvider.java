package com.locoupon.common.service;

import static com.locoupon.common.types.serialization.EnumeratedTypeProvider.getSubTypes;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.locoupon.common.types.Id;
import com.locoupon.common.types.deserialization.BooleanDeserializer;
import com.locoupon.common.types.deserialization.StrictEnumeratedTypeDeserializer;
import com.locoupon.common.types.serialization.EnumeratedTypeSerializer;
import com.locoupon.common.types.serialization.IdSerializer;

public class NorthboundObjectMapperModuleProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(NorthboundObjectMapperModuleProvider.class);

	public Map<Class<?>, JsonSerializer<?>> getSerializers() {
		Map<Class<?>, JsonSerializer<?>> jsonSerializers = new LinkedHashMap<>();

		for (Class subType : getSubTypes()) {
			jsonSerializers.put(subType, new EnumeratedTypeSerializer<>());
		}

		jsonSerializers.put(Id.class, IdSerializer.INSTANCE);

		return jsonSerializers;
	}

	@SuppressWarnings({ "unchecked" })
	public Map<Class<?>, JsonDeserializer<?>> getDeserializers() {
		Map<Class<?>, JsonDeserializer<?>> jsonDeserializers = new LinkedHashMap<>();

		for (Class subType : getSubTypes()) {

			jsonDeserializers.put(subType, new StrictEnumeratedTypeDeserializer<>(subType));
			LOGGER.debug("Registering Deserializer for :{} using:{}", subType.getCanonicalName(),
					jsonDeserializers.get(subType));
		}

		jsonDeserializers.put(Boolean.class, BooleanDeserializer.INSTANCE);

		return jsonDeserializers;
	}
}
