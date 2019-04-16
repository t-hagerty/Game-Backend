package com.example.demo;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCUserDAOImpl implements JDBCUserDAO
{
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public void insert(User user)
    {

        String sql = "INSERT INTO USERS " +
                "(ID, USERNAME, PASSWORD, EMAIL, DATE_CREATED) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql, new Object[]{user.getId(),
                user.getUserName(), user.getPassword(), user.getEmail(), user.getDateCreated()});
    }

    @SuppressWarnings({"unchecked"})
    public User findById(long id)
    {
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
            User user = new User(Long.parseLong(String.valueOf(row.get("ID"))),
                    (String) row.get("USERNAME"),
                    (String) row.get("PASSWORD"),
                    (String) row.get("EMAIL"),
                    (Date) row.get("DATE_CREATED"));
            users.add(user);
        }

        return users;
    }

    public String findUserNameById(long id)
    {
        String sql = "SELECT USERNAME FROM USERS WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, String.class);
    }

    public String findEmailById(long id)
    {
        String sql = "SELECT EMAIL FROM USERS WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, String.class);
    }

    public Date findDateCreatedById(long id)
    {
        String sql = "SELECT DATE_CREATED FROM USERS WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Date.class);
    }

    public void insertBatch1(final List<User> users)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "INSERT INTO USERS " +
                "(ID, USERNAME, PASSWORD, EMAIL, DATE_CREATED) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter()
        {
            public void setValues(PreparedStatement ps, int i) throws SQLException
            {
                User user = users.get(i);
                ps.setLong(1, user.getId());
                ps.setString(2, user.getUserName());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getEmail());
                ps.setDate(5, user.getDateCreated() );
            }

            public int getBatchSize()
            {
                return users.size();
            }
        });
    }

    public void insertBatch2(final String sql)
    {
        jdbcTemplate.batchUpdate(sql);
    }
}