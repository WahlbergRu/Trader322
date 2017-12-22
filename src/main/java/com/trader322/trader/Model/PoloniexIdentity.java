package com.trader322.trader.Model;


import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PoloniexIdentity implements Serializable {

    @NotNull
    private int id;

    @NotNull
    private long timestamp;

    public PoloniexIdentity() {

    }

    public PoloniexIdentity(int id, long timestamp){
        this.id = id;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public PoloniexIdentity setId(int id) {
        this.id = id;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public PoloniexIdentity setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
