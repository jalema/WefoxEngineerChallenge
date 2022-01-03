package com.technicaltest.payments.jpaadapter.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountJPA {
    @Id
    @Column(name = "account_id")
    Integer accountId;

    @Column(name = "name", length = 150)
    String name;

    @Column(name = "email", length = 100, unique = true, nullable = false)
    String email;

    @Column(name = "birthdate")
    LocalDate birthdate;

    @Column(name = "last_payment_date")
    LocalDateTime lastPaymentDate;
}
