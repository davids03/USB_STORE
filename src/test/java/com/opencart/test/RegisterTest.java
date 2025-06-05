package com.opencart.test;

import com.opencart.pages.HomePage;
import com.opencart.pages.RegisterPage;
import com.opencart.utils.ExcelReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterTest extends BaseTest {

    @Test
    public void registroUsuariosDesdeExcel() {
        String excelPath = "src/main/resources/inputData.xlsx";
        ExcelReader reader = new ExcelReader(excelPath, "UsuariosRegistro");
        int filas = reader.getRowCount();

        for (int fila = 1; fila < filas; fila++) {
            String nombre = reader.getCellData(fila, 0);
            String apellido = reader.getCellData(fila, 1);
            String correo = reader.getCellData(fila, 2);
            String telefono = reader.getCellData(fila, 3);
            String contrasena = reader.getCellData(fila, 4);

            HomePage homePage = new HomePage(driver);
            homePage.goToRegisterPage();

            RegisterPage registro = new RegisterPage(driver);
            registro.enterFirstName(nombre);
            registro.enterLastName(apellido);
            registro.enterEmail(correo);
            registro.enterTelephone(telefono);
            registro.enterPassword(contrasena);
            registro.acceptPrivacyPolicy();
            registro.clickContinue();

            assertTrue(registro.isRegistrationSuccessful(), "No se pudo registrar el usuario: " + correo);
        }
    }
}
