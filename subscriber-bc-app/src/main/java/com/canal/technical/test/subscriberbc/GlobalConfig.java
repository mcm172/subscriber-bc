package com.canal.technical.test.subscriberbc;

import com.canal.technical.test.subscriber.domain.ports.CreateSubscriberService;
import com.canal.technical.test.subscriber.domain.ports.SearchSubscriberService;
import com.canal.technical.test.subscriber.domain.ports.UpdateSubscriberService;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.repositories.SubscriberModelRepository;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.servcies.DbCreateSubscriberService;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.servcies.DbSearchSubscriberService;
import com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.servcies.DbUpdateSubscriberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
public class GlobalConfig {


    @Bean
    public CreateSubscriberService createSubscriberService(SubscriberModelRepository subscriberRepository) {
        return new DbCreateSubscriberService(subscriberRepository);
    }

    @Bean
    public UpdateSubscriberService updateSubscriberService(SubscriberModelRepository subscriberRepository){
        return new DbUpdateSubscriberService(subscriberRepository);
    }

    @Bean
    public SearchSubscriberService searchSubscriberService(SubscriberModelRepository subscriberRepository){
        return new DbSearchSubscriberService(subscriberRepository);
    }
}
