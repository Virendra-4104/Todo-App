package com.todoapp.todoAppBackend.service;

import com.todoapp.todoAppBackend.entity.User;
import com.todoapp.todoAppBackend.repository.TaskRepository;
import com.todoapp.todoAppBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //    create account
    public void createAccount(User registerRequest) {
        String username = registerRequest.getUsername() != null ? registerRequest.getUsername().trim() : "";
        String password = registerRequest.getPassword() != null ? registerRequest.getPassword().trim() : "";
        if (username.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Username or Password can't be empty");
        }
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        registerRequest.setUsername(username);
        registerRequest.setPassword(passwordEncoder.encode(password));
        userRepository.save(registerRequest);
    }

    //    login
    public User loginAccount(User loginRequest) {
        if (loginRequest.getUsername() == null || loginRequest.getUsername().trim().isEmpty() ||
                loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Username or Password can't be empty");
        }

        User user =userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Password is wrong");
        }
        return user;
    }

    //    update user
    public User updateAccount(String username, User updateRequest) {
        User oldUser = userRepository.findByUsername(username.trim())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        // Update username only if provided
        if (updateRequest.getUsername() != null && !updateRequest.getUsername().trim().isEmpty()) {
            oldUser.setUsername(updateRequest.getUsername().trim());
        }

        // Update password only if provided
        if (updateRequest.getPassword() != null && !updateRequest.getPassword().trim().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(updateRequest.getPassword().trim());
            oldUser.setPassword(encodedPassword);
        }
        userRepository.save(oldUser);
        return oldUser;
    }

//    delete account and all there task
    public void deleteAccount(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.getTasks().forEach(task -> taskRepository.deleteById(task.getId()));
        userRepository.delete(user);
    }
}
