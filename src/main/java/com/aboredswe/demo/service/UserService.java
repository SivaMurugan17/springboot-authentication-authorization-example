package com.aboredswe.demo.service;

import com.aboredswe.demo.model.User;
import com.aboredswe.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User[] findAllUsers(){
        Iterator<User> iterator = userRepository.findAll().iterator();
        List<User> list = new ArrayList<>();
        while(iterator.hasNext()){
            list.add(iterator.next());
        }
        User[] array = new User[list.size()];
        for(int i=0;i<array.length;i++) array[i] = list.get(i);
        return array;
    }

    public boolean addUser(User user){
        User savedUser = userRepository.save(user);
        return savedUser != null;
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public User findUserById(long id){
        return userRepository.findById(id).orElse(null);
    }
}
