package com.trader322.trader.Model;

import javax.persistence.*;

@Entity
@Table(name = "poloniex")
public class PoloniexModel {

    @EmbeddedId
    private PoloniexIdentity poloniexIdentity;

    @Column(insertable = false, updatable = false)
    private int id;

    @Column
    private String name;

    @Column
    private double last;

    @Column
    private double lowestAsk;

    @Column
    private double highestBid;

    @Column
    private double percentChange;

    @Column
    private double baseVolume;

    @Column
    private double quoteVolume;

    @Column
    private double high24hr;

    @Column
    private double low24hr;

    public PoloniexIdentity getPoloniexIdentity() {
        return poloniexIdentity;
    }

    public PoloniexModel setPoloniexIdentity(PoloniexIdentity poloniexIdentity) {
        this.poloniexIdentity = poloniexIdentity;
        return this;
    }

    public int getId() {
        return id;
    }

    public PoloniexModel setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PoloniexModel setName(String name) {
        this.name = name;
        return this;
    }

    public double getLast() {
        return last;
    }

    public PoloniexModel setLast(double last) {
        this.last = last;
        return this;
    }

    public double getLowestAsk() {
        return lowestAsk;
    }

    public PoloniexModel setLowestAsk(double lowestAsk) {
        this.lowestAsk = lowestAsk;
        return this;
    }

    public double getHighestBid() {
        return highestBid;
    }

    public PoloniexModel setHighestBid(double highestBid) {
        this.highestBid = highestBid;
        return this;
    }

    public double getPercentChange() {
        return percentChange;
    }

    public PoloniexModel setPercentChange(double percentChange) {
        this.percentChange = percentChange;
        return this;
    }

    public double getBaseVolume() {
        return baseVolume;
    }

    public PoloniexModel setBaseVolume(double baseVolume) {
        this.baseVolume = baseVolume;
        return this;
    }

    public double getQuoteVolume() {
        return quoteVolume;
    }

    public PoloniexModel setQuoteVolume(double quoteVolume) {
        this.quoteVolume = quoteVolume;
        return this;
    }

    public double getHigh24hr() {
        return high24hr;
    }

    public PoloniexModel setHigh24hr(double high24hr) {
        this.high24hr = high24hr;
        return this;
    }

    public double getLow24hr() {
        return low24hr;
    }

    public PoloniexModel setLow24hr(double low24hr) {
        this.low24hr = low24hr;
        return this;
    }
}
