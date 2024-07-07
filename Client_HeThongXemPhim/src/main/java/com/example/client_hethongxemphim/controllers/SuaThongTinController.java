package com.example.client_hethongxemphim.controllers;

import com.example.client_hethongxemphim.models.ShowTime;
import com.example.client_hethongxemphim.models.User;
import com.example.client_hethongxemphim.socket.GoiTin;
import com.example.client_hethongxemphim.socket.YeuCau;
import com.example.client_hethongxemphim.util.AlertUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class SuaThongTinController {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField dobField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField emailField;

    public Stage dialogStage;
    private HomeController homeController;
    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
    private TaiKhoanController taiKhoanController;
    public void setTaiKhoanController(TaiKhoanController taiKhoanController) {
        this.taiKhoanController = taiKhoanController;
    }
    @FXML
    private void initialize() {
        // Initialize the personal info fields if needed
    }
    private User user;
    public void setUser(User user){
        this.user = user;
        setPersonalInfo(user);
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    private boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    private boolean isInputValid() {
        String errorMessage = "";

        if (fullNameField.getText() == null || fullNameField.getText().length() == 0) {
            errorMessage += "Tên không hợp lệ!\n";
        }

        if (dobField.getText() == null || dobField.getText().length() == 0) {
            errorMessage += "Ngày sinh không hợp lệ!\n";
        } else {
            if (!isValidDate(dobField.getText())) {
                errorMessage += "Định dạng ngày sinh không hợp lệ (yyyy-mm-dd)!\n";
            }
        }

        if (phoneNumberField.getText() == null || phoneNumberField.getText().length() == 0) {
            errorMessage += "Số điện thoại không hợp lệ!\n";
        }

        if (emailField.getText() == null || emailField.getText().length() == 0) {
            errorMessage += "Email không hợp lệ!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            AlertUtil.showAlertError("Lỗi", errorMessage);

            return false;
        }
    }
    public void setPersonalInfo(User user) {
        fullNameField.setText(user.getFullName());
        dobField.setText(user.getDob().toString());
        phoneNumberField.setText(user.getPhoneNumber());
        emailField.setText(user.getEmail());
    }

    public User getUserFromFields() {
        String fullName = fullNameField.getText();
        String dobText = dobField.getText();
        String phoneNumber = phoneNumberField.getText();
        String email = emailField.getText();

        Date dob = null;
        try {
            dob = Date.valueOf(dobText); // Chuyển đổi chuỗi ngày sinh sang Date
        } catch (IllegalArgumentException e) {
            // Xử lý ngoại lệ khi ngày sinh không hợp lệ
            e.printStackTrace();
        }

        User user = this.user;
        user.setFullName(fullName);
        user.setDob(dob);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);

        return user;
    }
    @FXML
    private void handleSave() {
        if (isInputValid()) {
            User user = getUserFromFields();
            SendEditUser(user);

            homeController.setUser(user);
            taiKhoanController.setUser(user);
            dialogStage.close();
        }
    }

    public void SendEditUser(User user) {
        try (Socket socket = new Socket("localhost", 8888);
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            ArrayList<User> list = new ArrayList<>();
            list.add(user);
            // Tạo yêu cầu lấy danh sách suất chiếu
            GoiTin<User> goiTin = new GoiTin<>(list, YeuCau.SuaThongTinCaNhan);
            Gson gson = new Gson();
            String json = gson.toJson(goiTin);
            printWriter.println(json);

            // Đọc phản hồi từ server
            String mess = in.readLine();
            AlertUtil.showAlertSucces("Thông báo", mess);
            // Hiển thị danh sách suất chiếu


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}






