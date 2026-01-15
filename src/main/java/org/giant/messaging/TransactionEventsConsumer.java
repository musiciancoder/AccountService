package org.giant.messaging;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.giant.dto.TransferEvent;
import org.giant.repository.AuditRepository;

@ApplicationScoped
public class TransactionEventsConsumer {

    @Inject
    AuditRepository audit;

    @Incoming("events-transfers-completed")
    @Transactional
    public void onCompleted(TransferEvent evt) {
        if (audit.exists(evt.correlationId(), evt.type())) return; // idempotency
        audit.record(evt); // persist audit row with COMPLETED
    }

    @Incoming("events-transfers-failed")
    @Transactional
    public void onFailed(TransferEvent evt) {
        if (audit.exists(evt.correlationId(), evt.type())) return;
        audit.record(evt); // persist audit row with FAILED + reason
    }
}

