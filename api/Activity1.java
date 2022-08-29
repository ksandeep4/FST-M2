package activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity1 {
    @Test(priority = 1)
    public void postPetDetails() {
        String baseURI = "https://petstore.swagger.io/v2/pet";
        String reqBody = "{\"id\": 77999,\"name\": \"Boxer\",\"status\": \"alive\" }";
        Response response = given().contentType(ContentType.JSON).body(reqBody)
                .when().post(baseURI);
        String responseBody = response.getBody().asPrettyString();
        System.out.println("Response body is : " + responseBody);
        response.then().statusCode(200);
        response.then().body("id", equalTo(77999));
        response.then().body("status", equalTo("alive"));

    }

    @Test(priority = 2)
    public void getPetDetails() {
        String baseURI = "https://petstore.swagger.io/v2/pet";
        Response response = given().contentType(ContentType.JSON).pathParam("petId", 77999)
                .when().get(baseURI + "/{petId}");
        String responseBody = response.getBody().asPrettyString();
        System.out.println("Response body is : " + responseBody);
        response.then().statusCode(200);
        response.then().body("id", equalTo(77999));
        response.then().body("status", equalTo("alive"));

    }

    @Test(priority = 3)
    public void deletePetDetails() {
        String baseURI = "https://petstore.swagger.io/v2/pet";
        Response response = given().contentType(ContentType.JSON).pathParam("petId", 77999)
                .when().delete(baseURI + "/{petId}");
        String responseBody = response.getBody().asPrettyString();
        System.out.println("Response body is : " + responseBody);
        response.then().statusCode(200);
        response.then().body("message", equalTo("77999"));

    }
}
