package JiraTestApi;

import ReusableMethods.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class JiraAPICreateIssueTest {
    @Test
    public void JiraAPICreateIssue()
    {
        //Creating Issue/Defect
        RestAssured.baseURI= "http://localhost:8080";
        SessionFilter session = new SessionFilter();
        String  res=given().header("Content-Type", "application/json").body("{"+
                "\"fields\": {"+
                "\"project\":{"+
                "\"key\": \"RES\""+
                "},"+
                "\"summary\": \"Issue 5 for adding comment\","+
                "\"description\": \"Creating my second bug\","+
                "\"issuetype\": {"+
                "\"name\": \"Bug\""+
                "}"+
                "}}").filter(session).when().post("/rest/api/2/issue").then().statusCode(201).extract().response().toString();

        JsonPath js= ReUsableMethods.rawToJson(res);
        String id=js.get("id");
        System.out.println(id);
    }
}
