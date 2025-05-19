//
//
//
//
//
//
//
//
package com.travel.compass;
//
//
//
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class LoginSeleniumTest {
//
//    private WebDriver driver;
//
//    @BeforeEach
//    public void setUp() {
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver();
//    }
//
//    @Test
//    public void testLogin() {
//        driver.get("http://localhost:3000/login");
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//
//        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
//        WebElement passwordField = driver.findElement(By.id("password"));
//        WebElement loginButton = driver.findElement(By.id("loginButton"));
//
//        usernameField.sendKeys("chamika@c");
//        passwordField.sendKeys("123456");
//        loginButton.click();
//
//        // Assert login success
//        WebElement welcomeText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("welcome")));
//        assertTrue(welcomeText.getText().contains("Welcome"));
//    }
//}



import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginSeleniumTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        // Set path to your chromedriver executable if needed
        // System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("http://localhost:3000/login"); // ✅ adjust the URL if different
    }

    @Test
    public void testLoginWithValidCredentials() {
        // Wait for elements
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginButton")));

        // Enter credentials
        emailField.sendKeys("chamika@c");       // Replace with a valid test email
        passwordField.sendKeys("123456");       // Replace with the correct password
        loginButton.click();

        // Wait and validate that the user is redirected or a modal appears
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
        String message = modal.getText();
        System.out.println("Modal message: " + message);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
