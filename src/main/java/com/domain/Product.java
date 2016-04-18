package com.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="PRODUCT")
public class Product {
    private int id;
    private String name;
    private double price;
    private List<Rebate> rebateList;

    public Product() {}

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Id
    @Column(name = "Prod_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId()                  { return id; }
    public void setId(int id)           { this.id = id; }

    @Column(name = "Prod_Name", nullable = false)
    public String getName()             { return name; }
    public void setName(String name)    { this.name = name; }

    @Column(name = "Prod_Price", nullable = false)
    public double getPrice()            { return price; }
    public void setPrice(double price)   { this.price = price; }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "productList", targetEntity = ProductRebate.class)
    public List<Rebate> getRebateList() {
        return rebateList;
    }
    public void setRebateList(List<Rebate> rebates) {
        this.rebateList = rebates;
    }

}
