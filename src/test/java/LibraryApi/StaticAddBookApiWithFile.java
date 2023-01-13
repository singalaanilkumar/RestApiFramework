package LibraryApi;


import ReusableMethods.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class StaticAddBookApiWithFile {

    @Test(priority = 1)
    public void AddBookApiWithAddBookJsonFileFromBelowMethod() throws IOException {
        RestAssured.baseURI = "http://216.10.245.166";
        String AddBookResponse =given().log().all().header("Content-Type","application/json")
                .body(GenerateStringFromResource("C:\\Users\\as61837\\Documents\\GitHub\\RestApi\\src\\main\\AddBook.json"))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js= ReUsableMethods.rawToJson(AddBookResponse);
        String id=  js.get("ID");
        System.out.println(id);
    }

    @Test(priority = 2)
    public void AddBookApiWithAddBookJsonFile() throws IOException {
        RestAssured.baseURI = "http://216.10.245.166";
        String AddBookResponse =given().log().all().header("Content-Type","application/json")
                .body(new String(Files.readAllBytes(Paths.get("C:\\Users\\as61837\\Documents\\GitHub\\RestApi\\src\\main\\AddBook.json"))))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js= ReUsableMethods.rawToJson(AddBookResponse);
        String id=  js.get("ID");
        System.out.println(id);
    }

    public static String GenerateStringFromResource(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}