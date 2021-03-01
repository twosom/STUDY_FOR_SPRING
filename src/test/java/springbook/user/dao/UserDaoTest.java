package springbook.user.dao;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import springbook.user.dao.Exception.DuplicateUserIdException;
import springbook.user.domain.User;


import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DataSource dataSource;

    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setUp() throws SQLException {


        System.out.println(this);
        user1 = new User("SM", "SOMANG", "123123");
        user2 = new User("YS", "YESONG", "123123");
        user3 = new User("CS", "CHANSONG", "123123");

        //DB초기화 후 검증
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);
    }

    @Test
    public void addAndGet() throws Exception {
        //given


        //when
        userDao.add(user1);
        userDao.add(user2);

        User findUser1 = userDao.get(user1.getId());
        User findUser2 = userDao.get(user2.getId());

        //then

        assertThat(userDao.getCount()).isEqualTo(2);
        assertThat(user1.getName()).isEqualTo(findUser1.getName());
        assertThat(user1.getPassword()).isEqualTo(findUser1.getPassword());

        assertThat(user2.getName()).isEqualTo(findUser2.getName());
        assertThat(user2.getPassword()).isEqualTo(findUser2.getPassword());
    }

    @Test
    public void getCount_테스트() throws Exception {
        //given


        //when & then
        userDao.add(user1);

        assertThat(userDao.getCount()).isEqualTo(1);

        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        userDao.add(user3);
        assertThat(userDao.getCount()).isEqualTo(3);

    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void get메소드_실패_테스트() throws Exception {
        //given

        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        //when
        User 실패ID = userDao.get("실패ID");
        //then
        fail("실패해야한다.");

    }

    @Test
    public void getAll() throws Exception {
        //given

        List<User> users0 = userDao.getAll();
        assertThat(users0).isEqualTo(null);


        userDao.add(user1);
        List<User> users1 = userDao.getAll();
        assertThat(users1.size()).isEqualTo(1);
        checkSameUser(user1, users1.get(0));

        userDao.add(user2);
        List<User> users2 = userDao.getAll();
        assertThat(users2.size()).isEqualTo(2);
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));


        userDao.add(user3);
        List<User> users3 = userDao.getAll();
        assertThat(users3.size()).isEqualTo(3);
        checkSameUser(user3, users3.get(0));
        checkSameUser(user1, users3.get(1));
        checkSameUser(user2, users3.get(2));
    }

    @Test(expected = DuplicateKeyException.class)
    public void 중복_아이디_검사() throws Exception {

        userDao.add(user1);
        userDao.add(user1);

        fail("중복 아이디 예외가 발생해야 한다.");
    }

    @Test
    public void sqlExceptionTranslate() throws Exception {
        try {
            userDao.add(user1);
            userDao.add(user1);
        } catch (DuplicateKeyException e) {
            SQLException sqlEx = (SQLException) e.getRootCause();
            SQLErrorCodeSQLExceptionTranslator errorTranslator = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);

            assertThat(errorTranslator.translate(null, null, sqlEx)).isInstanceOf(DuplicateKeyException.class);
        }
    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
    }

}