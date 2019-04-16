package com.example.demo;

import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface JDBCLevelDAO
{
    public void insert(Level level);
    public Level findById(long id);
    public List<Level> findAll() throws SQLException;
    public Long findAuthorIdById(long id);
    public String findNameById(long id);
    public Date findDateSubmittedById(long id);
    public float findRatingById(long id);
    public float findPercentWonById(long id);
    public Blob findLevelMapById(long id);
    public void insertBatch1(final List<Level> users);
    public void insertBatch2(final String sql);
}
