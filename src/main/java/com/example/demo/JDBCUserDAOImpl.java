package com.example.demo;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class JDBCUserDAOImpl implements JDBCUserDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(User user) {

        String sql = "INSERT INTO USERS " +
                "(ID, USERNAME, PASSWORD, EMAIL, DATE_CREATED) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql, new Object[]{user.getId(),
                user.getUserName(), user.getPassword(), user.getEmail(), user.getDateCreated()});
    }

    @SuppressWarnings({"unchecked"})
    public User findById(int id) {
        String sql = "SELECT * FROM USERS WHERE ID = ?";

        jdbcTemplate = new JdbcTemplate(dataSource);
        User user = (User) jdbcTemplate.queryForObject(
                sql, new Object[]{id}, new UserRowMapper());

        return user;
    }

    @SuppressWarnings("rawtypes")
    public List<User> findAll()
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM USERS";

        List<User> users = new ArrayList<User>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows)
        {
            User user = new User(Integer.parseInt(String.valueOf(row.get("ID"))),
                    (String)row.get("USERNAME"),
                    (String)row.get("PASSWORD"),
                    (String)row.get("EMAIL"),
                    (Date)row.get("DATE_CREATED"));
            users.add(user);
        }

        return users;
    }

    public String findUserNameById(int id)
    {
        String sql = "SELECT USERNAME FROM USERS WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {id}, String.class);
    }

    public String findEmailById(int id)
    {
        String sql = "SELECT EMAIL FROM USERS WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {id}, String.class);
    }

    public Date findDateCreatedById(int id)
    {
        String sql = "SELECT DATE_CREATED FROM USERS WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {id}, Date.class);
    }
}