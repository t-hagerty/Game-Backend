package com.example.demo;

import java.sql.Date;
import java.util.List;

public interface JDBCUserDAO
{
    public Boolean insert(User user);
    public Boolean insertBatch1(final List<User> users);
    public Boolean insertBatch2(final String sql);
    public User findById(long id);
    public List<User> findAll();
    public List<User> findRange(long startId, long endId);
    public String findUserNameById(long id);
    public String findEmailById(long id);
    public Date findDateCreatedById(long id);
    public Boolean updateEntry(User user, long id);
    public Boolean deleteById(long id);
}
