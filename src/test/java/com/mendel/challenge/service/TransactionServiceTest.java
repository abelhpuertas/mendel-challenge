package com.mendel.challenge.service;

import com.mendel.challenge.dto.TransactionDto;
import com.mendel.challenge.exception.CantOverwriteChildException;
import com.mendel.challenge.exception.RepeatedTransactionException;
import com.mendel.challenge.exception.TransactionNotFoundException;
import com.mendel.challenge.model.Transaction;
import com.mendel.challenge.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private TransactionService service;
    private final Long nonRepeatedTransactionId = 1L;
    private final String anyType = "anyType";
    private final Double anyAmount = 1.0;
    private Long existingParentId = 2L;
    private Transaction existingParentTransaction, existingTransaction, anotherTransaction;
    private final Long nonExistingTransactionId = 3L;
    private final Long repeatedTransactionId = 4L;
    private final Long anotherTransactionId = 5L;
    private List<Transaction> transactionList;

    @BeforeEach
    public void setupTests() {
        existingParentTransaction = Transaction.create(existingParentId, anyAmount, anyType);
        existingTransaction = Transaction.create(repeatedTransactionId, anyAmount, anyType);
        anotherTransaction = Transaction.create(anotherTransactionId, anyAmount, anyType);
        existingTransaction.setChild(anotherTransaction);
        existingParentTransaction.setChild(existingTransaction);

        this.transactionList = List.of(existingParentTransaction, existingTransaction);

        when(this.transactionRepository.findById(eq(existingParentId)))
                .thenReturn(Optional.of(existingParentTransaction));

        when(this.transactionRepository.findById(eq(repeatedTransactionId)))
                .thenReturn(Optional.of(existingTransaction));

        when(this.transactionRepository.findById(eq(anotherTransactionId)))
                .thenReturn(Optional.of(anotherTransaction));

        when(this.transactionRepository.findByType(eq(anyType))).thenReturn(this.transactionList);


    }

    @Test
    public void whatPutingAParentTransactionShouldDo() {
        this.service.putTransaction(
                nonRepeatedTransactionId,
                TransactionDto.create(anyType, anyAmount, null)
        );

        ArgumentCaptor<Transaction> argumentCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(this.transactionRepository, times(1))
                .save(argumentCaptor.capture());
        assertNull(argumentCaptor.getValue().getChild());
        assertEquals(argumentCaptor.getValue().getAmount(), anyAmount);
        assertEquals(argumentCaptor.getValue().getType(), anyType);
        assertEquals(argumentCaptor.getValue().getId(), nonRepeatedTransactionId);
    }

    @Test
    public void whatPuttingAChildTransactionShouldDo() {
        this.service.putTransaction(
                nonRepeatedTransactionId,
                TransactionDto.create(anyType, anyAmount, anotherTransactionId)
        );

        verify(this.transactionRepository, times(1))
                .findById(eq(anotherTransactionId));

        verify(this.transactionRepository, times(1))
                .findById(eq(nonRepeatedTransactionId));

        ArgumentCaptor<Transaction> argumentCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(this.transactionRepository, times(2))
                .save(argumentCaptor.capture());
    }

    @Test
    public void usingAnInvalidParentIdShouldThrow() {
        assertThrows(
                TransactionNotFoundException.class,
                () -> this.service.putTransaction(
                        nonRepeatedTransactionId,
                        TransactionDto.create(anyType, anyAmount, nonExistingTransactionId))
        );
    }

    @Test
    public void usingAParentWithOtherChildShouldThrow() {
//        when(this.transactionRepository.findByParentId(eq(repeatedTransactionId)))
//                .thenReturn(Optional.of(anotherTransaction));

        assertThrows(
                CantOverwriteChildException.class,
                () -> this.service.putTransaction(
                        nonRepeatedTransactionId,
                        TransactionDto.create(anyType, anyAmount, repeatedTransactionId))
        );
    }


    @Test
    public void usingARepeatedTransactionIdShouldThrow() {
        assertThrows(
                RepeatedTransactionException.class,
                () -> this.service.putTransaction(
                        repeatedTransactionId,
                        TransactionDto.create(anyType, anyAmount, null))
        );
    }

    @Test
    public void whatGetByTypeShouldDo() {
        List<Long> transactionIds = this.service.getByType(anyType);

        assertEquals(transactionIds.size(), transactionList.size());
    }

    @Test
    public void whatGetSumShouldDo() {
        assertEquals(
                this.service.getSum(existingParentId).getSum(),
                anyAmount * 3
        );

    }

    @Test
    public void getSumWithAnInvalidTransactionIdShouldThrow() {
        assertThrows(
                TransactionNotFoundException.class,
                () -> this.service.getSum(
                        nonRepeatedTransactionId)
        );
    }
}
