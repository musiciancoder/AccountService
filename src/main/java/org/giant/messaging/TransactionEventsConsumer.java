package org.giant.messaging;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.giant.dto.TransferEvent;
import org.giant.repository.AuditRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TransactionEventsConsumer {

    @Inject
    AuditRepository audit;

    private final Logger logger = LoggerFactory.getLogger(TransactionEventsConsumer.class);

    @Incoming("events-transfers-completed")
    @Transactional
    public void onCompleted(TransferEvent evt) {
        logger.info("Received COMPLETED event for transaction id {}", evt.transactionId());
        if (audit.exists(evt.correlationId(), evt.type())) return; // idempotency
        audit.record(evt); // persist audit row with COMPLETED
        logger.info("Processed COMPLETED event for transaction id {}", evt.transactionId());
    }

    @Incoming("events-transfers-failed")
    @Transactional
    public void onFailed(TransferEvent evt) {
        logger.info("Received FAILED event for transaction id {} with reason {}", evt.transactionId(), evt.reason());
        if (audit.exists(evt.correlationId(), evt.type())) return;
        audit.record(evt); // persist audit row with FAILED + reason
        logger.info("Processed FAILED event for transaction id {}", evt.transactionId());
    }
}

