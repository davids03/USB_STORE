package com.opencart.test;

import com.opencart.pages.HomePage;
import com.opencart.pages.LoginPage;
import com.opencart.utils.ExcelReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LoginTest extends BaseTest {

    @Test
    public void loginFromExcel() {
        String excelPath = "src/main/resources/inputData.xlsx";
        ExcelReader reader = new ExcelReader(excelPath, "LoginData");

        int rowCount = reader.getRowCount();

        for (int i = 1; i < rowCount; i++) { // Empieza desde 1 para omitir encabezado
            String email = reader.getCellData(i, 0);
            String password = reader.getCellData(i, 1);
            String expectedResult = reader.getCellData(i, 2); // "exito" o "fallo"

            // Navegar a la página de login
            HomePage home = new HomePage(driver);
            home.goToLoginPage();

            LoginPage login = new LoginPage(driver);
            login.enterEmail(email);
            login.enterPassword(password);
            login.clickLogin();

            if ("exito".equalsIgnoreCase(expectedResult)) {
                assertTrue(login.isLoginSuccessful(), "El login debería ser exitoso para: " + email);
            } else {
                assertTrue(login.isLoginErrorShown(), "El login debería fallar para: " + email);
            }
        }
    }
}
