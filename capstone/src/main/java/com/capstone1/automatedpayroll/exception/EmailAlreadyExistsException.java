package com.capstone1.automatedpayroll.exception;

public class EmailAlreadyExistsException  extends  RuntimeException{
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
