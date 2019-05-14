package com.example.demo;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LevelRowMapper implements RowMapper<Level>
{
    @Override
    public Level mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        Level level = new Level(rs.getLong("ID"),
                rs.getLong("AUTHOR_ID"),
                rs.getString("NAME"),
                rs.getDate("DATE_SUBMITTED"),
                rs.getFloat("RATING"),
                rs.getFloat("PERCENT_WON"),
                rs.getBlob("LEVEL_MAP"),
                rs.getLong("NUMBER_RATINGS"),
                rs.getLong("NUMBER_PLAYED"));
        return level;
    }
}
