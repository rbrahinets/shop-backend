package com.shop.controllers;

import com.shop.models.Basket;
import com.shop.services.BasketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = BasketController.BASKETS_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class BasketController {
    public static final String BASKETS_URL = "/web-api/baskets";
    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping
    public List<Basket> findAllBaskets() {
        return basketService.findAll();
    }

    @GetMapping("/{id}")
    public Basket findByIdBasket(@PathVariable int id) {
        return basketService.findById(id);
    }

    @PostMapping
    public Basket saveBasket(
        @RequestBody Basket basket,
        @RequestBody int personId
    ) {
        return basketService.save(basket, personId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBasket(@PathVariable int id) {
        basketService.delete(id);
    }
}
