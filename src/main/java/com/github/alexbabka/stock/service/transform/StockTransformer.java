package com.github.alexbabka.stock.service.transform;

import com.github.alexbabka.stock.repository.entity.Stock;
import com.github.alexbabka.stock.service.model.StockModel;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class StockTransformer {
    /**
     * Transform stock entity to model, that can be used in REST API.
     *
     * @param stock stock entity from persistence storage
     * @return stock model
     */
    public StockModel toModel(Stock stock) {
        StockModel stockModel = new StockModel();

        stockModel.setId(stock.getId());
        stockModel.setName(stock.getName());
        stockModel.setCurrentPrice(stock.getCurrentPrice());
        stockModel.setLastUpdate(stock.getLastUpdate());

        return stockModel;
    }

    /**
     * Transform stock model into persistent entity.
     *
     * @param stockModel stock model
     * @return stock entity
     */
    public Stock fromModel(StockModel stockModel) {
        Stock stock = new Stock();

        stock.setName(stockModel.getName());
        stock.setCurrentPrice(stockModel.getCurrentPrice());
        stock.setLastUpdate(ZonedDateTime.now(ZoneId.of("UTC")));

        return stock;
    }
}
