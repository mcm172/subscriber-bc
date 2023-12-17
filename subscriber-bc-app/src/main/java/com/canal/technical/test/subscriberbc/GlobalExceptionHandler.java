package com.canal.technical.test.subscriberbc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public GlobalExceptionHandler() {
        super();
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleInternal(final Exception ex, final WebRequest request) throws Exception {
        logger.error(ex.getMessage(), ex);
        return super.handleException(ex, request);
    }
}
