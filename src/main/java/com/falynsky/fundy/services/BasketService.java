package com.falynsky.fundy.services;

import com.falynsky.fundy.models.Basket;
import com.falynsky.fundy.models.User;
import com.falynsky.fundy.repositories.BasketRepository;
import org.springframework.stereotype.Service;

@Service
public class BasketService {

    private final BasketRepository basketRepository;

    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public Basket getCurrentUserBasket(User user) throws Exception {
        Basket basket = basketRepository.findByUserId(user);
        if (basket == null) {
            throw new Exception("BASKET NOT FOUND");
        } else {
            return basket;
        }
    }
}
