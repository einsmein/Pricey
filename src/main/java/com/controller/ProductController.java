package com.controller;

import com.domain.Product;
import com.domain.Rebate;
import com.repository.ProductRepo;
import com.repository.RebateBaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepo productRepo;

    @RequestMapping(method = RequestMethod.GET)
    public List findAll() { return productRepo.findAll(); }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Product findOne(@PathVariable Integer id) { return productRepo.findOne(id); }


}
