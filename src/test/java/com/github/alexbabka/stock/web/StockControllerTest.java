package com.github.alexbabka.stock.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alexbabka.stock.service.model.StockModel;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StockControllerTest {
    public static final String STOCKS_API_BASE_PATH = "/api/v1/stocks";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnAllStocks() throws Exception {
        mockMvc.perform(get(STOCKS_API_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(Matchers.greaterThanOrEqualTo(5))));
    }

    @Test
    public void shouldFindStockById() throws Exception {
        mockMvc.perform(get(STOCKS_API_BASE_PATH + "/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("ABN")))
                .andExpect(jsonPath("$.currentPrice", Matchers.greaterThanOrEqualTo(0D)))
                .andExpect(jsonPath("$.lastUpdate", Matchers.notNullValue()));
    }

    @Test
    public void shouldNotFindStock() throws Exception {
        mockMvc.perform(get(STOCKS_API_BASE_PATH + "/100")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateStock() throws Exception {
        String stockName = "ING";
        BigDecimal newPrice = new BigDecimal("1.45");

        StockModel stockModel = new StockModel();
        stockModel.setName(stockName);
        stockModel.setCurrentPrice(newPrice);

        // update price
        mockMvc.perform(put(STOCKS_API_BASE_PATH + "/2")
                .content(objectMapper.writeValueAsBytes(stockModel))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk());

        // Check stock is actually updated
        mockMvc.perform(get(STOCKS_API_BASE_PATH + "/2")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(2)))
                .andExpect(jsonPath("$.name", Matchers.is(stockName)))
                .andExpect(jsonPath("$.currentPrice", Matchers.greaterThanOrEqualTo(newPrice.doubleValue())))
                .andExpect(jsonPath("$.lastUpdate", Matchers.notNullValue()));
    }

    @Test
    public void shouldCreateStock() throws Exception {
        String stockName = "New Company";
        BigDecimal price = new BigDecimal("12.9");

        StockModel stockModel = new StockModel();
        stockModel.setName(stockName);
        stockModel.setCurrentPrice(price);

        mockMvc.perform(post(STOCKS_API_BASE_PATH)
                .content(objectMapper.writeValueAsBytes(stockModel))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.name", Matchers.is(stockName)))
                .andExpect(jsonPath("$.currentPrice", Matchers.greaterThanOrEqualTo(price.doubleValue())))
                .andExpect(jsonPath("$.lastUpdate", Matchers.notNullValue()));
    }

    @Test
    public void shouldNotCreateStockWithoutName() throws Exception {
        StockModel stockModel = new StockModel();
        stockModel.setCurrentPrice(new BigDecimal("12.9"));

        mockMvc.perform(post(STOCKS_API_BASE_PATH)
                .content(objectMapper.writeValueAsBytes(stockModel))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotCreateStockWithoutPrice() throws Exception {
        StockModel stockModel = new StockModel();
        stockModel.setName("New stock");

        mockMvc.perform(post(STOCKS_API_BASE_PATH)
                .content(objectMapper.writeValueAsBytes(stockModel))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest());
    }
}
