package com.canal.technical.test.subscriber.usecases;

import com.canal.technical.test.subscriber.domain.entity.Subscriber;
import com.canal.technical.test.subscriber.domain.errors.SubscriberNotFoundException;
import com.canal.technical.test.subscriber.domain.ports.SearchSubscriberService;

public class GetSubscriberUseCase {
    private final SearchSubscriberService searchSubscriberService;

    public GetSubscriberUseCase(SearchSubscriberService searchSubscriberService) {
        this.searchSubscriberService = searchSubscriberService;
    }

    public Subscriber execute(String subscriberId) throws SubscriberNotFoundException {
        return searchSubscriberService.getSubscriberBySubscriberId(subscriberId);
    }
}
