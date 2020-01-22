package gui;


import db.DB_utility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("E0_menu.fxml"));
        primaryStage.setTitle("LAB");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();

        /** Spaghetti code section **/

        ArrayList<String> columns = new ArrayList<>(Arrays.asList("K1","K2","K3"));

        Connection c = DB_utility.getConnection();
        ResultSet rs = DB_utility.executeQuery("select * from test");

        printResult(rs,columns);

        DB_utility.killConnection();
    }

    public void printResult(ResultSet rs, ArrayList<String> colNames) throws SQLException {
        ResultSetMetaData rsMetaData = rs.getMetaData();

        while (rs.next()){
            for(int i = 0; i< rsMetaData.getColumnCount(); i++){
                switch (rsMetaData.getColumnClassName(i))
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}