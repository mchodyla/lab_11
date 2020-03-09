package gui;

import db.DB_utility;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
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
    public Button b_save;
    @FXML
    public ComboBox<Employee> providerBox;
    @FXML
    public ComboBox<ServiceType> serviceBox;
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

    private ObservableList<Employee> providerEmployees = Employee.getListFromTable();
    private ObservableList<ServiceType> serviceTypes = ServiceType.getListFromTable();
    private ObservableList<String> resourceTypes = FXCollections.observableArrayList(Arrays.asList("Maszyny","Materiały","Narzędzia"));

    private ObservableList<Resource> machinesList = FXCollections.observableArrayList(Machine.getListFromTable());
    private ObservableList<Resource> materialsList = FXCollections.observableArrayList(Material.getListFromTable());
    private ObservableList<Resource> toolsList = FXCollections.observableArrayList(Tool.getListFromTable());

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

    public void saveReport(){
        System.out.println("ZAPISZ WYKONANIE USŁUGI WEWNĘTRZNEJ : ");
        System.out.println("Provider : " + providerBox.getSelectionModel().getSelectedItem().getName());
        System.out.println("Data : " + this.getStatusLabelRight().getText());
        System.out.println("Typ : " + serviceBox.getSelectionModel().getSelectedItem().getName());
        System.out.println("Opis : " + descriptionArea.getText());
        System.out.println("Wykorzystane zasoby : ");
        for (Resource r : rightListView.getItems()){
            System.out.println(r.getResourceName());
        }
    }

    public void initialize(){

    /** UI setup :
        DATE, DESCRIPTION AREA, COMBO BOXES **/

        updateStatusLeft("Data: ");
        updateStatusRight(new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Timestamp(System.currentTimeMillis())));
        getStatusLabelRight().setVisible(true);

        descriptionArea.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= MAX_CHARS ? change : null));

        resourceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue == null){
                searchField.setDisable(true);
                searchField.setText("");
            }
            else {
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

        serviceBox.setCellFactory(new Callback<ListView<ServiceType>, ListCell<ServiceType>>() {
            @Override
            public ListCell<ServiceType> call(ListView<ServiceType> serviceTypeListView) {
                return new ListCell<>(){
                    @Override
                    protected void updateItem(ServiceType serviceType, boolean b) {
                        super.updateItem(serviceType, b);
                        if(serviceType==null || b){
                            setText("");
                        }else {
                            setText(serviceType.getName());
                        }
                    }
                };
            }
        });
        serviceBox.setButtonCell(new ListCell<>(){
            @Override
            protected void updateItem(ServiceType serviceType, boolean b) {
                super.updateItem(serviceType, b);
                if(serviceType != null){
                    setText(serviceType.getName());
                }
            }
        });
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

        Label placeholderLeft = new Label("     Wyszukiwarka\n(wybierz typ zasobu)");
        Label placeholderRight = new Label("     Wykorzystane zasoby\n(dodawanie z wyszukiwarki)");

        leftListView.setPlaceholder(placeholderLeft);
        rightListView.setPlaceholder(placeholderRight);

    /** UI setup :
        FILTERING **/

        searchField.textProperty().addListener((observableValue, s, t1) -> {
            filterResources(s,t1);
        });

    /** Save button bindings **/
        //b_save.disableProperty().bind(providerBox.valueProperty().isEqualTo(providerBox.placeholderProperty()));

        b_save.disableProperty().bind(
                Bindings.or(
                        providerBox.valueProperty().isEqualTo(providerBox.placeholderProperty()),
                        serviceBox.valueProperty().isEqualTo(serviceBox.placeholderProperty())));

    }
}
