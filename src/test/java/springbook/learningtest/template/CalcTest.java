package springbook.learningtest.template;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class CalcTest {

    Calculator calculator;
    String numFilePath;

    @Before
    public void setUp() {
        this.calculator = new Calculator();
        numFilePath = getClass().getResource("/numbers.txt").getPath();
        System.out.println("numFilePath = " + numFilePath);

    }

    @Test
    public void sumTest() throws IOException {
        Assertions.assertThat(calculator.calcSum(this.numFilePath)).isEqualTo(10);
    }

    @Test
    public void multiplyOfNumbers() throws IOException {
        Assertions.assertThat(calculator.calcMultiply(this.numFilePath)).isEqualTo(24);
    }

    @Test
    public void sumTest2() throws IOException {
        Assertions.assertThat(calculator.calcSum2(this.numFilePath)).isEqualTo(10);
    }

    @Test
    public void multiplyOfNumbers2() throws IOException {
        Assertions.assertThat(calculator.calcMultiply2(this.numFilePath)).isEqualTo(24);
    }

    @Test
    public void stringConcat() throws IOException {
        Assertions.assertThat(calculator.concatenate(this.numFilePath)).isEqualTo("1234");
    }
}
