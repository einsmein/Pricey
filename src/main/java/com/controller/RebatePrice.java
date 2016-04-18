package com.controller;

import com.domain.Rebate;

/**
 * Created by admin on 4/16/2016.
 */
public class RebatePrice {
    private double newPrice;
    private Rebate rebate;
    private int minVolume = 1;

    public RebatePrice(double np, Rebate r) {
        this.newPrice = np;
        this.rebate = r;
    }


    public RebatePrice(double np, Rebate r, int v) {
        this.newPrice = np;
        this.rebate = r;
        this.minVolume = v;
    }

    public double getNewprice()             { return newPrice; }
    public void setNewPrice(double price)   { this.newPrice = price; }

    public Rebate getRebate()               { return rebate; }
    public void setRebate(Rebate rebate)    { this.rebate = rebate; }

    public int getMinVolume()               { return minVolume; }
    public void setMinVolume(int volume)    { this.minVolume = volume; }
}
