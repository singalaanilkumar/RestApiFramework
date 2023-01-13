package LibraryApi;


import ReusableMethods.ReUsableMethods;
import Payload.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class DynamicJsonAddBookApi {

    @Test
    public void addbookapi()
    {
        RestAssured.baseURI = "http://216.10.245.166";
        String AddBookResponse =given().log().all().header("Content-Type","application/json")
                .body(payload.addbook())
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js= ReUsableMethods.rawToJson(AddBookResponse);
        String id=  js.get("ID");
        System.out.println(id);

       /* JsonPath js = new JsonPath(AddBookResponse); //for parsing Json
        String id=js.get("ID");
        System.out.println(id);*/
    }
}