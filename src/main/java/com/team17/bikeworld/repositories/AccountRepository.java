package com.team17.bikeworld.repositories;

import com.team17.bikeworld.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Account findAccountByUsername(String username);

    Optional<Account> findAccountByUsernameAndPassword(String username, String password);

    List<Account> findByRoleId_Id(Integer roleId);
}
