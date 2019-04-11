package com.example.demo;

import java.sql.Blob;
import java.sql.Date;

public class Level
{
    private long id, authorId;
    private String name;
    private Date dateSubmitted;
    private float rating, percentWon;
    private Blob levelMap;

    public Level(long id, long authorId, String name, Date dateSubmitted, float rating, float percentWon, Blob levelMap)
    {
        this.id = id;
        this.authorId = authorId;
        this.name = name;
        this.dateSubmitted = dateSubmitted;
        this.rating = rating;
        this.percentWon = percentWon;
        this.levelMap = levelMap;
    }

    @Override
    public String toString()
    {
        return "Level{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", name='" + name + '\'' +
                ", dateSubmitted=" + dateSubmitted +
                ", rating=" + rating +
                ", percentWon=" + percentWon +
                '}';
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public long getAuthorId()
    {
        return authorId;
    }

    public void setAuthorId(long authorId)
    {
        this.authorId = authorId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getDateSubmitted()
    {
        return dateSubmitted;
    }

    public void setDateSubmitted(Date dateSubmitted)
    {
        this.dateSubmitted = dateSubmitted;
    }

    public float getRating()
    {
        return rating;
    }

    public void setRating(float rating)
    {
        this.rating = rating;
    }

    public float getPercentWon()
    {
        return percentWon;
    }

    public void setPercentWon(float percentWon)
    {
        this.percentWon = percentWon;
    }

    public Blob getLevelMap()
    {
        return levelMap;
    }

    public void setLevelMap(Blob levelMap)
    {
        this.levelMap = levelMap;
    }
}