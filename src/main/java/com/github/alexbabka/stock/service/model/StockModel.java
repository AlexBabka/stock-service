package com.github.alexbabka.stock.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Pojo class used in rest api to represent the stock object.
 */
@JsonIgnoreProperties(value = {"id", "lastUpdate"}, allowGetters = true)
public class StockModel {
    private Long id;
    @NotNull
    @Size(max = 50)
    private String name;
    @NotNull
    @Min(0)
    private BigDecimal currentPrice;
    private ZonedDateTime lastUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public ZonedDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
