package com.falynsky.fundy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.falynsky.fundy.models.User;
import com.falynsky.fundy.models.DTO.UserDTO;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findFirstByOrderByIdDesc();

    @Query("SELECT new com.falynsky.fundy.models.DTO.UserDTO(a.id, a.username, a.password, a.mail, a.role) FROM User a")
    List<UserDTO> retrieveUserAsDTO();

    @Query("SELECT new com.falynsky.fundy.models.DTO.UserDTO(a.id, a.username, a.password, a.mail, a.role) FROM User a where a.id = :userId")
    UserDTO retrieveUserAsDTObyId(@Param("userId") Integer userId);
}
