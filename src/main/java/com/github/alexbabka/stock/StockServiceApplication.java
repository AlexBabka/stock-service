package com.github.alexbabka.stock;


import com.github.alexbabka.stock.repository.StockRepository;
import com.github.alexbabka.stock.repository.entity.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class StockServiceApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(StockServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StockRepository stockRepository) {
        // Initialized with dummy data for testing purposes
        return args -> Arrays.asList("ABN", "ING", "RABO", "SNS", "BUNQ")
                .forEach(
                        name -> {
                            Stock stock = new Stock();
                            stock.setName(name);
                            stock.setCurrentPrice(
                                    new BigDecimal(ThreadLocalRandom.current().nextDouble(0, 1000), MathContext.DECIMAL32));
                            stock.setLastUpdate(ZonedDateTime.now(ZoneId.of("UTC")));

                            stockRepository.save(stock);

                            LOGGER.debug("New stock created: " + stock);
                        });
    }
}
