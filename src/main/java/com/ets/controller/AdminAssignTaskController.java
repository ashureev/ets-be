package com.ets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ets.model.AdminAssignTask;
import com.ets.service.AdminAssignTaskService;

@RestController
@RequestMapping("/api/admin/assign/tasks")
public class AdminAssignTaskController {

    @Autowired
    private AdminAssignTaskService service;

    @PostMapping("/create")
    public AdminAssignTask createTask(@RequestBody AdminAssignTask task){

        return service.createTask(task);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id){

        service.deleteTask(id);
        return "Task Deleted Successfully";
    }

}














