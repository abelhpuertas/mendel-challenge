package com.mendel.challenge.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    private Long id;
    private Double amount;
    private String type;
    private Transaction child;

    private Transaction(Long id, Double amount, String type) {
        this.id = id;
        this.amount = amount;
        this.type = type;
    }

    public static Transaction create(Long transactionId, Double amount, String type) {
        return new Transaction(
                transactionId,
                amount,
                type
        );
    }
}
