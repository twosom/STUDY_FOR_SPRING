package springbook.user.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker {

    @Override
    public Connection makeConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tobySpring", "root", ""
        );
    }
}
