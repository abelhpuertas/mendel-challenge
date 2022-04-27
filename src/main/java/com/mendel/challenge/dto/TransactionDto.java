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
}
