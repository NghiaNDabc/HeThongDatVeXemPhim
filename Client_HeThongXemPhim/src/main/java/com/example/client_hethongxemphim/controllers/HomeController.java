package com.example.client_hethongxemphim.controllers;

import com.example.client_hethongxemphim.models.GenreClient;
import com.example.client_hethongxemphim.models.MovieClient;
import com.example.client_hethongxemphim.models.User;
import com.example.client_hethongxemphim.socket.GoiTin;
import com.example.client_hethongxemphim.socket.YeuCau;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    List<MovieClient> movieClients;
    @FXML
    private BorderPane root;
    @FXML
    private VBox content;
    @FXML
    private Label labelUserFullname;
    private User user;
    @FXML
    private TextField txtTimKiem;
    @FXML
    private Button btnDangXuat;
    @FXML
    private Button btnTaiKhoan;
    static  String pathServer= "";
    private static ArrayList<GenreClient> listgenre = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ViewHienThiPhim();
    }

    @FXML
    void TimKiem(KeyEvent actionEvent) {
        if (txtTimKiem.getText().equals("")) {
            ViewHienThiPhim();
            return;
        }
        ArrayList<MovieClient> timKiem = new ArrayList<>();
        for (MovieClient movieClient : movieClients) {
            if (movieClient.getMovieName().toLowerCase().contains(txtTimKiem.getText().toLowerCase())) {
                timKiem.add(movieClient);
            }
        }
        content.getChildren().clear();
        for (MovieClient movieClient : timKiem) {
            content.getChildren().add(MovieBox(movieClient));
        }
    }

    public void setUser(User user) {
        this.user = user;
        labelUserFullname.setText("Xin chào, " + user.getFullName());
    }

    void ViewHienThiPhim() {
        movieClients = getListMovie2();
        content.getChildren().clear();
        for (MovieClient movieClient : movieClients) {
            content.getChildren().add(MovieBox(movieClient));
        }
    }
    String getGenreName(int id){
        for(GenreClient genreClient : listgenre){
            if (genreClient.getId_Genre() == id) return  genreClient.getGenreName();
        }
        return null;
    }
    HBox MovieBox(MovieClient movieClient) {
        HBox movieBox = new HBox();
        movieBox.setSpacing(10);
        movieBox.setStyle("-fx-border-color: black; -fx-padding: 10px;");

        ImageView movieImageView = new ImageView();
        movieImageView.setFitHeight(180);
        movieImageView.setFitWidth(100);
        //String imagePath = pathServer + movieClient.getImage();
        String userdir = System.getProperty("user.dir");
        String imagePath = "/com/example/client_hethongxemphim/images/" + movieClient.getImage();
        try {
            Image movieImage = new Image(getClass().getResourceAsStream(imagePath));
            movieImageView.setImage(movieImage);
        } catch (Exception e) {
            System.out.println("Error loading image: " + imagePath);
            //e.printStackTrace();
        }

        VBox movieInfo = new VBox();
        movieInfo.setSpacing(5);

        Label titleLabel = new Label(movieClient.getMovieName());
        titleLabel.getStyleClass().add("title");
        Label descriptionLabel = new Label(movieClient.getDescripsion());
        descriptionLabel.getStyleClass().add("description");
        Label TheLaoai = new Label("Thể loại: " + getGenreName(movieClient.getId_Genre()));
        TheLaoai.getStyleClass().add("duration");
        Label durationLabel = new Label("Thời lượng: " + movieClient.getDuration() + " minutes");
        durationLabel.getStyleClass().add("duration");
        Label ratingLabel = new Label("Quốc gia: " + movieClient.getCountry());
        ratingLabel.getStyleClass().add("rating");

        Button bookButton = new Button("Đặt vé");
        bookButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/client_hethongxemphim/ShowTime.fxml"));
                Parent showtimeRoot = loader.load();
                ShowTimeController showtimeController = loader.getController();
                showtimeController.setSelectedMovie(movieClient);
                showtimeController.setUser(user);
                showtimeController.setHomeController(this);
                Scene showtimeScene = new Scene(showtimeRoot);

                Stage currentStage = new Stage();
                currentStage.setTitle("Chọn suất chiếu");
                currentStage.setScene(showtimeScene);
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Unable to load showtime page.");
                alert.showAndWait();
            }
        });
        movieInfo.getChildren().addAll(titleLabel, descriptionLabel,TheLaoai, durationLabel, ratingLabel, bookButton);
        movieBox.getChildren().addAll(movieImageView, movieInfo);
        return movieBox;
    }

    public static ArrayList<MovieClient> getListMovie() {
        ArrayList<MovieClient> list = null;
        String imageFolderPath =pathServer;
        Path imageFolderPathPath = Paths.get(imageFolderPath);

        if (!Files.exists(imageFolderPathPath)) {
            try {
                Files.createDirectories(imageFolderPathPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Files.walk(imageFolderPathPath)
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Socket socket = new Socket("localhost", 8888);
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {

            GoiTin<MovieClient> goiTin = new GoiTin<>(null, YeuCau.XemDanhSachPhim);
            Gson gson = new Gson();
            String json = gson.toJson(goiTin);
            printWriter.println(json);

            String responseJson = in.readLine();
            list = gson.fromJson(responseJson, new TypeToken<ArrayList<MovieClient>>(){}.getType());

            while (true) {
                String fileName = dis.readUTF();
                if (fileName.equals("END")) break;
                int fileLength = dis.readInt();
                byte[] fileContent = new byte[fileLength];
                dis.readFully(fileContent);

                try (FileOutputStream fos = new FileOutputStream(imageFolderPath + "/" + fileName)) {
                    fos.write(fileContent);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<MovieClient> getListMovie2() {
        ArrayList<MovieClient> list = null;
        try (Socket socket = new Socket("localhost", 8888);
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            GoiTin<MovieClient> goiTin = new GoiTin<>(null, YeuCau.XemDanhSachPhim);
            Gson gson = new Gson();
            String json = gson.toJson(goiTin);
            printWriter.println(json);
            String impath = in.readLine();
            System.out.println("Path server : "+ impath);
            pathServer = impath;
            String responseJson = in.readLine();
            Type listType = new TypeToken<ArrayList<MovieClient>>(){}.getType();
            list = gson.fromJson(responseJson, listType);
            String responseJson2 = in.readLine();
            Type listType2 = new TypeToken<ArrayList<GenreClient>>(){}.getType();
            listgenre = gson.fromJson(responseJson2, listType2);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @FXML
    public  void btnTaiKhoanClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/client_hethongxemphim/Account.fxml"));
        Parent accRoot = loader.load();

        TaiKhoanController taiKhoanController = loader.getController();
        taiKhoanController.setUser(this.user);
        taiKhoanController.setHomeController(this);
        Scene scene = new Scene(accRoot);

        Stage stage = new Stage();
        stage.setTitle("Tài khoản");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void btnDangXuatClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/client_hethongxemphim/DangNhap.fxml"));
        Parent loginRoot = loader.load();
        Scene homeScene = new Scene(loginRoot);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(homeScene);
        currentStage.show();
    }
}
