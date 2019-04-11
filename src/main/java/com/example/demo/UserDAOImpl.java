package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class UserDAOImpl implements UserDAO
{
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public void insert(User user)
    {

        String sql = "INSERT INTO USERS " +
                "(ID, USERNAME, PASSWORD, EMAIL, DATE_CREATED) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;

        try
        {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, user.getId());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setDate(5, user.getDateCreated());

            ps.executeUpdate();
            ps.close();

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);

        }
        finally
        {
            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (SQLException e) {}
            }
        }
    }

    public User findById(int id)
    {

        String sql = "SELECT * FROM USERS WHERE ID = ?";

        Connection conn = null;

        try
        {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            User user = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                user = new User(
                        rs.getLong("ID"),
                        rs.getString("USERNAME"),
                        rs.getString("PASSWORD"),
                        rs.getString("EMAIL"),
                        rs.getDate("DATE_CREATED")
                );
            }
            rs.close();
            ps.close();
            return user;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (SQLException e) {}
            }
        }
    }
}