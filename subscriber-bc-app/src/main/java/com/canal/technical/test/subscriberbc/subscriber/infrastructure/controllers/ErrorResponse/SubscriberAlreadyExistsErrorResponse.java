package com.canal.technical.test.subscriberbc.subscriber.infrastructure.controllers.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

public class SubscriberAlreadyExistsErrorResponse extends ErrorResponseException {

    public SubscriberAlreadyExistsErrorResponse(Throwable cause, String messageDetailCode, Object[] messageDetailArguments) {
        super(HttpStatus.CONFLICT, ProblemDetail.forStatus(HttpStatus.CONFLICT), cause, messageDetailCode, messageDetailArguments);
    }
}
