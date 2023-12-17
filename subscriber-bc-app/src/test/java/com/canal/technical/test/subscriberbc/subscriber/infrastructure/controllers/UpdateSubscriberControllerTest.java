package com.canal.technical.test.subscriberbc.subscriber.infrastructure.controllers;

import com.canal.technical.test.subscriber.domain.errors.SubscriberDatabaseException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberNotFoundException;
import com.canal.technical.test.subscriber.domain.ports.UpdateSubscriberService;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UpdateSubscriberControllerTest extends SubscriberControllerTest {

    private static final String URL_PATTERN = URL+"/%s";
    private static final String CANCEL_URL = String.format(URL_PATTERN, SUBSCRIBER_ID+"/cancel-subscription");

    private static final String UPDATE_URL = String.format(URL_PATTERN, SUBSCRIBER_ID+"/update");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageSource messageSource;

    @MockBean
    private UpdateSubscriberService updateSubscriberService;

    @Test
    void cancelSubscriptionOk() throws Exception {
        doNothing().when(updateSubscriberService).cancelSubscription(anyString());
        this.mockMvc.perform(put("/subscribers/subscriberid/cancel-subscription"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
    @Test
    void cancelSubscriptionOnSubscriberNotFoundException() throws Exception {
        doThrow(new SubscriberNotFoundException("exists", SUBSCRIBER_ID)).when(updateSubscriberService).cancelSubscription(anyString());

        this.mockMvc.perform(put(CANCEL_URL))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(getProblemDetail(HttpStatus.NOT_FOUND, CANCEL_URL, "subscriber.cancel.not.found", null, SUBSCRIBER_ID)));
    }

    @Test
    void cancelSubscriptionOnSubscriberDatabaseException() throws Exception {
        doThrow(new SubscriberDatabaseException("exists", SUBSCRIBER_ID)).when(updateSubscriberService).cancelSubscription(anyString());
        this.mockMvc.perform(put(CANCEL_URL))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(getProblemDetail(HttpStatus.BAD_REQUEST, CANCEL_URL, "subscriber.cancel.error", null, SUBSCRIBER_ID)));
    }

    @Test
    void updateSubscriberOk() throws Exception {
        doNothing().when(updateSubscriberService).updateSubscriber(anyString(), anyString(),anyString(),anyString(),anyString());
        this.mockMvc.perform(put(UPDATE_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getStringPayload(new PersonalDataPayLoad("firstanme", "lastname", "mail@mail.com", "0606060606"))))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
    @Test
    void updateSubscriberOnSubscriberNotFoundException() throws Exception {
        doThrow(new SubscriberNotFoundException("exists", SUBSCRIBER_ID)).when(updateSubscriberService).updateSubscriber(anyString(), anyString(),anyString(),anyString(),anyString());

        this.mockMvc.perform(put(UPDATE_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getStringPayload(new PersonalDataPayLoad("firstanme", "lastname", "mail@mail.com", "0606060606"))))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(getProblemDetail(HttpStatus.NOT_FOUND, UPDATE_URL, "subscriber.update.not.found", null, SUBSCRIBER_ID)));
    }

    @Test
    void updateSubscriberOnSubscriberDatabaseException() throws Exception {
        doThrow(new SubscriberDatabaseException("exists", SUBSCRIBER_ID)).when(updateSubscriberService).updateSubscriber(anyString(), anyString(),anyString(),anyString(),anyString());
        this.mockMvc.perform(put(UPDATE_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getStringPayload(new PersonalDataPayLoad("firstanme", "lastname", "mail@mail.com", "0606060606"))))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(getProblemDetail(HttpStatus.BAD_REQUEST, UPDATE_URL, "subscriber.update.error", null, SUBSCRIBER_ID)));
    }

    @Override
    protected MessageSource getMessageSource() {
        return this.messageSource;
    }
}