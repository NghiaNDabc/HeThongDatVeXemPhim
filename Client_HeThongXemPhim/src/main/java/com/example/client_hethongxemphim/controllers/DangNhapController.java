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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class DangNhapController {
    @FXML
    Button btnDangNhap;
    @FXML
    Button btnDangKy;

    @FXML
    TextField txtTenDangNhap;
    @FXML
    PasswordField txtMatKhau;
    @FXML
    Label labelThongBao;

    boolean checkText(){
        if(txtTenDangNhap.getText().isEmpty() || txtMatKhau.getText().isEmpty()){
            return false;
        }
        return true;
    }
    public User DangNhap(String username, String password){

        if(checkText() == false){
            AlertUtil.showAlertError("Null", "Không được để trống");
            return null ;
        }
        User user =null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try{
            Socket client =  new Socket("localhost",8888);
            GoiTin<User> tin =  new GoiTin<>();
            User check = new User();
            check.setUserName(username);
            check.setPassWord(password);
            tin.setYeuCau(YeuCau.DangNhap);
            ArrayList<User> list = new ArrayList<>();
            list.add(check);
            tin.setListT(list);

            Gson gson = new Gson();
            String json = gson.toJson(tin);
            System.out.println(json);
            PrintWriter printWriter =  new PrintWriter(client.getOutputStream(),true);
            printWriter.println(json);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String responseJson = in.readLine();
            user = gson.fromJson(responseJson, User.class);
            printWriter.close();
            ois.close();
            client.close();
        }catch (Exception e){

        }
        if (user != null){
            return user;
        }
        AlertUtil.showAlertError("Đăng nhập thất bại", "Sai tên đăng nhập hoặc mật khẩu");
        return null;
    }

    @FXML
    public void btnDangKyClick(ActionEvent event){
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/com/example/client_hethongxemphim/DangKy.fxml"));
        try {
            Parent dangKyRoot = loader.load();
            Scene dangKyScene = new Scene(dangKyRoot);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(dangKyScene);
            window.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
   public void btnDangNhapClick(ActionEvent event) throws IOException {

       String username =txtTenDangNhap.getText();
       String password = txtMatKhau.getText();//HashUtil.sha256(
       User user = DangNhap(username, password);
       System.out.println(user);
       if(user != null){
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/client_hethongxemphim/Home.fxml"));
           Parent homeRoot = loader.load();
            HomeController homeController = loader.getController();
            homeController.setUser(user);

           // Tạo Scene mới
           Scene homeScene = new Scene(homeRoot);

           // Lấy Stage hiện tại từ sự kiện
           Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

           // Đặt Scene mới cho Stage
           currentStage.setScene(homeScene);
           currentStage.show();
       }
    }
}
