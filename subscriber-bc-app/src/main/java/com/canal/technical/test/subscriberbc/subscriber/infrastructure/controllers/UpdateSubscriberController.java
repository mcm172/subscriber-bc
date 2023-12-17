package com.canal.technical.test.subscriberbc.subscriber.infrastructure.controllers;

import com.canal.technical.test.subscriber.domain.errors.SubscriberDatabaseException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberNotFoundException;
import com.canal.technical.test.subscriber.domain.ports.UpdateSubscriberService;
import com.canal.technical.test.subscriber.usecases.CancelSubscriptionUseCase;
import com.canal.technical.test.subscriber.usecases.UpdateSubscriberUseCase;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.controllers.ErrorResponse.SubscriberBadRequestErrorResponse;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.controllers.ErrorResponse.SubscriberNotFoundErrorResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscribers")
public class UpdateSubscriberController {

    private final UpdateSubscriberService updateSubscriberService;

    public UpdateSubscriberController(UpdateSubscriberService updateSubscriberService) {
        this.updateSubscriberService = updateSubscriberService;
    }

    @PutMapping(value ="/{subscriberId}/cancel-subscription", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelSubscription(@PathVariable @NotNull String subscriberId){
        try {
            new CancelSubscriptionUseCase(updateSubscriberService).execute(subscriberId);
        } catch (SubscriberNotFoundException ex) {
            throw new SubscriberNotFoundErrorResponse(ex, "subscriber.cancel.not.found", new String [] {ex.getDetails()});
        } catch (SubscriberDatabaseException sde) {
            throw new SubscriberBadRequestErrorResponse(sde, "subscriber.cancel.error", new String [] {sde.getDetails()});
        }
        return buildNoContentResponseEntity();
    }

    @PutMapping(value ="/{subscriberId}/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateSubscription(@PathVariable @NotNull String subscriberId, @Valid @RequestBody PersonalDataPayLoad payload){
        try {
            new UpdateSubscriberUseCase(updateSubscriberService).execute(subscriberId, payload.firstName(), payload.lastName(), payload.mail(), payload.phoneNumber());
        } catch (SubscriberNotFoundException ex) {
            throw new SubscriberNotFoundErrorResponse(ex, "subscriber.update.not.found", new String [] {ex.getDetails()});
        } catch (SubscriberDatabaseException sde) {
            throw new SubscriberBadRequestErrorResponse(sde, "subscriber.update.error", new String [] {sde.getDetails()});
        }
        return buildNoContentResponseEntity();
    }

    private static ResponseEntity<Object> buildNoContentResponseEntity() {
        return ResponseEntity.noContent().build();
    }


}
