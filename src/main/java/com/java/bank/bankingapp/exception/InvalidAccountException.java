package com.java.bank.bankingapp.exception;

public class InvalidAccountException extends RuntimeException {
    public InvalidAccountException(String s) {
        super(s);
    }
}
