package com.example.demo;

public interface UserDAO
{
    public void insert(User user);
    public User findById(int id);
}