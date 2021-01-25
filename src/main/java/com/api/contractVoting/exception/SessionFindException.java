package com.api.contractVoting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.LOCKED)
public class SessionFindException extends RuntimeException {

    public SessionFindException(String mensagem) {
        super(mensagem);
    }
}
