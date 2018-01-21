package com.github.alexbabka.stock.service;

import com.github.alexbabka.stock.exception.StockNotFoundException;
import com.github.alexbabka.stock.repository.StockRepository;
import com.github.alexbabka.stock.repository.entity.Stock;
import com.github.alexbabka.stock.service.model.StockModel;
import com.github.alexbabka.stock.service.transform.StockTransformer;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceTest {
    private static final String FIRST_STOCK_NAME = "First";
    private static final String SECOND_STOCK_NAME = "Second";

    @InjectMocks
    private StockService stockService;
    @Mock
    private StockRepository stockRepository;
    @Spy
    private StockTransformer stockTransformer;

    @Test
    public void shouldFindAllStocks() {
        // given
        Mockito.when(stockRepository.findAll())
                .thenReturn(Arrays.asList(
                        createStockInstance(1, FIRST_STOCK_NAME, new BigDecimal("1.45")),
                        createStockInstance(2, SECOND_STOCK_NAME, new BigDecimal("2.45"))
                ));

        // when
        List<StockModel> stocks = stockService.findAllStocks();

        // then
        Mockito.verify(stockRepository).findAll();

        Assertions.assertThat(stocks).hasSize(2);
        Assertions.assertThat(stocks.get(0)).hasFieldOrPropertyWithValue("name", FIRST_STOCK_NAME);
        Assertions.assertThat(stocks.get(1)).hasFieldOrPropertyWithValue("name", SECOND_STOCK_NAME);
    }

    @Test
    public void shouldFindStockById() throws Exception {
        // given
        long stockId = 1L;
        BigDecimal price = new BigDecimal("1.45");
        Mockito.when(stockRepository.findOne(stockId))
                .thenReturn(createStockInstance(1, FIRST_STOCK_NAME, price));

        // when
        StockModel stock = stockService.findStock(stockId);

        // then
        Mockito.verify(stockRepository).findOne(stockId);

        Assertions.assertThat(stock).isNotNull();
        Assertions.assertThat(stock.getId()).isEqualTo(stockId);
        Assertions.assertThat(stock.getName()).isEqualTo(FIRST_STOCK_NAME);
        Assertions.assertThat(stock.getCurrentPrice()).isEqualTo(price);
        Assertions.assertThat(stock.getLastUpdate()).isNotNull();
    }

    @Test
    public void shouldCreateStock() throws Exception {
        // given
        String name = "New Stock";
        BigDecimal price = new BigDecimal("4.45");

        StockModel stockModel = new StockModel();
        stockModel.setName(name);
        stockModel.setCurrentPrice(price);

        long newStockId = 10L;
        Mockito.when(stockRepository.save(any(Stock.class))).thenReturn(createStockInstance(newStockId, name, price));

        // when
        StockModel newStock = stockService.createStock(stockModel);

        // then
        Mockito.verify(stockRepository).save(any(Stock.class));

        Assertions.assertThat(newStock).isNotNull();
        Assertions.assertThat(newStock.getId()).isEqualTo(newStockId);
        Assertions.assertThat(newStock.getName()).isEqualTo(name);
        Assertions.assertThat(newStock.getCurrentPrice()).isEqualTo(price);
        Assertions.assertThat(newStock.getLastUpdate()).isNotNull();
    }

    @Test
    public void shouldUpdateExistingStock() throws Exception {
        // given
        long stockId = 1L;
        BigDecimal newPrice = new BigDecimal("1.45");

        Mockito.when(stockRepository.updateStock(stockId, newPrice)).thenReturn(1);

        // when
        stockService.updateStock(stockId, newPrice);

        // then
        Mockito.verify(stockRepository).updateStock(stockId, newPrice);
    }

    @Test(expected = StockNotFoundException.class)
    public void shouldNotUpdateNonExistingStock() throws Exception {
        // given
        long stockId = 100L;
        BigDecimal newPrice = new BigDecimal("1.45");

        Mockito.when(stockRepository.updateStock(stockId, newPrice)).thenReturn(0);

        // when
        stockService.updateStock(stockId, newPrice);

        // then
        Mockito.verify(stockRepository).updateStock(stockId, newPrice);
    }

    private Stock createStockInstance(long id, String name, BigDecimal price) {
        Stock stock = new Stock();

        stock.setId(id);
        stock.setName(name);
        stock.setCurrentPrice(price);
        stock.setLastUpdate(ZonedDateTime.now(ZoneId.of("UTC")));

        return stock;
    }

}