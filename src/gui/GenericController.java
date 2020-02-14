package gui;

import java.sql.ResultSet;

public class GenericController {
    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    private ResultSet resultSet;
}
