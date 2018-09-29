package it.intre.messagedispatcher.model;

public class KafkaConfiguration extends Configuration {

    private String groupId;

    private String clientId;

    private String topic;

    public KafkaConfiguration(final String host, final String port, final String groupId, final String clientId, final String topic) {
        this.host = host;
        this.port = port;
        this.groupId = groupId;
        this.clientId = clientId;
        this.topic = topic;
    }

    public String getBootstrapServer() {
        return this.host + ":" + this.port;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
