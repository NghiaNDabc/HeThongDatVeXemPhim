package com.example.client_hethongxemphim.controllers;

import com.example.client_hethongxemphim.models.TicketInfo;
import com.example.client_hethongxemphim.models.User;
import com.example.client_hethongxemphim.socket.GoiTin;
import com.example.client_hethongxemphim.socket.YeuCau;
import com.example.client_hethongxemphim.util.AlertUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TicketViewController implements Initializable {

    @FXML
    private VBox ticketContainer;

    private List<TicketInfo> ticketInfoList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize controller
    }
    private User user;
    public void setUser(User user) {
        this.user = user;
        loadTickets();
        updateUI();
    }
    public void setTicketInfoList(List<TicketInfo> ticketInfoList) {
        //this.ticketInfoList = ticketInfoList;
        //updateUI();
    }
    private void loadTickets() {
        try (Socket socket = new Socket("localhost", 8888);
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            ArrayList<User> users = new ArrayList<>();
            users.add(user);
            GoiTin<User> goiTin = new GoiTin<>(users, YeuCau.XemListVe);
            Gson gson = new Gson();
            String json = gson.toJson(goiTin);
            printWriter.println(json);

            String responseJson = in.readLine();
            Type listType = new TypeToken<ArrayList<TicketInfo>>(){}.getType();
            ArrayList<TicketInfo> tickets = gson.fromJson(responseJson, listType);
            ticketInfoList = tickets;
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.showAlertError("Lỗi", "Không thể tải danh sách vé.");
        }
    }
    private void updateUI() {
        ticketContainer.getChildren().clear();
        if (ticketInfoList != null && !ticketInfoList.isEmpty()) {
            for (TicketInfo ticketInfo : ticketInfoList) {
                VBox ticketBox = createTicketInfoBox(ticketInfo);
                ticketContainer.getChildren().add(ticketBox);
            }
        } else {
            Label noTicketsLabel = new Label("Không có vé nào");
            ticketContainer.getChildren().add(noTicketsLabel);
        }
    }

    private VBox createTicketInfoBox(TicketInfo ticketInfo) {
        VBox hbox = new VBox();
        hbox.setSpacing(10);
        hbox.getStyleClass().add("ticket-box");
        Label id = new Label("Mã vẽ: " + ticketInfo.id);
        id.getStyleClass().add("info-label");
        Label movieNameLabel = new Label("Tên phim: " + ticketInfo.getMovieName());
        movieNameLabel.getStyleClass().add("info-label");
        Label startTimeLabel = new Label("Bắt đầu: " + ticketInfo.getStartTime().toString());
        startTimeLabel.getStyleClass().add("info-label");
        Label endTimeLabel = new Label("Kết thúc: " + ticketInfo.getEndTime().toString());
        endTimeLabel.getStyleClass().add("info-label");
        Label dateLabel = new Label("Ngày: " + ticketInfo.getDate().toString());
        dateLabel.getStyleClass().add("info-label");
        Label seatsLabel = new Label("Ghế: " + ticketInfo.getSeats());
        seatsLabel.getStyleClass().add("info-label");
        String[] arr = ticketInfo.getSeats().split(",");
        Label priceLabel = new Label("Giá vé: " + 50000*arr.length +"đ");
        priceLabel.getStyleClass().add("price-label");

        hbox.getChildren().addAll(id,movieNameLabel, startTimeLabel, endTimeLabel, dateLabel, seatsLabel, priceLabel);
        return hbox;
    }
}
