package com.canal.technical.test.subscriber.domain.entity;

public record Subscriber(String subscriberId,
                         String firstName,
                         String lastName,
                         String mail,
                         String phoneNumber,
                         boolean isActive) {
}
