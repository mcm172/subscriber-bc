package com.canal.technical.test.subscriber.usecases;

import com.canal.technical.test.subscriber.domain.errors.SubscriberDatabaseException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberNotFoundException;
import com.canal.technical.test.subscriber.domain.ports.UpdateSubscriberService;

public class CancelSubscriptionUseCase {

    private final UpdateSubscriberService updateSubscriberService;


    public CancelSubscriptionUseCase(UpdateSubscriberService updateSubscriberService) {
        this.updateSubscriberService = updateSubscriberService;
    }

    public void execute(String subscriberId) throws SubscriberNotFoundException, SubscriberDatabaseException {
        this.updateSubscriberService.cancelSubscription(subscriberId);
    }
}
