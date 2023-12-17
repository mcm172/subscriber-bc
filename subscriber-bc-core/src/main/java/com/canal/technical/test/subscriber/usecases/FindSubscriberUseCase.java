package com.canal.technical.test.subscriber.usecases;

import com.canal.technical.test.subscriber.domain.entity.Subscriber;
import com.canal.technical.test.subscriber.domain.errors.SubscriberNotFoundException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberSearchIncorrectResultSizeException;
import com.canal.technical.test.subscriber.domain.ports.SearchSubscriberService;

public class FindSubscriberUseCase {

    private final SearchSubscriberService searchSubscriberService;


    public FindSubscriberUseCase(SearchSubscriberService searchSubscriberService) {
        this.searchSubscriberService = searchSubscriberService;
    }

    public Subscriber execute(String firstName, String lastName, String mail, String phoneNumber) throws SubscriberNotFoundException, SubscriberSearchIncorrectResultSizeException {
        return searchSubscriberService.findSubscriber(firstName, lastName, mail, phoneNumber);
    }
}
