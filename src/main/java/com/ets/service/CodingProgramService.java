package com.ets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ets.model.CodingProgram;
import com.ets.repository.CodingProgramRepository;

import java.util.List;

@Service
public class CodingProgramService {

    @Autowired
    private CodingProgramRepository repository;
    
    public CodingProgramService(CodingProgramRepository repository) {
    	this.repository= repository;
    }

    public CodingProgram create(CodingProgram coding) {
        return repository.save(coding);
    }

    public List<CodingProgram> getAll() {
        return repository.findAll();
    }

    public CodingProgram getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coding Program not found with id" +id));
    }

    public CodingProgram update(Long id, CodingProgram updated) {
        CodingProgram coding = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coding Program not found"));

        coding.setTitle(updated.getTitle());
        coding.setDescription(updated.getDescription());

        return repository.save(coding);
    }

    public void delete(Long id) {
    	CodingProgram coding = getById(id);
        repository.delete(coding);
    }
}
