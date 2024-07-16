package demo;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

public class TestCases {
    ChromeDriver driver;

    public TestCases(){
        System.out.println("Start the Testcases");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public void endTest(){
        System.out.println("TestCase End");
        driver.close();
        driver.quit();
    }

    public void testcase01() throws InterruptedException{
        System.out.println("Start Test Case 01");
        try{
            openURL(" www.flipkart.com");
            Thread.sleep(3000); 
            driver.findElement(By.xpath("//span[@role='button']")).click();
            Thread.sleep(2000);
            //search product
            searchProduct("Washing Machine");
            Thread.sleep(2000);
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            Thread.sleep(3000);
            //sort by popularity
            driver.findElement(By.xpath("//div[@class='zg-M3Z' and contains(text(),'Popularity')]")).click();
            Thread.sleep(3000);
            //Count the item review less than or equal to 4
            int count = 0;
            List<WebElement> ratings = driver.findElements(By.className("XQDdHH"));
            for(WebElement rating : ratings){
                int ratingText = Integer.parseInt(rating.getText());
                if(ratingText <= 4){
                    count++;
                    System.out.println(count);
                }
            }


        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failure!");
        }
    }

    //@Test

    public void openURL(String url) throws InterruptedException {
        // TODO Auto-generated method stub
        System.out.println("Open URL:"+url);
        Thread.sleep(3000);
        driver.get(url);
        System.out.println("Success!");
    }

    public void searchProduct(String productName){
        System.out.println("Search product:" +productName);
        driver.findElement(By.xpath("//input[@class='Pke_EE']")).click();

    }


}
