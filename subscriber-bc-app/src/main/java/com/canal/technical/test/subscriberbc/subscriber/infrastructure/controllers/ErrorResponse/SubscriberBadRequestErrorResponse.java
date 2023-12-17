package com.canal.technical.test.subscriberbc.subscriber.infrastructure.controllers.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

public class SubscriberBadRequestErrorResponse extends ErrorResponseException {

    public SubscriberBadRequestErrorResponse(Throwable cause, String messageDetailCode, Object[] messageDetailArguments) {
        super(HttpStatus.BAD_REQUEST, ProblemDetail.forStatus(HttpStatus.BAD_REQUEST), cause, messageDetailCode, messageDetailArguments);
    }
}
