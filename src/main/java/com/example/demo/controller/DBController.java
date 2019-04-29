package com.example.demo.controller;

import com.example.demo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
public class DBController
{
    @Autowired
    JDBCUserDAOImpl jdbcUserDAO;
    @Autowired
    JDBCLevelDAO jdbcLevelDAO;

    //USERS

    @PostMapping("/users")
    public Boolean createUser(@RequestBody User user, HttpServletRequest request)
    {
        String s = "POST createUser called with User " + user;
        logConnectionInfo(s, request);
        return jdbcUserDAO.insert(user);
    }

    @GetMapping("/users")
    public List<User> readUsers(HttpServletRequest request)
    {
        String s = "GET readUsers called";
        logConnectionInfo(s, request);
        return jdbcUserDAO.findAll();
    }

    @GetMapping("/users/{startId}/{endId}")
    public List<User> readUsers(@PathVariable long startId, @PathVariable long endId, HttpServletRequest request)
    {
        String s = "GET readUsers called on User ID range of " + startId + " to " + endId;
        logConnectionInfo(s, request);
        return jdbcUserDAO.findRange(startId, endId);
    }

    @GetMapping("/users/{id}")
    public User readUser(@PathVariable long id, HttpServletRequest request) throws Exception
    {
        String s = "GET readUser called on User ID " + id;
        logConnectionInfo(s, request);
        return jdbcUserDAO.findById(id);
    }

    @PutMapping("/users/{id}")
    public Boolean updateUser(@RequestBody User user, @PathVariable long id, HttpServletRequest request)
    {
        String s = "PUT updateUser called on User ID " + id;
        logConnectionInfo(s, request);
        return jdbcUserDAO.updateEntry(user, id);
    }

    @DeleteMapping("/users/{id}")
    public Boolean deleteUser(@PathVariable long id, HttpServletRequest request)
    {
        String s = "DELETE deleteUser called on User ID " + id;
        logConnectionInfo(s, request);
        return jdbcUserDAO.deleteById(id);
    }

    //LEVELS

    @PostMapping("/levels")
    public Boolean createLevel(@RequestBody Level level, HttpServletRequest request)
    {
        String s = "POST createLevel called with Level " + level;
        logConnectionInfo(s, request);
        return jdbcLevelDAO.insert(level);
    }

    @GetMapping("/levels")
    public List<Level> readLevels(HttpServletRequest request)
    {
        String s = "GET readLevels called";
        logConnectionInfo(s, request);
        return jdbcLevelDAO.findAll();
    }

    @GetMapping("/levels/{startId}/{endId}")
    public List<Level> readLevels(@PathVariable long startId, @PathVariable long endId, HttpServletRequest request)
    {
        String s = "GET readLevels called on Level ID range of " + startId + " to " + endId;
        logConnectionInfo(s, request);
        return jdbcLevelDAO.findRange(startId, endId);
    }

    @GetMapping("/levels/{id}")
    public Level readLevel(@PathVariable long id, HttpServletRequest request) throws Exception
    {
        String s = "GET readLevel called on Level ID " + id;
        logConnectionInfo(s, request);
        return jdbcLevelDAO.findById(id);
    }

    @PutMapping("/levels/{id}")
    public Boolean updateLevel(@RequestBody Level level, @PathVariable long id, HttpServletRequest request)
    {
        String s = "PUT updateLevel called on Level ID " + id;
        logConnectionInfo(s, request);
        return jdbcLevelDAO.updateEntry(level, id);
    }

    @DeleteMapping("/levels/{id}")
    public Boolean deleteLevel(@PathVariable long id, HttpServletRequest request)
    {
        String s = "DELETE deleteLevel called on Level ID " + id;
        logConnectionInfo(s, request);
        return jdbcLevelDAO.deleteById(id);
    }

    public void logConnectionInfo(String logMessage, HttpServletRequest request)
    {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        log.info(logMessage + " by " + ServletUriComponentsBuilder.fromCurrentRequest().toUriString() + " from " + ipAddress);
    }
}
