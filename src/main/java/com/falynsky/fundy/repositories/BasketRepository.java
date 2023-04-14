package com.falynsky.fundy.repositories;

import com.falynsky.fundy.models.Basket;
import com.falynsky.fundy.models.DTO.BasketDTO;
import com.falynsky.fundy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Integer> {

    Basket findByUserId(User user);

    Basket findFirstByOrderByIdDesc();

    @Query("SELECT new com.falynsky.fundy.models.DTO.BasketDTO(b.id, b.name, b.userId.id) FROM Basket b")
    List<BasketDTO> retrieveBasketsAsDTO();

    @Query("SELECT new com.falynsky.fundy.models.DTO.BasketDTO(b.id, b.name, b.userId.id) FROM Basket b where b.id = :basketId")
    BasketDTO retrieveBasketAsDTObyId(@Param("basketId") Integer basketId);


    @Query("SELECT new com.falynsky.fundy.models.DTO.BasketDTO(b.id, b.name, b.userId.id) FROM Basket b where b.userId.id = :userId")
    BasketDTO retrieveBasketAsDTObyUserId(@Param("userId") Integer userId);
}
