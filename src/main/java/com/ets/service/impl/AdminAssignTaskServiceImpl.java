package com.ets.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ets.model.AdminAssignTask;
import com.ets.repository.AdminAssignTaskRepository;
import com.ets.service.AdminAssignTaskService;

@Service
public class AdminAssignTaskServiceImpl implements AdminAssignTaskService {

    @Autowired
    private AdminAssignTaskRepository repository;

    @Override
    public AdminAssignTask createTask(AdminAssignTask task) {

        return repository.save(task);
    }

    @Override
    public void deleteTask(Long id) {

        repository.deleteById(id);
    }

}