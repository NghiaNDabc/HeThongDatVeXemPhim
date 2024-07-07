package com.example.client_hethongxemphim.controllers;

import com.example.client_hethongxemphim.models.User;
import com.example.client_hethongxemphim.socket.GoiTin;
import com.example.client_hethongxemphim.socket.YeuCau;
import com.example.client_hethongxemphim.util.AlertUtil;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ChangePasswordController {

    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label statusLabel;

    @FXML
    private Button changePasswordButton;

    @FXML
    private Button cancelButton;
    private User user;
    private HomeController homeController;
    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
    public void setUser(User user) {
        this.user = user;
        System.out.println("Pass "+ user.getPassWord());
    }
    private boolean isInputValid() {
        String errorMessage = "";

        if (currentPasswordField.getText() == null || currentPasswordField.getText().isEmpty()) {
            errorMessage += "Mật khẩu hiện tại không được để trống!\n";
        }
        if (newPasswordField.getText() == null || newPasswordField.getText().isEmpty()) {
            errorMessage += "Mật khẩu mới không được để trống!\n";
        }
        if (confirmPasswordField.getText() == null || confirmPasswordField.getText().isEmpty()) {
            errorMessage += "Xác nhận mật khẩu không được để trống!\n";
        } else {
            if (!confirmPasswordField.getText().equals(newPasswordField.getText())) {
                errorMessage += "Xác nhận mật khẩu không khớp với mật khẩu mới!\n";
            }
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            AlertUtil.showAlertError("Lỗi", errorMessage);
            return false;
        }
    }
    @FXML
    private void handleChangePassword() {
        if(isInputValid() == false){
            statusLabel.setText("Mật khẩu mới không khớp");
            return;
        }
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (currentPasswordField.getText().equals(user.getPassWord()) == true) {
            // Thực hiện thay đổi mật khẩu
            // Ví dụ: Gọi phương thức thay đổi mật khẩu trong lớp dịch vụ
             User user = this.user;
             user.setPassWord(newPassword);
             SendEditUser(user);
             homeController.setUser(user);

        } else {
            statusLabel.setText("");
            statusLabel.setText("Mật khau xac nhan không đúng");
        }
    }
    @FXML
    private void handleCancel() {
        // Đóng cửa sổ hoặc thực hiện các hành động hủy bỏ
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
            statusLabel.setText("");
            statusLabel.setText(mess);
            // Hiển thị danh sách suất chiếu


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        currentPasswordField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();
    }
}
