package ReusableMethods;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class ReUsableMethods {
    public static JsonPath rawToJson(String response) //this is wrapper class
    {
        JsonPath js1 =new JsonPath(response);
        return js1;
    }

    public static JsonPath rawToJsonThroughResponse(Response r)
    {
        String respon=r.asString();
        JsonPath x=new JsonPath(respon);
        System.out.println(respon);
        return x;
    }
}
