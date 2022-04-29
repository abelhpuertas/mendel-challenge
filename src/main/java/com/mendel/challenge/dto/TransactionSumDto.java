package com.mendel.challenge.dto;

import lombok.Getter;

@Getter
public class TransactionSumDto {
    private Double sum;

    public TransactionSumDto(Double sum) {
        this.sum = sum;
    }

    public static TransactionSumDto create(Double sum) {
        return new TransactionSumDto(sum);
    }
}
