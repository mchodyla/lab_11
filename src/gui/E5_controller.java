package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class E5_controller extends GenericController{

    @FXML
    public TableView tableView;

    public void initialize(){
        tableView.setPlaceholder(new Label("Dane z wybranej tabeli"));
    }
}