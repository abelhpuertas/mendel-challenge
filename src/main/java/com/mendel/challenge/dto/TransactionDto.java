package com.mendel.challenge.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class TransactionDto {
    @NotNull
    private Double amount;
    @NotEmpty
    private String type;
    private Long parentId;

    private TransactionDto(String type, Double amount, Long parentId) {
        this.type = type;
        this.amount = amount;
        this.parentId = parentId;
    }

    public static TransactionDto create(String type, Double amount, Long parentId) {
        return new TransactionDto(type, amount, parentId);
    }
}
