package com.botplatform.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JsonWrapper {

    private final ObjectMapper mapper;

    public JsonWrapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String getProperty(String json, String property) {
        try {
            var props = property.split("\\.");
            var node = mapper.readTree(json);

            for (int i = 0; i < props.length; i++) {
                node = node.get(props[i]);
                if (node == null) {
                    return null;
                }
            }

            return node.asText();

        } catch (JsonProcessingException e) {
            log.error("Unexpected error while looking for a property {} - {}", property, e.getMessage());
            return null;
        }
    }
}
