package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.DataBaseEntity;

import java.sql.*;
import java.util.Properties;

public class DB_utility {

    private static Connection connection;
    private static Properties connectionProps;
    private static Statement statement;
    private static ResultSet resultSet;
    private static int timeout = 5;

    public static Connection getConnection(){
        connectionProps = new Properties();
        connectionProps.put("user", "inf127294");
        connectionProps.put("password", "pass");

        DriverManager.setLoginTimeout(timeout);
        String url = "jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/" + "dblab02_students.cs.put.poznan.pl";
        System.out.println("Trying to connect to DB with " + timeout + " sec timeout...");

        try {
            connection = DriverManager.getConnection(url, connectionProps);
            System.out.println("Połączono z bazą danych");
        } catch (SQLException e){
            System.err.println("Nie udało sie połączyć z bazą");
            return null;
        }

        return connection;
    }

    public static void killConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("DB connection closed!");
            }
        } catch(SQLException e){
            System.err.println("Could not close DB connection!");
        }
    }

    public static ResultSet executeQuery(String query) {
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch(SQLException e){
            System.err.println("Error executing query!");
            System.err.println(e);
        }
        return resultSet;
    }

    /*
    public static <T extends DataBaseEntity> ObservableList<T> getTableToList(T model){
        ObservableList<T> output = FXCollections.observableArrayList();
        executeQuery("SELECT * FROM " + model.getTableName());
        try{
            ResultSetMetaData md = resultSet.getMetaData();
            while(resultSet.next()){
                T element = new T();
            }
        }catch (SQLException e){
        }
        return null;
    }

     */
}
