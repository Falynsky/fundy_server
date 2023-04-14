package com.falynsky.fundy.controllers;

import com.falynsky.fundy.models.DTO.ProductDTO;
import com.falynsky.fundy.repositories.ProductRepository;
import com.falynsky.fundy.utils.ResponseMapUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    final ProductRepository productRepository;
    final JdbcTemplate jdbcTemplate;

    public ProductController(ProductRepository productRepository, JdbcTemplate jdbcTemplate) {
        this.productRepository = productRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/typeId")
    public ResponseEntity<Map<String, Object>> getAllProductsByTypeId(@RequestBody Map<String, Object> map) {
        Integer id = (Integer) map.get("id");
        List<ProductDTO> productsList = productRepository.retrieveProductAsDTObyTypeId(id);
        Map<String, Object> body = ResponseMapUtils.buildResponse(productsList);
        return ResponseEntity.ok(body);
    }


}
