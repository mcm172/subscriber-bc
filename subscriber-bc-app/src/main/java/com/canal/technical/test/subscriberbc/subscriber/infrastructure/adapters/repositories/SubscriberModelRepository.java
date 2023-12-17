package com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.repositories;

import com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.repositories.models.SubscriberModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriberModelRepository extends JpaRepository<SubscriberModel, Long>{

    Optional<SubscriberModel> findBySubscriberId(String subscriberId);
}
