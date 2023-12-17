package com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.servcies;

import com.canal.technical.test.subscriber.domain.entity.Subscriber;
import com.canal.technical.test.subscriber.domain.errors.SubscriberAlreadyExistsException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberDatabaseException;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.repositories.SubscriberModelRepository;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.repositories.models.SubscriberModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DbCreateSubscriberServiceTest extends SubscriberServiceTest{

    private DbCreateSubscriberService serviceToTest;

    private SubscriberModelRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(SubscriberModelRepository.class);
        serviceToTest = new DbCreateSubscriberService(repository);
    }


    @Test
    void createSubscriberOk() throws SubscriberAlreadyExistsException, SubscriberDatabaseException {
        when(repository.findBySubscriberId(anyString())).thenReturn(Optional.empty());

        Subscriber subscriber = createSubscriber();
        when(repository.save(any(SubscriberModel.class))).thenReturn(fromSubscriber(subscriber));

        String result = serviceToTest.createSubscriber(subscriber);

        assertNotNull(SUBSCRIBER_ID, result);
    }

    @Test
    void createSubscriberOnSubscriberAlreadyExistsException() {
        when(repository.findBySubscriberId(anyString())).thenReturn(Optional.of(fromSubscriber(createSubscriber())));

        assertThrows(SubscriberAlreadyExistsException.class, ()-> serviceToTest.createSubscriber(createSubscriber()));
    }

    @Test
    void createSubscriberOnSubscriberDatabaseException() {
        when(repository.findBySubscriberId(anyString())).thenReturn(Optional.empty());

        when(repository.save(any(SubscriberModel.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(SubscriberDatabaseException.class, ()-> serviceToTest.createSubscriber(createSubscriber()));
    }
}