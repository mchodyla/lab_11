package db;

import java.sql.*;
import java.util.Properties;

public class DB_utility {

    private static Connection connection;
    private static Properties connectionProps;
    private static Statement statement;
    private static ResultSet resultSet;

    public static Connection getConnection() {
        connectionProps = new Properties();
        connectionProps.put("user", "inf127294");
        connectionProps.put("password", "pass");

        String url = "jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/" + "dblab02_students.cs.put.poznan.pl";

        try {
            connection = DriverManager.getConnection(url, connectionProps);
            statement = connection.createStatement();
            System.out.println("Połączono z bazą danych");
        } catch (Exception e) {
            System.err.println("Nie udało sie połączyć z bazą");
            e.printStackTrace();
            return null;
        }

        return connection;
    }

    public static void killConnection() throws SQLException {
        resultSet.close();
        statement.close();

        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static ResultSet executeQuery(String query){
        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            resultSet = null;
        } finally {
            return resultSet;
        }
    }
}
