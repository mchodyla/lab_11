package gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class GenericController {
    @FXML
    private Label statusLabel;
    @FXML
    private Label statusLabel2;
    @FXML
    public Button b_cancel;

    private ResultSet resultSet;

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public void updateStatusLeft(String status){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                statusLabel.setText(status);
            }
        });
    }

    public void updateStatusRight(String status){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                statusLabel2.setText(status);
            }
        });
    }

    public Label getStatusLabelLeft() {
        return statusLabel;
    }

    public Label getStatusLabelRight() {
        return statusLabel2;
    }

    public void gotoMenu(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("E0_menu.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
        window.setResizable(false);
        window.setTitle("Menu");
        window.show();
    }
}
