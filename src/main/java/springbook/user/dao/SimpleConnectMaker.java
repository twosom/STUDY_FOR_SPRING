package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectMaker {
    public Connection makeNewConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tobySpring", "root", "");


    }
}
