package com.workintech.s18d4.controller;

import com.workintech.s18d4.dto.CustomerResponse;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getAll() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public Customer get(Long id) {
        return customerService.find(id);
    }

    @PostMapping
    public CustomerResponse save(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.save(customer);
        return new CustomerResponse(savedCustomer.getId(), savedCustomer.getEmail(), savedCustomer.getSalary());
    }

}
