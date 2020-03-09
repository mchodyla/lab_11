package gui;

import db.DB_utility;
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
import model.BorrowRecord;
import model.Employee;
import model.Location;
import model.Tool;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class E3_controller extends GenericController{

    @FXML
    public TabPane tabPane;

    @FXML
    public Tab rentTab;
        @FXML
        public RadioButton rentRadioButtonReturn;
        @FXML
        public RadioButton rentRadioButtonRent;
        @FXML
        public ComboBox<Employee> rentComboBoxWho;
        @FXML
        public TextField rentSearchField;
        @FXML
        public ListView<Tool> rentLeftListView;
        @FXML
        public ListView<Tool> rentRightListView;
        @FXML
        public TableView<Tool> rentTableDescription;
        @FXML
        public Button buttonRent;
        @FXML
        public Button buttonRight;
    @FXML
    public Tab historyTab;
        @FXML
        public TableView<BorrowRecord> historyTable;
    @FXML
    public Tab addTab;
        @FXML
        public TextField addTextFieldToolName;
        @FXML
        public ComboBox<Location> addComboBoxLocation;
        @FXML
        public DatePicker addDatePickerBorrowed;
        @FXML
        public TextField addTextFieldToolPrice;
    @FXML
    public Tab deleteTab;
        @FXML
        public TextField deleteSearchField;
        @FXML
        public ListView<Tool> deleteListView;
        @FXML
        public TableView<Tool> deleteDescriptionTable;

        //todo: POTRZEBNE LISTY : ToolBorrowRecords, FreeLocations

    private ObservableList<Tool> toolsList = Tool.getListFromTable();
    private ObservableList<Tool> selectedToolsList = FXCollections.observableArrayList();
    private ObservableList<Employee> employees = Employee.getListFromTable();
    private ObservableList<Location> locations = FXCollections.observableArrayList();
    private ObservableList<BorrowRecord> borrowRecords = BorrowRecord.getListFromTable();

    public void filterList(String oldValue, String newValue){

        ObservableList<Tool> filteredList = FXCollections.observableArrayList();
        if(rentSearchField == null || (newValue.length() < oldValue.length()) || newValue == " ") {
            rentLeftListView.setItems(toolsList);
        } else {
            for (Tool tool : rentLeftListView.getItems()) {
                String filterText = tool.getResourceName();
                if (filterText.toUpperCase().contains(rentSearchField.getText().toUpperCase())) {
                    filteredList.add(tool);
                }
            }
            rentLeftListView.setItems(filteredList);
        }
    }

    public void rentTools(){
        //Renting method
        if(rentComboBoxWho.getSelectionModel().getSelectedItem() != null && !rentRightListView.getItems().isEmpty()) {
            Employee e = rentComboBoxWho.getSelectionModel().getSelectedItem();
            ArrayList<Tool> tools = new ArrayList<Tool>(rentRightListView.getItems());

            System.out.println("Employee : " + e.getName());
            System.out.println("Tools to rent: ");
            for (Tool t : tools) {
                System.out.println(t.getResourceName());
            }
        }
    }

    public void rightButtonMethod(){
        if(tabPane.getSelectionModel().getSelectedItem().getText().equals(rentTab.getText())){
            /** Rent method **/

            ArrayList<Tool> tools = new ArrayList<>(rentRightListView.getItems());
            System.out.println("Tools to return: ");
            for (Tool t : tools) {
                System.out.println(t.getResourceName());
            }

            Alert a = new Alert(Alert.AlertType.INFORMATION, "what");
            a.showAndWait();

        }else if (tabPane.getSelectionModel().getSelectedItem().getText().equals(addTab.getText())){
            /** Add method **/
            System.out.println(addTextFieldToolName.getText());
            System.out.println(addComboBoxLocation.getSelectionModel().getSelectedItem().toString());
            System.out.println(addDatePickerBorrowed.getValue());
            System.out.println(addTextFieldToolPrice.getText());
        }else if (tabPane.getSelectionModel().getSelectedItem().getText().equals(deleteTab.getText())){
            /** Delete method **/
            if(deleteListView.getSelectionModel().getSelectedItem() != null){
                System.out.println("Narzędzie do usunięcia : " + deleteListView.getSelectionModel().getSelectedItem().getResourceName());
            } else System.out.println("Select something!");
        }
    }

    public void initialize(){
    //TODO : procedura "pożyczanie / oddawanie" narzędzi : check if rightlist empty, potem check statusWypożyczenia
    //TODO : napisać SQL "select wypożyczone" i "select niewypożyczone", wpisać do dwóch różnych list, wyświetlać różne w zależności od stanu toggle buttona
    //TODO : dodać kartę usuwanie
    //TODO : leftlist selection listener - żeby w tabeli wyswietlało info nie tylko po kliknieciu myszka

    /** UI : TABPANE SETUP **/

    tabPane.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Tab>() {
                @Override
                public void changed(ObservableValue<? extends Tab> observableValue, Tab t, Tab t1) {
                    if(t1.getText().equals(rentTab.getText())) {
                        /** RENT **/

                        buttonRight.setText("Oddaj");
                        buttonRent.setVisible(true);
                        buttonRight.setVisible(true);

                        buttonRight.disableProperty().bind(
                                rentRadioButtonReturn.selectedProperty().not());

                        buttonRent.disableProperty().bind(
                                Bindings.or(
                                        rentRadioButtonRent.selectedProperty().not(),
                                        rentComboBoxWho.valueProperty().isEqualTo(rentComboBoxWho.placeholderProperty())));

                        rentTableDescription.getColumns().clear();
                        rentTableDescription.getColumns().addAll(deleteDescriptionTable.getColumns());
                    }else if(t1.getText().equals(historyTab.getText())){
                        /** HISTORY **/

                        buttonRent.setVisible(false);
                        buttonRight.setVisible(false);

                        historyTable.getItems().clear();

                        //todo: dodać kolumny i wypełnić listę
                    }else if(t1.getText().equals(addTab.getText())){
                        /** ADD **/

                        buttonRight.setText("Dodaj");
                        buttonRight.setVisible(true);
                        buttonRent.setVisible(false);

                        buttonRight.disableProperty().bind(
                                Bindings.or(
                                        addComboBoxLocation.valueProperty().isEqualTo(addComboBoxLocation.placeholderProperty()),
                                        addTextFieldToolName.textProperty().isEmpty()));

                        locations.clear();
                        locations.addAll(Location.getFreeSpaceList());
                        addComboBoxLocation.setItems(locations);

                    }else if(t1.getText().equals(deleteTab.getText())){
                        /** DELETE **/

                        buttonRight.setText("Usuń");
                        buttonRight.setVisible(true);
                        buttonRent.setVisible(false);
                        buttonRight.disableProperty().unbind();
                        buttonRight.setDisable(false);

                        deleteDescriptionTable.getColumns().clear();
                        deleteDescriptionTable.getColumns().addAll(rentTableDescription.getColumns());
                    }
                }
            }
    );

    /** UI : DatePicker setup **/

        addDatePickerBorrowed.setDayCellFactory(picker -> new DateCell(){
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) > 0);
            }
        });
        addDatePickerBorrowed.getEditor().setDisable(true);

        rentSearchField.textProperty().addListener((observableValue, s, t1) -> {
            filterList(s,t1);
        });

    /** UI : COMBOBOXES **/

        rentComboBoxWho.disableProperty().bind(rentRadioButtonReturn.selectedProperty());
        rentComboBoxWho.setCellFactory(new Callback<ListView<Employee>, ListCell<Employee>>() {
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
        rentComboBoxWho.setButtonCell(new ListCell<>(){
            @Override
            protected void updateItem(Employee employee, boolean b) {
                super.updateItem(employee, b);
                if(employee != null){
                    setText(employee.getName());
                }
            }
        });
        rentComboBoxWho.setItems(employees);

        addComboBoxLocation.setCellFactory(new Callback<ListView<Location>, ListCell<Location>>() {
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

    /** UI : BUTTONS **/

        buttonRent.disableProperty().bind(
                Bindings.or(
                        rentRadioButtonRent.selectedProperty().not(),
                        rentComboBoxWho.valueProperty().isEqualTo(rentComboBoxWho.placeholderProperty())));

        buttonRight.disableProperty().bind(
                        rentRadioButtonReturn.selectedProperty().not());

    /** UI : TABLEVIEWS **/

        rentTableDescription.setPlaceholder(new Label("Opis wybranego narzędzia"));

        TableColumn<Tool, String> idColumn = new TableColumn<>("ID");
        TableColumn<Tool, String> nameColumn = new TableColumn<>("NAZWA");
        TableColumn<Tool, String> locationColumn = new TableColumn<>("POŁOŻENIE");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        locationColumn.setCellValueFactory(data -> {
            if(data != null){
                return new ReadOnlyStringWrapper(data.getValue().getLocation().toString());
            } else return new ReadOnlyStringWrapper("");
        });

        rentTableDescription.getColumns().addAll(idColumn, nameColumn, locationColumn);

        deleteDescriptionTable.setPlaceholder(new Label("Opis wybranego narzędzia"));

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

    /** UI : LISTVIEWS **/

        deleteListView.setCellFactory(param -> new ListCell<>(){
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

        rentRightListView.setPlaceholder(new Label("*Wybrane narzędzia (doubleclick)"));

        rentLeftListView.setCellFactory(param -> new ListCell<>(){
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

        rentRightListView.setCellFactory(param -> new ListCell<>(){
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

        rentLeftListView.getSelectionModel().selectedItemProperty().addListener((observableValue, tool, t1) -> {
            if(t1!=null){
                rentTableDescription.getItems().clear();
                rentTableDescription.getItems().add(rentLeftListView.getSelectionModel().getSelectedItem());
            }
        });

        rentLeftListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount() == 2 && rentLeftListView.getSelectionModel().getSelectedItem() != null){
                    selectedToolsList.add(rentLeftListView.getSelectionModel().getSelectedItem());
                    toolsList.remove(rentLeftListView.getSelectionModel().getSelectedItem());
                    rentRightListView.setItems(selectedToolsList);
                }
            }
        });

        rentRightListView.getSelectionModel().selectedItemProperty().addListener((observableValue, tool, t1) -> {
            if(t1!=null){
                rentTableDescription.getItems().clear();
                rentTableDescription.getItems().add(rentRightListView.getSelectionModel().getSelectedItem());
            }
        });

        rentRightListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount() == 2 && rentRightListView.getSelectionModel().getSelectedItem() != null){
                    toolsList.add(rentRightListView.getSelectionModel().getSelectedItem());
                    selectedToolsList.remove(rentRightListView.getSelectionModel().getSelectedItem());
                    rentLeftListView.setItems(toolsList);
                }
            }
        });

        deleteListView.getSelectionModel().selectedItemProperty().addListener((observableValue, tool, t1) -> {
            deleteDescriptionTable.getItems().clear();
            deleteDescriptionTable.getItems().add(deleteListView.getSelectionModel().getSelectedItem());
        });

        deleteListView.setItems(toolsList);
        rentLeftListView.setItems(toolsList);
        rentRightListView.setItems(selectedToolsList);

    /** UI : TEXTFIELD FILTRATION **/

        addTextFieldToolName.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 100 ? change : null));

        addTextFieldToolPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!t1.matches("\\d{0,8}([\\.]\\d{0,2})?")){
                    addTextFieldToolPrice.setText(s);
                }
            }
        });

        updateStatusLeft("Status narzędzia : ");
        updateStatusRight("?");
    }
}