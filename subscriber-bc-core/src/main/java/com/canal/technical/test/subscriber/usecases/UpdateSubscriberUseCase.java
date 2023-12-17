package com.canal.technical.test.subscriber.usecases;

import com.canal.technical.test.subscriber.domain.errors.SubscriberDatabaseException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberNotFoundException;
import com.canal.technical.test.subscriber.domain.ports.UpdateSubscriberService;

public class UpdateSubscriberUseCase {

    private final UpdateSubscriberService updateSubscriberService;


    public UpdateSubscriberUseCase(UpdateSubscriberService updateSubscriberService) {
        this.updateSubscriberService = updateSubscriberService;
    }

    public void execute(String subscriberId, String firstName, String lastName, String mail, String phoneNumber) throws SubscriberNotFoundException, SubscriberDatabaseException {
        updateSubscriberService.updateSubscriber(subscriberId, firstName, lastName, mail, phoneNumber);
    }
}
