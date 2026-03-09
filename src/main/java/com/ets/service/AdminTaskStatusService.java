package com.ets.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ets.model.AdminTaskStatus;
import com.ets.repository.AdminTaskStatusRepository;

@Service
public class AdminTaskStatusService {

    @Autowired
    private AdminTaskStatusRepository repository;

    public List<AdminTaskStatus> getAllTasks() {
        return repository.findAll();
    }

    public void saveTask(AdminTaskStatus task) {
        repository.save(task);
    }

    public void deleteTask(Long id) {
        repository.deleteById(id);
    }
}