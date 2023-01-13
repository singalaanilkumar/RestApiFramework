package HashMapAddBookApi;
import ReusableMethods.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.util.HashMap;
import static io.restassured.RestAssured.given;

public class AbbBookUsingHashApp {
    public static void main(String[] args) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Learn Appium Automation with Java");
        map.put("isbn", "bdfcdzx");
        map.put("aisle","204073" );//change everytime this value
        map.put("author", "John foe");

	/*	HashMap<String, Object>  map2 = new HashMap<>();
		map.put("lat", "12");
		map.put("lng", "34");
		map.put("location", map2);*/


        RestAssured.baseURI="http://216.10.245.166";
        String resp=given().log().all().
                header("Content-Type","application/json").
                body(map).
                when().
                post("/Library/Addbook.php ").
                then().assertThat().statusCode(200).
                extract().response().asString();
        System.out.println(resp);

       /* JsonPath js1 = new JsonPath(resp) ;
        String id=js1.get("ID");
        System.out.println(id);*/

        JsonPath js= ReUsableMethods.rawToJson(resp);
        String Id=js.get("ID");
        System.out.println(Id);

    }
}
