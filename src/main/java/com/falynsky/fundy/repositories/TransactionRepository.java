package com.falynsky.fundy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.falynsky.fundy.models.Transaction;
import com.falynsky.fundy.models.DTO.TransactionDTO;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findFirstByOrderByIdDesc();

    @Query("SELECT new com.falynsky.fundy.models.DTO.TransactionDTO(b.id, b.name, b.amount, b.transactionInfo, b.documentId.id) FROM Transaction b")
    List<TransactionDTO> retrieveTransactionsAsDTO();

    @Query("SELECT new com.falynsky.fundy.models.DTO.TransactionDTO(b.id, b.name, b.amount, b.transactionInfo, b.documentId.id) FROM Transaction b where b.id = :transactionId")
    TransactionDTO retrieveTransactionAsDTObyId(@Param("transactionId") Integer transactionId);
}
