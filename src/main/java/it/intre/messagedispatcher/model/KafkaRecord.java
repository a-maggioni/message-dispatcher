package it.intre.messagedispatcher.model;

import java.util.Objects;

public class KafkaRecord<K, V> extends Record<K, V> {

    private String topic;

    public KafkaRecord(final String topic, final K key, final V value) {
        this.topic = topic;
        this.key = key;
        this.value = value;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KafkaRecord)) return false;
        KafkaRecord<?, ?> kafkaRecord = (KafkaRecord<?, ?>) o;
        return Objects.equals(getTopic(), kafkaRecord.getTopic()) &&
                Objects.equals(getKey(), kafkaRecord.getKey()) &&
                Objects.equals(getValue(), kafkaRecord.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTopic(), getKey(), getValue());
    }

    @Override
    public String toString() {
        return "KafkaRecord{" +
                "topic='" + topic + '\'' +
                ", key=" + key +
                ", value=" + value +
                '}';
    }
}
