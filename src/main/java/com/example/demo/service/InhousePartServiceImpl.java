package com.example.demo.service;

import com.example.demo.domain.InhousePart;
import com.example.demo.repositories.InhousePartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class InhousePartServiceImpl implements InhousePartService {
    private final InhousePartRepository partRepository;

    @Autowired
    public InhousePartServiceImpl(InhousePartRepository partRepository) {   this.partRepository = partRepository; }

    @Override
    public List<InhousePart> findAll() { return (List<InhousePart>) partRepository.findAll(); }

    @Override
    public InhousePart findById(int Id) {
        Optional<InhousePart> result = partRepository.findById((long) Id);
        InhousePart part = null;

        if (result.isPresent()) {
            part = result.get();
        }
        return part;
    }

    @Override
    public Long getNextId() {
        Long maxId = partRepository.findMaxId();
        return maxId + 1;
    }

    @Override
    public void save(InhousePart part) {
        partRepository.save(part);
    }

    @Override
    public void deleteById(int partId) {
        partRepository.deleteById((long) partId);
    }

}
