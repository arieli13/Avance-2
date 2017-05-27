package com.selenium;

import java.io.File;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Test de sistema con Selenium
 * 
 * Se prueba que el sistema no deje ejecutar con un area mínima menor a 0.
 * Se carga un lote de 5 imágenes, posteriormente de define un area mínima igual a -5.
 * Al ejecutar el algoritmo se puede ver que muetra el mensaje de error de color rojo indicando
 * que se debe ingresar un area mínima válida.
 * 
 * @author José Pablo Navarro j.pablonavarro95@gmail.com
 *
 */
public class NinethTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    File file = new File("Librerias/geckodriver.exe");
    System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
    
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testNine() throws Exception {
    driver.get(baseUrl + "/SistemaModelado/index.jsp");
    driver.findElement(By.cssSelector("input.upload")).clear();
    driver.findElement(By.cssSelector("input.upload")).sendKeys("C:\\Users\\ariel\\Desktop\\Ejemplo\\groundtruth\\cel.png");
    driver.findElement(By.xpath("//input[@value='50']")).clear();
    driver.findElement(By.xpath("//input[@value='50']")).sendKeys("-5");
    driver.findElement(By.linkText("Upload and execute")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
