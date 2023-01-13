package GraphQl;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GraphQlTest {
    /*public static void main(String[] args) {*/

    @Test
    public void GraphQlTestWithQueryAndMutation(){
        /** query */
        String queryResponse= given().log().all().header("Content-Type","application/json").body("{\"query\":\"query($episodeId : Int!){\\n  episode(episodeId: $episodeId) {\\n    name\\n    air_date\\n    id\\n    characters {\\n      name\\n      species\\n      image\\n      origin {\\n        id\\n      }\\n    }\\n  }\\n}\\n\\n\",\"variables\":{\"episodeId\":10}}").when().post("https://rahulshettyacademy.com/gq/graphql").then().extract().response().asString();
        System.out.println("query response is " + queryResponse);
        JsonPath js = new JsonPath(queryResponse) ;
        System.out.println(js.getString("data.episode.name"));

        /** mutation */
        String mutationResponse= given().log().all().header("Content-Type","application/json")
                .body("{\"query\":\"mutation($locationName:String!){\\n  createLocation(location : {name:$locationName, type: \\\"hjvb\\\", dimension: \\\"anilb\\\"})\\n  {\\n    id\\n  }\\n}\\n\",\"variables\":{\"locationName\":\"india\"}}").when().post("https://rahulshettyacademy.com/gq/graphql").then().extract().response().asString();
        System.out.println("mutation response is " + mutationResponse);

        JsonPath js1 = new JsonPath(mutationResponse) ;
        System.out.println(js1.getInt("data.createLocation.id"));
    }
    }

