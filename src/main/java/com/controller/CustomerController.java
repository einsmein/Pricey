package com.controller;


import com.domain.Customer;
import com.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerRepo custRepo;

    @RequestMapping(method = RequestMethod.GET)
    public List findAll() {
        return custRepo.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Customer findOne(@PathVariable Integer id) { return custRepo.findOne(id); }

}
