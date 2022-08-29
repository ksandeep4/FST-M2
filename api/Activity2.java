package activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.io.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity2 {
        String baseURI = " https://petstore.swagger.io/v2/user";

        @Test(priority = 1)
        public void postUserDetails() throws IOException {
            // Import JSON file
            File file = new File("src/test/resources/input.json");
            FileInputStream inputJSON = new FileInputStream(file);
            // Get all bytes from JSON file
            byte[] bytes = new byte[(int) file.length()];
            inputJSON.read(bytes);
            // Read JSON file as String
            String reqBody = new String(bytes, "UTF-8");
            System.out.println(reqBody);
            Response response = given().contentType(ContentType.JSON).body(reqBody)
                    .when().post(baseURI);
            String responseBody = response.getBody().asPrettyString();
            System.out.println("Response body is : " + responseBody);
            inputJSON.close();
            //response.then().statusCode(200);
            response.then().body("code", equalTo(200));
            response.then().body("message", equalTo("1234"));
        }
        @Test(priority = 2)
        public void getUserDetails() {
            // Import JSON file to write to
            File outputJSON = new File("src/test/resources/output.json");
            Response response = given().contentType(ContentType.JSON).pathParam("username", "Sandeep")
                    .when().get(baseURI + "/{username}");
            String responseBody = response.getBody().asPrettyString();
            System.out.println("Response body is : " + responseBody);
            try {
                // Create JSON file
                outputJSON.createNewFile();
                // Write response body to external file
                FileWriter writer = new FileWriter(outputJSON.getPath());
                writer.write(responseBody);
                writer.close();
            } catch (IOException excp) {
                excp.printStackTrace();
            }
            response.then().statusCode(200);
            response.then().body("id", equalTo(1234));
            response.then().body("username", equalTo("Sandeep"));
            response.then().body("firstName", equalTo("Sandeep"));
            response.then().body("lastName", equalTo("Kachneria"));
            response.then().body("email", equalTo("sandeep.kachneria@test.com"));
            response.then().body("password", equalTo("sandeep"));
            response.then().body("phone", equalTo("12345678"));
        }
        @Test(priority = 3)
        public void deleteUserDetails() {
            Response response = given().contentType(ContentType.JSON).pathParam("username", "Sandeep")
                    .when().delete(baseURI + "/{username}");
            String responseBody = response.getBody().asPrettyString();
            System.out.println("Response body is : " + responseBody);
            response.then().statusCode(200);
            response.then().body("message", equalTo("Sandeep"));
        }
}
