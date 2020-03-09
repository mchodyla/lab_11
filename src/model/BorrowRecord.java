package model;

import db.DB_utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class BorrowRecord {
    private Timestamp borrowTimestamp;
    private Timestamp returnTimestamp;
    private String employeeName;

    public BorrowRecord(Timestamp borrowTimestamp, Timestamp returnTimestamp, String employeeName) {
        this.borrowTimestamp = borrowTimestamp;
        this.returnTimestamp = returnTimestamp;
        this.employeeName = employeeName;
    }

    public static ObservableList<BorrowRecord> getListFromTable(){
        ResultSet rs = DB_utility.executeQuery("SELECT * FROM TOOL_LEND");
        ObservableList<BorrowRecord> outputList = FXCollections.observableArrayList();
        try{
            while (rs.next()){
                BorrowRecord borrowRecord = new BorrowRecord(
                        rs.getTimestamp("BORROW"),
                        rs.getTimestamp("RETURN"),
                        rs.getString("EMPLOYEE_NAME"));
                outputList.add(borrowRecord);
            }
            return outputList;
        }catch(SQLException e){
            System.err.println("SQLException ! : " + e.toString());
            return null;
        }
    }

    public Timestamp getBorrowTimestamp() {
        return borrowTimestamp;
    }

    public void setBorrowTimestamp(Timestamp borrowTimestamp) {
        this.borrowTimestamp = borrowTimestamp;
    }

    public Timestamp getReturnTimestamp() {
        return returnTimestamp;
    }

    public void setReturnTimestamp(Timestamp returnTimestamp) {
        this.returnTimestamp = returnTimestamp;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public String toString() {
        return employeeName + " " + borrowTimestamp.getTime();
    }
}
