package org.giant.dto;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class TransferEventDeserializer extends ObjectMapperDeserializer<TransferEvent> {
    public TransferEventDeserializer() {
        super(TransferEvent.class);
    }
}
