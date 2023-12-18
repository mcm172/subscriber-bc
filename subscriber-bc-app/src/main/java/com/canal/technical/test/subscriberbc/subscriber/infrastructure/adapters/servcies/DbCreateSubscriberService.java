package com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.servcies;

import com.canal.technical.test.subscriber.domain.entity.Subscriber;
import com.canal.technical.test.subscriber.domain.errors.SubscriberAlreadyExistsException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberDatabaseException;
import com.canal.technical.test.subscriber.domain.ports.CreateSubscriberService;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.repositories.SubscriberModelRepository;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.repositories.models.SubscriberModel;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;


public class DbCreateSubscriberService implements CreateSubscriberService {

    private final SubscriberModelRepository subscriberRepository;

    public DbCreateSubscriberService(SubscriberModelRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    public String createSubscriber(Subscriber subscriber) throws SubscriberAlreadyExistsException, SubscriberDatabaseException {

        Optional<SubscriberModel> existingModel = subscriberRepository.findBySubscriberId(subscriber.subscriberId());
        if (existingModel.isPresent()) {
            throw new SubscriberAlreadyExistsException("Entity already exists", subscriber.subscriberId());
        }
        try {
            SubscriberModel model = subscriberRepository.save(fromSubscriber(subscriber));
            return model.getSubscriberId();
        } catch (DataIntegrityViolationException ex) {
            throw new SubscriberDatabaseException("Error on update/persist", ex, subscriber.subscriberId());
        }
    }

    private SubscriberModel fromSubscriber(Subscriber subscriber) {
        return new SubscriberModel(null, subscriber.subscriberId(), subscriber.firstName(), subscriber.lastName(), subscriber.mail(), subscriber.phoneNumber(), subscriber.isActive());
    }
}
