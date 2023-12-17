package com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.servcies;

import com.canal.technical.test.subscriber.domain.errors.SubscriberDatabaseException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberNotFoundException;
import com.canal.technical.test.subscriber.domain.ports.UpdateSubscriberService;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.repositories.SubscriberModelRepository;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.repositories.models.SubscriberModel;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DbUpdateSubscriberService implements UpdateSubscriberService {

    private final SubscriberModelRepository subscriberRepository;

    public DbUpdateSubscriberService(SubscriberModelRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    public void cancelSubscription(String subscriberId) throws SubscriberNotFoundException, SubscriberDatabaseException {
        Optional<SubscriberModel> existingModel = subscriberRepository.findBySubscriberId(subscriberId);
        if (existingModel.isPresent()){
            SubscriberModel model = existingModel.get();
            model.setActive(false);
            performUpdate(model);
            return;
        }
        throw new SubscriberNotFoundException("Not entity with subscriberId: '" + subscriberId + "' exists", subscriberId);

    }

    private void performUpdate(SubscriberModel model) throws SubscriberDatabaseException {
        try {
            subscriberRepository.save(model);
        } catch (DataIntegrityViolationException ex) {
            throw new SubscriberDatabaseException("Error on update/persist", ex, model.getSubscriberId());
        }
    }

    @Override
    public void updateSubscriber(String subscriberId, String firstName, String lastName, String mail, String phoneNumber) throws SubscriberNotFoundException, SubscriberDatabaseException {
        Optional<SubscriberModel> existingModel = subscriberRepository.findBySubscriberId(subscriberId);
        if (existingModel.isPresent()){
            SubscriberModel model = existingModel.get();

            if(firstName != null){
                model.setFirstName(firstName);
            }

            if(lastName != null) {
                model.setLastName(lastName);
            }
            if(mail != null){
                model.setMail(mail);
            }

            if(phoneNumber != null){
                model.setPhoneNumber(phoneNumber);
            }

            performUpdate(model);
            return;
        }
        throw new SubscriberNotFoundException("No entity with subscriberId: '" + subscriberId + "' exists", subscriberId);


    }
}
