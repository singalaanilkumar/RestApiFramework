package LibraryApi;


import ReusableMethods.ReUsableMethods;
import Payload.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ParameterizeWithDataProviderOnAddBookApi {

    @Test(dataProvider="BooksData")
    public void SendingParametersWithMultipleDataToAddBookPayLoad(String isbn,String aisle)
    {
        RestAssured.baseURI = "http://216.10.245.166";
        String AddBookResponse =given().log().all().header("Content-Type","application/json")
                .body(payload.SendParameterAddBook(isbn,aisle))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js= ReUsableMethods.rawToJson(AddBookResponse);
        String id=  js.get("ID");
        System.out.println(id);
    }

    @DataProvider(name="BooksData")
    public Object[][]  getData()
    {
//array=collection of elements
//multidimensional array= collection of arrays
        return new Object[][] { {"oj0cwty","934563"},{"cwetedfe","4ff253"}, {"ok4dmfet","53vdg3"} };
    }
}