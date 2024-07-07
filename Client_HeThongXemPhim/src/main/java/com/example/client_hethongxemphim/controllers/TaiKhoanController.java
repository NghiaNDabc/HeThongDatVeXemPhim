package com.example.client_hethongxemphim.controllers;

import com.example.client_hethongxemphim.models.User;
import com.example.client_hethongxemphim.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class TaiKhoanController {

    @FXML
    private Label labelFullName;

    @FXML
    private Label labelDob;
    private HomeController homeController;
    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
    public User user;
    // Phương thức để thiết lập thông tin người dùng
    public void setUser(User   user) {
        this.user = user;
        labelFullName.setText("Họ và tên: "+user.getFullName());
        labelDob.setText("Ngày sinh: "+user.getDob().toString());
    }

    @FXML
    private void handleManagePersonalInfo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/client_hethongxemphim/quanlythongtin.fxml"));
            Parent parent = loader.load();
            SuaThongTinController controller = loader.getController();
            controller.setUser(user);
            controller.setHomeController(homeController);
            controller.setTaiKhoanController(this);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Quản lý thông tin cá nhân");
            stage.setScene(scene);
            controller.setDialogStage(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Unable to load showtime page.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleChangePassword() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/client_hethongxemphim/changepass.fxml"));
            Parent parent = loader.load();
            ChangePasswordController changePasswordController = loader.getController();
            changePasswordController.setUser(user);
            changePasswordController.setHomeController(homeController);
//            controller.setHomeController(homeController);
//            controller.setTaiKhoanController(this);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Quản lý thông tin cá nhân");
            stage.setScene(scene);
//            controller.setDialogStage(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.showAlertError("Lỗi", "Lỗi khi chuyển màn hình");
        }
    }

    @FXML
    private void handleViewTickets() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/client_hethongxemphim/listticket.fxml"));
            Parent parent = loader.load();
            TicketViewController ticketViewController = loader.getController();
            ticketViewController.setUser(user);

//            controller.setHomeController(homeController);
//            controller.setTaiKhoanController(this);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Xem vé");
            stage.setScene(scene);
//            controller.setDialogStage(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.showAlertError("Lỗi", "Lỗi khi chuyển màn hình");
        }
    }

    @FXML
    private void handleLogout() {
        // Mã xử lý khi người dùng nhấn vào "Đăng xuất"
        // Ví dụ: Đóng cửa sổ hiện tại và quay lại màn hình đăng nhập
        Stage stage = (Stage) labelFullName.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleBack() {
        // Mã xử lý khi người dùng nhấn vào "Quay lại"
        // Ví dụ: Đóng cửa sổ hiện tại và quay lại màn hình trước đó
        Stage stage = (Stage) labelFullName.getScene().getWindow();
        stage.close();
    }
}
