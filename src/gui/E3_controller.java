package gui;

import db.DB_utility;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
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
import model.BorrowRecord;
import model.Employee;
import model.Location;
import model.Tool;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class E3_controller extends GenericController{

    @FXML
    public Button b_rent;
    @FXML
    public Button b_return;
    @FXML
    public TextField searchField;
    @FXML
    public TextField toolNameField;
    @FXML
    public TextField toolPriceField;
    @FXML
    public ListView<Tool> leftListView;
    @FXML
    public ListView<Tool> rightListView;
    @FXML
    public TableView<Tool> descriptionTable;
    @FXML
    public TableView<BorrowRecord> historyTable;
    @FXML
    public ComboBox<Employee> comboBox;
    @FXML
    public ComboBox<Location> locationBox;
    @FXML
    public RadioButton radioButtonReturning;
    @FXML
    public RadioButton radioButtonRenting;
    @FXML
    public TabPane tabPane;
    @FXML
    public Tab tabRent;
    @FXML
    public Tab tabHistory;
    @FXML
    public Tab tabAdd;
    @FXML
    public DatePicker datePicker;

    private ObservableList<Tool> toolsList = FXCollections.observableArrayList();
    private ObservableList<Tool> selectedToolsList = FXCollections.observableArrayList();
    private ObservableList<Employee> employees = FXCollections.observableArrayList();
    private ObservableList<Location> locations = FXCollections.observableArrayList();
    //private ObservableList<BorrowRecord> borrowRecords = FXCollections.observableArrayList();

    public void filterList(String oldValue, String newValue){

        ObservableList<Tool> filteredList = FXCollections.observableArrayList();
        if(searchField == null || (newValue.length() < oldValue.length()) || newValue == " ") {
            leftListView.setItems(toolsList);
        } else {
            for (Tool tool : leftListView.getItems()) {
                String filterText = tool.getResourceName();
                if (filterText.toUpperCase().contains(searchField.getText().toUpperCase())) {
                    filteredList.add(tool);
                }
            }
            leftListView.setItems(filteredList);
        }
    }

    public void rentTools(){
        //Renting method
        if(comboBox.getSelectionModel().getSelectedItem() != null && !rightListView.getItems().isEmpty()) {
            Employee e = comboBox.getSelectionModel().getSelectedItem();
            ArrayList<Tool> tools = new ArrayList<Tool>(rightListView.getItems());

            System.out.println("Employee : " + e.getName());
            System.out.println("Tools to rent: ");
            for (Tool t : tools) {
                System.out.println(t.getResourceName());
            }
        }
    }

    public void rightButtonMethod(){
        if(tabPane.getSelectionModel().getSelectedItem().getText().equals(tabRent.getText())){
            //Rent method

            ArrayList<Tool> tools = new ArrayList<>(rightListView.getItems());
            System.out.println("Tools to return: ");
            for (Tool t : tools) {
                System.out.println(t.getResourceName());
            }

            Alert a = new Alert(Alert.AlertType.INFORMATION, "what");
            a.showAndWait();

        }else if (tabPane.getSelectionModel().getSelectedItem().getText().equals(tabAdd.getText())){
            //Add method
            System.out.println(toolNameField.getText());
            System.out.println(locationBox.getSelectionModel().getSelectedItem().toString());
            System.out.println(datePicker.getValue());
            System.out.println(toolPriceField.getText());
        }
    }

    public void initialize(){
    //TODO : procedura "pożyczanie / oddawanie" narzędzi : check if rightlist empty, potem check statusWypożyczenia
    //TODO : napisać SQL "select wypożyczone" i "select niewypożyczone", wpisać do dwóch różnych list, wyświetlać różne w zależności od stanu toggle buttona
    //TODO : dodać kartę usuwanie (???)

    /** Initial DB queries **/

        updateStatusLeft("Sending SQL queries...");

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
            updateStatusLeft("SQLException!");
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
            updateStatusLeft("SQLException!");
        }

        searchField.textProperty().addListener((observableValue, s, t1) -> {
            filterList(s,t1);
        });

    /** UI setup :
        COMBOBOXES **/

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

    /** UI SETUP:
        BUTTONS, TABPANE **/

        b_rent.disableProperty().bind(
                Bindings.or(
                        radioButtonRenting.selectedProperty().not(),
                        comboBox.valueProperty().isEqualTo(comboBox.placeholderProperty())));

        b_return.disableProperty().bind(
                        radioButtonReturning.selectedProperty().not());

        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> observableValue, Tab t, Tab t1) {
                        if(t1.getText().equals(tabRent.getText())) {
                            /** RENT **/
                            b_return.disableProperty().bind(
                                            radioButtonReturning.selectedProperty().not());
                            b_return.setText("Oddaj");
                            b_rent.disableProperty().bind(
                                    Bindings.or(
                                            radioButtonRenting.selectedProperty().not(),
                                            comboBox.valueProperty().isEqualTo(comboBox.placeholderProperty())));
                            b_rent.setVisible(true);
                            b_return.setVisible(true);
                        }
                        if(t1.getText().equals(tabHistory.getText())){
                            /** HISTORY **/

                            b_rent.setVisible(false);
                            b_return.setVisible(false);
                            historyTable.getItems().clear();

                            setResultSet(DB_utility.executeQuery("SELECT * FROM TOOL_LEND"));

                            try {
                                while (getResultSet().next()) {
                                    BorrowRecord borrowRecord = new BorrowRecord(
                                            getResultSet().getTimestamp("BORROW"),
                                            getResultSet().getTimestamp("RETURN"),
                                            getResultSet().getString("EMPLOYEE_NAME"));
                                    historyTable.getItems().add(borrowRecord);
                                }
                            } catch (SQLException e){
                                System.err.println("SQLException podczas ładowania wyniku borrowRecords!");
                                updateStatusLeft("SQLException!");
                            }
                        }
                        if(t1.getText().equals(tabAdd.getText())){
                            /** ADD **/
                            b_return.disableProperty().bind(
                                    Bindings.or(
                                            locationBox.valueProperty().isEqualTo(locationBox.placeholderProperty()),
                                            toolNameField.textProperty().isEmpty()));
                            b_rent.setVisible(false);
                            b_return.setText("Dodaj");
                            b_return.setVisible(true);

                            setResultSet(DB_utility.executeQuery("select room, place from location left outer join tool on (location.room = tool.location_room and location.place=tool.location_place) left outer join machine on (location.room = machine.location_room and location.place=machine.location_place) left outer join material on (location.room = material.location_room and location.place=material.location_place) where tool.name is null and machine.name is null and material.name is null order by room"));

                            locations.clear();

                            try {
                                while (getResultSet().next()) {
                                    Location location = new Location(
                                            getResultSet().getString("ROOM"),
                                            getResultSet().getString("PLACE"));
                                    locations.add(location);
                                }
                            } catch (SQLException e){
                                System.err.println("SQLException podczas ładowania wyniku tools!");
                                updateStatusLeft("SQLException!");
                            }

                            locationBox.setItems(locations);
                        }
                    }
                }
        );

    /** UI setup :
        TABLEVIEWS **/

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

        descriptionTable.getColumns().addAll(idColumn, nameColumn, locationColumn);

        historyTable.setPlaceholder(new Label("Historia wypożyczeń"));

        TableColumn<BorrowRecord,String> borrowColumn = new TableColumn<>("WYPOŻYCZONO");
        TableColumn<BorrowRecord,String> returnColumn = new TableColumn<>("ODDANO");
        TableColumn<BorrowRecord,String> borrowerColumn = new TableColumn<>("KOMU");

        borrowColumn.setCellValueFactory(new PropertyValueFactory<>("borrowTimestamp"));
        returnColumn.setCellValueFactory(new PropertyValueFactory<>("returnTimestamp"));
        borrowerColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));

        historyTable.getColumns().addAll(borrowColumn,borrowerColumn,returnColumn);

        historyTable.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount() == 2 ){
                System.out.println(historyTable.getItems().get(historyTable.getSelectionModel().getFocusedIndex()).toString());
            }
        });

    /** UI setup :
        LISTVIEWS **/

        rightListView.setPlaceholder(new Label("*Wybrane narzędzia (doubleclick)"));

        leftListView.setCellFactory(param -> new ListCell<>(){
            @Override
            protected void updateItem(Tool item, boolean b) {
                super.updateItem(item, b);
                if(item==null){
                    setText(null);
                }else {
                    setText(item.getResourceName());
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
                    setText(item.getResourceName());
                }
            }
        });

        leftListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount() == 1 && leftListView.getSelectionModel().getSelectedItem() != null){
                    descriptionTable.getItems().clear();
                    descriptionTable.getItems().add(leftListView.getSelectionModel().getSelectedItem());
                    updateStatusRight("wolny-test");
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
                    updateStatusRight("wypozyczony-test");
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

    /** TEXTFIELD FILTRATION **/

        toolNameField.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 100 ? change : null));

        toolPriceField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!t1.matches("\\d{0,8}([\\.]\\d{0,2})?")){
                    toolPriceField.setText(s);
                }
            }
        });

        updateStatusLeft("Status narzędzia : ");
        updateStatusRight("?");
    }
}