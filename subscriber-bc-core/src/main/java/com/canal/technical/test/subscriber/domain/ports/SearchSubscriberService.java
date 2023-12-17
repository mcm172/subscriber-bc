package com.canal.technical.test.subscriber.domain.ports;

import com.canal.technical.test.subscriber.domain.entity.Subscriber;
import com.canal.technical.test.subscriber.domain.errors.SubscriberNotFoundException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberSearchIncorrectResultSizeException;

public interface SearchSubscriberService {
    Subscriber getSubscriberBySubscriberId(String subscriberId) throws SubscriberNotFoundException;
    Subscriber findSubscriber(String firstName, String lastName, String mail, String phoneNumber) throws SubscriberNotFoundException, SubscriberSearchIncorrectResultSizeException;
}
