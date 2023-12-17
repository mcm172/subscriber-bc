package com.canal.technical.test.subscriberbc.subscriber.infrastructure.controllers.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

public class SubscriberNotFoundErrorResponse extends ErrorResponseException {

    public SubscriberNotFoundErrorResponse(Throwable cause, String messageDetailCode, Object[] messageDetailArguments) {
        super(HttpStatus.NOT_FOUND, ProblemDetail.forStatus(HttpStatus.NOT_FOUND), cause, messageDetailCode, messageDetailArguments);
    }
}
