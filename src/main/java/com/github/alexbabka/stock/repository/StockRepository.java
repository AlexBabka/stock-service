package com.github.alexbabka.stock.repository;

import com.github.alexbabka.stock.repository.entity.Stock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface StockRepository extends CrudRepository<Stock, Long> {
    @Modifying
    @Query(value = "update Stock s set currentPrice = :newPrice where s.id = :stockId")
    int updateStock(@Param(value = "stockId") long stockId, @Param("newPrice") BigDecimal newPrice);
}
