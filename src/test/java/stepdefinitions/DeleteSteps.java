package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import utils.ConfigReader;

public class DeleteSteps {

    private Response response;
    private String userId;

    @Given("user sends DELETE request with created userId")
    public void user_sends_delete_request_with_created_user_id() {
        RestAssured.baseURI = ConfigReader.getProperty("base.url");
        userId = PostSteps.getCreatedUserId();  // ← get userId from PostSteps
    }

    @When("request is submitted to delete user by userId")
    public void request_is_submitted_to_delete_user_by_user_id() {
        response = RestAssured
                .given()
                .auth().preemptive().basic(
                    ConfigReader.getProperty("username"),
                    ConfigReader.getProperty("password"))
                .when()
                .delete("/uap/deleteuser/" + userId);

        System.out.println("DELETE Response:");
        System.out.println(response.getBody().asPrettyString());
    }

    @Then("API should return status code 200 for userId deletion")
    public void api_should_return_status_code_200_for_user_id_deletion() {
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
