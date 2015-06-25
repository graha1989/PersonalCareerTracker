package com.pct.domain.enums.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.pct.domain.enums.AcademicCommunityContributionType;

public class AcademicCommunityContributionTypeEnumDeserializer extends JsonDeserializer<AcademicCommunityContributionType> {

	@Override
	public AcademicCommunityContributionType deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {

		JsonNode node = jp.getCodec().readTree(jp);
        String title = (String) ((TextNode) node.get("title")).textValue();

        AcademicCommunityContributionType type = AcademicCommunityContributionType.getByTitle(title);
		if (type != null) {
           return type;
        }
        throw new JsonMappingException("Invalid value for type.");
	}

}
