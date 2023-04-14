package com.falynsky.fundy.repositories;

import com.falynsky.fundy.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    Document findFirstByOrderByIdDesc();

    Document findByDocName(@Param("fileName") String fileName);
}
