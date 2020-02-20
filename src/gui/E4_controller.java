package gui;

import db.DB_utility;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import model.Location;
import model.Material;

import java.sql.SQLException;

public class E4_controller extends GenericController {

    @FXML
    public TableView<Material> tableView;
    @FXML
    public ListView<Material> listView;
    @FXML
    public TextField searchField;

    private ObservableList<Material> materialsList = FXCollections.observableArrayList();

    public void filterList(String oldValue, String newValue){
        ObservableList<Material> filteredList = FXCollections.observableArrayList();
        if(searchField == null || (newValue.length() < oldValue.length()) || newValue == " "){
            listView.setItems(materialsList);
        } else {
            for (Material m : listView.getItems()){
                String filterText = m.getResourceName();
                if (filterText.toUpperCase().contains(searchField.getText().toUpperCase())){
                    filteredList.add(m);
                }
            }
            listView.setItems(filteredList);
        }
    }

    public void initialize(){
        tableView.setPlaceholder(new Label("Opis wybranego materiału"));
        Platform.runLater(() -> listView.requestFocus());

        /** Initial DB Requests **/

        setResultSet(DB_utility.executeQuery("SELECT * FROM MATERIAL"));

        try{
            while(getResultSet().next()){
                Material material = new Material();
                material.setId(getResultSet().getInt("ID"));
                material.setName(getResultSet().getString("NAME"));
                material.setAmount(getResultSet().getString("AMOUNT"));
                material.setPurchase_date(getResultSet().getDate("PURCHASE_DATE"));
                material.setPrice(getResultSet().getFloat("PRICE"));
                material.setLocation(new Location(getResultSet().getString("LOCATION_ROOM"), getResultSet().getString("LOCATION_PLACE")));
                materialsList.add(material);
            }
        } catch (SQLException e){
            System.err.println("SQLException podczas ładowania wyniku tools!");
            updateStatusLeft("SQLException!");
        }

        /** UI setup **/

        searchField.textProperty().addListener((observableValue, s, t1) -> {
            filterList(s,t1);
        });

        listView.setCellFactory(materialListView -> new ListCell<>(){
            @Override
            protected void updateItem(Material item, boolean b) {
                super.updateItem(item, b);
                if(item==null){
                    setText(null);
                }else{
                    setText(item.getResourceName());
                }
            }
        });

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount() == 1 && listView.getSelectionModel().getSelectedItem() != null){
                    tableView.getItems().clear();
                    tableView.getItems().add(listView.getSelectionModel().getSelectedItem());
                }
            }
        });

        TableColumn<Material, String> idCol = new TableColumn<>("ID");
        TableColumn<Material, String> nameCol = new TableColumn<>("NAZWA");
        TableColumn<Material, String> amtCol = new TableColumn<>("ILOŚĆ JEDN.");
        TableColumn<Material, String> locationCol = new TableColumn<>("POŁOŻENIE");

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        amtCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        locationCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Material, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Material, String> data) {
                if (data != null){
                    return new ReadOnlyStringWrapper(data.getValue().getLocation().toString());
                } else return new ReadOnlyStringWrapper("");
            }
        });

        tableView.getColumns().add(idCol);
        tableView.getColumns().add(nameCol);
        tableView.getColumns().add(amtCol);
        tableView.getColumns().add(locationCol);

        idCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        nameCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));
        amtCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.25));
        locationCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.34));

        idCol.setResizable(false);
        nameCol.setResizable(false);
        amtCol.setResizable(false);
        locationCol.setResizable(false);

        listView.setItems(materialsList);
    }

}