package it.intre.messagedispatcher.consumer;

import it.intre.messagedispatcher.common.serialization.ObjectDeserializer;
import it.intre.messagedispatcher.model.KafkaConfiguration;
import it.intre.messagedispatcher.model.KafkaRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.*;

public class KafkaConsumer<K, V> extends Consumer<K, V, KafkaRecord<K, V>> {

    private KafkaConfiguration kafkaConfiguration;
    private org.apache.kafka.clients.consumer.KafkaConsumer<K, V> consumer;

    public KafkaConsumer(final KafkaConfiguration kafkaConfiguration, final Class<K> keyClass, final Class<V> valueClass) {
        this.kafkaConfiguration = kafkaConfiguration;
        Properties properties = new Properties();
        properties.put("bootstrap.servers", kafkaConfiguration.getBootstrapServer());
        properties.put("group.id", kafkaConfiguration.getGroupId());
        properties.put("client.id", kafkaConfiguration.getClientId() + UUID.randomUUID().toString());
        properties.put("auto.commit.enable", false);
        properties.put("key.deserializer", ObjectDeserializer.class);
        properties.put("value.deserializer", ObjectDeserializer.class);
        properties.put("keyClass", keyClass);
        properties.put("valueClass", valueClass);
        this.consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(properties);
        this.consumer.subscribe(Collections.singletonList(kafkaConfiguration.getTopic()));
    }

    @Override
    public List<KafkaRecord<K, V>> receive() {
        List<KafkaRecord<K, V>> recordList = new ArrayList<>();
        try {
            this.logger.trace("Receiving records...");
            ConsumerRecords<K, V> records = this.consumer.poll(Duration.ofSeconds(1L));
            for (ConsumerRecord<K, V> record : records) {
                recordList.add(new KafkaRecord<>(this.kafkaConfiguration.getTopic(), record.key(), record.value()));
            }
            this.logger.trace("Records received: {}", recordList);
        } catch (Exception e) {
            this.logger.error("receive exception:", e);
        }
        return recordList;
    }

    @Override
    public boolean commit() {
        try {
            this.consumer.commitSync();
            return true;
        } catch (Exception e) {
            this.logger.error("commit exception:", e);
            return false;
        }
    }

}
