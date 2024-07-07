-- Drop database if exists
DROP DATABASE IF EXISTS Cinema_System;

-- Create database
CREATE DATABASE Cinema_System;

-- Use the new database
USE Cinema_System;

-- Create Cinema table
CREATE TABLE Cinema (
    Id_Cinema INT NOT NULL AUTO_INCREMENT,
    CinemaName VARCHAR(50),
    Address VARCHAR(120),
    Status VARCHAR(50),
    PRIMARY KEY (Id_Cinema)
);
CREATE TABLE Genre (
    Id_Genre INT NOT NULL AUTO_INCREMENT,
    GenreName VARCHAR(50),
    PRIMARY KEY (Id_Genre)
);
-- Create Room table
CREATE TABLE Room (
    Id_Room INT NOT NULL AUTO_INCREMENT,
    Id_Cinema INT NOT NULL,
    RoomName VARCHAR(50),
    PRIMARY KEY (Id_Room),
    FOREIGN KEY (Id_Cinema) REFERENCES Cinema(Id_Cinema)
);
CREATE TABLE Movie (
    Id_Movie INT NOT NULL AUTO_INCREMENT,
    Id_Genre INT NOT NULL,
    MovieName VARCHAR(50),
    ReleasedDate DATE,
    Country VARCHAR(50),
    Duration TIME,
    Director VARCHAR(50),
    Language VARCHAR(50),
    Censorship INT,
    Version FLOAT,
    Price FLOAT,
    Descripsion VARCHAR(400),
    Image VARCHAR(50),
    PRIMARY KEY (Id_Movie),
    FOREIGN KEY (Id_Genre) REFERENCES Genre(Id_Genre)
);
-- Create ShowTime table
CREATE TABLE ShowTime (
    Id_ShowTime INT NOT NULL AUTO_INCREMENT,
    Id_Room INT NOT NULL,
    Id_Movie INT NOT NULL,
    Seats VARCHAR(300),
    StartTime TIME,
    EndTime TIME,
    Date DATE,
    PRIMARY KEY (Id_ShowTime),
    FOREIGN KEY (Id_Room) REFERENCES Room(Id_Room),
    FOREIGN KEY (Id_Movie) REFERENCES Movie(Id_Movie)
);

-- Create Movie table
CREATE TABLE User (
    UserName VARCHAR(50) NOT NULL,
    PassWord VARCHAR(200),
    Role VARCHAR(50),
    FullName VARCHAR(50),
    Dob DATE,
    PhoneNumber VARCHAR(12),
    Email VARCHAR(50),
    PRIMARY KEY (UserName)
);

-- Create Ticket table
CREATE TABLE Ticket (
    Id_Ticket INT NOT NULL AUTO_INCREMENT,
    Id_ShowTime INT NOT NULL,
    UserName VARCHAR(50) NOT NULL,
    Seats VARCHAR(50) NOT NULL,
    Date DATETIME,
    Status NVARCHAR(40),
    PRIMARY KEY (Id_Ticket),
    FOREIGN KEY (Id_ShowTime) REFERENCES ShowTime(Id_ShowTime),
    FOREIGN KEY (UserName) REFERENCES User(UserName)
);

INSERT INTO User (UserName, PassWord, Role, FullName, Dob, PhoneNumber, Email) 
VALUES 
    ('admin', '1', 'admin', 'Admin', '1985-04-12', '0123456789', 'user1@example.com'),
    ('user', '1', 'user', 'Pham Nghia', '1992-08-25', '0987654321', 'user2@example.com');
    
    INSERT INTO cinema( CinemaName, Address, Status) VALUES (N'CGV Hà Nội Centerpoint',N'Tầng 5 Centerpoint, 27 Lê Văn Lương, Phường Nhân Chính, Thanh Xuân, HN',N'OK');
 INSERT INTO cinema( CinemaName, Address, Status) VALUES (N'CGV Hà Nội Mac Plaza',N'Tầng 7 Trung tâm thương mại Mac Plaze, 10 Trần Phú, Q.Hà Đông, HN',N'OK');
INSERT INTO room(Id_Cinema, Roomname) VALUES(1, 'Phòng số 1');
INSERT INTO room(Id_Cinema, Roomname) VALUES(1, 'Phòng số 2');
INSERT INTO room(Id_Cinema, Roomname) VALUES(1, 'Phòng số 3');
INSERT INTO room(Id_Cinema, Roomname) VALUES(1, 'Phòng số 4');
INSERT INTO room(Id_Cinema, Roomname) VALUES(1, 'Phòng số 5');
INSERT INTO room(Id_Cinema, Roomname) VALUES(1, 'Phòng số 6');

INSERT INTO genre VALUES(1,N'Hoạt hình'),( 2,N'Phiêu lưu'),(3, N'Hành động');

INSERT INTO Movie (Id_Genre, MovieName, ReleasedDate, Country, Duration, Director, Language, Censorship, Version, Price, Descripsion, Image)
VALUES
    (1, 'The Shawshank Redemption', '1994-09-23', 'USA', '02:22:00', 'Frank Darabont', 'English', 15, 1.0, 10.0, 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 'a1.png'),
    (2, 'The Godfather', '1972-03-24', 'USA', '02:55:00', 'Francis Ford Coppola', 'English', 17, 1.0, 12.0, 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 'a2.png'),
    (3, 'The Dark Knight', '2008-07-18', 'USA', '02:32:00', 'Christopher Nolan', 'English', 13, 1.0, 15.0, 'When the menace known as The Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.', 'a3.png'),
    (1, 'Pulp Fiction', '1994-10-14', 'USA', '02:34:00', 'Quentin Tarantino', 'English', 18, 1.0, 8.0, 'The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.', 'a4.png'),
    (2, 'Forrest Gump', '1994-07-06', 'USA', '02:22:00', 'Robert Zemeckis', 'English', 13, 1.0, 9.0, 'The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75.', 'a5.png'),
    (3, 'Inception', '2010-07-16', 'USA', '02:28:00', 'Christopher Nolan', 'English', 13, 1.0, 14.0, 'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.', 'a6.png'),
    (1, 'Fight Club', '1999-10-15', 'USA', '02:19:00', 'David Fincher', 'English', 18, 1.0, 10.0, 'An insomniac office worker and a devil-may-care soap maker form an underground fight club that evolves into much more.', 'a7.png'),
    (2, 'The Matrix', '1999-03-31', 'USA', '02:16:00', 'Lana Wachowski, Lilly Wachowski', 'English', 15, 1.0, 11.0, 'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.', 'a8.png'),
    (3, 'Goodfellas', '1990-09-21', 'USA', '02:26:00', 'Martin Scorsese', 'English', 18, 1.0, 12.0, 'The story of Henry Hill and his life in the mob, covering his relationship with his wife Karen Hill and his mob partners Jimmy Conway and Tommy DeVito.', 'a9.png'),
    (1, 'The Silence of the Lambs', '1991-02-14', 'USA', '01:58:00', 'Jonathan Demme', 'English', 17, 1.0, 10.0, 'A young F.B.I. cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer, a madman who skins his victims.', 'a10.png');


-- Create Genre table


