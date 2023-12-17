package com.canal.technical.test.subscriber.usecases;

import com.canal.technical.test.subscriber.domain.entity.Subscriber;
import com.canal.technical.test.subscriber.domain.errors.SubscriberDatabaseException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberAlreadyExistsException;
import com.canal.technical.test.subscriber.domain.ports.CreateSubscriberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateSubscriberUseCaseTest {

    private CreateSubscriberUseCase useCaseToTest;

    private CreateSubscriberService createSubscriberService;

    @BeforeEach
    void setUp() {
        createSubscriberService = mock(CreateSubscriberService.class);
        useCaseToTest = new CreateSubscriberUseCase(createSubscriberService);
    }

    @Test
    void executeOk() throws SubscriberAlreadyExistsException, SubscriberDatabaseException {
        String expectedId = "subscriberId";
        when(createSubscriberService.createSubscriber(any(Subscriber.class))).thenReturn(expectedId);

        Subscriber payload = getPayload("id",
                "firstName",
                "lastName",
                "mail",
                "phoneNumber");
        String subscriberId = useCaseToTest.execute(payload.subscriberId(), payload.firstName(), payload.lastName(), payload.mail(), payload.phoneNumber());

        assertNotNull(subscriberId);
        assertEquals(expectedId, subscriberId);
    }

    @Test
    void executeEntityAlreadyExists() throws SubscriberAlreadyExistsException, SubscriberDatabaseException {
        String expectedId = "subscriberId";
        when(createSubscriberService.createSubscriber(any(Subscriber.class))).thenThrow(new SubscriberAlreadyExistsException("Exists", "exists"));

        Subscriber payload = getPayload("id",
                "firstName",
                "lastName",
                "mail",
                "phoneNumber");

        assertThrows(SubscriberAlreadyExistsException.class, () -> useCaseToTest.execute(payload.subscriberId(), payload.firstName(), payload.lastName(), payload.mail(), payload.phoneNumber()));

    }

    @Test
    void executeOnSubscriberDatabaseException() throws SubscriberAlreadyExistsException, SubscriberDatabaseException {
        String expectedId = "subscriberId";
        when(createSubscriberService.createSubscriber(any(Subscriber.class))).thenThrow(new SubscriberDatabaseException("Exists", "exists"));

        Subscriber payload = getPayload("id",
                "firstName",
                "lastName",
                "mail",
                "phoneNumber");

        assertThrows(SubscriberDatabaseException.class, () -> useCaseToTest.execute(payload.subscriberId(), payload.firstName(), payload.lastName(), payload.mail(), payload.phoneNumber()));

    }

    private Subscriber getPayload(String id, String firstName, String lastName, String mail, String phoneNumber) {
        return new Subscriber(id, firstName, lastName, mail, phoneNumber, true);
    }
}