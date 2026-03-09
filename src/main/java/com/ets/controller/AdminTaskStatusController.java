package com.ets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ets.model.AdminTaskStatus;
import com.ets.service.AdminTaskStatusService;

@RestController
@RequestMapping("/admin/tasks")
public class AdminTaskStatusController {

    @Autowired
    private AdminTaskStatusService service;

    @GetMapping
    public List<AdminTaskStatus> getAllTasks() {
        return service.getAllTasks();
    }

    @PostMapping("/add")
    public AdminTaskStatus addTask(@RequestBody AdminTaskStatus task) {
        service.saveTask(task);
        return task;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        service.deleteTask(id);
        return "Task deleted successfully";
    }
}