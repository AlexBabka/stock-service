package com.github.alexbabka.stock.web;

import com.github.alexbabka.stock.service.StockService;
import com.github.alexbabka.stock.service.model.StockModel;
import com.github.alexbabka.stock.web.error.ApiError;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class StockController {
    @Autowired
    private StockService stockService;

    @ApiOperation(value = "Find all stocks", notes = "Retrieving the collection of stocks", response = StockModel[].class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = StockModel[].class),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    @GetMapping(value = "stocks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<StockModel> getAll() {
        return stockService.findAllStocks();
    }

    @ApiOperation(value = "Find stock by id", notes = "Retrieving stock by provided id", response = StockModel.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = StockModel.class),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @GetMapping(value = "stocks/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public StockModel getOne(@PathVariable(name = "id") long stockId) {
        return stockService.findStock(stockId);
    }

    @ApiOperation(value = "Update stock price", notes = "Update price for a particular stock, identified by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @PutMapping(value = "stocks/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateStock(@PathVariable(name = "id") long stockId, @Valid @RequestBody StockModel stockModel) {
        stockService.updateStock(stockId, stockModel.getCurrentPrice());
    }

    @ApiOperation(code = 201,
            value = "Create new stock",
            notes = "Create new stock according to the provided model")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = StockModel.class),
            @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    @PostMapping(value = "stocks", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<StockModel> createStock(@Valid @RequestBody StockModel stockModel) {
        StockModel createdStockModel = stockService.createStock(stockModel);

        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdStockModel.getId()).toUri();
        return ResponseEntity.created(location).body(createdStockModel);
    }
}
