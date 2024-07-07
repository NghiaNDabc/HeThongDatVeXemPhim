package com.example.client_hethongxemphim.models;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class ShowTime implements Serializable {
    private int Id_ShowTime;
    private int Id_Room;
    private int Id_Movie;
    private String Seats;
    private Time StartTime;
    private Time EndTime;
    private Date Date;
    public  ShowTime(){}
    public ShowTime(int id_ShowTime, int id_Room, int id_Movie, String seats, Time startTime, Time endTime, java.sql.Date date) {
        Id_ShowTime = id_ShowTime;
        Id_Room = id_Room;
        Id_Movie = id_Movie;
        Seats = seats;
        StartTime = startTime;
        EndTime = endTime;
        Date = date;
    }
    public ShowTime( int id_Room, int id_Movie, String seats, Time startTime, Time endTime, java.sql.Date date) {

        Id_Room = id_Room;
        Id_Movie = id_Movie;
        Seats = seats;
        StartTime = startTime;
        EndTime = endTime;
        Date = date;
    }

    // Getters and Setters
    public int getId_ShowTime() {
        return Id_ShowTime;
    }

    public void setId_ShowTime(int id_ShowTime) {
        this.Id_ShowTime = id_ShowTime;
    }

    public int getId_Room() {
        return Id_Room;
    }

    public void setId_Room(int id_Room) {
        this.Id_Room = id_Room;
    }

    public int getId_Movie() {
        return Id_Movie;
    }

    public void setId_Movie(int id_Movie) {
        this.Id_Movie = id_Movie;
    }

    public String getSeats() {
        return Seats;
    }

    public void setSeats(String seats) {
        this.Seats = seats;
    }

    public Time getStartTime() {
        return StartTime;
    }

    public void setStartTime(Time startTime) {
        this.StartTime = startTime;
    }

    public Time getEndTime() {
        return EndTime;
    }

    public void setEndTime(Time endTime) {
        this.EndTime = endTime;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        this.Date = date;
    }
}
