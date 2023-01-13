package EcommerceApiTest;

import Pojo.LoginResponseForEcom;
import Pojo.OrderDetailForEcom;
import Pojo.OrdersForEcom;
import Pojo.LoginRequestForEcom;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

public class ECommerceAPITest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
//Handle SSL issues use this relaxedHTTPSValidation()

        /** Login */
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).build();

        LoginRequestForEcom loginRequest = new LoginRequestForEcom();
        loginRequest.setUserEmail("singalaanilkumar@gmail.com");
        loginRequest.setUserPassword("AnilClient@1234");


        RequestSpecification reqLogin = given().relaxedHTTPSValidation().log().all().spec(req).body(loginRequest);
        LoginResponseForEcom loginResponse = reqLogin.when().post("/api/ecom/auth/login").then().log().all().extract().response()
                .as(LoginResponseForEcom.class);
        System.out.println(loginResponse.getToken());
        String token = loginResponse.getToken();
        System.out.println(loginResponse.getUserId());
        String userId = loginResponse.getUserId();

        /** Add Product */
        RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token)
                .build();

        RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseReq).param("productName", "Laptop")
                .param("productAddedBy", userId).param("productCategory", "fashion")
                .param("productSubCategory", "shirts").param("productPrice", "11500")
                .param("productDescription", "Lenova").param("productFor", "men")
                .multiPart("productImage", new File("C:\\Users\\as61837\\Documents\\GitHub\\RestApi\\InProgressIcon.png"));

        String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product").
                then().log().all().extract().response().asString();
        JsonPath js = new JsonPath(addProductResponse);
        String productId = js.get("productId");


        /**Create Order*/
        RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token).setContentType(ContentType.JSON)
                .build();
        OrderDetailForEcom orderDetail = new OrderDetailForEcom();
        orderDetail.setCountry("India");
        orderDetail.setProductOrderedId(productId);

        List<OrderDetailForEcom> orderDetailList = new ArrayList<OrderDetailForEcom>();
        orderDetailList.add(orderDetail);
        OrdersForEcom orders = new OrdersForEcom();
        orders.setOrders(orderDetailList);

        RequestSpecification createOrderReq = given().log().all().spec(createOrderBaseReq).body(orders);

        String responseAddOrder = createOrderReq.when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();
        System.out.println(responseAddOrder);
        JsonPath js1 = new JsonPath(responseAddOrder);
        String OrdersNumber = js1.get("orders[0]");
        System.out.println("ordersNumber is = " + OrdersNumber);

        /**view order details Product */

        RequestSpecification ViewOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("id", OrdersNumber)
                .addHeader("authorization", token).build();
        RequestSpecification ViewOrderReq = given().log().all().spec(ViewOrderBaseReq);

        String VewOrderDetailsResponse = ViewOrderReq.when().get("/api/ecom/order/get-orders-details").then().log().all().
                extract().response().asString();
        System.out.println(VewOrderDetailsResponse);

      /**Delete Product */
        RequestSpecification deleteProdBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token).setContentType(ContentType.JSON)
                .build();

        RequestSpecification deleteProdReq = given().log().all().spec(deleteProdBaseReq).pathParam("productId", productId);

        String deleteProductResponse = deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().
                extract().response().asString();

        JsonPath js2 = new JsonPath(deleteProductResponse);

        Assert.assertEquals("Product Deleted Successfully", js2.get("message"));
    }
}
