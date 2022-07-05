package com.lynn.demoanz.service;

import com.lynn.demoanz.entity.Transaction;
import com.lynn.demoanz.repository.TransactionRepository;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Either<String, Transaction> findTransaction(Long id) {
        var txOptional = transactionRepository.findById(id);
        if (txOptional.isEmpty()) {
            var message = "[Get Transaction] Cannot get tx by id " + id;
            log.error(message);
            return Either.left(message);
        }

        return Either.right(txOptional.get());
    }

    public List<Transaction> findTransactions() {
        return transactionRepository.findAll();
    }

    public Either<String, Transaction> saveTransaction(Transaction transaction) {
        var txId = transaction.getId();
        if (txId != null && transactionRepository.findById(txId).isEmpty()) {
            var message = "[Update Transaction] Cannot get tx by id " + txId;
            log.error(message);
            return Either.left(message);
        }

        transactionRepository.save(transaction);
        return Either.right(transaction);
    }

    public Either<String, Transaction> deleteTransaction(Long id) {
        var txOptional = transactionRepository.findById(id);
        if (txOptional.isEmpty()) {
            var message = "[Delete Transaction] Cannot get tx by id " + id;
            log.error(message);
            return Either.left(message);
        }

        var transaction = txOptional.get();
        transaction.setEnabled(false);
        transactionRepository.save(transaction);
        return Either.right(transaction);
    }

}

