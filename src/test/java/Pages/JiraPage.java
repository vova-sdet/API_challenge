package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class JiraPage {

    public JiraPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "login-form-username")
    public WebElement usernameBox;

    @FindBy(id = "login-form-password")
    public WebElement passwordBox;

    @FindBy(id = "login")
    public WebElement loginButton;

    @FindBy(xpath = "//a[contains(@title,'Manage')]")
    public WebElement boardsButton;

    @FindBy(xpath = "//li[@id='rapidb_lnk_1']//a[.='TEC board']")
    public WebElement tecBoard;

    @FindBy(xpath = "//ul[@class='aui-nav']//span[.='Backlog']")
    public WebElement backlogButton;

    @FindBy(xpath = "//div[contains(@class,'js-issue')]//span[@class='ghx-inner']")
    public List<WebElement> listOfIssues;

    @FindBy(xpath = "//span[@class='ghx-type']")
    public List<WebElement> listOfIssueTypes;

    @FindBy(xpath = "//div[@class= 'user-content-block']//p")
    public WebElement issueDescription;

}
