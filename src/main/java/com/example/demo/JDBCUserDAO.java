package com.example.demo;

import java.sql.Date;
import java.util.List;

public interface JDBCUserDAO
{
    public void insert(User user);
    public User findById(long id);
    public List<User> findAll();
    public String findUserNameById(long id);
    public String findEmailById(long id);
    public Date findDateCreatedById(long id);
    public void insertBatch1(final List<User> users);
    public void insertBatch2(final String sql);
}
