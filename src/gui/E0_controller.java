package gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class E0_controller implements Initializable {

    @FXML
    private VBox rootVBox;

    @FXML
    private Label l_status;

    @FXML
    private Button b_internal;

    @FXML
    private Button b_external;

    @FXML
    private Button b_tools;

    @FXML
    private Button b_materials;

    @FXML
    private Button b_tables;

    private StringProperty statusProperty = new SimpleStringProperty("Status");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO : Odpalić connection do DB.
        statusProperty.addListener((observableValue, oldValue, newValue) -> {
            setStatusText(newValue);
        });
    }

    public void setStatusText(String newStatus){
        this.l_status.setText(newStatus);
    }

    public void gotoInternal(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("E1_internal.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 500, 300));
        window.show();
    }

    public void gotoExternal(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("E2_external.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 500, 300));
        window.show();
    }

    public void gotoTools(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("E3_tools.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 500, 300));
        window.show();
    }

    public void gotoMaterials(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("E4_materials.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 500, 300));
        window.show();
    }

    public void gotoTables(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("E5_tables.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 500, 300));
        window.show();
    }
}

