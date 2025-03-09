package com.example.demo.service;

import com.example.demo.domain.OutsourcedPart;

import java.util.List;


public interface OutsourcedPartService {
        public List<OutsourcedPart> findAll();
        public OutsourcedPart findById(int Id);
        public void save (OutsourcedPart part);
        public void deleteById(int Id);
        public Long getNextId();
}
