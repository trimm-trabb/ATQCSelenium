package GmailTestSuite;

import com.automation.dataproviders.ConfigFileReader;
import com.automation.driver.DriverManager;
import com.automation.pageobjects.gmail.InboxPage;
import com.automation.pageobjects.gmail.NewEmailPage;
import com.automation.pageobjects.gmail.PasswordPage;
import com.automation.pageobjects.gmail.UsernamePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class GmailTest {

    private WebDriver driver;
    private InboxPage inboxObj;
    private InboxPage draftsObj;
    private ConfigFileReader reader;
    private final String testSubject = "subject";
    private final String updatedSubject = "updated subject";

    @BeforeTest
    public void initialize() {
        driver = DriverManager.getDriverInstance();
        driver.manage().window().maximize();
        reader = new ConfigFileReader();
        driver.get(reader.getGmailUrl());
    }

    @BeforeTest
    public void login() {
        UsernamePage usernameObj = new UsernamePage(driver);
        usernameObj.enterUsername(reader.getUsername());
        usernameObj.clickNext();
        PasswordPage passwordObj = new PasswordPage(driver);
        passwordObj.enterPassword(reader.getPassword());
        passwordObj.clickNext();
    }

    @Test(priority = 0)
    public void getLastEmails() {
        int numOfLastEmails = 5;
        inboxObj = new InboxPage(driver);
        inboxObj.printLastEmails(inboxObj.getLastEmails(numOfLastEmails));
        assertEquals(inboxObj.getLastEmails(numOfLastEmails).size(), numOfLastEmails);
    }

    @Test(priority = 1)
    public void createDraft() {
        inboxObj.clickComposeButton();
        NewEmailPage emailObj = new NewEmailPage(driver);
        emailObj.createDraft(reader.getUsername(), testSubject);
        inboxObj.clickDraftsLink();
        draftsObj = new InboxPage(driver);
        assertTrue(draftsObj.checkIfItemPresent(testSubject));
    }

    @Test(priority = 2)
    public void editDraft() {
        draftsObj.openEmail(testSubject);
        NewEmailPage draftObj = new NewEmailPage(driver);
        draftObj.editDraftSubject(updatedSubject);
        assertTrue(draftsObj.checkIfItemPresent(updatedSubject));
    }

    @Test(priority = 3)
    public void deleteDraft() {
        draftsObj.openEmail(updatedSubject);
        NewEmailPage updatedDraftObj = new NewEmailPage(driver);
        updatedDraftObj.deleteEmail();
        assertFalse(draftsObj.checkIfItemPresent(updatedSubject));
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}