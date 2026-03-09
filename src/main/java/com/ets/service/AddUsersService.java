package com.ets.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ets.model.AddUsers;
import com.ets.repository.AddUsersRepository;

@Service
public class AddUsersService {

    private final AddUsersRepository addUsersRepository;

    public AddUsersService(AddUsersRepository addUsersRepository) {
        this.addUsersRepository = addUsersRepository;
    }

    public AddUsers saveUser(AddUsers addUsers) {
        if (addUsersRepository.existsByEmailAddress(addUsers.getEmailAddress())) {
            throw new RuntimeException("Email already exists: " + addUsers.getEmailAddress());
        }
        return addUsersRepository.save(addUsers);
    }

    public List<AddUsers> getAllUsers() {
        return addUsersRepository.findAll();
    }

    public Optional<AddUsers> getUserById(Long id) {
        return addUsersRepository.findById(id);
    }

    public AddUsers updateUser(Long id, AddUsers addUsers) {
        AddUsers existingUser = addUsersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        Optional<AddUsers> userWithSameEmail =
                addUsersRepository.findByEmailAddress(addUsers.getEmailAddress());

        if (userWithSameEmail.isPresent() && !userWithSameEmail.get().getId().equals(id)) {
            throw new RuntimeException("Email already exists: " + addUsers.getEmailAddress());
        }

        existingUser.setNameUsername(addUsers.getNameUsername());
        existingUser.setEmailAddress(addUsers.getEmailAddress());
        existingUser.setAccessPassword(addUsers.getAccessPassword());
        existingUser.setPhone(addUsers.getPhone());
        existingUser.setDept(addUsers.getDept());

        return addUsersRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        AddUsers existingUser = addUsersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        addUsersRepository.delete(existingUser);
    }
}