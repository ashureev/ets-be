package com.ets.service;

import java.util.List;

import com.ets.model.AdminAssignTask;

public interface AdminAssignTaskService {

    AdminAssignTask createTask(AdminAssignTask task);

    void deleteTask(Long id);

    List<AdminAssignTask> getAllTasks();

}