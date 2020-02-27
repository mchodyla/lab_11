package gui;

import db.DB_utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.SQLException;

public class E5_controller extends GenericController{

    @FXML
    public TableView tableView;
    @FXML
    public ComboBox<String> comboBox;

    private ObservableList<String> tableList = FXCollections.observableArrayList();

    public void initialize(){

    /** Initial DB queries **/

        setResultSet(DB_utility.executeQuery("SELECT table_name FROM user_tables ORDER BY table_name"));

        try{
            while(getResultSet().next()){
                tableList.add(getResultSet().getString("table_name"));
            }
        } catch (SQLException e){
            System.err.println("SQLException podczas ładowania wyniku tools!");
            updateStatusLeft("SQLException!");
        }

        comboBox.setItems(tableList);

        tableView.setPlaceholder(new Label("Dane z wybranej tabeli"));
    }
}