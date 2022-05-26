package com.minsait.architecture.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExtraInfoConverter extends StdSerializer<JsonNode> {

	private static final long serialVersionUID = 9142262216981680247L;

	public ExtraInfoConverter() {
		this(null);
	}

	public ExtraInfoConverter(Class<JsonNode> t) {
		super(t);
	}

	@Override
	public void serialize(JsonNode jsonNode, JsonGenerator jsonGenerator, SerializerProvider provider)
			throws IOException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			final Object json = mapper.readValue(jsonNode.toString(), Object.class);
			jsonGenerator.writeObject(json);
		} catch (IOException e) {
			log.error("Error serializinng extraInfo", e);
		}
	}

}
