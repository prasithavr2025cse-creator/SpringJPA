package com.uam.hub.service;

import com.uam.hub.entity.Account;
import com.uam.hub.exception.ResourceNotFoundException;
import com.uam.hub.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
    }

    public List<Account> getAccountsByType(String accountType) {
        return accountRepository.findByAccountType(accountType);
    }

    public Account createAccount(Account account) {
        if (account.getActive() == null) account.setActive(true);
        return accountRepository.save(account);
    }

    public Account updateAccount(Long id, Account details) {
        Account account = getAccountById(id);
        if (details.getAccountCode() != null) account.setAccountCode(details.getAccountCode());
        if (details.getAccountName() != null) account.setAccountName(details.getAccountName());
        if (details.getAccountType() != null) account.setAccountType(details.getAccountType());
        if (details.getDescription() != null) account.setDescription(details.getDescription());
        if (details.getActive() != null) account.setActive(details.getActive());
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        Account account = getAccountById(id);
        accountRepository.delete(account);
    }
}
