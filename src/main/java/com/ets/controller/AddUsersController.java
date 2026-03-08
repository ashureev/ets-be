package com.ets.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ets.model.AddUsers;
import com.ets.service.AddUsersService;

@RestController
@RequestMapping("/api/users")
public class AddUsersController {

    private final AddUsersService addUsersService;

    public AddUsersController(AddUsersService addUsersService) {
        this.addUsersService = addUsersService;
    }

    @PostMapping
    public ResponseEntity<AddUsers> createUser(@RequestBody AddUsers addUsers) {
        return ResponseEntity.ok(addUsersService.saveUser(addUsers));
    }

    @GetMapping
    public ResponseEntity<List<AddUsers>> getAllUsers() {
        return ResponseEntity.ok(addUsersService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddUsers> getUserById(@PathVariable Long id) {
        return addUsersService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddUsers> updateUser(@PathVariable Long id, @RequestBody AddUsers addUsers) {
        return ResponseEntity.ok(addUsersService.updateUser(id, addUsers));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        addUsersService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}