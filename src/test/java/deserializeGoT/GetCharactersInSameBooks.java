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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GetCharactersInSameBooks {

//    GET https://api.got.show/api/map/characters
//    Deserialize using POJO class
//    Count number of characters who appeared in the same books

    @Test
    public void getCharactersFromSameBook() throws URISyntaxException, IOException {

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
        Map<List<String>, Integer> result = new HashMap<>();

        for (int i = 0; i < gotCharacters.size(); i++) {
            List<String> books = gotCharacters.get(i).getBooks();
            if (!result.containsKey(books)) {
                result.put(books, 1);
            } else {
                int count = result.get(gotCharacters.get(i).getBooks());
                result.replace(books, ++count);
            }
        }

        Set<List<String>> booksSet = result.keySet();

        for (List<String> set : booksSet) {
            System.out.println("There are " + result.get(set) + " character(s) who appeared in this set of books: " + set);
        }
    }
}
