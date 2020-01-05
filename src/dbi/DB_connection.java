package dbi;

import oracle.jdbc.OracleDriver;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB_connection {

    public Connection connection;

    public Connection getConnection(){
        String dbName="";
        String userName="";
        String password="";

        //TODO: wypelnic passami i sprawdzic polaczenie

        try {
            Class.forName("oracle.jdbc.OracleDriver");

            connection= DriverManager.getConnection(""+dbName+userName+password);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}
