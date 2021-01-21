package Tests.GmailTestSuite;

import com.automation.businessobjects.gmail.CreateDraftFlow;
import com.automation.businessobjects.gmail.LoginFlow;
import com.automation.dataproviders.ConfigFileReader;
import com.automation.dataproviders.TestDataGenerator;
import com.automation.driver.WebDriverManager;
import com.automation.pageobjects.gmail.InboxPage;
import com.automation.pageobjects.gmail.NewEmailPage;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class GmailTest {

    private ConfigFileReader reader;

    @BeforeMethod
    public void initialize() {
        reader = new ConfigFileReader();
    }

    @Test(priority = 0)
    public void getLastEmails() {
        WebDriver driver = WebDriverManager.getDriver();
        driver.get(reader.getGmailUrl());
        LoginFlow flow = new LoginFlow(driver);
        flow.login();
        int numOfLastEmails = 5;
        InboxPage inboxObj = new InboxPage(driver);
        inboxObj.printLastEmails(inboxObj.getLastEmails(numOfLastEmails));
        assertEquals(inboxObj.getLastEmails(numOfLastEmails).size(), numOfLastEmails);
    }

    @Test(priority = 1)
    public void createDraft() {
        WebDriver driver = WebDriverManager.getDriver();
        driver.get(reader.getGmailUrl());
        LoginFlow loginFlow = new LoginFlow(driver);
        loginFlow.login();
        String originalSubject = "subject" + TestDataGenerator.generateRandomInt(1000, 10000);
        InboxPage inboxObj = new InboxPage(driver);
        CreateDraftFlow createDraftFlow = new CreateDraftFlow(driver);
        createDraftFlow.createDraft(originalSubject);
        inboxObj.clickDraftsLink();
        InboxPage draftsObj = new InboxPage(driver);
        assertTrue(draftsObj.checkIfItemPresent(originalSubject));
    }

    @Test(priority = 2)
    public void editDraft() {
        WebDriver driver = WebDriverManager.getDriver();
        driver.get(reader.getGmailUrl());
        LoginFlow loginFlow = new LoginFlow(driver);
        loginFlow.login();
        String originalSubject = "subject" + TestDataGenerator.generateRandomInt(1000, 10000);
        InboxPage inboxObj = new InboxPage(driver);
        CreateDraftFlow createDraftFlow = new CreateDraftFlow(driver);
        createDraftFlow.createDraft(originalSubject);
        inboxObj.clickDraftsLink();
        InboxPage draftsObj = new InboxPage(driver);
        draftsObj.openEmail(originalSubject);
        NewEmailPage draftObj = new NewEmailPage(driver);
        String updatedSubject = "subject" + TestDataGenerator.generateRandomInt(10000, 100000);
        draftObj.editDraftSubject(updatedSubject);
        assertTrue(draftsObj.checkIfItemPresent(updatedSubject));
    }

    @Test(priority = 3)
    public void deleteDraft() {
        WebDriver driver = WebDriverManager.getDriver();
        driver.get(reader.getGmailUrl());
        LoginFlow loginFlow = new LoginFlow(driver);
        loginFlow.login();
        String originalSubject = "subject" + TestDataGenerator.generateRandomInt(1, 1000);
        InboxPage inboxObj = new InboxPage(driver);
        CreateDraftFlow createDraftFlow = new CreateDraftFlow(driver);
        createDraftFlow.createDraft(originalSubject);
        inboxObj.clickDraftsLink();
        InboxPage draftsObj = new InboxPage(driver);
        draftsObj.openEmail(originalSubject);
        NewEmailPage updatedDraftObj = new NewEmailPage(driver);
        updatedDraftObj.deleteEmail();
        assertFalse(draftsObj.checkIfItemPresent(originalSubject));
    }
}