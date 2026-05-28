package com.example.notifications.Controllers;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.example.notifications.Services.UserService;
import com.example.notifications.Entities.User;

@RestController
@RequestMapping("/api/users")

public class UserController {
@Autowired
    private  UserService userService;

//    @PostMapping
//    public User createUser(@RequestBody User user) {
//        return userService.createUser(user);
//    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }
    @PutMapping("/email/{email}")
    public User updateUserByEmail(@Valid @PathVariable String email, @RequestBody User user) {
        return userService.updateUserByEmail(email, user);
    }

//    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//    }
    @DeleteMapping("/email/{email}")
    public void deleteUserByEmail(@PathVariable String email) {
        userService.deleteUserByEmail(email);
    }
}
