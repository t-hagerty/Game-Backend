package com.example.demo;

import java.sql.Date;

public class User
{
    long id;
    String userName;
    String password; //TODO: obviously this is not secure, and will be made so later.
    String email;
    Date dateCreated;

    public User(long id, String userName, String password, String email, Date dateCreated)
    {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Date getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }
}