package com.github.alexbabka.stock.service.transform;

import com.github.alexbabka.stock.repository.entity.Stock;
import com.github.alexbabka.stock.service.model.StockModel;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class StockTransformerTest {
    private static final long ID = 1L;
    private static final String NAME = "Company";
    private static final BigDecimal PRICE = BigDecimal.ONE;
    private static final ZonedDateTime LAST_UPDATE_DATE = ZonedDateTime.now(ZoneId.of("UTC"));

    private StockTransformer stockTransformer = new StockTransformer();

    @Test
    public void shouldTransformToModel() {
        Stock stock = new Stock();
        stock.setId(ID);
        stock.setName(NAME);
        stock.setCurrentPrice(PRICE);
        stock.setLastUpdate(LAST_UPDATE_DATE);

        StockModel stockModel = stockTransformer.toModel(stock);

        Assertions.assertThat(stockModel).isNotNull();
        Assertions.assertThat(stockModel.getId()).isEqualTo(ID);
        Assertions.assertThat(stockModel.getName()).isEqualTo(NAME);
        Assertions.assertThat(stockModel.getCurrentPrice()).isEqualTo(PRICE);
        Assertions.assertThat(stockModel.getLastUpdate()).isEqualTo(LAST_UPDATE_DATE);
    }

    @Test
    public void shouldTransformFromModel() {
        StockModel stockModel = new StockModel();
        stockModel.setName(NAME);
        stockModel.setCurrentPrice(PRICE);

        Stock stock = stockTransformer.fromModel(stockModel);

        Assertions.assertThat(stock).isNotNull();
        Assertions.assertThat(stock.getId()).isNull();
        Assertions.assertThat(stock.getName()).isEqualTo(NAME);
        Assertions.assertThat(stock.getCurrentPrice()).isEqualTo(PRICE);
        Assertions.assertThat(stock.getLastUpdate()).isNotNull();
    }
}
