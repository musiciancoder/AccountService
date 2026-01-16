package org.giant.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.giant.dto.TransferEvent;
import org.giant.resource.BalanceResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class AuditRepository {

    private final Logger logger = LoggerFactory.getLogger(BalanceResource.class);

    private final Set<String> processed = Collections.synchronizedSet(new HashSet<>());

    public boolean exists(String correlationId, String type) {
        logger.info("Checking audit for correlationId {} and type {}", correlationId, type);
        return processed.contains(key(correlationId, type));
    }

    public void record(TransferEvent evt) {
        logger.info("Recording audit for transaction id {} with type {}", evt.transactionId(), evt.type());
        processed.add(key(evt.correlationId(), evt.type()));
        // TODO: persist audit row (evt.transactionId, evt.type, evt.sourceAccount, evt.targetAccount, evt.amount, evt.reason)

    }
    private String key(String correlationId, String type) {
        return correlationId + ":" + type;
    }
}
