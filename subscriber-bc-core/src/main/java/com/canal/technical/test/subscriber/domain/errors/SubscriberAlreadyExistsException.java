package com.canal.technical.test.subscriber.domain.errors;

/**
 * Exception Thrown on subscriber creation if th subscriber exists
 */
public class SubscriberAlreadyExistsException extends SubscriberDomainException {


    /**
     * Constructs a new SubscriberAlreadyExistsException with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     * @param details Details for identifying the entity on error
     */
    public SubscriberAlreadyExistsException(String message, String details) {
        super(message, details);

    }
}
