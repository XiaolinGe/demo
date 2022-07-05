package com.lynn.demoanz.controller;

import com.lynn.demoanz.config.AppProperties;
import com.lynn.demoanz.entity.Transaction;
import com.lynn.demoanz.exception.ErrorDto;
import com.lynn.demoanz.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    private final AppProperties appProperties;

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions() {
        log.info("get transactions");
        return ResponseEntity.ok(transactionService.findTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransaction(@PathVariable("id") final Long id) {
        log.info("get a transaction");
        return transactionService.findTransaction(id)
                .fold(
                        error -> ResponseEntity.badRequest().body(new ErrorDto(error)),
                        ResponseEntity::ok
                );
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody @Validated Transaction transaction) {
        log.info("create a transaction");
        return transactionService.saveTransaction(transaction)
                .fold(
                        error -> ResponseEntity.badRequest().body(new ErrorDto(error)),
                        ResponseEntity::ok
                );
    }

    @PostMapping("{/id}")
    public ResponseEntity<?> updateTransaction(@RequestBody @Validated Transaction transaction) {
        log.info("update a transaction");
        return transactionService.saveTransaction(transaction)
                .fold(
                        error -> ResponseEntity.badRequest().body(new ErrorDto(error)),
                        ResponseEntity::ok
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("id") final Long id) {
        log.info("disable a transaction");
        return transactionService.deleteTransaction(id)
                .fold(
                        error -> ResponseEntity.badRequest().body(new ErrorDto(error)),
                        ResponseEntity::ok
                );
    }
}
