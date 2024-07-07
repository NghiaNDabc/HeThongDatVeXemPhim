package com.example.client_hethongxemphim.controllers;


import com.example.client_hethongxemphim.models.User;
import com.example.client_hethongxemphim.socket.GoiTin;
import com.example.client_hethongxemphim.socket.YeuCau;
import com.example.client_hethongxemphim.util.AlertUtil;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;

public class DangKyController {
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;
    Socket clSocket = null;
    final int portServer = 8888;
    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtFullName;

    @FXML
    private DatePicker datePickerDob;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtEmail;

    @FXML
    private void registerButtonAction(ActionEvent event) {
        String username = (txtUsername.getText());
        String password = (txtPassword.getText());
        System.out.println(username + " " + password);
        String confirmPassword = txtConfirmPassword.getText();
        String fullName = txtFullName.getText();
        Date dob = Date.valueOf(datePickerDob.getValue());
        String phoneNumber = txtPhoneNumber.getText();
        String email = txtEmail.getText();

        if (!password.equals(confirmPassword)) {
            // Handle password mismatch
           AlertUtil.showAlertError("Lỗi","Mật khẩu không khớp!");
            return;
        }

        // Create User object
        User user = new User(username, password, "KH", fullName, dob, phoneNumber, email);
        System.out.println(user);
        // Save user to database
        try {
            DangKy(user);

            // Chuyển về màn hình đăng nhập sau khi đăng ký thành công
            switchToLogin(event);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Đăng ký thất bại!");
        }
    }void DangKy(User user) {
        try (Socket clSocket = new Socket("localhost", portServer);
             PrintWriter printWriter = new PrintWriter(clSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clSocket.getInputStream()))) {

            ArrayList<User> list = new ArrayList<>();
            list.add(user);
            GoiTin<User> goiTin = new GoiTin<>();
            goiTin.setListT(list);
            goiTin.setYeuCau(YeuCau.DangKy);

            Gson gson = new Gson();
            String json = gson.toJson(goiTin);
            printWriter.println(json);

            // Đọc phản hồi từ server
            String responseJson = in.readLine();
            String mess = gson.fromJson(responseJson, String.class);
            System.out.println(mess);
            AlertUtil.showAlertSucces("Thông báo", mess);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void goBackAction(ActionEvent event) throws IOException {
        // Load the login FXML file and set it to the current stage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/client_hethongxemphim/DangNhap.fxml"));
        Parent loginRoot = loader.load();
        Scene loginScene = new Scene(loginRoot);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(loginScene);
        currentStage.show();
    }

    private void switchToLogin(ActionEvent event) throws IOException {
        // Load the login FXML file and set it to the current stage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/client_hethongxemphim/DangNhap.fxml"));
        Parent loginRoot = loader.load();
        Scene loginScene = new Scene(loginRoot);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(loginScene);
        currentStage.show();
    }
}
