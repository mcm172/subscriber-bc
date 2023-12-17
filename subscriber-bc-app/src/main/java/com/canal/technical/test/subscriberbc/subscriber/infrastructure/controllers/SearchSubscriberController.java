package com.canal.technical.test.subscriberbc.subscriber.infrastructure.controllers;

import com.canal.technical.test.subscriber.domain.entity.Subscriber;
import com.canal.technical.test.subscriber.domain.errors.SubscriberNotFoundException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberSearchIncorrectResultSizeException;
import com.canal.technical.test.subscriber.domain.ports.SearchSubscriberService;
import com.canal.technical.test.subscriber.usecases.FindSubscriberUseCase;
import com.canal.technical.test.subscriber.usecases.GetSubscriberUseCase;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.controllers.ErrorResponse.SubscriberBadRequestErrorResponse;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.controllers.ErrorResponse.SubscriberNotFoundErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscribers")
public class SearchSubscriberController {

    private final SearchSubscriberService searchSubscriberService;

    public SearchSubscriberController(SearchSubscriberService searchSubscriberService) {
        this.searchSubscriberService = searchSubscriberService;
    }

    @GetMapping(value ="/{subscriber-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubscriberPayload> getBySubscriberId(@PathVariable("subscriber-id") String subscriberId){
        Subscriber subscriber;
        try {
            subscriber = new GetSubscriberUseCase(searchSubscriberService).execute(subscriberId);
        } catch (SubscriberNotFoundException ex) {
            throw new SubscriberNotFoundErrorResponse(ex, "subscriber.get.by.id.not.found", new String [] {ex.getDetails()});
        }
        return ResponseEntity.ok().body(fromSubscriber(subscriber));
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> search(@RequestParam(name = "firstname", required = false) String firstName, @RequestParam(name = "lastname", required = false) String lastName, @RequestParam(name = "mail", required = false) String mail, @RequestParam(name = "phonenumber", required = false) String phoneNumber){
        if(firstName == null && lastName == null && mail == null && phoneNumber == null){
            throw new SubscriberBadRequestErrorResponse(null, "subscriber.search.all.params.null", null);
        }
        Subscriber subscriber;
        try {
            subscriber = new FindSubscriberUseCase(searchSubscriberService).execute(firstName,lastName ,mail, phoneNumber);
        } catch (SubscriberNotFoundException ex) {
            throw new SubscriberNotFoundErrorResponse(ex, "subscriber.search.criteria.not.found", null);
        } catch (SubscriberSearchIncorrectResultSizeException e) {
            throw new SubscriberBadRequestErrorResponse(e, "subscriber.search.criteria.multiple.result", null);
        }
        return ResponseEntity.ok().body(fromSubscriber(subscriber));
    }

    private SubscriberPayload fromSubscriber(Subscriber subscriber) {
        return new SubscriberPayload(subscriber.subscriberId(), subscriber.firstName(), subscriber.lastName(), subscriber.mail(), subscriber.phoneNumber(), subscriber.isActive());
    }

}
