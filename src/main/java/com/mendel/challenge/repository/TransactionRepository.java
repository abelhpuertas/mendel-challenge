package com.mendel.challenge.repository;

import com.mendel.challenge.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TransactionRepository {
    private List<Transaction> transactions = new ArrayList<>();

    public Transaction save(Transaction transaction) {

        this.findById(transaction.getId()).ifPresentOrElse(
                existingTransaction -> existingTransaction = transaction
                , () -> this.transactions.add(transaction)
        );

        return transaction;
    }

    public Optional<Transaction> findById(Long id) {
        return this.transactions.stream().filter(transaction -> transaction.getId().equals(id)).findFirst();
    }

    public List<Transaction> findAll() {
        return transactions;
    }

    public List<Transaction> findByType(String type) {
        return this.transactions.stream().filter(transaction -> transaction.getType().equals(type)).collect(Collectors.toList());
    }
}
