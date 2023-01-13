package CourseJsonLogicsCode;

import Payload.payload;
import io.restassured.path.json.JsonPath;

public class CourseJsonLogics {
    public static void main(String[] args) {
        // TODO Auto-generated method stub


        JsonPath js = new JsonPath(payload.CoursePrice());

        //Print No of courses returned by API
        int count = js.getInt("courses.size()");
        System.out.println("Print No of courses returned by API :- " + count);

        //Print Purchase Amount
        int totalAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println("Print Purchase Amount :- " +totalAmount);

        //Print Title of the first course
        String titleFirstCourse = js.get("courses[0].title");
        System.out.println("Print Title of the first course :- " +titleFirstCourse);

        //Print All course titles and their respective Prices
        System.out.println("Print All course titles and their respective Prices :- ");
        for (int i = 0; i < count; i++) {
            String courseTitles = js.get("courses[" + i + "].title");
            System.out.println(courseTitles);
            System.out.println(js.get("courses[" + i + "].price").toString());
        }

        //Print no of copies sold by RPA Course
        System.out.println("Print no of copies sold by RPA Course :- ");
        for (int i = 0; i < count; i++) {
            String courseTitles = js.get("courses[" + i + "].title");
            if (courseTitles.equalsIgnoreCase("RPA")) {
                int copies = js.get("courses[" + i + "].copies");
                System.out.println(copies);
                break;
            }
        }


    }
}
