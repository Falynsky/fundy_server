package com.falynsky.fundy.repositories;

import com.falynsky.fundy.models.DTO.SalesDTO;
import com.falynsky.fundy.models.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Integer> {

    Sales findFirstByOrderByIdDesc();

    @Query("SELECT new com.falynsky.fundy.models.DTO.SalesDTO(s.id, s.productId.name, s.description, s.discount, s.productId.price, s.productId.id, s.productId.documentId.docName, s.productId.documentId.docType) FROM Sales s")
    List<SalesDTO> retrieveSalesAsDTO();

    @Query("SELECT new com.falynsky.fundy.models.DTO.SalesDTO(s.id, s.productId.name, s.description, s.discount, s.productId.price, s.productId.id, s.productId.documentId.docName, s.productId.documentId.docType) FROM Sales s WHERE s.productId.id = :productId")
    SalesDTO retrieveSaleAsDTOByProductId(@Param("productId") Integer productId);
}
