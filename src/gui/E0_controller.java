package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import javafx.scene.control.*;

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

    @FXML
    //private void load;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO : Odpalić connection do DB.
    }
}
