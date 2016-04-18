package com.domain;

import javax.persistence.*;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all kinds of rebate
 */

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Rebate {
    private int id;
    private Period period;
    private double rate;
    private List<Customer> customerList = new ArrayList<Customer>();


    public Rebate() { rate = 1.0; }

    public Rebate(double rate) {
        this.rate = rate;
    }

    public Rebate(double rate, Period period) {
        this.rate = rate;
        this.period = period;
    }

    public Rebate(double rate, List<Customer> customers, Period period) {
        this.rate = rate;
        this.customerList = customers;
        this.period = period;
    }

    public Rebate(double rate, List<Customer> customers) {
        this.rate = rate;
        this.customerList = customers;
    }


    @Id
    @Column(name="Rebate_Id")
    @GeneratedValue(strategy = GenerationType.TABLE)
    public int getId()                     { return id; }
    public void setId(int id)              { this.id = id; }

    @Column(name="Rebate_Rate", nullable = false)
    public double getRate()                 { return rate; }
    public void setRate(double rate)        { this.rate = rate; }

    @Column(name="Rebate_Period")
    public Period getPeriod()              { return period; }
    public void setPeriod(Period period)   { this.period = period; }

    @ManyToMany
    @JoinTable(name = "REBATE_CUSTOMER",
            joinColumns = @JoinColumn(name = "REBATE_ID"),
            inverseJoinColumns = @JoinColumn(name = "CUST_ID"))
    public List<Customer> getCustomerList()    { return customerList; }
    public void setCustomerList(List<Customer> customers)   { this.customerList = customers; }

}
