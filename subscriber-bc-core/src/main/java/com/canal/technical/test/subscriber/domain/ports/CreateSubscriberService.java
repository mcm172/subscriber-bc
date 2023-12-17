package com.canal.technical.test.subscriber.domain.ports;

import com.canal.technical.test.subscriber.domain.entity.Subscriber;
import com.canal.technical.test.subscriber.domain.errors.SubscriberAlreadyExistsException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberDatabaseException;

public interface CreateSubscriberService {

    String createSubscriber(Subscriber subscriber) throws SubscriberAlreadyExistsException, SubscriberDatabaseException;
}
