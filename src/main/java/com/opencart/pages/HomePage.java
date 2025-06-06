package com.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {

    //Constructor
    public HomePage(WebDriver driver) {
        super(driver);
    }

    //Crear elementos de la pagina
    private By category(String category) {
        return By.xpath("//a[text()='" + category + "']");
    }

    private By subCategory(String subCategory) {
        return By.xpath("//div[@class='dropdown-inner']//a[contains(text(),'" + subCategory + "')]");
    }

    //Metodos o acciones de la pagina
    public void selectCategory(String category) {
        driver.findElement(category(category)).click();
    }

    public void selectSubCategory(String subCategory) {
        //Fluent Wait
        Wait<WebDriver> waitF = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15)) //Tiempo maximo de espera
                .pollingEvery(Duration.ofMillis(300)) //Frecuencia de verificacion
                .ignoring(NoSuchFieldError.class); //Excepciones que Ignora

        WebElement subCat = waitF.until(driver -> driver.findElement(subCategory(subCategory)));
        subCat.click();
    }

    // En HomePage.java
    public void goToRegisterPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        logoutIfLoggedIn();
        WebElement myAccount = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("a.dropdown-toggle[title='My Account']")
        ));
        myAccount.click();

        try {
            WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']/li/a[text()='Logout']")
            ));
            logoutLink.click();

            wait.until(ExpectedConditions.urlContains("route=account/logout"));

            // Haz click de nuevo en el <a> para abrir el menú
            myAccount = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a.dropdown-toggle[title='My Account']")
            ));
            myAccount.click();

        } catch (TimeoutException e) {
            System.out.println("Usuario no estaba logueado o enlace 'Logout' no encontrado. Procediendo a buscar 'Register'.");
        }

        // Espera a que el menú esté visible antes de buscar "Register"
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']")
        ));

        // Ahora, busca el enlace "Register"
        WebElement registerLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']/li/a[text()='Register']")
        ));
        registerLink.click();
    }

    // Método reutilizable para hacer logout si está logueado
    public void logoutIfLoggedIn() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        WebElement myAccount = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("a.dropdown-toggle[title='My Account']")
        ));
        myAccount.click();
        try {
            WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']/li/a[text()='Logout']")
            ));
            logoutLink.click();
            wait.until(ExpectedConditions.urlContains("route=account/logout"));
        } catch (TimeoutException e) {
            // No estaba logueado, no hacer nada
        }
    }

    public void goToLoginPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        logoutIfLoggedIn();
        WebElement myAccount = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("a.dropdown-toggle[title='My Account']")
        ));
        myAccount.click();

        // Espera a que el menú esté visible antes de buscar "Login"
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']")
            ));
        } catch (TimeoutException e) {
            // Si el menú no se abrió, intenta hacer click de nuevo
            myAccount.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']")
            ));
        }

        // Ahora busca el enlace "Login"
        WebElement loginLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']/li/a[text()='Login']")
        ));
        loginLink.click();
    }

}
