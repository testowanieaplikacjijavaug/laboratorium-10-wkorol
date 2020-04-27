package org.example;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class GoogleTest {

	private static WebDriver driver;

	@BeforeAll
	public static void setUpDriver() {
		System.setProperty("webdriver.chrome.driver", "resources/chromedriver" + (System.getProperty("os.name").toLowerCase().contains("win") ? ".exe" : "" ));
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setHeadless(true);
		chromeOptions.addArguments("--lang=en-US");
		driver = new ChromeDriver(chromeOptions);
		// Implicity wait -> max czas na znalezienie elementu na stronie
		driver.manage()
				.timeouts()
				.implicitlyWait(10, TimeUnit.SECONDS);
	}

	@BeforeEach
	public void setUp() throws Exception {
		driver.get("https://duckduckgo.com/");
	}

	@AfterAll
	public static void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void testTitlePage() {
		assertEquals("DuckDuckGo — Privacy, simplified.", driver.getTitle());
	}

	@Test
	public void testSource() {
		String source = driver.getPageSource();
		assertTrue(source.contains("DuckDuckGo"));
	}

	@Test
	public void testClick() {
		driver.findElement(By.id("search_form_input_homepage"))
				.sendKeys("Mateusz Miotk");
		driver.findElement(By.id("search_button_homepage"))
				.click();
		assertEquals("Mateusz Miotk at DuckDuckGo", driver.getTitle());
	}

}