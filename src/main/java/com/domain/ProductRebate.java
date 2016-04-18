package com.domain;

import javax.persistence.*;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;


/**
 * Rebate that specifies certain type of products to get refund.
 */

@Entity
@Table(name ="PROD_REBATE")
public class ProductRebate extends Rebate {
    private List<Product> productList = new ArrayList<Product>();

    public ProductRebate() {}

    public ProductRebate(List<Product> products, double rate) {
        this.productList = products;
        super.setRate(rate);
    }


    public ProductRebate(List<Product> products, double rate, Period period) {
        this.productList = products;
        super.setRate(rate);
        super.setPeriod(period);
    }

    public ProductRebate(List<Product> products, double rate, List<Customer> customers) {
        this.productList = products;
        super.setRate(rate);
        super.setCustomerList(customers);
    }


    public ProductRebate(List<Product> products, double rate, Period period, List<Customer> customers) {
        this.productList = products;
        super.setRate(rate);
        super.setPeriod(period);
        super.setCustomerList(customers);
    }


    @ManyToMany
    @JoinTable(name = "PROD_REBATE_DETAIL",
            joinColumns = @JoinColumn(name = "Rebate_Id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "Prod_Id", nullable = false))
    public List<Product> getProductList() {
        return productList;
    }
    public void setProductList(List<Product> products) {
        this.productList = products;
    }

    public void addCustomer(Customer customer) { this.getCustomerList().add(customer); }

    //TODO: change to prod-specific --> total relation
    //TODO: add relationship with customers --> in Rebate
}
