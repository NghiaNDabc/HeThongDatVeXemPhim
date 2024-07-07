package com.example.client_hethongxemphim.models;

import java.io.Serializable;

public class GenreClient implements Serializable {
    private int Id_Genre;
    private String GenreName;

    public GenreClient(){}
    public GenreClient(int id_Genre, String genreName) {
        GenreName = genreName;
        Id_Genre = id_Genre;
    }
    public GenreClient(String genreName) {
        GenreName = genreName;

    }

    // Getters and Setters
    public int getId_Genre() {
        return Id_Genre;
    }

    public void setId_Genre(int id_Genre) {
        this.Id_Genre = id_Genre;
    }

    public String getGenreName() {
        return GenreName;
    }

    public void setGenreName(String genreName) {
        this.GenreName = genreName;
    }
}
