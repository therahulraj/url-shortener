package com.wisestep.urlShortener.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String shortenedUrl;

    private String originalUrl;

    String timestamp;

    public void setShortenedUrl(String shortenedUrl) {
        this.shortenedUrl = shortenedUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Url() {
    }

    public Url(int id, String shortenedUrl, String originalUrl, String timestamp) {
        this.id = id;
        this.shortenedUrl = shortenedUrl;
        this.originalUrl = originalUrl;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortenedUrl() {
        return shortenedUrl;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


}
