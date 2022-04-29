package com.mendel.challenge.exception;

public class TransactionNotFoundException extends IllegalArgumentException {
    private final Long id;

    public TransactionNotFoundException(Long invalidId) {
        this.id = invalidId;
    }

    @Override
    public String getLocalizedMessage() {
        return "Transaction not found.";
    }

    @Override
    public String getMessage() {
        return String.format("Transaction with id %s not found.", this.id);
    }
}
