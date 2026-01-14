package org.giant.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.giant.entity.Account;

import java.util.List;

@ApplicationScoped
public class AccountRepository implements PanacheRepository<Account> {
    public Account findByNumber(String number) { return find("number", number).firstResult(); }
    public List<Account> listByCustomer(Long customerId) { return list("customerId", customerId); }
}
