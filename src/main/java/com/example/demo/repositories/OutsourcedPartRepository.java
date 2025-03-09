package com.example.demo.repositories;

import com.example.demo.domain.OutsourcedPart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface OutsourcedPartRepository extends CrudRepository<OutsourcedPart,Long> {
    @Query("SELECT COALESCE(MAX(p.id), 0) FROM InhousePart p")
    Long findMaxId();
}
