package com.ets.service;

import com.ets.model.AdminAssignTask;

public interface AdminAssignTaskService {

    AdminAssignTask createTask(AdminAssignTask task);

    void deleteTask(Long id);

}