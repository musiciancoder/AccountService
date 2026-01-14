package org.giant.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Account extends PanacheEntity {
    @NotBlank public String number;
    @NotNull public Long customerId;
    @NotNull public BigDecimal balance;
    @NotBlank public String type; // CHECKING
    @NotBlank public String status; // ACTIVE | INACTIVE
    @Version public long version; // optimistic locking
 }