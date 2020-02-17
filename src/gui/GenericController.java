package gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.ResultSet;

public class GenericController {
    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public void updateStatus(String status){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                statusLabel.setText(status);
            }
        });
    }

    private ResultSet resultSet;

    @FXML
    private Label statusLabel;
}
