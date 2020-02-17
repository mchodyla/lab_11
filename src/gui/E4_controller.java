package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

public class E4_controller {

    @FXML
    public Button b_cancel;

    public void gotoMenu(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("E0_menu.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
        window.show();
    }
}