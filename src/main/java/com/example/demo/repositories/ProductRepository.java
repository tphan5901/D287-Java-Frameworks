package com.example.demo.repositories;
import com.example.demo.domain.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface ProductRepository extends CrudRepository<Product,Long> {
    @Query("SELECT item FROM Product item WHERE item.name LIKE %:keyword%")
    List<Product> search(String keyword);
}
