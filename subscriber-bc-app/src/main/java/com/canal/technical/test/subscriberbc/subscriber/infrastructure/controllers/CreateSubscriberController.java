package com.canal.technical.test.subscriberbc.subscriber.infrastructure.controllers;

import com.canal.technical.test.subscriber.domain.errors.SubscriberAlreadyExistsException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberDatabaseException;
import com.canal.technical.test.subscriber.domain.ports.CreateSubscriberService;
import com.canal.technical.test.subscriber.usecases.CreateSubscriberUseCase;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.controllers.ErrorResponse.SubscriberAlreadyExistsErrorResponse;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.controllers.ErrorResponse.SubscriberBadRequestErrorResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/subscribers")
public class CreateSubscriberController {
    private final CreateSubscriberService createSubscriberService;

    public CreateSubscriberController(CreateSubscriberService createSubscriberService) {
        this.createSubscriberService = createSubscriberService;
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addNewSubscriber(@Valid @RequestBody SubscriberPayload subscriber) throws URISyntaxException {
        String subscriberId;
        try{
            subscriberId = new CreateSubscriberUseCase(createSubscriberService).execute(subscriber.subscriberId(),subscriber.firstName(), subscriber.lastName(), subscriber.mail(), subscriber.phoneNumber());
        } catch (SubscriberAlreadyExistsException ex) {
            throw new SubscriberAlreadyExistsErrorResponse(ex, "subscriber.create.already.exists", new String [] {ex.getDetails()});
        } catch (SubscriberDatabaseException sde) {
            throw new SubscriberBadRequestErrorResponse(sde, "subscriber.create.error", new String [] {sde.getDetails()});
        }

        return ResponseEntity.created(new URI("/subscribers/"+subscriberId)).build();
    }


}
