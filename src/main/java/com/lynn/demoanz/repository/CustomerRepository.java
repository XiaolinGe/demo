package com.lynn.demoanz.repository;

import com.lynn.demoanz.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByEmail(String username);

    @Query(
            value = "SELECT * FROM USERS u WHERE u.enabled = 1",
            nativeQuery = true)
    Collection<Customer> findAllActiveUsersNative();
}
