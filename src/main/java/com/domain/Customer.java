package com.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="CUSTOMER")
public class Customer {
    private int id;
    private String name;
    private List<Rebate> rebateList;

    public Customer() {}

    public Customer(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "Cust_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId()                  { return id; }
    public void setId(int id)           { this.id = id; }

    @Column(name = "Cust_Name")
    public String getName()             { return name; }
    public void setName(String name)    { this.name = name; }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "customerList", targetEntity = Rebate.class)
    public List<Rebate> getRebateList() { return rebateList; }
    public void setRebateList(List<Rebate> rebates) { this.rebateList = rebates; }

}
