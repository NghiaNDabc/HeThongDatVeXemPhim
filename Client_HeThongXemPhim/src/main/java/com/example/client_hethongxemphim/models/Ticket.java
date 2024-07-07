package com.example.client_hethongxemphim.models;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Ticket implements Serializable {
    private int Id_Ticket;
    private int Id_ShowTime;
    private String UserName;
    private java.sql.Date Date;
    private String Status;
    private String Seats;


    public Ticket() {
    }

    public Ticket(int id_Ticket, int id_ShowTime, String userName,String seats, Date date, String status) {
        Id_Ticket = id_Ticket;
        Id_ShowTime = id_ShowTime;
        UserName = userName;
        this.Seats = seats;
        Date = date;
        Status = status;
    }

    public String getSeats() {
        return Seats;
    }

    public void setSeats(String seats) {
        Seats = seats;
    }

    public Ticket(int id_ShowTime, String userName, Date date, String status) {

        Id_ShowTime = id_ShowTime;
        UserName = userName;
        Date = date;
        Status = status;
    }

    // Getters and Setters
    public int getId_Ticket() {
        return Id_Ticket;
    }

    public void setId_Ticket(int id_Ticket) {
        this.Id_Ticket = id_Ticket;
    }

    public int getId_ShowTime() {
        return Id_ShowTime;
    }

    public void setId_ShowTime(int id_ShowTime) {
        this.Id_ShowTime = id_ShowTime;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        this.Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }
}
