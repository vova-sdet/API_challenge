package pojoDeserializeHW.SWspecies;

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

public class FilterSpecies {

//    GET https://swapi.dev/api/species
//    Deserialize response using POJO class
//    Filter all species by classification. ex: mammal -> Human, Yoda's species etc...

    @Test
    public void sortSpecies() throws URISyntaxException, IOException {

        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder uriBuilder = new URIBuilder();
        // https://swapi.dev/api/species
        URI uri = uriBuilder.setScheme("https").setHost("swapi.dev").setPath("api/species").build();
        HttpGet get = new HttpGet(uri);
        get.setHeader("Accept", "application/json");

        HttpResponse response = client.execute(get);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SWSpecies_Pojo species_pojo = objectMapper.readValue(response.getEntity().getContent(), SWSpecies_Pojo.class);
        ;
        List<Result> results = species_pojo.getResults();
        Map<String, List<String>> filter = new HashMap<>();

        for (int i = 0; i < results.size(); i++) {
            filter.put(results.get(i).getClassification(), new ArrayList<String>());
        }

        Set<String> classifications = filter.keySet();
        List<String> names = new ArrayList<>();

        for (String classification : classifications) {
            names = filter.get(classification);
            for (int i = 0; i < results.size(); i++) {
                if (classification.equals(results.get(i).getClassification())) {
                    names.add(results.get(i).getName());
                }
            }
            filter.replace(classification, names);
        }

        for (String classification : classifications) {
            System.out.println(filter.get(classification) + " belong to such classification: " + classification);
        }
    }
}
