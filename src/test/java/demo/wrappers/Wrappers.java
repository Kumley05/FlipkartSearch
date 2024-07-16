package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    ChromeDriver driver;
    public Wrappers(ChromeDriver driver) {
        this.driver = driver;
        System.out.println(this.driver);
    }

    
    public void openURL(String url)throws InterruptedException{
        System.out.println("Open URL:" +url);
        driver.get(url);
        Thread.sleep(2000);
        System.out.println("Success!");
    }

    public void searchProduct(String productName) throws InterruptedException{
        System.out.println("Search Product:" +productName);
        WebElement searchBox = driver.findElement(By.xpath("//input[@title ='Search for Products, Brands and More']"));
        searchBox.click();
        searchBox.sendKeys(productName);
        searchBox.sendKeys(Keys.ENTER);
        Thread.sleep(3000);
    }
    
}
