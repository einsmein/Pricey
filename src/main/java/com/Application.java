package com;

import com.domain.*;
import com.repository.CustomerRepo;
import com.repository.ProductRebateRepo;
import com.repository.ProductRepo;
import com.repository.VolumeRebateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    private ProductRepo prodRepo;
    @Autowired
    private CustomerRepo custRepo;
    @Autowired
    private ProductRebateRepo prodRebtRepo;
    @Autowired
    private VolumeRebateRepo volRebtRepo;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    @Override
    @Transactional
    public void run(String... strings) throws Exception {
        Customer[] customers = {
                new Customer("Honor Interior"),
                new Customer("Emart"),
                new Customer("Telenor"),
                new Customer("Aker"),
                new Customer("Homeplus"),
                new Customer("Coop")
        };
        for (Customer c: customers){ custRepo.save(c); }

        Product[] products = {
                new Product("Book Shelf", 87.6),
                new Product("Lamp", 15),
                new Product("Curtain", 12),
                new Product("Sofa", 55),
                new Product("Bord", 12.3),
                new Product("Stol", 32.1),
        };
        for (Product p : products){ prodRepo.save(p); }

        LocalDate today = LocalDate.now();

        /* Rebate agreement with customer */
        // Rebate1: 10% rebate for "Honor Interior"
        List<Customer> customer1 = new ArrayList<Customer>();
        customer1.add(customers[0]);
        Rebate rebate1 = new Rebate(0.10, customer1);
        prodRebtRepo.save(rebate1);
        // Rebate2: 10% rebate for "Emart" and "Telenor" for buying curtains
        List<Customer> customer2 = new ArrayList<Customer>();
        customer2.add(customers[1]);
        customer2.add(customers[2]);
        List<Product> productSet1 = new ArrayList<Product>();
        productSet1.add(products[2]);
        Rebate rebate2 = new ProductRebate(productSet1, 0.10, customer2);
        prodRebtRepo.save(rebate2);

        /* Extension: Volume rebate agreement*/
        // Rebate3: 15% for any customer who buys more than 500 pieces of a product
        Rebate rebate3 = new VolumeRebate(500, 0.15);
        volRebtRepo.save(rebate3);
        // Rebate4: 10% for "Coop" when buying more than 200 pieces of a product
        List<Customer> customer4 = new ArrayList<Customer>();
        customer4.add(customers[5]);
        Rebate rebate4 = new VolumeRebate(200, 0.10, customer4);
        volRebtRepo.save(rebate4);

        /* Extension: Non customer-specific rebate */
        // Rebate5: 5% rebate for all products to all customers during this week
        LocalDate until = LocalDate.of(2016, Month.APRIL, 23);
        Period period = Period.between(today, until);
        Rebate rebate5 = new Rebate(0.05, period);
        prodRebtRepo.save(rebate5);
        // Rebate6: 8% rebate if buying "Bord" or "Stol" in this year
        until = LocalDate.of(2016, Month.DECEMBER, 31);
        List<Product> productSet2 = new ArrayList<Product>();
        productSet2.add(products[4]);
        productSet2.add(products[5]);
        Rebate rebate6 = new ProductRebate(productSet2, 0.08, period);
        prodRebtRepo.save(rebate6);

    }
}