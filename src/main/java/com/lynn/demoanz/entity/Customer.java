package com.lynn.demoanz.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
public class Customer extends BaseEntity {

    @Column
    @NotEmpty
    private String customerNumber;

    @Column
    @NotEmpty
    private String username;

    @Column
    @NotEmpty
    private String email;

    @Column
    private String address;

    @NotNull
    @Column(nullable = false)
    private Boolean enabled;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

}
