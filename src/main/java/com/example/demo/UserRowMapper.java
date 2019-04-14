package com.example.demo;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User>
{
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        User user = new User(rs.getLong("ID"),
                rs.getString("USERNAME"),
                rs.getString("PASSWORD"),
                rs.getString("EMAIL"),
                rs.getDate("DATE_CREATED"));
        return user;
    }
}
