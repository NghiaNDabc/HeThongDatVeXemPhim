package com.example.client_hethongxemphim.controllers;

import com.example.client_hethongxemphim.models.MovieClient;
import com.example.client_hethongxemphim.models.ShowTime;
import com.example.client_hethongxemphim.models.User;
import com.example.client_hethongxemphim.socket.GoiTin;
import com.example.client_hethongxemphim.socket.YeuCau;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowTimeController implements Initializable {

    @FXML
    private VBox showtimeContent;

    @FXML
    private Button btnDayMinus3;
    @FXML
    private Button btnDayMinus2;
    @FXML
    private Button btnDayMinus1;
    @FXML
    private Button btnToday;
    @FXML
    private Button btnDayPlus1;
    @FXML
    private Button btnDayPlus2;
    @FXML
    private Button btnDayPlus3;
    HomeController homeController;
    private MovieClient selectedMovie;
    private LocalDate selectedDate;
    private User user;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedDate = LocalDate.now();
        btnToday.getStyleClass().add("selected");
        selectedButton = btnToday;
    }
    @FXML

    public void setSelectedMovie(MovieClient movie) {
        this.selectedMovie = movie;
        updateDayButtons();
        loadShowtimes(selectedDate);
    }
    public  void setHomeController(HomeController home){
        this.homeController = home;
    }
    public void setUser(User user){
        this.user = user;
    }
    private void updateDayButtons() {
        LocalDate today = LocalDate.now();

        btnToday.setText("Today");
        btnDayPlus1.setText(formatDate(today.plusDays(1)));
        btnDayPlus2.setText(formatDate(today.plusDays(2)));
        btnDayPlus3.setText(formatDate(today.plusDays(3)));
        btnDayMinus3.setText(formatDate(today.plusDays(4)));
        btnDayMinus2.setText(formatDate(today.plusDays(5)));
        btnDayMinus1.setText(formatDate(today.plusDays(6)));
    }

    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        return date.format(formatter);
    }
    private Button selectedButton = null;
    @FXML
    private void handleDaySelection(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        // Remove selected class from previous selected button
        if (selectedButton != null) {
            selectedButton.getStyleClass().remove("selected");
        }

        // Add selected class to the clicked button
        clickedButton.getStyleClass().add("selected");
        selectedButton = clickedButton;
        LocalDate today = LocalDate.now();
        if (clickedButton == btnDayMinus3) {
            selectedDate = today.plusDays(4);
        } else if (clickedButton == btnDayMinus2) {
            selectedDate = today.plusDays(5);
        } else if (clickedButton == btnDayMinus1) {
            selectedDate = today.plusDays(6);
        } else if (clickedButton == btnToday) {
            selectedDate = today;
        } else if (clickedButton == btnDayPlus1) {
            selectedDate = today.plusDays(1);
        } else if (clickedButton == btnDayPlus2) {
            selectedDate = today.plusDays(2);
        } else if (clickedButton == btnDayPlus3) {
            selectedDate = today.plusDays(3);
        }
        loadShowtimes(selectedDate);
    }

    public void loadShowtimes(LocalDate date) {
        showtimeContent.getChildren().clear();
        try (Socket socket = new Socket("localhost", 8888);
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            ArrayList<ShowTime> list = new ArrayList<>();
            ShowTime showTime1 = new ShowTime();
            showTime1.setId_Movie(selectedMovie.getId_Movie());
            showTime1.setDate(Date.valueOf(date));
            list.add(showTime1);
            // Tạo yêu cầu lấy danh sách suất chiếu
            GoiTin<ShowTime> goiTin = new GoiTin<>(list, YeuCau.XemSuatchieuPhimTheoNgay);

            Gson gson = new Gson();
            String json = gson.toJson(goiTin);
            printWriter.println(json);

            // Đọc phản hồi từ server
            String responseJson = in.readLine();
            Type listType = new TypeToken<List<ShowTime>>() {}.getType();
            List<ShowTime> showTimes = gson.fromJson(responseJson, listType);

            // Hiển thị danh sách suất chiếu
            if (showTimes != null) {

                for (ShowTime showTime : showTimes) {
                    showtimeContent.getChildren().add(createShowTimeBox(showTime));
                    if(selectedShowtime!=null && showTime.getId_ShowTime() == selectedShowtime.getId_ShowTime()){
                        selectedShowtime = showTime;
                    }
                }

            } else {
                Label noShowTimesLabel = new Label("Không có suất chiều nào");
                showtimeContent.getChildren().add(noShowTimesLabel);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void switchToSeatSelection(ShowTime showTime) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/client_hethongxemphim/SelectSeat.fxml"));
            Parent root = loader.load();
            SeatSelectionController seatSelectionController = loader.getController();
            seatSelectionController.setShowTime(showTime);
            seatSelectionController.setUser(this.user);
            seatSelectionController.setLocalDate(selectedDate);
            seatSelectionController.setShowTimeController(this);
            Stage stage = new Stage();
            stage.setTitle("Chọn chỗ ngồi");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ShowTime selectedShowtime;
    private HBox createShowTimeBox(ShowTime showTime) {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.getStyleClass().add("showtime-box");

        Label startTimeLabel = new Label("Bắt đầu: "+showTime.getStartTime().toString());
        Label endTimeLabel = new Label("Kết thúc: " +showTime.getEndTime().toString());
        Label roomLabel = new Label("Phòng: " + showTime.getId_Room());

        Button bookButton = new Button("Đặt vé");
        bookButton.getStyleClass().add("book-button");
        bookButton.setOnAction(event ->  {
            selectedShowtime = showTime;
            switchToSeatSelection(showTime);
        });

        hbox.getChildren().addAll(startTimeLabel, endTimeLabel, roomLabel, bookButton);
        return hbox;
    }
}
