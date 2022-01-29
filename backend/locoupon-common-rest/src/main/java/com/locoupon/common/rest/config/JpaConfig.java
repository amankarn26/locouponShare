package com.locoupon.common.rest.config;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS;
import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES;
import static com.fasterxml.jackson.databind.MapperFeature.SORT_PROPERTIES_ALPHABETICALLY;
import static com.fasterxml.jackson.databind.PropertyNamingStrategy.UPPER_CAMEL_CASE;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.locoupon.common.service.NorthboundObjectMapperModuleProvider;

@Configuration
@EntityScan(basePackages = { "com.locoupon.common.entity" })
@ComponentScan({ "com.locoupon.common", "com.locoupon.common.service" })
@EnableJpaRepositories({ "com.locoupon.common.service" })
@EnableAutoConfiguration
public class JpaConfig {

	@Bean
	public Jackson2ObjectMapperBuilder jacksonBuilder() {
		Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder = new Jackson2ObjectMapperBuilder();
		NorthboundObjectMapperModuleProvider mapperModuleProvider = new NorthboundObjectMapperModuleProvider();

		jackson2ObjectMapperBuilder.propertyNamingStrategy(UPPER_CAMEL_CASE).serializationInclusion(Include.NON_NULL)
				.serializersByType(mapperModuleProvider.getSerializers())
				.deserializersByType(mapperModuleProvider.getDeserializers())
				.featuresToEnable(INDENT_OUTPUT, FAIL_ON_UNKNOWN_PROPERTIES, ACCEPT_CASE_INSENSITIVE_ENUMS,
						ACCEPT_CASE_INSENSITIVE_PROPERTIES)
				.featuresToDisable(WRITE_DATES_AS_TIMESTAMPS, WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS,
						SORT_PROPERTIES_ALPHABETICALLY, FAIL_ON_EMPTY_BEANS)
				.indentOutput(true);

		return jackson2ObjectMapperBuilder;
	}
}
