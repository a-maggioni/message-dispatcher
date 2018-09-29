package it.intre.messagedispatcher.common.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ObjectDeserializer<O> implements Deserializer<O> {

    private ObjectMapper objectMapper;
    private Class<O> objectClass;

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> config, boolean isKey) {
        this.objectMapper = new ObjectMapper();
        this.objectClass = (Class<O>) (isKey ? config.get("keyClass") : config.get("valueClass"));
    }

    @Override
    public O deserialize(String topic, byte[] objectBytes) {
        try {
            return objectMapper.readValue(objectBytes, this.objectClass);
        } catch (Exception e1) {
            try {
                return this.objectClass.getDeclaredConstructor(byte[].class).newInstance(objectBytes);
            } catch (Exception e2) {
                throw new SerializationException("Error deserializing object", e2);
            }
        }
    }

}
