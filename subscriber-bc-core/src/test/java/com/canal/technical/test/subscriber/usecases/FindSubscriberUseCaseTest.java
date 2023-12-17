package com.canal.technical.test.subscriber.usecases;

import com.canal.technical.test.subscriber.domain.entity.Subscriber;
import com.canal.technical.test.subscriber.domain.errors.SubscriberNotFoundException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberSearchIncorrectResultSizeException;
import com.canal.technical.test.subscriber.domain.ports.SearchSubscriberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindSubscriberUseCaseTest {

    private static final String subscriberId = "username";
    private static final String firstName = "firstName";
    private static final String lastName = "lastName";
    private static final String mail = "mail";
    private static final String phoneNumber = "phoneNumber";
    private FindSubscriberUseCase useCaseToTest;

    private SearchSubscriberService searchSubscriberService;

    @BeforeEach
    void setUp() {
        searchSubscriberService = mock(SearchSubscriberService.class);
        useCaseToTest = new FindSubscriberUseCase(searchSubscriberService);
    }

    @Test
    void executeOk() throws SubscriberNotFoundException, SubscriberSearchIncorrectResultSizeException {
        Subscriber expected = createSubscriber();
        when(searchSubscriberService.findSubscriber(anyString(), anyString(), anyString(), anyString())).thenReturn(expected);

        Subscriber result = useCaseToTest.execute(firstName, lastName, mail, phoneNumber);

        assertNotNull(result);
        assertEquals(expected.subscriberId(), result.subscriberId());
        assertEquals(expected.firstName(), result.firstName());
        assertEquals(expected.lastName(), result.lastName());

    }

    private Subscriber createSubscriber() {
        return new Subscriber(subscriberId, firstName, lastName, mail, phoneNumber, true);
    }

    @Test
    void executeSubscriberNotFoundException() throws SubscriberNotFoundException, SubscriberSearchIncorrectResultSizeException {

        when(searchSubscriberService.findSubscriber(anyString(), anyString(), anyString(), anyString())).thenThrow(new SubscriberNotFoundException("Exists", "exists"));
        assertThrows(SubscriberNotFoundException.class, () -> useCaseToTest.execute(firstName, lastName, mail, phoneNumber));

    }

    @Test
    void executeOnSubscriberSearchIncorrectResultSizeException() throws SubscriberNotFoundException, SubscriberSearchIncorrectResultSizeException {
        when(searchSubscriberService.findSubscriber(anyString(), anyString(), anyString(), anyString())).thenThrow(new SubscriberSearchIncorrectResultSizeException("Exists", "exists"));
        assertThrows(SubscriberSearchIncorrectResultSizeException.class, () -> useCaseToTest.execute(firstName, lastName, mail, phoneNumber));
    }
}