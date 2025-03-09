package com.example.demo.service;
import com.example.demo.domain.Part;
import com.example.demo.repositories.PartRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PartServiceImpl implements PartService{
        private final PartRepository partRepository;
        private Part part;

    public PartServiceImpl(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    @Override
    public List<Part> getAll() {
        List<Part> parts = (List<Part>) partRepository.findAll();
        System.out.println("All Parts: " + parts);
        return parts;
    }

    public List<Part> searchPart(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            List<Part> searchResults = partRepository.search(keyword);
            System.out.println("Found Parts: " + searchResults); // Log results
            return searchResults;
        }
        return getAll(); // Return all parts if no keyword is provided
    }

    @Override
    public Part findById(int Id) {
        Optional<Part> result = partRepository.findById((long) Id);
        result.ifPresent(value -> part = value);
        return part;
    }

    @Override
    public void save(Part part) { partRepository.save(part); }

    @Override
    public void deleteById(int partId) {
        partRepository.deleteById((long) partId);
    }

}
