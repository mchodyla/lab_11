package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import db.DB_utility;
import model.Location;
import model.Tool;

import java.sql.SQLException;

public class E3_controller extends GenericController{

    @FXML
    public Button b_cancel;
    @FXML
    public TextField searchField;
    @FXML
    public ListView<Tool> leftListView;
    @FXML
    public ListView<String> rightListView;
    @FXML
    public TextArea descriptionArea;

    private ObservableList<Tool> toolsList = FXCollections.observableArrayList();

    public void gotoMenu(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("E0_menu.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 500, 300));
        window.show();
    }

    public void filterList(String oldValue, String newValue){
        System.out.println("Filtering!");

        ObservableList<Tool> filteredList = FXCollections.observableArrayList();
        if(searchField == null || (newValue.length() < oldValue.length()) || newValue == " ") {
            leftListView.setItems(toolsList);
        } else {
            for (Tool tool : leftListView.getItems()) {
                String filterText = tool.getUniqueName();
                if (filterText.toUpperCase().contains(searchField.getText().toUpperCase())) {
                    filteredList.add(tool);
                }
            }
            leftListView.setItems(filteredList);
        }
    }

    public void initialize(){
        System.out.println("This is E3 initializer speaking.");

        searchField.textProperty().addListener((observableValue, s, t1) -> {
            filterList(s,t1);
        });

        setResultSet(DB_utility.executeQuery("SELECT * FROM TOOL"));

        try {
            while (getResultSet().next()) {
                Tool tool = new Tool();
                tool.setId(getResultSet().getInt("ID"));
                tool.setName(getResultSet().getString("NAME"));
                tool.setPurchase_date(getResultSet().getDate("PURCHASE_DATE"));
                tool.setPrice(getResultSet().getFloat("PRICE"));
                tool.setLocation(new Location(getResultSet().getString("LOCATION_ROOM"),getResultSet().getString("LOCATION_PLACE")));
                toolsList.add(tool);
            }
        } catch (SQLException e){
            System.err.println("SQLException podczas ładowania wyniku tools!");
        }

        leftListView.setCellFactory(param -> new ListCell<>(){
            @Override
            protected void updateItem(Tool item, boolean b) {
                super.updateItem(item, b);
                if(item==null){
                    setText(null);
                }else {
                    setText(item.getUniqueName());
                }
            }
        });

        leftListView.setItems(toolsList);
    }
}