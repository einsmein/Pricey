package com.controller;

import com.domain.*;
import com.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rebates")
public class RebateController {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    RebateRepo rebateRepo;
    @Autowired
    ProductRepo ProductRepo;
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    ProductRebateRepo productRebateRepo;
    @Autowired
    VolumeRebateRepo volumeRebateRepo;

    @RequestMapping(method = RequestMethod.GET)
    public List findAll() {
        return rebateRepo.findAll();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Rebate findOne(@PathVariable Integer id) { return rebateRepo.findOne(id); }

    @RequestMapping(value="/product", method = RequestMethod.GET)
    public List<ProductRebate> allByProduct() {
        Query query = em.createQuery("SELECT pr FROM ProductRebate pr");
        return query.getResultList();
    }

    /* functions for query different kind of rebate from database
     * Keyword: ByX: X must be given, ForAllX: X must not be given
     * i.e. findByProductForAllCustomer: find rebates associated with the given product that are not specific to customers.
     **/
    private List<ProductRebate> findByProduct(Integer id) {
        Query query = em.createQuery("SELECT pr FROM ProductRebate pr " +
                "JOIN pr.productList product WHERE product.id = (:pid)").setParameter("pid", id);
        return query.getResultList();
    }

    private List<Rebate> findByCustomer(Integer id) {
        Query query = em.createQuery("SELECT r FROM Rebate r " +
                "JOIN r.customerList c WHERE c.id = (:cid)").setParameter("cid", id);
        return query.getResultList();
    }

    public List<VolumeRebate> findAllVol() {
        Query query = em.createQuery("SELECT vr FROM VolumeRebate vr");
        return query.getResultList();
    }

    private List<ProductRebate> findForAllCustomer() {
        Query query = em.createQuery("SELECT r FROM Rebate r WHERE size(r.customerList) = 0");
        return query.getResultList();
    }

    private List<ProductRebate> findByProductForAllCustomer(Integer pid) {
        Query query = em.createQuery("SELECT pr FROM ProductRebate pr " +
                "JOIN pr.productList product WHERE product.id = (:pid) AND size(pr.customerList)=0")
                .setParameter("pid", pid);
        return query.getResultList();
    }

    private List<ProductRebate> findByCustomerForAllProduct(Integer cid) {
        Query query = em.createQuery("SELECT r FROM Rebate r " +
                "JOIN r.customerList customer WHERE customer.id = (:cid) AND r NOT IN " +
                "(SELECT pr FROM ProductRebate pr)")
                .setParameter("cid", cid);
        return query.getResultList();
    }

    private List<Rebate> findForAllProductAndCustomer() {
        Query query = em.createQuery("SELECT r FROM Rebate r WHERE size(r.customerList)=0 AND r NOT IN " +
                "(SELECT pr FROM ProductRebate pr)");
        return query.getResultList();
    }

    private List<ProductRebate> findByProductAndCustomer(@PathVariable Integer pid, @PathVariable Integer cid) {
        Query query = em.createQuery("SELECT pr FROM ProductRebate pr " +
                    "INNER JOIN pr.productList p " +
                    "INNER JOIN pr.customerList c " +
                    "WHERE (c.id = (:cid) AND p.id = (:pid))")
                    .setParameter("cid", cid)
                    .setParameter("pid", pid);
        return query.getResultList();
    }

    /* API providing service to find all the rebates that apply for various
     * specifications and calculate the price regarding rebate agreement
     * */
    // Different from findAll s.t. customer, product specific rebate agreement will not be shown
    @RequestMapping(value="/forall", method = RequestMethod.GET)
    public List<Rebate> findAppliedForAll() {
         return findForAllProductAndCustomer();
    }

    @RequestMapping(value="/customer/{cid}", method = RequestMethod.GET)
    public List<Rebate> findAppliedForCustomer(@PathVariable Integer cid) {
        List<Rebate> appRebate = new ArrayList<Rebate>();
        appRebate.addAll(findByCustomerForAllProduct(cid));
        appRebate.addAll(findForAllCustomer());
        return appRebate;
    }

    @RequestMapping(value="/product/{pid}/customer/{cid}", method = RequestMethod.GET)
    public List<Rebate> findAppliedForProductAndCustomer(@PathVariable Integer pid, @PathVariable Integer cid) {
        List<Rebate> appRebate = new ArrayList<Rebate>();
        appRebate.addAll(findByProductAndCustomer(pid, cid));
        appRebate.addAll(findForAllProductAndCustomer());
        appRebate.addAll(findByProductForAllCustomer(pid));
        appRebate.addAll(findByCustomerForAllProduct(cid));
        return appRebate;
    }

    @RequestMapping(value="/product/{pid}", method = RequestMethod.GET)
    public List<Rebate> findAppliedForProduct(@PathVariable Integer pid) {
        List<Rebate> appRebate = new ArrayList<Rebate>();
        appRebate.addAll(findByProductForAllCustomer(pid));
        appRebate.addAll(findForAllProductAndCustomer());
        return appRebate;
    }

    @RequestMapping(value="/price/product/{pid}/customer/{cid}", method = RequestMethod.GET)
    public List<RebatePrice> findPriceForProductAndCustomer(@PathVariable Integer pid, @PathVariable Integer cid) {
        List<Rebate> appRebate = findAppliedForProductAndCustomer(pid, cid);

        Product product = ProductRepo.findOne(pid);
        return calculatePrice(appRebate, product);
    }

    @RequestMapping(value="/price/product/{pid}", method = RequestMethod.GET)
    public List<RebatePrice> findPriceForProduct(@PathVariable Integer pid) {
        List<Rebate> appRebate = findAppliedForProduct(pid);

        Product product = ProductRepo.findOne(pid);
        return calculatePrice(appRebate, product);
    }

    public List<RebatePrice> calculatePrice(List<Rebate> rebates, Product product) {
        List<RebatePrice> appPrice = new ArrayList<RebatePrice>();
        double stdPrice = product.getPrice();
        double newPrice;
        double rate;

        List<VolumeRebate> vr = findAllVol();
        boolean isVolRebt;
        for (Rebate r: rebates) {
            isVolRebt = false;
            rate = r.getRate();
            newPrice = stdPrice - stdPrice * rate;

            for (VolumeRebate v: vr) {
                if (v.getId() == r.getId()) {
                    appPrice.add(new RebatePrice(newPrice, r, v.getVolume()));
                    isVolRebt = true;
                    break;
                }
            }
            if(!isVolRebt) { appPrice.add(new RebatePrice(newPrice, r)); }
        }
        return appPrice;
    }
}