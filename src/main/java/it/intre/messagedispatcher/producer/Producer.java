package it.intre.messagedispatcher.producer;

import it.intre.messagedispatcher.model.Record;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Producer<K, V, R extends Record<K, V>> {

    protected final Logger logger = LogManager.getLogger(Producer.class);

    public abstract boolean send(R record);

}
