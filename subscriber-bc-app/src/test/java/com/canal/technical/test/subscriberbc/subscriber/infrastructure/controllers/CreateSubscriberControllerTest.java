package com.canal.technical.test.subscriberbc.subscriber.infrastructure.controllers;

import com.canal.technical.test.subscriber.domain.entity.Subscriber;
import com.canal.technical.test.subscriber.domain.errors.SubscriberAlreadyExistsException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberDatabaseException;
import com.canal.technical.test.subscriber.domain.ports.CreateSubscriberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CreateSubscriberControllerTest extends SubscriberControllerTest{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageSource messageSource;

    @MockBean
    private CreateSubscriberService createSubscriberService;

    @Test
    void addNewSubscriber() throws Exception {
        when(createSubscriberService.createSubscriber(any(Subscriber.class))).thenReturn(SUBSCRIBER_ID);
        SubscriberPayload payload = getPayload(SUBSCRIBER_ID, "firstanme", "lastname", "mail@mail.com", "0606060606");
        this.mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_VALUE).content(new ObjectMapper().writeValueAsString(payload)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", URL+"/"+SUBSCRIBER_ID));
    }

    @Test
    void addExistingSubscriber() throws Exception {

        when(createSubscriberService.createSubscriber(any(Subscriber.class))).thenThrow(new SubscriberAlreadyExistsException("exists",SUBSCRIBER_ID));

        SubscriberPayload payload = getPayload(SUBSCRIBER_ID, "firstanme", "lastname", "mail@mail.com", "0606060606");
        this.mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_VALUE).content(new ObjectMapper().writeValueAsString(payload)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(getProblemDetail(HttpStatus.CONFLICT, URL, "subscriber.create.already.exists", null, SUBSCRIBER_ID)));
    }

    @Test
    void addSubscriberOnSubscriberDatabaseException() throws Exception {

        when(createSubscriberService.createSubscriber(any(Subscriber.class))).thenThrow(new SubscriberDatabaseException("exists",SUBSCRIBER_ID));

        SubscriberPayload payload = getPayload(SUBSCRIBER_ID, "firstanme", "lastname", "mail@mail.com", "0606060606");
        this.mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_VALUE).content(new ObjectMapper().writeValueAsString(payload)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(getProblemDetail(HttpStatus.BAD_REQUEST, URL, "subscriber.create.error", null, SUBSCRIBER_ID)));
    }

    @Test
    void addNewSubscriberWithNullValues() throws Exception {
        SubscriberPayload payload = getPayload(null, null, null, null, null);
        this.mockMvc.perform(post("/subscribers").contentType(MediaType.APPLICATION_JSON_VALUE).content(new ObjectMapper().writeValueAsString(payload)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(getProblemDetail(HttpStatus.BAD_REQUEST, URL, "","Invalid request content.", SUBSCRIBER_ID)));
    }

    @Test
    void addNewSubscriberWithEmptyValues() throws Exception {
        SubscriberPayload payload = getPayload("", "", "", "", "");
        this.mockMvc.perform(post("/subscribers").contentType(MediaType.APPLICATION_JSON_VALUE).content(new ObjectMapper().writeValueAsString(payload)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(getProblemDetail(HttpStatus.BAD_REQUEST, URL, "", "Invalid request content.", SUBSCRIBER_ID)));
    }



    @Override
    protected MessageSource getMessageSource() {
        return this.messageSource;
    }
}