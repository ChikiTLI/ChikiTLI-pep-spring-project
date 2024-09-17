package com.example.repository;

import com.example.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>  {
    
    // @Query("FROM account a WHERE a.username = :username")
    // Account findAccountByUsername(@Param("username") String username);
    Optional<Account> findAccountByUsername(String username);
    Optional<Account> findAccountByUsernameAndPassword(String username, String Password);
}
