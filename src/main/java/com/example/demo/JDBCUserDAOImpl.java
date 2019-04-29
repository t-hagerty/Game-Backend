package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
@ComponentScan("com.example.demo")
public class JDBCUserDAOImpl implements JDBCUserDAO
{
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Bean
    public JDBCUserDAOImpl jdbcUserDAO()
    {
        return new JDBCUserDAOImpl();
    }

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public Boolean insert(User user)
    {
        try
        {
            String sql = "INSERT INTO USERS " +
                    "(ID, USERNAME, PASSWORD, EMAIL, DATE_CREATED) VALUES (?, ?, ?, ?, ?)";

            jdbcTemplate = new JdbcTemplate(dataSource);

            jdbcTemplate.update(sql, new Object[]{user.getId(),
                    user.getUserName(), user.getPassword(), user.getEmail(), user.getDateCreated()});
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public Boolean insertBatch1(final List<User> users)
    {
        try
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
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public Boolean insertBatch2(final String sql)
    {
        try
        {
            jdbcTemplate.batchUpdate(sql);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
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

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        return mapResultListToUsers(rows);
    }

    @SuppressWarnings("rawtypes")
    public List<User> findRange(long startId, long endId)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM USERS WHERE ID BETWEEN ? AND ?";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, startId, endId);

        return mapResultListToUsers(rows);
    }

    private List<User> mapResultListToUsers(List<Map<String, Object>> rows)
    {
        List<User> users = new ArrayList<User>();
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

    public Boolean updateEntry(User user, long id)
    {
        String sql = "UPDATE USERS SET USERNAME = ?, PASSWORD = ?, EMAIL = ?, DATE_CREATED = ? WHERE ID = ?";

        jdbcTemplate = new JdbcTemplate(dataSource);

        try
        {
            jdbcTemplate.update(sql, new Object[]{user.getUserName(),
                    user.getPassword(), user.getEmail(), user.getDateCreated(), id});
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public Boolean deleteById(long id)
    {
        try
        {
            jdbcTemplate = new JdbcTemplate(dataSource);
            String sql = "DELETE * FROM LEVELS WHERE AUTHOR_ID = ?";
            jdbcTemplate.update(sql, id);
            sql = "DELETE * FROM USERS WHERE ID = ?";
            jdbcTemplate.update(sql, id);

            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
}