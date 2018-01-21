package com.github.alexbabka.stock.service;

import com.github.alexbabka.stock.exception.StockNotFoundException;
import com.github.alexbabka.stock.repository.StockRepository;
import com.github.alexbabka.stock.service.model.StockModel;
import com.github.alexbabka.stock.service.transform.StockTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StockService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

    private StockRepository stockRepository;
    private StockTransformer stockTransformer;

    public StockService(StockRepository stockRepository, StockTransformer stockTransformer) {
        this.stockRepository = stockRepository;
        this.stockTransformer = stockTransformer;
    }

    /**
     * Find all stocks and return list of models.
     *
     * @return list of stock models
     */
    public List<StockModel> findAllStocks() {
        return StreamSupport.stream(stockRepository.findAll().spliterator(), false)
                .map(stockTransformer::toModel)
                .collect(Collectors.toList());
    }

    /**
     * Find stock by the provided id and transform into model.
     *
     * @param stockId stock id
     * @return stock model
     */
    public StockModel findStock(long stockId) {
        return Optional.of(stockId)
                .map(stockRepository::findOne)
                .map(stockTransformer::toModel)
                .orElseThrow(() -> new StockNotFoundException(stockId));
    }

    /**
     * Create new stock object based on the provided model and save into the database.
     *
     * @param stockModel stock model to create
     * @return model of the newly created stock
     */
    @Transactional
    public StockModel createStock(StockModel stockModel) {
        return Optional.of(stockModel)
                .map(stockTransformer::fromModel)
                .map(stockRepository::save)
                .map(stockTransformer::toModel)
                .orElseThrow(RuntimeException::new);
    }

    /**
     * Update price value of the stock identified by provided id.
     *
     * @param stockId  stock id
     * @param newPrice new price
     */
    @Transactional
    public void updateStock(long stockId, BigDecimal newPrice) {
        int updatedStocks = stockRepository.updateStock(stockId, newPrice);

        if (updatedStocks == 0) {
            LOGGER.debug("Stock with id ({}) not found", stockId);

            throw new StockNotFoundException(stockId);
        }
    }
}
