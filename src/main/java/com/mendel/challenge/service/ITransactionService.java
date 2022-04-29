package com.mendel.challenge.service;

import com.mendel.challenge.dto.TransactionDto;
import com.mendel.challenge.dto.TransactionSumDto;

import java.util.List;

public interface ITransactionService {
    void putTransaction(Long transactionId, TransactionDto dto);

    List<Long> getByType(String type);

    TransactionSumDto getSum(Long parentTransactionId);
}
