package com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.servcies;

import com.canal.technical.test.subscriber.domain.entity.Subscriber;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.repositories.models.SubscriberModel;

public class SubscriberServiceTest {
    protected static final String SUBSCRIBER_ID = "subscriber_id";

    protected Subscriber createSubscriber() {
        return new Subscriber(SUBSCRIBER_ID, "firstName", "lastName", "mail", "phoneNumber", true);
    }

    protected SubscriberModel fromSubscriber(Subscriber subscriber) {
        return new SubscriberModel(null, subscriber.subscriberId(), subscriber.firstName(), subscriber.lastName(), subscriber.mail(), subscriber.phoneNumber(), subscriber.isActive());
    }
}
