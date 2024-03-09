package com.example.contact_list.exception;

public class ContactNotFoundException  extends RuntimeException {
    public ContactNotFoundException(String message) {
        super(message);
    }
}
