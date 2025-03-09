package com.example.demo.service;
import com.example.demo.domain.Part;
import java.util.List;

public interface PartService {
    public List<Part> getAll();
    public Part findById(int Id);
    public void save (Part part);
    public void deleteById(int Id);
    public List<Part> searchPart(String keyword);
}
