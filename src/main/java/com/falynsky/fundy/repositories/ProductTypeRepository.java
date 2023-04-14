package com.falynsky.fundy.repositories;

import com.falynsky.fundy.models.DTO.ProductTypeDTO;
import com.falynsky.fundy.models.Product;
import com.falynsky.fundy.models.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTypeRepository extends JpaRepository<Product, Integer> {


    ProductType findFirstByOrderByIdDesc();

    @Query("SELECT new com.falynsky.fundy.models.DTO.ProductTypeDTO(pt.id, pt.name) FROM ProductType pt")
    List<ProductTypeDTO> retrieveProductTypesAsDTO();

    @Query("SELECT new com.falynsky.fundy.models.DTO.ProductTypeDTO(pt.id, pt.name) FROM ProductType pt where pt.id = :productTypeId")
    ProductTypeDTO retrieveProductTypeAsDTObyId(@Param("productTypeId") Integer productTypeId);
}
