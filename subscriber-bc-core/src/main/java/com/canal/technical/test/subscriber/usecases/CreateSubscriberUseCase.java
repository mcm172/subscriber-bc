package com.canal.technical.test.subscriber.usecases;


import com.canal.technical.test.subscriber.domain.entity.Subscriber;
import com.canal.technical.test.subscriber.domain.errors.SubscriberAlreadyExistsException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberDatabaseException;
import com.canal.technical.test.subscriber.domain.ports.CreateSubscriberService;

import java.util.UUID;

public class CreateSubscriberUseCase {

    private final CreateSubscriberService createSubscriberService;

    public CreateSubscriberUseCase(CreateSubscriberService createSubscriberService) {
        this.createSubscriberService = createSubscriberService;
    }

    public String execute(String id,
                          String firstName,
                          String lastName,
                          String mail,
                          String phoneNumber) throws SubscriberAlreadyExistsException, SubscriberDatabaseException {
        if(id == null || id.isEmpty() || id.isBlank()){
            id = UUID.randomUUID().toString();
        }
        Subscriber subscriber = new Subscriber(id,
                firstName,
                lastName,
                mail,
                phoneNumber, true);

        return createSubscriberService.createSubscriber(subscriber);
    }
}
