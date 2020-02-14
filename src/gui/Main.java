package gui;

import com.sun.javafx.logging.Logger;
import db.DB_utility;
import javafx.application.Application;
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
        primaryStage.setTitle("LAB");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();

        Connection c = DB_utility.getConnection();

        //ResultSet rs = DB_utility.executeQuery("select * from test");
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