package com.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By emailInput = By.id("input-email");
    private By passwordInput = By.id("input-password");
    private By loginButton = By.cssSelector("input.btn-primary");
    private By warningMessage = By.cssSelector(".alert-danger");
    private By myAccountHeader = By.xpath("//h2[text()='My Account']");

    public void enterEmail(String email) {
        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public boolean isLoginSuccessful() {
        // Si aparece el header "My Account", el login fue exitoso
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(myAccountHeader));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoginErrorShown() {
        // Si aparece el mensaje de advertencia, el login falló
        return driver.findElements(warningMessage).size() > 0;
    }
}
