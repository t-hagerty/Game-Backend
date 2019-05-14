package com.example.demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Base64;

public class Level
{
    private long id;
    private long authorId;
    private long numberRatings;
    private long numberPlayed;
    private String name;
    private Date dateSubmitted;
    private float rating, percentWon;
    private Blob levelMap;
    private String encodedMap;

    public Level(long id, long authorId, String name, Date dateSubmitted, float rating, float percentWon, Blob levelMap, long numberRatings, long numberPlayed) throws SQLException
    {
        this.id = id;
        this.authorId = authorId;
        this.name = name;
        this.dateSubmitted = dateSubmitted;
        this.rating = rating;
        this.percentWon = percentWon;
        this.levelMap = levelMap;
        this.numberRatings = numberRatings;
        this.numberPlayed = numberPlayed;
        this.encodedMap = Base64.getEncoder().encodeToString(levelMap.getBytes(1, (int)levelMap.length()));
    }

    @JsonCreator
    public Level(@JsonProperty("authorId") long authorId, @JsonProperty("name") String name, @JsonProperty("levelMap") String base64EncodedLevelMap) throws SQLException
    {
        this.id = -1;
        this.authorId = authorId;
        this.name = name;
        this.dateSubmitted = new Date(System.currentTimeMillis());
        this.rating = 0;
        this.percentWon = 1;
        byte[] decodedMap = Base64.getDecoder().decode(base64EncodedLevelMap);
        levelMap = new SerialBlob(decodedMap);
        this.numberRatings = 0;
        this.numberPlayed = 1;
        this.encodedMap = Base64.getEncoder().encodeToString(levelMap.getBytes(1, (int)levelMap.length()));
    }

    @JsonValue
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
                ", levelMap=" + encodedMap +
                ", numberRatings=" + numberRatings +
                ", numberPlayed=" + numberPlayed +
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

    public long getNumberRatings() { return numberRatings; }

    public void setNumberRatings(long numberRatings) { this.numberRatings = numberRatings; }

    public long getNumberPlayed() { return numberPlayed; }

    public void setNumberPlayed(long numberPlayed) { this.numberPlayed = numberPlayed; }
}