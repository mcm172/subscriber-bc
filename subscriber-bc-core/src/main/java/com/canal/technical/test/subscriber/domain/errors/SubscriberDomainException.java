package com.canal.technical.test.subscriber.domain.errors;

public abstract class SubscriberDomainException extends Exception{

    protected final String details;

    public SubscriberDomainException(String message, String details) {
        super(message);
        this.details = details;
    }
    public SubscriberDomainException(String message, Throwable cause, String details) {
        super(message, cause);
        this.details = details;
    }

    public String getDetails() {
        return details;
    }
}
