package com.lynn.demoanz.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
public class Transaction extends BaseEntity {
    @Column
    private String txNumber;

    @Column
    @NotNull
    @Digits(integer = 6, fraction = 2)
    private BigDecimal amount;

    @NotNull
    @Column(nullable = false)
    private Boolean enabled;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
