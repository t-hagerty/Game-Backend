package com.example.demo;

import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface JDBCLevelDAO
{
    public Boolean insert(Level level);
    public Boolean insertBatch1(final List<Level> users);
    public Boolean insertBatch2(final String sql);
    public Level findById(long id);
    public List<Level> findAll();
    public List<Level> findRange(long startId, long endId);
    public Long findAuthorIdById(long id);
    public String findNameById(long id);
    public Date findDateSubmittedById(long id);
    public float findRatingById(long id);
    public float findPercentWonById(long id);
    public Blob findLevelMapById(long id);
    public Boolean updateEntry(Level level, long id);
    public Boolean deleteById(long id);
}
