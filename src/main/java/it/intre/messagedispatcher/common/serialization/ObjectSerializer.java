package it.intre.messagedispatcher.common.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ObjectSerializer<O> implements Serializer<O> {

    private ObjectMapper objectMapper;

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> config, boolean isKey) {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public byte[] serialize(String topic, O object) {
        if (object == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (Exception e) {
            throw new SerializationException("Error serializing object", e);
        }
    }

}
