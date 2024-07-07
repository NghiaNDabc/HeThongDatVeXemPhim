package com.example.client_hethongxemphim.models;

import java.sql.Time;
import java.sql.Date;

public class TicketInfo {
   public int id;
    private String movieName;
    private String genreName;
    private Time duration;
    private Time startTime;
    private Time endTime;
    private Date date;
    private String seats;
    private float price;

    // Constructor
    public TicketInfo(String movieName, String genreName, Time duration, Date date, String seats) {
        this.movieName = movieName;
        this.genreName = genreName;
        this.duration = duration;
        this.date = date;
        this.seats = seats;
    }

    // Getters and Setters

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
