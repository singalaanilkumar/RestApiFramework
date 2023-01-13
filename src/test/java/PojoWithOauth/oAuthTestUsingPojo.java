package PojoWithOauth;

import Pojo.Api;
import Pojo.OauthGetCourse;
import Pojo.WebAutomation;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class oAuthTestUsingPojo {
    public static void main(String[] args) throws InterruptedException {
        // TODO Auto-generated method stub
        String[] courseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};

        String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AWgavdf0sMrskFbFW2jJ8en7Cmi4X37jqJAUB0DOOejoZlwNyPST3GPN85tbkR4ggVMqQQ&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";

        String partialcode = url.split("code=")[1];
        String code = partialcode.split("&scope")[0];
        System.out.println(code);

        //Get Access Token
        String accessTokenResponse = given().urlEncodingEnabled(false)
                .queryParams("code", code)
                .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParams("grant_type", "authorization_code")
                .when().log().all()
                .post("https://www.googleapis.com/oauth2/v4/token").asString();
        JsonPath js = new JsonPath(accessTokenResponse);
        String accessToken = js.getString("access_token");




        /* .expect().defaultParser(Parser.JSON) :- it is useful to identify response as json,
        else it's confusing our java object is xml r json if we use expect().defaultParser(Parser.JSON) then it identified treat as json response*/

        //Actual Request
        OauthGetCourse gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
                .when()
                .get("https://rahulshettyacademy.com/getCourse.php").as(OauthGetCourse.class);
        System.out.println(gc);
        System.out.println(gc.getLinkedIn());
        System.out.println(gc.getInstructor());
        System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());


        List<Api> apiCourses = gc.getCourses().getApi();
        System.out.println(apiCourses.size());
        for (int i = 0; i < apiCourses.size(); i++) {
            if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
                System.out.println(apiCourses.get(i).getPrice());
            }
        }

        //Get the course names of WebAutomation
        ArrayList<String> a = new ArrayList<String>();


        List<WebAutomation> w = gc.getCourses().getWebAutomation();

        for (int j = 0; j < w.size(); j++) {
            a.add(w.get(j).getCourseTitle());
        }

        List<String> expectedList = Arrays.asList(courseTitles);

        Assert.assertTrue(a.equals(expectedList));


        //System.out.println(response);


    }

}
