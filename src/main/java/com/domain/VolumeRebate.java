package com.domain;

import javax.persistence.*;
import java.time.Period;
import java.util.List;

/**
 * Rebate that is not specific to products but volume of a product.
 */

@Entity
@Table(name="VOL_REBATE")
public class VolumeRebate extends Rebate {
    private int volume;

    public VolumeRebate() {}

    public VolumeRebate(int volume, double rate) {
        this.volume = volume;
        super.setRate(rate);
    }


    public VolumeRebate(int volume, double rate, Period period) {
        this.volume = volume;
        super.setRate(rate);
        super.setPeriod(period);
    }

    public VolumeRebate(int volume, double rate, List<Customer> customers) {
        this.volume = volume;
        super.setRate(rate);
        super.setCustomerList(customers);
    }

    public VolumeRebate(int volume, double rate, Period period, List<Customer> customers) {
        this.volume = volume;
        super.setRate(rate);
        super.setPeriod(period);
        super.setCustomerList(customers);
    }

    @Column(name="Volume", nullable = false, unique = true)
    public int getVolume()                 { return volume; }
    public void setVolume(int volume)        { this.volume = volume; }
}
