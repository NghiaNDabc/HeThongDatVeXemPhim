package com.example.client_hethongxemphim.models;

import java.io.Serializable;

public class Room implements Serializable {
    private int Id_Room;
    private int Id_Cinema;
    private String RoomName;
    public Room() {}

    public Room(int id_Room, int id_Cinema, String roomName) {
        Id_Room = id_Room;
        Id_Cinema = id_Cinema;
        RoomName = roomName;
    }
    public Room(int id_Cinema, String roomName) {
        Id_Cinema = id_Cinema;
        RoomName = roomName;
    }

    // Getters and Setters
    public int getId_Room() {
        return Id_Room;
    }

    public void setId_Room(int id_Room) {
        this.Id_Room = id_Room;
    }

    public int getId_Cinema() {
        return Id_Cinema;
    }

    public void setId_Cinema(int id_Cinema) {
        this.Id_Cinema = id_Cinema;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        this.RoomName = roomName;
    }
}

