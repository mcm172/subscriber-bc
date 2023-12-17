package com.canal.technical.test.subscriberbc.subscriber.infrastructure.controllers;

import com.canal.technical.test.subscriber.domain.entity.Subscriber;
import com.canal.technical.test.subscriber.domain.errors.SubscriberNotFoundException;
import com.canal.technical.test.subscriber.domain.errors.SubscriberSearchIncorrectResultSizeException;
import com.canal.technical.test.subscriber.domain.ports.SearchSubscriberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class SearchSubscriberControllerTest extends SubscriberControllerTest{

    private static final String SEARCH_URL = URL + "/search";

    private static final String GET_BY_ID_URL = URL + "/" + SUBSCRIBER_ID;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageSource messageSource;

    @MockBean
    private SearchSubscriberService searchSubscriberService;

    @Test
    void searchOk() throws Exception {
        when(searchSubscriberService.findSubscriber(anyString(), anyString(), anyString(), anyString())).thenReturn(createSubscriber(SUBSCRIBER_ID, "firstanme", "lastname", "mail@mail.com", "0606060606", true));
        SubscriberPayload payload = getPayload(SUBSCRIBER_ID, "firstanme", "lastname", "mail@mail.com", "0606060606");
        this.mockMvc.perform(get(SEARCH_URL)
                        .param("firstname", "firstname")
                        .param("lastname", "lastname")
                        .param("mail", "mail@mail.com")
                        .param("phonenumber", "0606060606"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(getStringPayload(payload)));
    }

    @Test
    void searchOnNullPArameters() throws Exception {
        this.mockMvc.perform(get(SEARCH_URL))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(getProblemDetail(HttpStatus.BAD_REQUEST, SEARCH_URL, "subscriber.search.all.params.null", null, SUBSCRIBER_ID)));
    }

    @Test
    void searchOnSubscriberNotFoundException() throws Exception {
        when(searchSubscriberService.findSubscriber(anyString(),anyString(), anyString(), anyString())).thenThrow(new SubscriberNotFoundException("", SUBSCRIBER_ID));
        this.mockMvc.perform(get(SEARCH_URL)
                        .param("firstname", "firstname")
                        .param("lastname", "lastname")
                        .param("mail", "mail@mail.com")
                        .param("phonenumber", "0606060606"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(getProblemDetail(HttpStatus.NOT_FOUND, SEARCH_URL, "subscriber.search.criteria.not.found", null, SUBSCRIBER_ID)));
    }

    @Test
    void SubscriberSearchIncorrectResultSizeException() throws Exception {
        when(searchSubscriberService.findSubscriber(anyString(),anyString(), anyString(), anyString())).thenThrow(new SubscriberSearchIncorrectResultSizeException("", null));
        this.mockMvc.perform(get(SEARCH_URL)
                        .param("firstname", "firstname")
                        .param("lastname", "lastname")
                        .param("mail", "mail@mail.com")
                        .param("phonenumber", "0606060606"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(getProblemDetail(HttpStatus.BAD_REQUEST, SEARCH_URL, "subscriber.search.criteria.multiple.result", null)));
    }

    @Test
    void getByIdOk() throws Exception {
        when(searchSubscriberService.getSubscriberBySubscriberId(anyString())).thenReturn(createSubscriber(SUBSCRIBER_ID, "firstanme", "lastname", "mail@mail.com", "0606060606", true));
        SubscriberPayload payload = getPayload(SUBSCRIBER_ID, "firstanme", "lastname", "mail@mail.com", "0606060606");
        this.mockMvc.perform(get(GET_BY_ID_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(getStringPayload(payload)));
    }

    @Test
    void getByIdOnSubscriberNotFoundException() throws Exception {
        when(searchSubscriberService.getSubscriberBySubscriberId(anyString())).thenThrow(new SubscriberNotFoundException("", SUBSCRIBER_ID));
        this.mockMvc.perform(get(GET_BY_ID_URL))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(getProblemDetail(HttpStatus.NOT_FOUND, GET_BY_ID_URL, "subscriber.get.by.id.not.found", null, SUBSCRIBER_ID)));
    }
    @Override
    protected MessageSource getMessageSource() {
        return messageSource;
    }

    private Subscriber createSubscriber(String subscriberId,
                                        String firstName,
                                        String lastName,
                                        String mail,
                                        String phoneNumber,
                                        boolean isActive){
        return  new Subscriber(subscriberId, firstName, lastName, mail, phoneNumber, isActive);
    }
}