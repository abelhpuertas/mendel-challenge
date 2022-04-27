package com.mendel.challenge.controller;

import com.mendel.challenge.dto.TransactionDto;
import com.mendel.challenge.dto.TransactionSumDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @PutMapping("/{transactionId}")
    public void putTransaction(@PathVariable Long transactionId, @RequestBody @Valid TransactionDto transactionDto) {

    }

    @GetMapping("/types/{type}")
    public List<Long> getTransactionsByType(@PathVariable String type) {
        return List.of();
    }

    @GetMapping("/sum/{transactionId}")
    public TransactionSumDto getTransactionSum(@PathVariable Long transactionId) {
        return null;
    }
}
