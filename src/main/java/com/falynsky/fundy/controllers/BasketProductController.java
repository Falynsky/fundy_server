package com.falynsky.fundy.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.falynsky.fundy.exceptions.NotEnoughQuantity;
import com.falynsky.fundy.models.Basket;
import com.falynsky.fundy.models.DTO.BasketProductDTO;
import com.falynsky.fundy.repositories.AccountRepository;
import com.falynsky.fundy.repositories.BasketProductRepository;
import com.falynsky.fundy.repositories.BasketRepository;
import com.falynsky.fundy.repositories.ProductRepository;
import com.falynsky.fundy.repositories.UserRepository;
import com.falynsky.fundy.services.BasketProductService;
import com.falynsky.fundy.utils.ResponseMapUtils;
import com.falynsky.fundy.utils.ResponseUtils;

@CrossOrigin
@RestController
@RequestMapping("/baskets_products")
public class BasketProductController {

    BasketProductRepository basketProductRepository;
    BasketRepository basketRepository;
    ProductRepository productRepository;
    AccountRepository accountRepository;
    UserRepository userRepository;
    BasketProductService basketProductService;

    public BasketProductController(
            BasketProductRepository basketProductRepository,
            BasketRepository basketRepository,
            ProductRepository productRepository,
            AccountRepository accountRepository,
            UserRepository userRepository,
            BasketProductService basketProductService) {
        this.basketProductRepository = basketProductRepository;
        this.basketRepository = basketRepository;
        this.productRepository = productRepository;
        this.basketProductService = basketProductService;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/getBasketId")
    public Map<String, Object> getBasketIdByUsername(
            @RequestHeader(value = "Auth", defaultValue = "empty") String userToken) throws Exception {

        Basket basket = basketProductService.getUserBasketByUserToken(userToken);
        Map<String, Object> data = new HashMap<>();
        data.put("basketId", basket.getId());
        return ResponseMapUtils.buildResponse(data, true);

    }

    @GetMapping("/getSummary")
    public Map<String, Object> getBasketSummaryByUsername(
            @RequestHeader(value = "Auth", defaultValue = "empty") String userToken) throws Exception {

        Basket basket = basketProductService.getUserBasketByUserToken(userToken);
        List<BasketProductDTO> basketProductDTOS = basketProductRepository
                .retrieveBasketProductAsDTObyBasketIdAndNotClosedYet(basket.getId());

        Map<String, Object> data = basketProductService.getSelectedBasketSummary(basketProductDTOS);
        return ResponseMapUtils.buildResponse(data, true);

    }

    @GetMapping("/getUserProducts")
    public Map<String, Object> getBasketProductsByUsername(
            @RequestHeader(value = "Auth", defaultValue = "empty") String userToken) throws Exception {

        Basket basket = basketProductService.getUserBasketByUserToken(userToken);
        List<BasketProductDTO> basketProductDTOS = basketProductRepository
                .retrieveBasketProductAsDTObyBasketIdAndNotClosedYet(basket.getId());
        List<Map<String, Object>> basketProductsData = basketProductService
                .getSelectedBasketProductsData(basketProductDTOS);
        return ResponseMapUtils.buildResponse(basketProductsData, true);

    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addToBasket(
            @RequestBody Map<String, Object> map,
            @RequestHeader(value = "Auth", defaultValue = "empty") String userToken) {
        String productName = "";
        try {
            productName = basketProductService.updateOrCreateBasketProduct(map, userToken);
        } catch (NotEnoughQuantity e) {
            return ResponseUtils.errorResponse(e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseUtils.sendCorrectResponse("Dodano " + productName + " do koszyka.");
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/remove")
    public ResponseEntity<Map<String, Object>> removeOneSelectedProductFromBasket(
            @RequestBody Map<String, Object> map,
            @RequestHeader(value = "Auth", defaultValue = "empty") String userToken) {
        String productName;
        try {
            productName = basketProductService.removeProductFromBasket(map, userToken);
        } catch (Exception ex) {
            return ResponseUtils.errorResponse(ex.getMessage());
        }
        return ResponseUtils.sendCorrectResponse("Usunięto jedną pozycję produktu - " + productName);
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/removeAllFromKind")
    public ResponseEntity<Map<String, Object>> removeSelectedProductsFromBasket(
            @RequestBody Map<String, Object> map,
            @RequestHeader(value = "Auth", defaultValue = "empty") String userToken) {
        String productName;
        try {
            productName = basketProductService.removeProductFromBasket(map, userToken);
        } catch (Exception ex) {
            return ResponseUtils.errorResponse(ex.getMessage());
        }
        return ResponseUtils.sendCorrectResponse("Usunięto wszystkie pozycję produktu - " + productName);
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/removeAll")
    public ResponseEntity<Map<String, Object>> removeAllProductsFromBasket(
            @RequestHeader(value = "Auth", defaultValue = "empty") String userToken) {
        try {
            basketProductService.removeAllProductsFromBasket(userToken);
        } catch (Exception ex) {
            return ResponseUtils.errorResponse(ex.getMessage());
        }
        return ResponseUtils.sendCorrectResponse("Koszyk został opróżniony");
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/purchaseAll")
    public ResponseEntity<Map<String, Object>> purchaseAllProductsFromBasket(
            @RequestHeader(value = "Auth", defaultValue = "empty") String userToken) {
        try {
            basketProductService.purchaseAllProductsFromBasket(userToken);
        } catch (Exception ex) {
            return ResponseUtils.errorResponse(ex.getMessage());
        }
        return ResponseUtils.sendCorrectResponse("Produkty zostały zakupione");
    }
}
