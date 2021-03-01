package springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Calculator {

    public Integer calcSum(String filePath) throws IOException {

        return fileReadTemplate(filePath, br -> {
            Integer result = 0;
            String line = null;
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                result += Integer.parseInt(line);
            }

            return result;
        });
    }

    public Integer calcMultiply(String filePath) throws IOException {
        return fileReadTemplate(filePath, br -> {
            Integer result = 1;
            String line = null;
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                result *= Integer.parseInt(line);

            }
            return result;
        });
    }

    private Integer fileReadTemplate(String filePath, BufferedReaderCallback callback) throws IOException {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filePath));
            Integer result = callback.doSomethingWithReader(br);

            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {

            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Integer calcSum2(String filePath) throws IOException {
        return lineReadTemplate(filePath, (line, result) -> {
            return result + Integer.parseInt(line);
        }, 0);
    }

    public Integer calcMultiply2(String filePath) throws IOException {
        return lineReadTemplate(filePath, (line, result) -> {
            return result * Integer.parseInt(line);
        }, 1);
    }

    public String concatenate(String filepath) throws IOException {
        return lineReadTemplate(filepath, (line, result) -> {
            return result + line;
        }, "");
    }

    public <T> T lineReadTemplate(String filePath, LineCallBack<T> callback, T initVal) throws IOException {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filePath));

            T result = initVal;
            String line = null;
            while ((line = br.readLine()) != null) {
                result = callback.doSomethingWithLine(line, result);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {

            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
