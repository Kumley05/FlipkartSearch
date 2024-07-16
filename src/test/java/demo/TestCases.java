package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import javax.swing.text.Utilities;

// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;
    Wrappers wrappers;
    WebDriverWait wait;

    /*
     * TODO: Write your tests here with testng @Test annotation.
     * Follow `testCase01` `testCase02`... format or what is provided in
     * instructions
     */

    /*
     * Do not change the provided methods unless necessary, they will help in
     * automation and assessment
     */
    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);
        wrappers = new Wrappers(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.manage().window().maximize();
    }

    @Test(priority = 0, description = "Search Washing Machine. Sort by popularity and print the count of items with rating less than or equal to 4 stars")
    public void testCase01() throws InterruptedException {
        System.out.println("Start testcase01:");
        wrappers.openURL("https://www.flipkart.com");
        wrappers.searchProduct("Washing Machine");
        driver.findElement(By.xpath("//div[contains(text(),'Popularity')]")).click();
        Thread.sleep(3000);
        List<WebElement> ratingElements = driver.findElements(By.className("XQDdHH"));
        int count = 0;
        for (WebElement ratingElement : ratingElements) {
            String ratingText = ratingElement.getText();
            try {
                if (Double.parseDouble(ratingElement.getText()) <= 4.0) {
                    count++;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format: " + ratingText);
            }
        }
        System.out.println("Count of ratings <= 4: " + count);
        System.out.println("End Testcase01");
        Thread.sleep(5000);
    }

    @Test(priority = 1, description = "Search \"iPhone\", print the Titles and discount % of items with more than 17% discount")
    public void testCase02() throws InterruptedException {
        System.out.println("Start Testcase02");
        wrappers.openURL("https://www.flipkart.com");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//span[@role='button']")).click();
        wrappers.searchProduct("iPhone");
        List<WebElement> productDiscounts = driver.findElements(By.className("UkUFwK"));
        for (WebElement productDiscount : productDiscounts) {
            String discountText = productDiscount.getText().replace("% off", "").trim();
            try {
                int discount = Integer.parseInt(discountText);
                if (discount > 17) {
                    List<WebElement> productTitles = driver
                            .findElements(By.xpath("//div[@class='UkUFwK']/ancestor::div[4]/div/div[@class='KzDlHZ']"));
                    for (WebElement productTitle : productTitles) {
                        String title = productTitle.getText();
                        System.out.println("Title: " + title + ", Discount: " + discount + "%");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid discount format: " + discountText);
            }

        }
        System.out.println("End Testcase02");
        Thread.sleep(5000);
    }

    @Test(priority = 2, description = "Search \"Coffee Mug\", select 4 stars and above, and print the Title and image URL of the 5 items with highest number of reviews")
    public void testCase03() throws InterruptedException {
        System.out.println("Start Testcase03");
        wrappers.openURL("https://www.flipkart.com");
        Thread.sleep(3000);
        //driver.findElement(By.xpath("//span[@role='button']")).click();
        wrappers.searchProduct("Coffee Mug");
        driver.findElement(By.xpath("//div[contains(text(),'4')]/ancestor::div[2]/div/label/div[@class='XqNaEv']"))
                .click();
        Thread.sleep(3000);
        List<WebElement> productReviews = driver.findElements(By.className("Wphh3N"));
        List<Product> products = new ArrayList<>();

        for (WebElement productReview : productReviews) {
            String ratingText = productReview.getText().trim();
            ratingText = ratingText.replaceAll("[^0-9.]", "");
            double rating = Double.valueOf(ratingText);
            WebElement productReviewText = productReview.findElement(By.xpath("./ancestor::div[2]/a[2]"));
            String title = productReviewText.getAttribute("title");
            String URL = productReviewText.getAttribute("href");
            products.add(new Product(title, URL, rating));

        }
        products.sort((n1, n2) -> Double.compare(n2.rating, n1.rating));

        for (int i = 0; i < Math.min(5, products.size()); i++) {
            Product product = products.get(i);

            System.out.println(product.title);
            System.out.println(product.url);
            System.out.println(product.rating);
            System.out.println("*------*------*");
        }
        System.out.println("End Testcase03");
        Thread.sleep(5000);

    }

    private static class Product {
        String title;
        String url;
        double rating;

        Product(String title, String url, double rating) {
            this.title = title;
            this.url = url;
            this.rating = rating;
        }
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }
}