package deserializeGoT;

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
import java.util.*;

public class GetMalesFemalesInHouse {

//    GET https://api.got.show/api/map/characters
//    Deserialize using POJO class
//    Count number of Male and Female characters in every house

    @Test
    public void countMalesFemales() throws URISyntaxException, IOException {

        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder uriBuilder = new URIBuilder();
        // https://api.got.show/api/map/characters
        URI uri = uriBuilder.setScheme("https").setHost("api.got.show").setPath("api/map/characters").build();

        HttpGet get = new HttpGet(uri);
        get.setHeader("Accept", "application/json");

        HttpResponse response = client.execute(get);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        GoT_Pojo goT_pojo = objectMapper.readValue(response.getEntity().getContent(), GoT_Pojo.class);
        List<GotCharacter> gotCharacters = goT_pojo.getData();

        Map<String, String> houseGender = new HashMap<>();

        Set<String> houses = new HashSet<>();

        for (GotCharacter character : gotCharacters) {
            houses.add(character.getHouse());
        }

        Set<String> listOfHomeless = new HashSet<>();
        List<String> housesList = new ArrayList<>(houses);

        for (int i = 0; i < housesList.size(); i++) {

            if (housesList.get(i) != null) {
                String genderStatement = null;
                int maleCount = 0;
                int femaleCount = 0;

                for (int a = 0; a < gotCharacters.size(); a++) {
                    if (housesList.get(i).equals(gotCharacters.get(a).getHouse())) {
                        if (gotCharacters.get(a).isMale()) {
                            maleCount++;
                        } else {
                            femaleCount++;
                        }
                    }

                    if (gotCharacters.get(a).getHouse() == null) {
                        listOfHomeless.add(gotCharacters.get(a).getName());
                    }

                    genderStatement = housesList.get(i) + " contains " + maleCount + " males and " + femaleCount + " females.";
                    houseGender.put(housesList.get(i), genderStatement);
                    houseGender.put(null, "There are " + listOfHomeless.size() + " homeless people!");
                }
            }
        }

        for (String house : housesList) {
            System.out.println(houseGender.get(house));
        }

        System.out.println("There are " + gotCharacters.size() + " characters!");
        System.out.println("There are " + houses.size() + " houses!");

    }
}
