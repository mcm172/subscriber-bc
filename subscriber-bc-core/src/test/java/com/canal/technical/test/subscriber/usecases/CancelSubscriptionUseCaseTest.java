package com.canal.technical.test.subscriber.usecases;

import com.canal.technical.test.subscriber.domain.errors.SubscriberDatabaseException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberNotFoundException;
import com.canal.technical.test.subscriber.domain.ports.UpdateSubscriberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CancelSubscriptionUseCaseTest {

    private CancelSubscriptionUseCase useCaseToTest;

    private UpdateSubscriberService updateSubscriberService;

    @BeforeEach
    void setUp() {
        updateSubscriberService = mock(UpdateSubscriberService.class);
        useCaseToTest = new CancelSubscriptionUseCase(updateSubscriberService);
    }

    @Test
    void executeOk() throws SubscriberDatabaseException, SubscriberNotFoundException {
        String expectedId = "subscriberId";
        doNothing().when(updateSubscriberService).cancelSubscription(expectedId);

        useCaseToTest.execute(expectedId);

        verify(updateSubscriberService, times(1)).cancelSubscription(expectedId);
    }

    @Test
    void executeEntityAlreadyExists() throws SubscriberDatabaseException, SubscriberNotFoundException {
        String expectedId = "subscriberId";
        doThrow(new SubscriberNotFoundException("Exists", "exists")).when(updateSubscriberService).cancelSubscription(expectedId);

        assertThrows(SubscriberNotFoundException.class, () -> useCaseToTest.execute(expectedId));

    }

    @Test
    void executeOnSubscriberDatabaseException() throws SubscriberDatabaseException, SubscriberNotFoundException {
        String expectedId = "subscriberId";
        doThrow(new SubscriberDatabaseException("Exists", "exists")).when(updateSubscriberService).cancelSubscription(expectedId);

        assertThrows(SubscriberDatabaseException.class, () -> useCaseToTest.execute(expectedId));
    }
}