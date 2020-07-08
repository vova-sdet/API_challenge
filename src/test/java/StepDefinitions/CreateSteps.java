package StepDefinitions;

import Pages.JiraPage;
import Utils.ConfigReader;
import Utils.Driver;
import Utils.Helpers;
import Utils.PayloadUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class CreateSteps {

    WebDriver driver = Driver.getDriver();
    JiraPage jiraPage = new JiraPage(driver);

    @Given("the user sends create a story in JIRA with {string}, {string}, {string}")
    public void the_user_sends_create_a_story_in_JIRA_with(String summary, String description, String issueType) throws URISyntaxException, IOException {

        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder uriBuilder = new URIBuilder();
        URI uri = uriBuilder.setScheme("http").setHost("localhost").setPort(8080).setPath("rest/api/2/issue").build();

        HttpPost post = new HttpPost(uri);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
        post.setHeader("Cookie", Helpers.getAuthCookieValue());

        HttpEntity entity = new StringEntity(PayloadUtils.getJiraCreateIssuePayload(summary, description, issueType));
        post.setEntity(entity);

        HttpResponse response = client.execute(post);

        Assert.assertEquals(HttpStatus.SC_CREATED, response.getStatusLine().getStatusCode());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> parsedResponse = objectMapper.readValue(response.getEntity().getContent(),
                new TypeReference<Map<String, String>>() {});

        for (String key : parsedResponse.keySet()) {
            System.out.printf("%s -> %s\n", key, parsedResponse.get(key));
        }
    }


    @Then("the user validates story creation through UI and {string}, {string}, {string}")
    public void the_user_validates_story_creation_through_UI_and(String summary, String description, String issueType) throws InterruptedException {

        WebDriver driver = Driver.getDriver();
        driver.get(ConfigReader.getProperties("jiraUrl"));

        try {
            if (jiraPage.usernameBox.isDisplayed())  {
                jiraPage.usernameBox.sendKeys(ConfigReader.getProperties("username"));
                jiraPage.passwordBox.sendKeys(ConfigReader.getProperties("password"));
                jiraPage.loginButton.click();
            }
        } catch (NoSuchElementException e) {
            System.out.println("You are already logged in!!!");
        }

        jiraPage.boardsButton.click();
        jiraPage.tecBoard.click();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", jiraPage.backlogButton);
        Thread.sleep(750);

        WebElement lastIssueCreated = jiraPage.listOfIssues.get(jiraPage.listOfIssues.size()-1);
        WebElement lastIssueType = jiraPage.listOfIssueTypes.get(jiraPage.listOfIssueTypes.size()-1);

        Assert.assertEquals(summary, lastIssueCreated.getText());
        Assert.assertEquals(issueType, lastIssueType.getAttribute("title"));

        lastIssueCreated.click();
        Thread.sleep(250);

        String lastIssueDescription = jiraPage.issueDescription.getText();
        Assert.assertEquals(description, lastIssueDescription);
    }
}
