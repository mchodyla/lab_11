package gui;

import db.DB_utility;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Employee;
import model.Location;
import model.Tool;

import java.sql.SQLException;

public class E3_controller extends GenericController{

    @FXML
    public Button b_cancel;
    @FXML
    public Button b_rent;
    @FXML
    public Button b_return;
    @FXML
    public TextField searchField;
    @FXML
    public ListView<Tool> leftListView;
    @FXML
    public ListView<Tool> rightListView;
    @FXML
    public TableView descriptionTable;
    @FXML
    public ComboBox<Employee> comboBox;
    @FXML
    public RadioButton radioButtonReturning;
    @FXML
    public RadioButton radioButtonRenting;

    private ObservableList<Tool> toolsList = FXCollections.observableArrayList();
    private ObservableList<Tool> selectedToolsList = FXCollections.observableArrayList();
    private ObservableList<Employee> employees = FXCollections.observableArrayList();

    public void gotoMenu(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("E0_menu.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
        window.show();
    }

    public void filterList(String oldValue, String newValue){

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

        updateStatus("Sending SQL query...");

     /** Initial DB queries **/

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
            updateStatus("");
        } catch (SQLException e){
            System.err.println("SQLException podczas ładowania wyniku tools!");
            updateStatus("SQLException!");
        }

        setResultSet(DB_utility.executeQuery("SELECT * FROM EMPLOYEE"));

        try{
            while (getResultSet().next()){
                Employee employee = new Employee();
                employee.setName(getResultSet().getString("NAME"));
                employee.setEmail(getResultSet().getString("EMAIL"));
                employee.setDepartmentName(getResultSet().getString("DEPARTMENT_NAME"));
                employees.add(employee);
            }
        }catch(SQLException e){
            System.err.println("SQLException podczas ładowania wyniku tools!");
            updateStatus("SQLException!");
        }

        searchField.textProperty().addListener((observableValue, s, t1) -> {
            filterList(s,t1);
        });

    /** UI setup :
        COMBOBOX, BUTTONS **/

        b_rent.disableProperty().bind(radioButtonReturning.selectedProperty());
        b_return.disableProperty().bind(radioButtonRenting.selectedProperty());

        comboBox.disableProperty().bind(radioButtonReturning.selectedProperty());
        comboBox.setCellFactory(new Callback<ListView<Employee>, ListCell<Employee>>() {
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
        comboBox.setButtonCell(new ListCell<>(){
            @Override
            protected void updateItem(Employee employee, boolean b) {
                super.updateItem(employee, b);
                if(employee != null){
                    setText(employee.getName());
                }
            }
        });
        comboBox.setItems(employees);

    /** UI setup :
        TABLEVIEW **/

        descriptionTable.setPlaceholder(new Label("Opis wybranego narzędzia"));

        TableColumn<Tool, String> idColumn = new TableColumn<>("ID");
        TableColumn<Tool, String> nameColumn = new TableColumn<>("NAZWA");
        TableColumn<Tool, String> locationColumn = new TableColumn<>("POŁOŻENIE");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        locationColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tool, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tool, String> data) {
                if(data!= null){
                    return new ReadOnlyStringWrapper(data.getValue().getLocation().toString());
                } else return new ReadOnlyStringWrapper("");
            }
        });

        descriptionTable.getColumns().add(idColumn);
        descriptionTable.getColumns().add(nameColumn);
        descriptionTable.getColumns().add(locationColumn);

    /** UI setup :
        LISTVIEWS **/

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

        rightListView.setCellFactory(param -> new ListCell<>(){
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

        leftListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount() == 1 && leftListView.getSelectionModel().getSelectedItem() != null){
                    descriptionTable.getItems().clear();
                    descriptionTable.getItems().add(leftListView.getSelectionModel().getSelectedItem());
                }
                if(mouseEvent.getClickCount() == 2 && leftListView.getSelectionModel().getSelectedItem() != null){
                    selectedToolsList.add(leftListView.getSelectionModel().getSelectedItem());
                    toolsList.remove(leftListView.getSelectionModel().getSelectedItem());
                    rightListView.setItems(selectedToolsList);
                }
            }
        });

        rightListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount() == 1 && rightListView.getSelectionModel().getSelectedItem() != null){
                    descriptionTable.getItems().clear();
                    descriptionTable.getItems().add(rightListView.getSelectionModel().getSelectedItem());
                }
                if(mouseEvent.getClickCount() == 2 && rightListView.getSelectionModel().getSelectedItem() != null){
                    toolsList.add(rightListView.getSelectionModel().getSelectedItem());
                    selectedToolsList.remove(rightListView.getSelectionModel().getSelectedItem());
                    leftListView.setItems(toolsList);
                }
            }
        });

        leftListView.setItems(toolsList);
        rightListView.setItems(selectedToolsList);
    }
}