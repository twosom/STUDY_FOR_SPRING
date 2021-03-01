package springbook.user.dao;

import springbook.user.dao.statemen_strategy.StatementStrategy;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {


    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();

            ps = stmt.makePreparedStatement(c);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void executeSql(final String query) throws SQLException {
        this.workWithStatementStrategy(c -> {
            return c.prepareStatement(query);
        });
    }

    public void executeSql(final String query, String... bindings) throws SQLException {
        this.workWithStatementStrategy(c -> {
            PreparedStatement ps = c.prepareStatement(query);
            int index = 1;
            for (String binding : bindings) {
                ps.setString(index, binding);
                index += 1;
            }

            return ps;
        });
    }
}
