package com.example.demo;

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

public class JDBCLevelDAOImpl implements JDBCLevelDAO
{
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public void insert(Level level)
    {

        String sql = "INSERT INTO LEVELS " +
                "(ID, AUTHOR_ID, NAME, DATE_SUBMITTED, RATING, PERCENT_WON, LEVEL_MAP) VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql, new Object[]{level.getId(), level.getAuthorId(),
                level.getName(), level.getDateSubmitted(), level.getRating(), level.getPercentWon(), level.getLevelMap()});
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
    public List<Level> findAll() throws SQLException
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM LEVELS";

        List<Level> levels = new ArrayList<Level>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
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

    public void insertBatch1(final List<Level> levels)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "INSERT INTO LEVELS " +
                "(ID, AUTHOR_ID, NAME, DATE_SUBMITTED, RATING, PERCENT_WON, LEVEL_MAP) VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter()
        {
            public void setValues(PreparedStatement ps, int i) throws SQLException
            {
                Level level = levels.get(i);
                ps.setLong(1, level.getId());
                ps.setLong(2, level.getAuthorId());
                ps.setString(3, level.getName());
                ps.setDate(4, level.getDateSubmitted());
                ps.setFloat(5, level.getRating() );
                ps.setFloat(6, level.getPercentWon() );
                ps.setBlob(7, level.getLevelMap() );
            }

            public int getBatchSize()
            {
                return levels.size();
            }
        });
    }

    public void insertBatch2(final String sql)
    {
        jdbcTemplate.batchUpdate(sql);
    }
}
