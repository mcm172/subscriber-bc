package com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.servcies;

import com.canal.technical.test.subscriber.domain.entity.Subscriber;
import com.canal.technical.test.subscriber.domain.errors.SubscriberNotFoundException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberSearchIncorrectResultSizeException;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.repositories.SubscriberModelRepository;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.repositories.models.SubscriberModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Example;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DbSearchSubscriberServiceTest extends SubscriberServiceTest{

    private DbSearchSubscriberService serviceToTest;
    private SubscriberModelRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(SubscriberModelRepository.class);
        serviceToTest = new DbSearchSubscriberService(repository);
    }
    @Test
    void getSubscriberBySubscriberIdOk() throws SubscriberNotFoundException {
        SubscriberModel expected = fromSubscriber(createSubscriber());
        when(repository.findBySubscriberId(anyString())).thenReturn(Optional.of(expected));

        Subscriber result = serviceToTest.getSubscriberBySubscriberId(SUBSCRIBER_ID);

        assertNotNull(result);
        assertEquals(expected.getSubscriberId(), result.subscriberId());
        assertEquals(expected.getMail(), result.mail());
        assertTrue(result.isActive());
    }

    @Test
    void getSubscriberBySubscriberIdOnSubscriberNotFoundException() {
        when(repository.findBySubscriberId(anyString())).thenReturn(Optional.empty());

        assertThrows(SubscriberNotFoundException.class, () -> serviceToTest.getSubscriberBySubscriberId(SUBSCRIBER_ID));
    }
    @Test
    void findSubscriberOk() throws SubscriberSearchIncorrectResultSizeException, SubscriberNotFoundException {
        SubscriberModel expected = fromSubscriber(createSubscriber());
        when(repository.findOne(any(Example.class))).thenReturn(Optional.of(expected));

        Subscriber result = serviceToTest.findSubscriber(expected.getFirstName(), expected.getLastName(), expected.getMail(), expected.getPhoneNumber());

        assertNotNull(result);
        assertEquals(expected.getSubscriberId(), result.subscriberId());
        assertEquals(expected.getMail(), result.mail());
        assertTrue(result.isActive());

    }
    @Test
    void findSubscriberOnSubscriberNotFoundException(){
        SubscriberModel expected = fromSubscriber(createSubscriber());
        when(repository.findOne(any(Example.class))).thenReturn(Optional.empty());

        assertThrows(SubscriberNotFoundException.class, () -> serviceToTest.findSubscriber(expected.getFirstName(), expected.getLastName(), expected.getMail(), expected.getPhoneNumber()));

    }

    @Test
    void findSubscriberOnIncorrectResultSizeDataAccessException(){
        SubscriberModel expected = fromSubscriber(createSubscriber());
        when(repository.findOne(any(Example.class))).thenThrow(IncorrectResultSizeDataAccessException.class);

        assertThrows(SubscriberSearchIncorrectResultSizeException.class, () -> serviceToTest.findSubscriber(expected.getFirstName(), expected.getLastName(), expected.getMail(), expected.getPhoneNumber()));

    }

}