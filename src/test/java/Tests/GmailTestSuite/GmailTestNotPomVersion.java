package Tests.GmailTestSuite;

import java.util.List;
import java.time.Duration;
import java.util.NoSuchElementException;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class GmailTestNotPomVersion {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private final int maxTimeout = 20; //in s
    private final int pollingTime = 1000; //in ms
    private final String url = "https://mail.google.com/";
    private final String testUsername = "trimm.trabb129@gmail.com";
    private final String testPassword = "M1u4s0e9!";
    private final String emailSubject = "My first test email!";
    private final String updatedSubject = "Updated subject";
    private final int emailsNum = 5;
    private final By usernameField = By.cssSelector("input[type='email']");
    private final By usernameNext = By.cssSelector("#identifierNext");
    private final By passwordField = By.xpath("//input[@name='password']");
    private final By passwordNext = By.id("passwordNext");
    private final By emailList = By.className("zA");
    private final By composeButton = By.cssSelector(".T-I.T-I-KE.L3");
    private final By toField = By.xpath("//textarea[@name='to']");
    private final By subjectField = By.xpath("//input[@name='subjectbox']");
    private final By emailCloseButton = By.cssSelector(".Ha");
    private final By draftsLink = By.cssSelector("a[href$='drafts']");
    private final By deleteButton = By.cssSelector(".oh.J-Z-I.J-J5-Ji.T-I-ax7");

    @Parameters({"browser"})
    @BeforeTest
    public void initialize(String browser) {
        if (browser.equals("Chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        if (browser.equals("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        if (driver != null) {
            driver.manage().window().maximize();
            wait = new WebDriverWait(driver, maxTimeout);
            driver.get(url);
        }
        else
            throw new RuntimeException("Could not start the web driver, check TestNG configuration");
    }

    @BeforeTest
    public void login() {
        //enter username
        WebElement username = driver.findElement(usernameField);
        username.sendKeys(testUsername);

        //proceed to the next login page
        driver.findElement(usernameNext).click();

        //enter password
        WebElement password = wait.until(ExpectedConditions.elementToBeClickable(passwordField));
        password.sendKeys(testPassword);

        //proceed with the login
        driver.findElement(passwordNext).click();
    }

    @Test()
    public void getLastEmails() {
        //get list of the last emails
        wait.until(ExpectedConditions.presenceOfElementLocated(emailList));
        List<WebElement> emails = driver.findElements(emailList).subList(0, emailsNum);

        //check number of emails retrieved equals the desired number
        assertEquals(emails.size(), emailsNum);

        //print Sender and Title of emails
        for (WebElement e : emails) {
            String emailText = e.getText();
            String[] lines = emailText.split("\\r?\\n");
            System.out.println("Sender: " + lines[0] + " Title: " + lines[1]);
        }
    }

    @Test(priority = 1)
    public void createDraft() {
        //click Compose button
        driver.findElement(composeButton).click();

        //fill in 'To' field
        WebElement to = wait.until(ExpectedConditions.elementToBeClickable(toField));
        to.sendKeys(testUsername);

        //fill in 'Subject' field
        WebElement subject = driver.findElement(subjectField);
        subject.sendKeys(emailSubject);

        //click Close button
        driver.findElement(emailCloseButton).click();

        //go to Drafts page
        Wait<WebDriver> fWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(maxTimeout))
                .pollingEvery(Duration.ofMillis(pollingTime))
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class);

        WebElement linkDrafts = fWait.until(driver -> driver.findElement(draftsLink));
        linkDrafts.click();
        wait.until(ExpectedConditions.urlContains("drafts"));

        //check there's an email
        boolean emailPresent = false;
        List<WebElement> drafts = wait.until(ExpectedConditions.
                presenceOfAllElementsLocatedBy(emailList));
        for (WebElement draft : drafts) {
            if (draft.isDisplayed() && draft.getText().contains(emailSubject)) {
                emailPresent = true;
                break;
            }
        }
        assertTrue(emailPresent);
    }

    @Test(priority = 2)
    public void editDraft() {

        //find and open the email sent earlier
        List<WebElement> drafts = wait.until(ExpectedConditions.
                presenceOfAllElementsLocatedBy(emailList));
        for (WebElement draft : drafts) {
            if (draft.getText().contains(emailSubject)) {
                draft.click();
                break;
            }
        }

        //update Subject field
        WebElement subject = wait.until(ExpectedConditions.elementToBeClickable(subjectField));
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.focus();");
        jse.executeScript("arguments[0].focus();", subject);
        subject.clear();
        subject.sendKeys(updatedSubject);

        //click Close button
        driver.findElement(emailCloseButton).click();

        //check there's an email with updated subject
        boolean updatedEmailPresent = false;
        drafts = wait.until(ExpectedConditions.
                presenceOfAllElementsLocatedBy(emailList));
        for (WebElement draft : drafts) {
            if (draft.isDisplayed() && draft.getText().contains(updatedSubject)) {
                updatedEmailPresent = true;
                break;
            }
        }
        assertTrue(updatedEmailPresent);
    }

    @Test(priority = 3)
    public void deleteDraft() {
        //on Drafts page search for the updated email and click on it
        List<WebElement> drafts = wait.until(ExpectedConditions.
                presenceOfAllElementsLocatedBy(emailList));
        for (WebElement draft : drafts) {
            if (draft.getText().contains(updatedSubject)) {
                draft.click();
                break;
            }
        }

        //click Delete button to delete the draft
        WebElement delete = wait.until(ExpectedConditions.
                presenceOfElementLocated(deleteButton));
        delete.click();

        //check email does not exist
        boolean emailPresent = false;
        drafts = wait.until(ExpectedConditions.
                presenceOfAllElementsLocatedBy(emailList));
        for (WebElement draft : drafts) {
            if (draft.isDisplayed() && draft.getText().contains(updatedSubject)) {
                emailPresent = true;
                break;
            }
        }
        assertFalse(emailPresent);
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}