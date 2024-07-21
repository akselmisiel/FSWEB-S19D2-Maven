package com.workintech.s18d4.controller;

import com.workintech.s18d4.dto.AccountResponse;
import com.workintech.s18d4.dto.CustomerResponse;
import com.workintech.s18d4.entity.Account;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.AccountService;
import com.workintech.s18d4.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    private AccountService accountService;
    private CustomerService customerService;

    @GetMapping
    public List<Account> findAll() {
        return accountService.findAll();
    }

    @PostMapping("/{customerId}")
    public AccountResponse save(@PathVariable Long customerId, @RequestBody Account account) {
        Customer customer = customerService.find(customerId);
        if (customer != null) {
            customer.getAccounts().add(account);
            account.setCustomer(customer);
            accountService.save(account);
        } else {
            throw new RuntimeException("Customer not found");
        }
        return
                new AccountResponse(account.getId(), account.getAccountName(), account.getMoneyAmount(),
                        new CustomerResponse(customer.getId(), customer.getEmail(), customer.getSalary()));
    }

    @PutMapping("/{customerId}")
    public AccountResponse update(@PathVariable Long customerId, @RequestBody Account account) {
        Customer customer = customerService.find(customerId);
        Account updatedAccount = null;

        for (Account acc : customer.getAccounts()) {
            if (acc.getId() == account.getId()) {
                updatedAccount = acc;
            }
        }

        if (updatedAccount != null) {
            throw new RuntimeException("Account not found");
        }

        int index = customer.getAccounts().indexOf(updatedAccount);

        customer.getAccounts().set(index, account);

        account.setCustomer(customer);

        accountService.save(account);

        return new AccountResponse(account.getId(), account.getAccountName(), account.getMoneyAmount(),
                new CustomerResponse(customer.getId(), customer.getEmail(), customer.getSalary()));

    }

    @DeleteMapping("/{id}")
    public AccountResponse remove(@PathVariable Long id) {
        Account account = accountService.find(id);
        if (account != null) {
            accountService.delete(id);
        } else {
            throw new RuntimeException("Account not found");
        }
        return new AccountResponse(account.getId(), account.getAccountName(), account.getMoneyAmount(),
                new CustomerResponse(account.getCustomer().getId(), account.getCustomer().getEmail(), account.getCustomer().getSalary()));

    }


}
