package activities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class Activity3 {
    RequestSpecification requestspec;
    ResponseSpecification responsespec;
    @BeforeClass
    public void setup() {
        requestspec = new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/v2/pet")
                .setContentType(ContentType.JSON)
                .build();
        responsespec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectResponseTime(lessThan(5L), TimeUnit.SECONDS)
                .build();
    }
    @Test(priority = 1)
    public void addPet() {
        String reqBody = "{\"id\": 70000, \"name\": \"Rocky\", \"status\": \"alive\"}";
        Response response = given().spec(requestspec)
                .body(reqBody)
                .when().post();
        System.out.println(response.getBody().asString());
        response.then().spec(responsespec);
    }
    @Test(priority = 2)
    public void getPet() {
        given().spec(requestspec).pathParam("petId", 70000).
                when().get("/{petId}").then().spec(responsespec).log().body();
    }
    @Test(priority = 3)
    public void deletePet() {
        given().spec(requestspec).pathParam("petId", 70000).
                when().delete("/{petId}").then().statusCode(200).log().body();
    }
}