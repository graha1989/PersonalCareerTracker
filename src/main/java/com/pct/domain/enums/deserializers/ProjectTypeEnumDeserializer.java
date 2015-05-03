package com.pct.domain.enums.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.pct.domain.enums.ProjectType;

public class ProjectTypeEnumDeserializer extends JsonDeserializer<ProjectType> {

	@Override
	public ProjectType deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {

		JsonNode node = jp.getCodec().readTree(jp);
        String title = (String) ((TextNode) node.get("title")).textValue();

        ProjectType type = ProjectType.getByTitle(title);
		if (type != null) {
           return type;
        }
        throw new JsonMappingException("Invalid value for type.");
	}

}
