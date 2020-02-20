package gui;

import com.sun.javafx.logging.Logger;
import db.DB_utility;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("E0_menu.fxml"));
        primaryStage.setTitle("Menu");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        if(DB_utility.getConnection() == null){
            Platform.exit();
            System.exit(0);
        };
    }

    @Override
    public void stop() throws Exception {
        System.out.println("App is closing...");
        DB_utility.killConnection();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}