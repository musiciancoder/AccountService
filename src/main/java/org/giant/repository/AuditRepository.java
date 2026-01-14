package org.giant.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.giant.dto.TransferEvent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class AuditRepository {
    private final Set<String> processed = Collections.synchronizedSet(new HashSet<>());

    public boolean exists(String correlationId, String type) {
        return processed.contains(key(correlationId, type));
    }

    public void record(TransferEvent evt) {
        processed.add(key(evt.correlationId(), evt.type()));
        // TODO: persist audit row (evt.transactionId, evt.type, evt.sourceAccount, evt.targetAccount, evt.amount, evt.reason)
    }
    private String key(String correlationId, String type) {
        return correlationId + ":" + type;
    }
}
