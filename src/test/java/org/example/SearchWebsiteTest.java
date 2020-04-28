package org.example;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import java.util.List;
import java.util.concurrent.TimeUnit;


import static org.junit.Assert.*;

public class SearchWebsiteTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUpDriver(){
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver" + (System.getProperty("os.name").toLowerCase().contains("win") ? ".exe" : "" ));
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        chromeOptions.addArguments("--lang=en-US");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void setUp() {
        driver.get("https://duckduckgo.com/");
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void testTitlePage() {
        assertEquals("DuckDuckGo — Privacy, simplified.", driver.getTitle());
    }

    @Test
    public void firstResult(){

        driver.findElement(By.id("search_form_input_homepage")).sendKeys("trojmiasto");
        driver.findElement(By.id("search_button_homepage")).click();
        List<WebElement> linkList = driver.findElements(By.tagName("a"));
        linkList.get(0).click();
        assertNotNull(driver.getTitle());
    }

    @Test
    public void secondResult(){
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("test");
        driver.findElement(By.id("search_button_homepage")).click();
        List<WebElement> linkList = driver.findElements(By.className("result__a"));
        linkList.get(2).click();
        assertNotNull(driver.getTitle());
    }

    @Test
    public void differentClickTypes(){
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("test");
        driver.findElement(By.id("search_button_homepage")).click();
        List<WebElement> linkList = driver.findElements(By.className("result__a"));
        linkList.get(0).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.history.go(-1)");
        Actions action = new Actions(driver);
        action.click(linkList.get(0));


    }
    @Test
    public void elementsThatDontExist(){
        Assertions.assertThrows(NotFoundException.class, () ->
                driver.findElement(By.linkText("testestq3ewrqwe")));
    }



}