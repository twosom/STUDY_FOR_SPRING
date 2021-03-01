import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "/junit.xml")
public class JUnitTest {

    @Autowired
    ApplicationContext context;

    static Set<JUnitTest> testObject = new HashSet<>();
    static ApplicationContext contextObject = null;

    @After
    public void afterPrint() {
        System.out.println("testObject = " + testObject);
        System.out.println(contextObject);
    }


    @Test
    public void test1() {
        assertThat(testObject).doesNotContain(this);
        testObject.add(this);

        Assert.assertTrue(contextObject == null || contextObject == this.context);
        contextObject = this.context;
    }

    @Test
    public void test2() {
        assertThat(testObject).doesNotContain(this);
        testObject.add(this);

        assertThat(contextObject == null || contextObject == this.context).isTrue();
        contextObject = this.context;
    }

    @Test
    public void test3() {
        assertThat(testObject).doesNotContain(this);
        testObject.add(this);

        assertThat(contextObject == null || contextObject == this.context).isTrue();
        contextObject = this.context;
    }
}
