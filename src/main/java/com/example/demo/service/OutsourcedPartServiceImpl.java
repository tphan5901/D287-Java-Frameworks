package com.example.demo.service;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.repositories.OutsourcedPartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OutsourcedPartServiceImpl implements OutsourcedPartService{
    private final OutsourcedPartRepository partRepository;

    @Autowired
    public OutsourcedPartServiceImpl(OutsourcedPartRepository partRepository) {
        this.partRepository = partRepository;
    }

    @Override
    public List<OutsourcedPart> findAll() {
        return (List<OutsourcedPart>) partRepository.findAll();
    }

    @Override
    public OutsourcedPart findById(int Id) {
        Optional<OutsourcedPart> result = partRepository.findById((long) Id);
        OutsourcedPart part = null;
        if (result.isPresent()) {
            part = result.get();
        }
        return part;
    }

    @Override
    public void save(OutsourcedPart part) {
        partRepository.save(part);
    }

    public Long getNextId() {
        Long maxId = partRepository.findMaxId();
        return maxId + 1;
    }

    @Override
    public void deleteById(int Id) {
        partRepository.deleteById((long) Id);
    }

}
