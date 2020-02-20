package gui;

import db.DB_utility;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import model.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class E1_controller extends GenericController{

    @FXML
    public Label dateLabel;
    @FXML
    public Button b_save;
    @FXML
    public ComboBox<Employee> providerBox;
    @FXML
    public ComboBox<String> serviceBox;
    @FXML
    public ComboBox<String> resourceBox;
    @FXML
    public TextField searchField;
    @FXML
    public ListView<Resource> leftListView;
    @FXML
    public ListView<Resource> rightListView;
    @FXML
    public TextArea descriptionArea;

    private ObservableList<Employee> providerEmployees = FXCollections.observableArrayList();
    private ObservableList<String> serviceTypes = FXCollections.observableArrayList();
    private ObservableList<String> resourceTypes = FXCollections.observableArrayList(Arrays.asList("Maszyny","Materiały","Narzędzia"));

    private ObservableList<Resource> machinesList = FXCollections.observableArrayList();
    private ObservableList<Resource> materialsList = FXCollections.observableArrayList();
    private ObservableList<Resource> toolsList = FXCollections.observableArrayList();

    private ObservableList<Resource> resourcesList;
    private ObservableList<Resource> selectedResourcesList = FXCollections.observableArrayList();

    private final int MAX_CHARS = 300;

    public void filterResources(String oldValue, String newValue){

        ObservableList<Resource> filteredList = FXCollections.observableArrayList();
        if(searchField == null || (newValue.length() < oldValue.length()) || newValue == " ") {
            leftListView.setItems(resourcesList);
        } else {
            for (Resource resource : leftListView.getItems()) {
                String filterText = resource.getResourceName();
                if (filterText.toUpperCase().contains(searchField.getText().toUpperCase())) {
                    filteredList.add(resource);
                }
            }
            leftListView.setItems(filteredList);
        }
    }

    public void initialize(){

    /** Initial DB queries **/

        setResultSet(DB_utility.executeQuery("SELECT * FROM EMPLOYEE"));

        try{
            while (getResultSet().next()){
                Employee employee = new Employee();
                employee.setName(getResultSet().getString("NAME"));
                employee.setEmail(getResultSet().getString("EMAIL"));
                employee.setDepartmentName(getResultSet().getString("DEPARTMENT_NAME"));
                providerEmployees.add(employee);
            }
        }catch(SQLException e){
            System.err.println("SQLException podczas ładowania wyniku EMPLOYEE!");
            updateStatusLeft("SQLException!");
        }

        setResultSet(DB_utility.executeQuery("SELECT * FROM SERVICE_TYPE"));

        try{
            while (getResultSet().next()){
                String serviceType = getResultSet().getString("SERVICE_TYPE");
                serviceTypes.add(serviceType);
            }
        }catch(SQLException e){
            System.err.println("SQLException podczas ładowania wyniku SERVICE_TYPE!");
            updateStatusLeft("SQLException!");
        }

        setResultSet(DB_utility.executeQuery("SELECT * FROM MACHINE"));

        try {
            while (getResultSet().next()) {
                Machine machine = new Machine();
                machine.setId(getResultSet().getInt("ID"));
                machine.setName(getResultSet().getString("NAME"));
                machine.setLocation(new Location(getResultSet().getString("LOCATION_ROOM"),getResultSet().getString("LOCATION_PLACE")));
                machinesList.add(machine);
            }
            updateStatusLeft("");
        } catch (SQLException e){
            System.err.println("SQLException podczas ładowania wyniku MACHINE!");
            updateStatusLeft("SQLException!");
        }

        setResultSet(DB_utility.executeQuery("SELECT * FROM MATERIAL"));

        try {
            while (getResultSet().next()) {
                Material material = new Material();
                material.setId(getResultSet().getInt("ID"));
                material.setName(getResultSet().getString("NAME"));
                material.setAmount(getResultSet().getString("AMOUNT"));
                material.setPurchase_date(getResultSet().getDate("PURCHASE_DATE"));
                material.setLocation(new Location(getResultSet().getString("LOCATION_ROOM"),getResultSet().getString("LOCATION_PLACE")));
                materialsList.add(material);
            }
            updateStatusLeft("");
        } catch (SQLException e){
            System.err.println("SQLException podczas ładowania wyniku MATERIAL!");
            updateStatusLeft("SQLException!");
        }

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
            updateStatusLeft("");
        } catch (SQLException e){
            System.err.println("SQLException podczas ładowania wyniku TOOL!");
            updateStatusLeft("SQLException!");
        }

    /** UI setup :
        DATE, DESCRIPTION AREA, COMBO BOXES **/

        updateStatusLeft("Data wykonania usługi: ");
        updateStatusRight(new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Timestamp(System.currentTimeMillis())));

        descriptionArea.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= MAX_CHARS ? change : null));

        resourceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue == null){
                searchField.setDisable(true);
                searchField.setText("");
                System.out.println(resourceBox.getSelectionModel().getSelectedItem());
            }
            else {
                System.out.println(resourceBox.getSelectionModel().getSelectedItem());
                searchField.setDisable(false);
                searchField.setText("");
                switch (resourceBox.getSelectionModel().getSelectedItem()){
                    case "Maszyny":
                        resourcesList = machinesList;
                        break;
                    case "Materiały":
                        resourcesList = materialsList;
                        break;
                    case "Narzędzia":
                        resourcesList = toolsList;
                        break;
                    default:
                        break;
                }
                leftListView.setItems(resourcesList);
            }
        });
        resourceBox.setItems(resourceTypes);

        providerBox.setCellFactory(new Callback<ListView<Employee>, ListCell<Employee>>() {
            @Override
            public ListCell<Employee> call(ListView<Employee> employeeListView) {
                return new ListCell<Employee>(){
                    @Override
                    protected void updateItem(Employee item, boolean b) {
                        super.updateItem(item, b);
                        if(item == null || b) {
                            setText("");
                        }else{
                            setText(item.getName());
                        }
                    }
                };
            }
        });
        providerBox.setButtonCell(new ListCell<>(){
            @Override
            protected void updateItem(Employee employee, boolean b) {
                super.updateItem(employee, b);
                if(employee != null){
                    setText(employee.getName());
                }
            }
        });
        providerBox.setItems(providerEmployees);

        serviceBox.setItems(serviceTypes);

    /** UI setup :
        LISTVIEWS **/

        leftListView.setCellFactory(param -> new ListCell<>(){
            @Override
            protected void updateItem(Resource resource, boolean b) {
                super.updateItem(resource, b);
                if(resource == null || b) {
                    setText("");
                }else{
                    setText(resource.getResourceName());
                }
            }
        });

        rightListView.setCellFactory(param -> new ListCell<>(){
            @Override
            protected void updateItem(Resource resource, boolean b) {
                super.updateItem(resource, b);
                if(resource == null || b) {
                    setText("");
                }else{
                    setText(resource.getResourceName());
                }
            }
        });

        leftListView.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount() == 2 && leftListView.getSelectionModel().getSelectedItem() != null){
                selectedResourcesList.add(leftListView.getSelectionModel().getSelectedItem());
                leftListView.getItems().remove(leftListView.getSelectionModel().getSelectedItem());
                rightListView.setItems(selectedResourcesList);
            }
        });

        rightListView.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount() == 2 && rightListView.getSelectionModel().getSelectedItem() != null){
                if(rightListView.getSelectionModel().getSelectedItem() instanceof Machine){
                    machinesList.add(rightListView.getSelectionModel().getSelectedItem());
                } else if(rightListView.getSelectionModel().getSelectedItem() instanceof Material){
                    materialsList.add(rightListView.getSelectionModel().getSelectedItem());
                } else if(rightListView.getSelectionModel().getSelectedItem() instanceof Tool){
                    toolsList.add(rightListView.getSelectionModel().getSelectedItem());
                }
                selectedResourcesList.remove(rightListView.getSelectionModel().getSelectedItem());
            }
        });

        Label placeholderLeft = new Label("Wyszukiwarka");
        Label placeholderRight = new Label("Wykorzystane zasoby");

        leftListView.setPlaceholder(placeholderLeft);
        rightListView.setPlaceholder(placeholderRight);

    /** UI setup :
        FILTERING **/

        searchField.textProperty().addListener((observableValue, s, t1) -> {
            filterResources(s,t1);
        });

        //TODO: po wybraniu pracownika z jednej z list usunąć go z drugiej i vice versa
        //TODO: Odblokować guzik "Zapis" wtw gdy : (provider, consumer, service type) są wybrane !

        b_save.disableProperty().bind(new ObservableBooleanValue() {
            @Override
            public boolean get() {
                return false;
            }

            @Override
            public void addListener(ChangeListener<? super Boolean> changeListener) {

            }

            @Override
            public void removeListener(ChangeListener<? super Boolean> changeListener) {

            }

            @Override
            public Boolean getValue() {
                return null;
            }

            @Override
            public void addListener(InvalidationListener invalidationListener) {

            }

            @Override
            public void removeListener(InvalidationListener invalidationListener) {

            }
        });

    }
}
