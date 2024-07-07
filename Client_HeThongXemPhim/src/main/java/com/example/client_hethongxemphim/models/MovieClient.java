package com.example.client_hethongxemphim.models;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class MovieClient implements Serializable {
    private int Id_Movie;
    private int Id_Genre;
    private String MovieName;
    private Date ReleasedDate;
    private String Country;
    private Time Duration;
    private String Director;
    private String Language;
    private Integer Censorship;
    private Float Version;
    private Float Price;
    private String Descripsion;
    private String Image;
    public MovieClient(){

    }

    public MovieClient(int id_Movie, int id_Genre, String movieName, Date releasedDate, String country, Time duration, String director, String language, Integer censorship, Float version, Float price, String descripsion, String image) {
        Id_Movie = id_Movie;
        Id_Genre = id_Genre;
        MovieName = movieName;
        ReleasedDate = releasedDate;
        Country = country;
        Duration = duration;
        Director = director;
        Language = language;
        Censorship = censorship;
        Version = version;
        Price = price;
        Descripsion = descripsion;
        Image = image;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "Id_Movie=" + Id_Movie +
                ", Id_Genre=" + Id_Genre +
                ", MovieName='" + MovieName + '\'' +
                ", ReleasedDate=" + ReleasedDate +
                ", Country='" + Country + '\'' +
                ", Duration=" + Duration +
                ", Director='" + Director + '\'' +
                ", Language='" + Language + '\'' +
                ", Censorship=" + Censorship +
                ", Version=" + Version +
                ", Price=" + Price +
                ", Descripsion='" + Descripsion + '\'' +
                ", Image='" + Image + '\'' +
                '}';
    }

    public MovieClient(int id_Genre, String movieName, Date releasedDate, String country, Time duration, String director, String language, Integer censorship, Float version, Float price, String descripsion, String image) {

        Id_Genre = id_Genre;
        MovieName = movieName;
        ReleasedDate = releasedDate;
        Country = country;
        Duration = duration;
        Director = director;
        Language = language;
        Censorship = censorship;
        Version = version;
        Price = price;
        Descripsion = descripsion;
        Image = image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    // Getters and Setters
    public int getId_Movie() {
        return Id_Movie;
    }

    public void setId_Movie(int id_Movie) {
        this.Id_Movie = id_Movie;
    }

    public int getId_Genre() {
        return Id_Genre;
    }

    public void setId_Genre(int id_Genre) {
        this.Id_Genre = id_Genre;
    }

    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        this.MovieName = movieName;
    }

    public Date getReleasedDate() {
        return ReleasedDate;
    }

    public void setReleasedDate(Date releasedDate) {
        this.ReleasedDate = releasedDate;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        this.Country = country;
    }

    public Time getDuration() {
        return Duration;
    }

    public void setDuration(Time duration) {
        this.Duration = duration;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        this.Director = director;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        this.Language = language;
    }

    public Integer getCensorship() {
        return Censorship;
    }

    public void setCensorship(Integer censorship) {
        this.Censorship = censorship;
    }

    public Float getVersion() {
        return Version;
    }

    public void setVersion(Float version) {
        this.Version = version;
    }

    public Float getPrice() {
        return Price;
    }

    public void setPrice(Float price) {
        this.Price = price;
    }

    public String getDescripsion() {
        return Descripsion;
    }

    public void setDescripsion(String descripsion) {
        this.Descripsion = descripsion;
    }
}

