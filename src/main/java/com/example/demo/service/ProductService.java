package com.example.demo.service;
import com.example.demo.domain.Product;
import java.util.List;


public interface ProductService {
    public List<Product> getAll();
    public Product findById(long Id);
    public void save (Product product);
    public void deleteById(int Id);
    public List<Product> searchProduct(String keyword);

}
