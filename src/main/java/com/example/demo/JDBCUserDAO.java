package com.example.demo;

import java.sql.Date;
import java.util.List;

public interface JDBCUserDAO
{
    public void insert(User user);
    public User findById(int id);
    public List<User> findAll();
    public String findUserNameById(int id);
    public String findEmailById(int id);
    public Date findDateCreatedById(int id);
}
