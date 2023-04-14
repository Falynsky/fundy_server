package com.falynsky.fundy.services;

import com.falynsky.fundy.models.DTO.ProductDTO;
import com.falynsky.fundy.models.DTO.SalesDTO;
import com.falynsky.fundy.repositories.SalesRepository;
import org.springframework.stereotype.Service;

@Service
public class SalesService {

    private final SalesRepository salesRepository;

    public SalesService(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    public Double getProductPriceAfterDiscount(double productPrice, ProductDTO product) {
        SalesDTO salesDTO = salesRepository.retrieveSaleAsDTOByProductId(product.id);
        if (salesDTO != null) {
            double discount = salesDTO.discount;
            return productPrice * (1 - discount);
        }
        return null;
    }
}
