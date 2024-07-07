package com.example.client_hethongxemphim.controllers;

import com.example.client_hethongxemphim.models.ShowTime;
import com.example.client_hethongxemphim.models.Ticket;
import com.example.client_hethongxemphim.models.User;
import com.example.client_hethongxemphim.socket.GoiTin;
import com.example.client_hethongxemphim.socket.YeuCau;
import com.example.client_hethongxemphim.util.AlertUtil;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class SeatSelectionController implements Initializable {

    @FXML
    private GridPane seatGrid;

    private Set<String> selectedSeats = new HashSet<>();
    private Set<String> availableSeats = new HashSet<>();
    private ShowTime selectedShowTime;
    private User user;
    private ShowTimeController showTimeController;
    private  LocalDate selectedDate;
    public void setLocalDate(LocalDate date) {
        selectedDate = date;
    }
    public void setShowTimeController(ShowTimeController showTimeController) {
        this.showTimeController = showTimeController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setUser(User user){
        this.user = user;
    }
    public void setShowTime(ShowTime showTime) {
        this.selectedShowTime = showTime;
        String[] seatsArray = showTime.getSeats().split(",");
        availableSeats.addAll(Arrays.asList(seatsArray));
        addSeats();
        // Further initialization if necessary
    }
    private void addSeats() {
        String[] seats = {
                "A1","A2","A3","A4","A5","A6","A7","A8","A9","A10",
                "B1","B2","B3","B4","B5","B6","B7","B8","B9","B10",
                "C1","C2","C3","C4","C5","C6","C7","C8","C9","C10",
                "D1","D2","D3","D4","D5","D6","D7","D8","D9","D10",
                "E1","E2","E3","E4","E5","E6","E7","E8","E9","E10",
                "F1","F2","F3","F4","F5","F6","F7","F8","F9","F10"
        };

        int row = 0;
        int col = 0;

        for (String seat : seats) {
            Button seatButton = new Button(seat);
            seatButton.getStyleClass().add("seat-button");

            // Disable seats not available in the showtime
            if (!availableSeats.contains(seat)) {
                seatButton.setDisable(true);
                seatButton.setStyle("-fx-background-color: #FF0000;"); // Highlight unavailable seat
            } else {
                seatButton.setOnAction(event -> toggleSeatSelection(seatButton));
            }

            seatGrid.add(seatButton, col, row);
            col++;
            if (col == 10) {
                col = 0;
                row++;
            }
        }
    }


    private void toggleSeatSelection(Button seatButton) {
        String seat = seatButton.getText();
        if (selectedSeats.contains(seat)) {
            selectedSeats.remove(seat);
            seatButton.setStyle("");
        } else {
            selectedSeats.add(seat);
            seatButton.setStyle("-fx-background-color: #00FF00;"); // Highlight selected seat
        }
    }

    @FXML
    private void confirmSelection() {
        // Handle the confirmation of seat selection
        System.out.println("Selected seats: " + selectedSeats);
        String seats = String.join(",",selectedSeats);
        Ticket ticket  = new Ticket();
        ticket.setId_ShowTime(selectedShowTime.getId_ShowTime());
        ticket.setDate(Date.valueOf(LocalDate.now()));
        ticket.setUserName(user.getUserName());
        ticket.setSeats(seats);
        SendTicket(ticket);
        showTimeController.loadShowtimes(selectedDate);
        selectedShowTime = showTimeController.selectedShowtime;
        String[] seatsArray = selectedShowTime.getSeats().split(",");
        availableSeats.clear();
        availableSeats.addAll(Arrays.asList(seatsArray));

        seatGrid.getChildren().clear();
        addSeats();
        // You can add code here to proceed with the booking using the selectedShowTime and selectedSeats
    }

    public void SendTicket(Ticket ticket){
        if(selectedSeats.stream().count() ==0){
            AlertUtil.showAlertError("Null", "Bạn chưa chọn ghế");
            return ;
        }
        try{
            Socket client =  new Socket("localhost",8888);
            GoiTin<Ticket> tin =  new GoiTin<>();
            tin.setYeuCau(YeuCau.DatVe);
            ArrayList<Ticket> list = new ArrayList<>();
            list.add(ticket);
            tin.setListT(list);
            Gson gson = new Gson();
            String json = gson.toJson(tin);
            System.out.println(json);
            PrintWriter printWriter =  new PrintWriter(client.getOutputStream(),true);
            printWriter.println(json);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String mess = in.readLine();
            AlertUtil.showAlertSucces("Thông báo", mess);
            printWriter.close();
            in.close();
            client.close();
        }catch (Exception e){

        }

    }
}
