package demo;
import java.net.MalformedURLException;


public class App {
    public void getGreeting() throws InterruptedException, MalformedURLException {
        System.out.println("Hello Autmation Wizards!");
        System.setProperty("java.util.logging.config.file", "logging.properties");
        TestCases tests =  new TestCases();
        tests.testcase01();

    }

    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        new App().getGreeting();
    }
}
