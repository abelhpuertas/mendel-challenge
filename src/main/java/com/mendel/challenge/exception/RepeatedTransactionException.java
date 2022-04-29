package com.mendel.challenge.exception;

public class RepeatedTransactionException extends IllegalArgumentException {
    private final Long id;

    public RepeatedTransactionException(Long repeatedId) {
        this.id = repeatedId;
    }

    @Override
    public String getLocalizedMessage() {
        return "Transaction id repeated.";
    }

    @Override
    public String getMessage() {
        return String.format("Transaction id %s is repeated.", this.id);
    }

}
