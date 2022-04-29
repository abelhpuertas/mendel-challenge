package com.mendel.challenge.service;

import com.mendel.challenge.dto.TransactionDto;
import com.mendel.challenge.dto.TransactionSumDto;
import com.mendel.challenge.exception.CantOverwriteChildException;
import com.mendel.challenge.exception.RepeatedTransactionException;
import com.mendel.challenge.exception.TransactionNotFoundException;
import com.mendel.challenge.model.Transaction;
import com.mendel.challenge.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService implements ITransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void putTransaction(Long transactionId, TransactionDto dto) {
        validateRepeatedTransactionId(transactionId);

        if (dto.getParentId() != null) {
            Transaction parent = getParentTransaction(dto);
            validateParentWithMultipleChilds(parent);

            Transaction savedTransaction = this.transactionRepository.save(Transaction.create(transactionId, dto.getAmount(), dto.getType()));
            parent.setChild(savedTransaction);
            this.transactionRepository.save(parent);
        } else {
            this.transactionRepository.save(Transaction.create(transactionId, dto.getAmount(), dto.getType()));
        }
    }

    @Override
    public List<Long> getByType(String type) {
        return this.transactionRepository.findByType(type)
                .stream().map(Transaction::getId).collect(Collectors.toList());
    }

    @Override
    public TransactionSumDto getSum(Long parentTransactionId) {
        Transaction transaction = this.transactionRepository.findById(parentTransactionId)
                .orElseThrow(() -> new TransactionNotFoundException(parentTransactionId));

        return TransactionSumDto.create(calculateRecursiveTotal(transaction));
    }

    private void validateParentWithMultipleChilds(Transaction parent) {
        if (parent.getChild() != null)
            throw new CantOverwriteChildException(parent.getId(), parent.getChild().getId());
    }

    private Transaction getParentTransaction(TransactionDto dto) {

        return this.transactionRepository.findById(dto.getParentId())
                .orElseThrow(() -> new TransactionNotFoundException(dto.getParentId()));
    }

    private void validateRepeatedTransactionId(Long transactionId) {
        if (this.transactionRepository.findById(transactionId).isPresent())
            throw new RepeatedTransactionException(transactionId);
    }

    private Double calculateRecursiveTotal(Transaction transaction) {
        Transaction child = transaction.getChild();
        if (child == null)
            return transaction.getAmount();
        else
            return transaction.getAmount() + calculateRecursiveTotal(transaction.getChild());
    }

}
