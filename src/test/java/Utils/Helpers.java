package Utils;

import Pojo.JiraLoginPojo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Helpers {

    public static String getAuthCookieValue() throws URISyntaxException, IOException {

        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder uriBuilder = new URIBuilder();
        URI uri = uriBuilder.setScheme("http").setHost("localhost").setPort(8080).setPath("rest/auth/1/session").build();

        HttpPost post = new HttpPost(uri);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        HttpEntity entity = new StringEntity(PayloadUtils.getJiraAuthorizationPayload());
        post.setEntity(entity);

        HttpResponse response = client.execute(post);

        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        ObjectMapper objectMapper = new ObjectMapper();
        JiraLoginPojo deserializedResponse = objectMapper.readValue(response.getEntity().getContent(), JiraLoginPojo.class);

        return String.format("%s=%s", deserializedResponse.getSession().get("name"), deserializedResponse.getSession().get("value"));
    }

}
