package model;

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
