package com.aboredswe.demo.controller;


import com.aboredswe.demo.model.User;
import com.aboredswe.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public User[] findAllUsers(){
        return userService.findAllUsers();
    }

    @PostMapping
    public boolean addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @GetMapping("/id/{id}")
    public User findUserById(@PathVariable long id){
        return userService.findUserById(id);
    }

    @GetMapping("/email/{email}")
    public User findUserByEmail(@PathVariable String email){
        return userService.findUserByEmail(email);
    }
}
