package springbook.user.dao.connection;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {

    int counter = 0;
    private final ConnectionMaker realConnectionMaker;

    public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
        this.realConnectionMaker = realConnectionMaker;
    }

    @Override
    public Connection makeConnection() throws SQLException {
        this.counter++;
        return this.realConnectionMaker.makeConnection();
    }

    public int getCounter() {
        return this.counter;
    }
}
