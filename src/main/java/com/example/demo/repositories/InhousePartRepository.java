package com.example.demo.repositories;

import com.example.demo.domain.InhousePart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface InhousePartRepository extends CrudRepository<InhousePart,Long> {

    @Query("SELECT COALESCE(MAX(p.id), 0) FROM InhousePart p")
    Long findMaxId();
}
