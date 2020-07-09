package RestAssured;

import Pages.JiraPage;
import Utils.ConfigReader;
import Utils.Driver;
import Utils.Helpers;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NumIssuesAssigned {

//    using Http client:
//    GET http://localhost:8080/rest/api/2/search?jql=assignee=updateName
//    verify in ui, that you have exactly same number of stories assigned to the user.

    public int getNumOfIssuesFromApi() throws URISyntaxException, IOException {

        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder uriBuilder = new URIBuilder();
        URI uri = uriBuilder.setScheme("http").setHost("localhost").setPort(8080).
                setPath("rest/api/2/search").setCustomQuery("jql=assignee=test_user").build();

        HttpGet get = new HttpGet(uri);
        get.setHeader("Accept", "application/json");
        get.setHeader("Content-Type", "application/json");
        get.setHeader("Cookie", Helpers.getAuthCookieValue());

        HttpResponse response = client.execute(get);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> parsedResponse = objectMapper.readValue(response.getEntity().getContent(), new TypeReference<Map<String, Object>>() {
        });
        System.out.println(response.getStatusLine().getStatusCode());
        List<Map<String, Object>> issues = (List) parsedResponse.get("issues");
        int numOfIssuesApi = issues.size();

        return numOfIssuesApi;
    }

    @Test
    public void verifyNumOfIssuesUI() throws InterruptedException, IOException, URISyntaxException {

        WebDriver driver = Driver.getDriver();
        JiraPage jiraPage = new JiraPage(driver);

        driver.get(ConfigReader.getProperties("jiraUrl"));

        jiraPage.usernameBox.sendKeys(ConfigReader.getProperties("username"));
        jiraPage.passwordBox.sendKeys(ConfigReader.getProperties("password"));
        jiraPage.loginButton.click();

        jiraPage.boardsButton.click();
        jiraPage.tecBoard.click();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", jiraPage.backlogButton);
        Thread.sleep(500);

        List<String> assignedIssueUI = new ArrayList<>();

        for (WebElement issue : jiraPage.listOfIssues) {
            issue.click();
            Thread.sleep(250);
            String name = jiraPage.assignee.getText();
            if (name.equals("Testing Tester")) {
                assignedIssueUI.add(name);
            }
        }

        int numOfIssuesUI = assignedIssueUI.size();
        Assert.assertEquals(getNumOfIssuesFromApi(), numOfIssuesUI);
    }
}
