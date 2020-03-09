package gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;


public class E0_controller extends GenericController {
    private FXMLLoader loader;

    @FXML
    private VBox rootVBox;

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

    public void initialize(){ updateStatusLeft("");
    }

    public void gotoInternal(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("E1_internal.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
        window.setTitle("Usługa Wewnętrzna");
        window.show();
    }

    public void gotoExternal(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("E2_external.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
        window.setTitle("Usługa Zewnętrzna");
        window.show();
    }

    public void gotoTools(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("E3_tools.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
        window.setTitle("Narzędzia");
        window.show();
    }

    public void gotoMaterials(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("E4_materials.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
        //window.setResizable(true);
        window.setTitle("Materiały i Maszyny");
        window.show();
    }

    public void gotoTables(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("E5_tables.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
        window.setTitle("Edycja Tabel");
        window.show();
    }
}

