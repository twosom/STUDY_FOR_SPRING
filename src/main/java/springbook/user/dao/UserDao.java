package springbook.user.dao;


import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    private RowMapper<User> userMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));

            return user;
        }
    };

    public void add(User user) throws SQLException {
        jdbcTemplate.update("insert into users(id, name, password) " +
                "values(?, ?, ?)", user.getId(), user.getName(), user.getPassword());
    }


    public User get(String id) throws SQLException {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{id}, userMapper);
    }

    public List<User> getAll() {
        List<User> list = this.jdbcTemplate.query("select * from users order by id", userMapper);

        if (list.size() == 0) {
            return null;
        }

        return list;
    }

    public void deleteAll() throws SQLException {
        this.jdbcTemplate.update("delete from users");
    }


    public int getCount() throws SQLException {
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

}

