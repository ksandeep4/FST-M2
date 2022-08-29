package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;


public class GitHub_RestAssured_Project {

    // declare object
    RequestSpecification requestSpec;

    String SSHKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDK1fCtwGXgXPyFLYn73RrQeiInXi+9RZaweOotsFeQjY99ZtCD0Z6UlDsrwmILQlLk8X5mXkouwqlaIWqe5VnetSptdElQg8DiwVRxd7mljMCDP1P+6GVi8kZ6IAkmtZte9rKtvqrqbUTBzJRdZ2sz6aIwJCUxOBUvQo6pgbzl03xdcR/jRGM7nXIyl2nN1CZNMza7adIlymwoGyU6NAvIFgGqyLCXq91uEKBOSFOrBwtT+/no55YZoyxN6i30/6C3PsbCNbuF5WvCqAZFG+oG//AErVBh65KlMt+DTA8Uipr9Icvbe9MoLFMhGmA6TJqKm5nXkZlhT6CleoByJGhD ";
    int GitId;

    @BeforeClass
    public void setUp() {
        //Build requestSpec
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAuth(oauth2("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"))
                .setBaseUri("https://api.github.com")
                .build();
    }

    @Test(priority = 1)
    public void postKey() {
        String reqBody = "{\"title\": \"TestAPIKey\", \"key\":\"" + SSHKey + "\"}";
        Response response = given().spec(requestSpec)
                .body(reqBody)
                .when().post("/user/keys");

        // print response
        System.out.println(response.getBody().asPrettyString());
        GitId = response.then().extract().path("id");

        //Assertions
        response.then().statusCode(201).log().all();
    }

    @Test(priority = 2)
    public void getKey() {
        //Generate response
        Response response = given().log().all().spec(requestSpec).pathParam("keyId", GitId)  // URI resource /user/keys/{keyId}
                .when().get("/user/keys/{keyId}");

        // print response
        System.out.println(response.getBody().asPrettyString());

        //Assertions
        response.then().statusCode(200).log().all();
    }

    @Test(priority = 3)
    public void deleteKey() {
        //Generate response
        Response response = given().spec(requestSpec).pathParam("keyId", GitId)
                .when().delete("/user/keys/{keyId}");

        // print response
        System.out.println(response.getBody().asPrettyString());

        //Assertions
        response.then().statusCode(204).log().all();
    }
}

