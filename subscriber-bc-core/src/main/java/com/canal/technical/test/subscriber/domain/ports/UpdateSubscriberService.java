package com.canal.technical.test.subscriber.domain.ports;

import com.canal.technical.test.subscriber.domain.errors.SubscriberDatabaseException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberNotFoundException;

public interface UpdateSubscriberService {
    void cancelSubscription(String subscriberId) throws SubscriberNotFoundException, SubscriberDatabaseException;

    void updateSubscriber(String subscriberId, String firstName, String lastName, String mail, String phoneNumber) throws SubscriberNotFoundException, SubscriberDatabaseException;


}
