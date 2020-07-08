package pojoDeserializeHW.deserializeBB;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GetRandomCharacter {

    static int numOfCharacters;

    public int getRandomId() throws URISyntaxException, IOException {

        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder uriBuilder = new URIBuilder();
        URI uri = uriBuilder.setScheme("https").setHost("breakingbadapi.com").setPath("api/characters").build();

        HttpGet get = new HttpGet(uri);
        get.setHeader("Accpet", "application/json");

        HttpResponse response = client.execute(get);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<BreakingB_Pojo> characters = objectMapper.readValue(response.getEntity().getContent(), new TypeReference<List<BreakingB_Pojo>>(){});
        numOfCharacters = characters.size();
        System.out.println(numOfCharacters);

        Random random = new Random();
        int characterId = random.nextInt(numOfCharacters);
        System.out.println(characterId);

        return characterId;
    }

    @Test
    public void getRandomCharacter() throws IOException, URISyntaxException {

        int randomId = getRandomId();

        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder uriBuilder = new URIBuilder();
        URI uri = uriBuilder.setScheme("https").setHost("breakingbadapi.com").setPath("api/characters/" + randomId).build();

        HttpGet get = new HttpGet(uri);
        get.setHeader("Accept", "application/json");

        HttpResponse response = client.execute(get);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Map<String, Object>> characters = objectMapper.readValue(response.getEntity().getContent(), new TypeReference<List<Map<String, Object>>>(){});
        System.out.println(characters.get(0).get("name"));

    }
}
