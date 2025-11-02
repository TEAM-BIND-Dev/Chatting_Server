package com.teambind.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JsonUtil {
	private final ObjectMapper objectMapper;
	Logger log = LoggerFactory.getLogger(JsonUtil.class);
	
	public JsonUtil(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	
	public <T> Optional<T> fromJson(String json, Class<T> clazz) {
		try {
			return Optional.of(objectMapper.readValue(json, clazz));
		} catch (Exception e) {
			log.error("Failed to convert JSON to object: {}", e.getMessage());
			return Optional.empty();
		}
	}
	
	public Optional<String> toJson(Object object) {
		try {
			return Optional.of(objectMapper.writeValueAsString(object));
		} catch (Exception e) {
			log.error("Failed to convert object to JSON: {}", e.getMessage());
			return Optional.empty();
		}
	}
	
	
}
