package it.intre.messagedispatcher.producer;

import it.intre.messagedispatcher.common.serialization.ObjectSerializer;
import it.intre.messagedispatcher.model.KafkaConfiguration;
import it.intre.messagedispatcher.model.KafkaRecord;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.UUID;

public class KafkaProducer<K, V> extends Producer<K, V, KafkaRecord<K, V>> {

    private KafkaConfiguration kafkaConfiguration;
    private org.apache.kafka.clients.producer.KafkaProducer<K, V> producer;

    public KafkaProducer(KafkaConfiguration kafkaConfiguration) {
        this.kafkaConfiguration = kafkaConfiguration;
        Properties properties = new Properties();
        properties.put("bootstrap.servers", kafkaConfiguration.getBootstrapServer());
        properties.put("client.id", kafkaConfiguration.getClientId() + UUID.randomUUID().toString());
        properties.put("key.serializer", ObjectSerializer.class);
        properties.put("value.serializer", ObjectSerializer.class);
        this.producer = new org.apache.kafka.clients.producer.KafkaProducer<>(properties);
    }

    @Override
    public boolean send(KafkaRecord<K, V> record) {
        try {
            this.logger.trace("Sending record: {}", record);
            this.producer.send(new ProducerRecord<>(record.getTopic(), record.getKey(), record.getValue())).get();
            this.logger.trace("Record sent");
            return true;
        } catch (Exception e) {
            this.logger.error("send exception:", e);
            return false;
        }
    }
}
