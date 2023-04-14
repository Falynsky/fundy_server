package com.falynsky.fundy.services;

import com.falynsky.fundy.models.DTO.BarsCodesDTO;
import com.falynsky.fundy.models.DTO.ProductDTO;
import com.falynsky.fundy.repositories.BarsCodesRepository;
import com.falynsky.fundy.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final BarsCodesRepository barsCodesRepository;

    public ProductService(
            ProductRepository productRepository,
            BarsCodesRepository barsCodesRepository) {
        this.productRepository = productRepository;
        this.barsCodesRepository = barsCodesRepository;
    }

    public ProductDTO getProductDTOByBarsCode(Integer barsCodeCode) {
        Optional<BarsCodesDTO> basket = barsCodesRepository.retrieveProductAsDTObyCode(barsCodeCode);
        if (basket.isPresent()) {
            final int productId = basket.get().productId;
            Optional<ProductDTO> productDTO = productRepository.retrieveProductAsDTObyId(productId);
            return productDTO.orElse(null);
        }
        return null;
    }

}
