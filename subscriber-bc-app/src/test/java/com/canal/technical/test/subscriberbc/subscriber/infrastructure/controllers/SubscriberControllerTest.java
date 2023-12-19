package com.canal.technical.test.subscriberbc.subscriber.infrastructure.controllers;

import com.canal.technical.test.subscriberbc.subscriber.infrastructure.controllers.payload.SubscriberPayload;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

import java.net.URI;

public abstract class SubscriberControllerTest {

    protected static final String SUBSCRIBER_ID = "subscriber_id";

    protected static final String URL = "/subscribers";

    protected abstract MessageSource getMessageSource();

    protected String getProblemDetail(HttpStatusCode status, String urlTemplate, String code, String defaultMessage, String... arguments) throws JsonProcessingException {

        String expectedDetailMessage = getMessageSource().getMessage(code, arguments, defaultMessage, LocaleContextHolder.getLocale());

        assert expectedDetailMessage != null;
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, expectedDetailMessage);
        problem.setInstance(URI.create(urlTemplate));
        problem.setProperties(null);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(problem);
    }

    protected SubscriberPayload getPayload(String id, String firstName, String lastName, String mail, String phoneNumber) {
        return new SubscriberPayload(id, firstName, lastName, mail, phoneNumber, true);
    }

    protected String getStringPayload(Object payloadObject) throws JsonProcessingException {
        return  new ObjectMapper().writeValueAsString(payloadObject);
    }
}
