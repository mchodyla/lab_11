package gui;

import db.DB_utility;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import model.*;

import java.sql.SQLException;

public class E4_controller extends GenericController {

    @FXML
    public TableView<Resource> tableView;
    @FXML
    public ListView<Resource> listView;
    @FXML
    public TextField searchField;
    @FXML
    public ComboBox<String> comboBox;
    @FXML
    public ComboBox<Location> locationBox;
    @FXML
    public TabPane tabPane;
    @FXML
    public Tab tabWarehouse;
    @FXML
    public Tab tabAdd;
    @FXML
    public Button b_add;

    private ObservableList<Resource> materialsList = FXCollections.observableArrayList();
    private ObservableList<Resource> machinesList = FXCollections.observableArrayList();
    private ObservableList<String> comboList = FXCollections.observableArrayList("Materiały","Maszyny");

    public void filterList(String oldValue, String newValue){
        ObservableList<Resource> filteredList = FXCollections.observableArrayList();
        if(searchField == null || (newValue.length() < oldValue.length()) || newValue == " "){
            listView.setItems(machinesList);
        } else {
            for (Resource m : listView.getItems()){
                String filterText = m.getResourceName();
                if (filterText.toUpperCase().contains(searchField.getText().toUpperCase())){
                    filteredList.add(m);
                }
            }
            listView.setItems(filteredList);
        }
    }

    public void initialize(){
        //todo: dodać możliwość agregowania materiałów po nazwie
        //TODO : wypełnienie dropdowna typów
        // TODO: DODAĆ USUWANIE
        // todo: zmiana kolumn w tabeli i opis

        // Utworzone w bazie danych podprogramy składowane mają być wywoływane
        //z chociaż jednego ekranu aplikacji (w przypadku funkcji jej wynik ma zostać
        //zaprezentowany w ramach danych ekranu).

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

        setResultSet(DB_utility.executeQuery("SELECT * FROM MACHINE"));

        try{
            while(getResultSet().next()){
                Machine machine = new Machine(
                        getResultSet().getInt("ID"),
                        getResultSet().getString("NAME"),
                        new Location(getResultSet().getString("LOCATION_ROOM"), getResultSet().getString("LOCATION_PLACE")));
                machinesList.add(machine);
            }
        } catch (SQLException e){
            System.err.println("SQLException podczas ładowania wyniku machines!");
            updateStatusLeft("SQLException!");
        }

        /** UI setup **/

        locationBox.setCellFactory(new Callback<ListView<Location>, ListCell<Location>>() {
            @Override
            public ListCell<Location> call(ListView<Location> locationListView) {
                return new ListCell<Location>(){
                    @Override
                    protected void updateItem(Location item, boolean b) {
                        super.updateItem(item, b);
                        if(item == null || b) {
                            setText("");
                        }else{
                            setText(item.toString());
                        }
                    }
                };
            }
        });

        setResultSet(DB_utility.executeQuery("select room, place from location left outer join tool on (location.room = tool.location_room and location.place=tool.location_place) left outer join machine on (location.room = machine.location_room and location.place=machine.location_place) left outer join material on (location.room = material.location_room and location.place=material.location_place) where tool.name is null and machine.name is null and material.name is null order by room"));

        try {
            while (getResultSet().next()) {
                Location location = new Location(
                        getResultSet().getString("ROOM"),
                        getResultSet().getString("PLACE"));
                locationBox.getItems().add(location);
            }
        } catch (SQLException e){
            System.err.println("SQLException podczas ładowania wyniku tools!");
            updateStatusLeft("SQLException!");
        }

        b_add.setDisable(true);

        tableView.setPlaceholder(new Label("Opis wybranego materiału"));
        Platform.runLater(() -> listView.requestFocus());

        comboBox.setItems(comboList);
        comboBox.getSelectionModel().selectFirst();
        comboBox.valueProperty().addListener((observableValue, s, t1) -> {
            if(t1 != null){
                if( t1 == "Materiały"){
                    listView.setItems(materialsList);
                }else if (t1 == "Maszyny"){
                    listView.setItems(machinesList);
                }
            }
        });

        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> observableValue, Tab t, Tab t1) {
                        if(t1.getText().equals(tabWarehouse.getText())) {
                            /** WAREHOUSE **/
                            b_add.setVisible(false);
                        }
                        if(t1.getText().equals(tabAdd.getText())){
                            /** ADD **/
                            b_add.setVisible(true);
                        }
                    }
                }
        );


        getStatusLabelLeft().setVisible(false);

        searchField.textProperty().addListener((observableValue, s, t1) -> {
            filterList(s,t1);
        });

        listView.setCellFactory(resourceListView -> new ListCell<>(){
            @Override
            protected void updateItem(Resource resource, boolean b) {
                super.updateItem(resource, b);
                setText(b ? null : resource.getResourceName());
            }
        });

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount() == 1 && listView.getSelectionModel().getSelectedItem() != null){
                    tableView.getItems().clear();
                    if(comboBox.getSelectionModel().getSelectedItem().equals("Materiały")){

                    } else if (comboBox.getSelectionModel().getSelectedItem().equals("Maszyny")){

                    }
                }
            }
        });

        TableColumn<Resource, String> idCol = new TableColumn<>("ID");
        TableColumn<Resource, String> nameCol = new TableColumn<>("NAZWA");
        TableColumn<Resource, String> amtCol = new TableColumn<>("ILOŚĆ JEDN.");
        TableColumn<Resource, String> locationCol = new TableColumn<>("POŁOŻENIE");

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        amtCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        locationCol.setCellValueFactory(resourceStringCellDataFeatures -> {
            return null;
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