package com.example.demo.service;

import com.example.demo.domain.InhousePart;
import java.util.List;

public interface InhousePartService {
    public List<InhousePart> findAll();
    public InhousePart findById(int Id);
    public void save (InhousePart part);
    public void deleteById(int Id);
    public Long getNextId();
}
