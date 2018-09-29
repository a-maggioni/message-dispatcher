package it.intre.messagedispatcher.consumer;

import it.intre.messagedispatcher.model.Configuration;
import it.intre.messagedispatcher.model.Record;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class Consumer<K, V, R extends Record<K, V>> {

    protected final Logger logger = LogManager.getLogger(Consumer.class);

    public abstract List<R> receive();

    public abstract boolean commit();

}
