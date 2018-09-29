package it.intre.messagedispatcher.model;

public abstract class Configuration {

    protected String host;

    protected String port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
