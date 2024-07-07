package com.example.client_hethongxemphim.models;

import java.io.Serializable;

public class Cinema implements Serializable {
    private int Id_Cinema;
    private String CinemaName;
    private String Address;
    private String Status;

    public Cinema() {}
    public Cinema(int id_Cinema, String cinemaName, String address, String status) {
        Id_Cinema = id_Cinema;
        CinemaName = cinemaName;
        Address = address;
        Status = status;
    }
    public Cinema( String cinemaName, String address, String status) {

        CinemaName = cinemaName;
        Address = address;
        Status = status;
    }

    // Getters and Setters
    public int getId_Cinema() {
        return Id_Cinema;
    }

    public void setId_Cinema(int id_Cinema) {
        this.Id_Cinema = id_Cinema;
    }

    public String getCinemaName() {
        return CinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.CinemaName = cinemaName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }
}

