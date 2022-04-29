package com.mendel.challenge.repository;

import com.mendel.challenge.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TransactionRepositoryTest {
    @InjectMocks
    private TransactionRepository repository;
    private final Double anyAmount = 123.0;
    private final String anyType = "anyType";
    private final Long anyId = 1L;
    private final Long existingTransactionId = 2L;
    private Transaction existingTransaction;
    private final Long nonExistingId = 3L;
    private Integer preSavedTransactions;

    @BeforeEach
    public void setupTests() {
        existingTransaction = Transaction.create(existingTransactionId, anyAmount, anyType);

        this.repository.save(existingTransaction);

        preSavedTransactions = 1;
    }

    @Test
    public void savingANewTransactionShouldAddItToTheTransactionCollection() {
        Integer transactionsBeforeSave = this.repository.findAll().size();

        this.repository.save(Transaction.create(anyId, anyAmount, anyType));

        Integer transactionsAfterSave = this.repository.findAll().size();

        assertEquals(transactionsAfterSave, transactionsBeforeSave + 1);
    }

    @Test
    public void findByIdWithAValidOneShouldReturnAnOptionalWithTransactionInside() {
        Transaction transaction = this.repository.findById(existingTransactionId).get();

        assertEquals(transaction.getId(), existingTransaction.getId());
    }

    @Test
    public void findByIdWithAnInvalidOneShouldReturnAnEmptyOptional() {
        Optional<Transaction> optionalTransaction = this.repository.findById(nonExistingId);

        assertTrue(optionalTransaction.isEmpty());
    }

    @Test
    public void findByTypeShouldReturnTransactionsMatchingTypes() {
        assertEquals(this.repository.findByType(anyType).size(), preSavedTransactions);
    }
}
