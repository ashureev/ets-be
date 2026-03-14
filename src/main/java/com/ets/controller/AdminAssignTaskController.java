package com.ets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ets.model.AdminAssignTask;
import com.ets.service.AdminAssignTaskService;

@RestController
@RequestMapping("/api/admin/assign/tasks")
@CrossOrigin(origins = "*")
public class AdminAssignTaskController {

    @Autowired
    private AdminAssignTaskService service;

    @PostMapping("/create")
    public ResponseEntity<AdminAssignTask> createTask(@RequestBody AdminAssignTask task){
        AdminAssignTask savedTask = service.createTask(task);
        return ResponseEntity.ok(savedTask);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id){
        service.deleteTask(id);
        return ResponseEntity.ok("Task Deleted Successfully");
    }

}














