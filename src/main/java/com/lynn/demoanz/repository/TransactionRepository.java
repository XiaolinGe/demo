package com.lynn.demoanz.repository;

import com.lynn.demoanz.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByTxNumber(String txNumber);

    List<Transaction> findByTxNumberAndAmount(String txNumber, BigDecimal amount);

    @Query(
            value = "SELECT * FROM TRANSACTIONS t WHERE t.enabled = 1",
            nativeQuery = true)
    Collection<Transaction> findAllActiveTransactionsNative();
}
