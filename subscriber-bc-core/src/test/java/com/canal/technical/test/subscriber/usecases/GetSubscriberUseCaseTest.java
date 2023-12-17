package com.canal.technical.test.subscriber.usecases;

import com.canal.technical.test.subscriber.domain.entity.Subscriber;
import com.canal.technical.test.subscriber.domain.errors.SubscriberNotFoundException;
import com.canal.technical.test.subscriber.domain.ports.SearchSubscriberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetSubscriberUseCaseTest {
    private static final String subscriberId = "username";
    private GetSubscriberUseCase useCaseToTest;

    private SearchSubscriberService searchSubscriberService;

    @BeforeEach
    void setUp() {
        searchSubscriberService = mock(SearchSubscriberService.class);
        useCaseToTest = new GetSubscriberUseCase(searchSubscriberService);
    }

    @Test
    void executeOk() throws SubscriberNotFoundException {
        final String firstName = "firstName";
        final String lastName = "lastName";
        final String mail = "mail";
        final String phoneNumber = "phoneNumber";

        Subscriber expected = new Subscriber(subscriberId, firstName, lastName, mail, phoneNumber, true);
        when(searchSubscriberService.getSubscriberBySubscriberId(anyString())).thenReturn(expected);

        Subscriber result = useCaseToTest.execute(subscriberId);

        assertNotNull(result);
        assertEquals(expected.subscriberId(), result.subscriberId());
    }



    @Test
    void executeSubscriberNotFoundException() throws SubscriberNotFoundException {

        when(searchSubscriberService.getSubscriberBySubscriberId(anyString())).thenThrow(new SubscriberNotFoundException("Exists", "exists"));
        assertThrows(SubscriberNotFoundException.class, () -> useCaseToTest.execute(subscriberId));

    }
}