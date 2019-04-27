package com.example.demo.controller;

import com.example.demo.JDBCLevelDAO;
import com.example.demo.JDBCUserDAO;
import com.example.demo.JDBCUserDAOImpl;
import com.example.demo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DBController
{
    @Autowired
    JDBCUserDAOImpl jdbcUserDAO;
    JDBCLevelDAO jdbcLevelDAO;

    public DBController()
    {
        System.out.println("test");
    }

    @PostMapping("/users")
    public Boolean createUser(@RequestBody User user)
    {
        System.out.println("POST called");
        return jdbcUserDAO.insert(user);
    }

    @GetMapping(value = "/users")
    public List<User> readUsers()
    {
        System.out.println("GET called");
        return jdbcUserDAO.findAll();
    }

    @GetMapping("/users/{startId}/{endId}")
    public List<User> readUsers(@PathVariable long startId, @PathVariable long endId)
    {
        System.out.println("GET called");
        return jdbcUserDAO.findRange(startId, endId);
    }

    @GetMapping(value = "/users/{id}")
    public User readUser(@PathVariable long id) throws Exception
    {
        System.out.println("GET called");
        return jdbcUserDAO.findById(id);
    }

    @PutMapping("/users/{id}")
    public Boolean updateUser(@RequestBody User user, @PathVariable long id)
    {
        System.out.println("PUT called");
       return jdbcUserDAO.updateEntry(user, id);
    }

    @DeleteMapping("/users/{id}")
    public Boolean deleteUser(@PathVariable long id)
    {
        System.out.println("DELETE called");
        return jdbcUserDAO.deleteById(id);
    }

}
