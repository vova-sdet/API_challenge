package RestAssured;

import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetHousesIds {

//    using RestAssured:
//    GET https://api.got.show/api/map/characters
//    get all houses and ids and store them in a map


    @Test
    public void getHousesAndIdsInMap() {

        Map<String, Object> characters = given().headers("Accept", ContentType.JSON).header("Content-Type", ContentType.JSON)
                .when().get("https://api.got.show/api/map/characters").then().statusCode(200)
                .contentType(ContentType.JSON).extract().as(new TypeRef<Map<String, Object>>() {
                });

        List<Map<String, String>> chars = (List<Map<String, String>>) characters.get("data");

        Map<String, String> result = new HashMap<>();

        for (Map<String, String> character : chars) {
            result.put(character.get("_id"), character.get("house"));
        }

        System.out.println(result.size());
    }
}
