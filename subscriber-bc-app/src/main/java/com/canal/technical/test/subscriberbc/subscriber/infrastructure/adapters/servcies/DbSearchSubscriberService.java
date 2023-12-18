package com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.servcies;

import com.canal.technical.test.subscriber.domain.entity.Subscriber;
import com.canal.technical.test.subscriber.domain.errors.SubscriberNotFoundException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberSearchIncorrectResultSizeException;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.repositories.SubscriberModelRepository;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.repositories.models.SubscriberModel;
import com.canal.technical.test.subscriber.domain.ports.SearchSubscriberService;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Optional;


public class DbSearchSubscriberService implements SearchSubscriberService {

    private final SubscriberModelRepository subscriberRepository;

    public DbSearchSubscriberService(SubscriberModelRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    public Subscriber getSubscriberBySubscriberId(String subscriberId) throws SubscriberNotFoundException {
        Optional<SubscriberModel> model = subscriberRepository.findBySubscriberId(subscriberId);
        if (model.isPresent()){
            return fromSubscriberModel(model.get());
        }

        throw new SubscriberNotFoundException("No subscriber with id: '" + subscriberId + "' exists", subscriberId);


    }

    @Override
    public Subscriber findSubscriber(String firstName, String lastName, String mail, String phoneNumber) throws SubscriberNotFoundException, SubscriberSearchIncorrectResultSizeException {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues()
                .withIgnorePaths("id")
                .withIgnorePaths("subscriberId")
                .withIgnorePaths("isActive");
        matcher = updateMatcher(matcher, firstName, "firstname");
        matcher = updateMatcher(matcher, lastName, "lastName");
        matcher = updateMatcher(matcher, mail, "mail");
        matcher = updateMatcher(matcher, phoneNumber, "phoneNumber");

        SubscriberModel example = new SubscriberModel(null, null, firstName, lastName, mail, phoneNumber, false);

        Optional<SubscriberModel> result;
        try {
            result = subscriberRepository.findOne(Example.of(example, matcher));
        } catch(IncorrectResultSizeDataAccessException ex){
            throw new SubscriberSearchIncorrectResultSizeException("More than one result found", ex,  Example.of(example, matcher).toString());
        }
        if (result.isPresent()){
            return fromSubscriberModel(result.get());
        }

        throw new SubscriberNotFoundException("No subscriber exists", "criteria");

    }

    private ExampleMatcher updateMatcher(ExampleMatcher matcher, String value, String propertyPath) {
        return value == null ? matcher.withIgnorePaths(propertyPath) : matcher;
    }



    private Subscriber fromSubscriberModel(SubscriberModel model){
        return  new Subscriber(model.getSubscriberId(), model.getFirstName(), model.getLastName(), model.getMail(), model.getPhoneNumber(), model.isActive());
    }
}
