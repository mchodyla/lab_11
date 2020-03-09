package gui;

import db.DB_utility;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.Employee;

import java.sql.SQLException;

public class E2_controller extends E1_controller {

    @FXML
    public ComboBox<Employee> consumerBox;

    private ObservableList<Employee> consumerEmployees = Employee.getListFromTable();

    @Override
    public void saveReport() {
        System.out.println("Consumer : " + consumerBox.getSelectionModel().getSelectedItem().getName());
        super.saveReport();
    }

    @Override
    public void initialize() {

        consumerBox.setCellFactory(new Callback<ListView<Employee>, ListCell<Employee>>() {
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
        consumerBox.setButtonCell(new ListCell<>(){
            @Override
            protected void updateItem(Employee employee, boolean b) {
                super.updateItem(employee, b);
                if(employee != null){
                    setText(employee.getName());
                }
            }
        });

        providerBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
            consumerBox.getItems().remove(newSelection);
            if(oldSelection != null && !consumerBox.getItems().contains(oldSelection)) consumerBox.getItems().add(oldSelection);
        });

        consumerBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
            providerBox.getItems().remove(newSelection);
            if(oldSelection != null && !providerBox.getItems().contains(oldSelection)) providerBox.getItems().add(oldSelection);
        });

        consumerBox.setItems(consumerEmployees);

        super.initialize();

        b_save.disableProperty().bind(
                Bindings.or(
                        Bindings.or(
                            providerBox.valueProperty().isEqualTo(providerBox.placeholderProperty()),
                            serviceBox.valueProperty().isEqualTo(serviceBox.placeholderProperty())),
                consumerBox.valueProperty().isEqualTo(consumerBox.placeholderProperty())));
    }
}