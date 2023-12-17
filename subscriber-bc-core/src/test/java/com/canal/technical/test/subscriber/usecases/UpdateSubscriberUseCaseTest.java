package com.canal.technical.test.subscriber.usecases;

import com.canal.technical.test.subscriber.domain.errors.SubscriberDatabaseException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberNotFoundException;
import com.canal.technical.test.subscriber.domain.ports.UpdateSubscriberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UpdateSubscriberUseCaseTest {
    private static final String subscriberId = "username";
    private static final String firstName = "firstName";
    private static final String lastName = "lastName";
    private static final String mail = "mail";
    private static final String phoneNumber = "phoneNumber";
    private UpdateSubscriberUseCase useCaseToTest;

    private UpdateSubscriberService updateSubscriberService;

    @BeforeEach
    void setUp() {
        updateSubscriberService = mock(UpdateSubscriberService.class);
        useCaseToTest = new UpdateSubscriberUseCase(updateSubscriberService);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void executeOk() throws SubscriberDatabaseException, SubscriberNotFoundException {
        doNothing().when(updateSubscriberService).updateSubscriber(anyString(), anyString(), anyString(), anyString(), anyString());

        useCaseToTest.execute(subscriberId, firstName, lastName, mail, phoneNumber);

        verify(updateSubscriberService, times(1)).updateSubscriber(subscriberId, firstName, lastName, mail, phoneNumber);
    }

    @Test
    void executeEntityAlreadyExists() throws SubscriberDatabaseException, SubscriberNotFoundException {

        doThrow(new SubscriberNotFoundException("Exists", "exists")).when(updateSubscriberService).updateSubscriber(anyString(), anyString(), anyString(), anyString(), anyString());

        assertThrows(SubscriberNotFoundException.class, () -> useCaseToTest.execute(subscriberId, firstName, lastName, mail, phoneNumber));

    }

    @Test
    void executeOnSubscriberDatabaseException() throws SubscriberDatabaseException, SubscriberNotFoundException {
        doThrow(new SubscriberDatabaseException("Exists", "exists")).when(updateSubscriberService).updateSubscriber(anyString(), anyString(), anyString(), anyString(), anyString());

        assertThrows(SubscriberDatabaseException.class, () -> useCaseToTest.execute(subscriberId, firstName, lastName, mail, phoneNumber));
    }
}