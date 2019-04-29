package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan("com.example.demo")
public class JDBCLevelDAOImpl implements JDBCLevelDAO
{
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Bean
    public JDBCLevelDAOImpl jdbcLevelDAO()
    {
        return new JDBCLevelDAOImpl();
    }

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public Boolean insert(Level level)
    {
        try
        {
            String sql = "INSERT INTO LEVELS " +
                    "(ID, AUTHOR_ID, NAME, DATE_SUBMITTED, RATING, PERCENT_WON, LEVEL_MAP) VALUES (?, ?, ?, ?, ?, ?, ?)";

            jdbcTemplate = new JdbcTemplate(dataSource);

            jdbcTemplate.update(sql, new Object[]{level.getId(), level.getAuthorId(),
                    level.getName(), level.getDateSubmitted(), level.getRating(), level.getPercentWon(), level.getLevelMap()});
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public Boolean insertBatch1(final List<Level> levels)
    {
        try
        {
            jdbcTemplate = new JdbcTemplate(dataSource);
            String sql = "INSERT INTO LEVELS " +
                    "(ID, AUTHOR_ID, NAME, DATE_SUBMITTED, RATING, PERCENT_WON, LEVEL_MAP) VALUES (?, ?, ?, ?, ?, ?, ?)";

            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Level level = levels.get(i);
                    ps.setLong(1, level.getId());
                    ps.setLong(2, level.getAuthorId());
                    ps.setString(3, level.getName());
                    ps.setDate(4, level.getDateSubmitted());
                    ps.setFloat(5, level.getRating());
                    ps.setFloat(6, level.getPercentWon());
                    ps.setBlob(7, level.getLevelMap());
                }

                public int getBatchSize() {
                    return levels.size();
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
    public Level findById(long id)
    {
        String sql = "SELECT * FROM LEVELS WHERE ID = ?";

        jdbcTemplate = new JdbcTemplate(dataSource);
        Level level = (Level) jdbcTemplate.queryForObject(
                sql, new Object[]{id}, new LevelRowMapper());

        return level;
    }

    @SuppressWarnings("rawtypes")
    public List<Level> findAll()
    {
        try
        {
            jdbcTemplate = new JdbcTemplate(dataSource);
            String sql = "SELECT * FROM LEVELS";

            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

            return mapResultListToLevels(rows);
        }
        catch(SQLException e)
        {
            return new ArrayList<Level>();
        }
    }

    @SuppressWarnings("rawtypes")
    public List<Level> findRange(long startId, long endId)
    {
        try
        {
            jdbcTemplate = new JdbcTemplate(dataSource);
            String sql = "SELECT * FROM LEVELS WHERE ID BETWEEN ? AND ?";

            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, startId, endId);

            return mapResultListToLevels(rows);
        }
        catch(SQLException e)
        {
            return new ArrayList<Level>();
        }
    }

    private List<Level> mapResultListToLevels(List<Map<String, Object>> rows) throws SQLException
    {
        List<Level> levels = new ArrayList<Level>();

        for (Map row : rows)
        {
            byte[] bytes = (byte[]) row.get("LEVEL_MAP");
            Blob b = new SerialBlob(bytes);
            Level level = new Level(Long.parseLong(String.valueOf(row.get("ID"))),
                    (Long) row.get("AUTHOR_ID"),
                    (String) row.get("NAME"),
                    (Date) row.get("DATE_SUBMITTED"),
                    (Float) row.get("RATING"),
                    (Float) row.get("PERCENT_WON"),
                    b);
            levels.add(level);
        }
        return levels;
    }

    public Long findAuthorIdById(long id)
    {
        String sql = "SELECT AUTHOR_ID FROM LEVELS WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Long.class);
    }

    public String findNameById(long id)
    {
        String sql = "SELECT NAME FROM LEVELS WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, String.class);
    }

    public Date findDateSubmittedById(long id)
    {
        String sql = "SELECT DATE_SUBMITTED FROM LEVELS WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Date.class);
    }

    public float findRatingById(long id)
    {
        String sql = "SELECT RATING FROM LEVELS WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Float.class);
    }

    public float findPercentWonById(long id)
    {
        String sql = "SELECT PERCENT_WON FROM LEVELS WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Float.class);
    }

    public Blob findLevelMapById(long id)
    {
        String sql = "SELECT LEVEL_MAP FROM LEVELS WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Blob.class);
    }

    public Boolean updateEntry(Level level, long id)
    {
        String sql = "UPDATE LEVELS SET AUTHOR_ID = ?, NAME = ?, DATE_SUBMITTED = ?, RATING = ?, PERCENT_WON = ?, LEVEL_MAP = ? WHERE ID = ?";

        jdbcTemplate = new JdbcTemplate(dataSource);

        try
        {
            jdbcTemplate.update(sql, new Object[]{level.getAuthorId(),
                    level.getName(), level.getDateSubmitted(), level.getRating(), level.getPercentWon(), level.getLevelMap(), id});
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
            String sql = "DELETE * FROM LEVELS WHERE ID = ?";
            jdbcTemplate.update(sql, id);

            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
}
