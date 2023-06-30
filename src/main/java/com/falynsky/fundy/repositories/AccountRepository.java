package com.falynsky.fundy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.falynsky.fundy.models.Account;
import com.falynsky.fundy.models.User;
import com.falynsky.fundy.models.DTO.AccountDTO;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findFirstByOrderByIdDesc();

    List<Account> findAllByUserId(User user);

    @Query("SELECT new com.falynsky.fundy.models.DTO.AccountDTO(u.id, u.name, u.userId.id) FROM Account u")
    List<AccountDTO> retrieveAccountAsDTO();

    @Query("SELECT new com.falynsky.fundy.models.DTO.AccountDTO(u.id, u.name, u.userId.id) FROM Account u where u.id = :accountId")
    AccountDTO retrieveAccountAsDTObyId(@Param("accountId") Integer accountId);
}
