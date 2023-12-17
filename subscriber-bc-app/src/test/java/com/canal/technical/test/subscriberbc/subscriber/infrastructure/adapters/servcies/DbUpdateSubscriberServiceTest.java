package com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.servcies;

import com.canal.technical.test.subscriber.domain.entity.Subscriber;
import com.canal.technical.test.subscriber.domain.errors.SubscriberDatabaseException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberNotFoundException;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.repositories.SubscriberModelRepository;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.repositories.models.SubscriberModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DbUpdateSubscriberServiceTest extends SubscriberServiceTest{

    private DbUpdateSubscriberService serviceToTest;
    private SubscriberModelRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(SubscriberModelRepository.class);
        serviceToTest = new DbUpdateSubscriberService(repository);
    }

    @Test
    void cancelSubscriptionOk() throws SubscriberNotFoundException, SubscriberDatabaseException {
        when(repository.findBySubscriberId(anyString())).thenReturn(Optional.of(fromSubscriber(createSubscriber())));

        Subscriber subscriber = createSubscriber();
        when(repository.save(any(SubscriberModel.class))).thenReturn(fromSubscriber(subscriber));

        serviceToTest.cancelSubscription(SUBSCRIBER_ID);

        verify(repository, times(1)).save(any(SubscriberModel.class));
    }

    @Test
    void cancelSubscriptionOnSubscriberNotFoundException() {
        when(repository.findBySubscriberId(anyString())).thenReturn(Optional.empty());

        assertThrows(SubscriberNotFoundException.class,() -> serviceToTest.cancelSubscription(SUBSCRIBER_ID));

        verify(repository, times(0)).save(any(SubscriberModel.class));
    }

    @Test
    void cancelSubscriptionDataIntegrityViolationException() {
        when(repository.findBySubscriberId(anyString())).thenReturn(Optional.of(fromSubscriber(createSubscriber())));

        when(repository.save(any(SubscriberModel.class))).thenThrow(DataIntegrityViolationException.class);
        assertThrows(SubscriberDatabaseException.class,() -> serviceToTest.cancelSubscription(SUBSCRIBER_ID));

    }

    @Test
    void updateSubscriberOk() throws SubscriberNotFoundException, SubscriberDatabaseException {
        when(repository.findBySubscriberId(anyString())).thenReturn(Optional.of(fromSubscriber(createSubscriber())));

        Subscriber subscriber = createSubscriber();
        when(repository.save(any(SubscriberModel.class))).thenReturn(fromSubscriber(subscriber));

        serviceToTest.updateSubscriber(SUBSCRIBER_ID, subscriber.firstName(), subscriber.lastName(), subscriber.mail(), subscriber.phoneNumber());

        verify(repository, times(1)).save(any(SubscriberModel.class));
    }

    @Test
    void updateSubscriberOnSubscriberNotFoundException() {
        when(repository.findBySubscriberId(anyString())).thenReturn(Optional.empty());

        Subscriber subscriber = createSubscriber();
        assertThrows(SubscriberNotFoundException.class,() -> serviceToTest.updateSubscriber(SUBSCRIBER_ID,subscriber.firstName(), subscriber.lastName(), subscriber.mail(), subscriber.phoneNumber()));

        verify(repository, times(0)).save(any(SubscriberModel.class));
    }

    @Test
    void updateSubscriberOnDataIntegrityViolationException() {
        when(repository.findBySubscriberId(anyString())).thenReturn(Optional.of(fromSubscriber(createSubscriber())));

        when(repository.save(any(SubscriberModel.class))).thenThrow(DataIntegrityViolationException.class);

        Subscriber subscriber = createSubscriber();
        assertThrows(SubscriberDatabaseException.class,() -> serviceToTest.updateSubscriber(SUBSCRIBER_ID, subscriber.firstName(), subscriber.lastName(), subscriber.mail(), subscriber.phoneNumber()));

    }


}