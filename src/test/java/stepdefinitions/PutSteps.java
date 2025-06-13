package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import pojos.User;
import pojos.UserAddress;
import utils.ConfigReader;
import utils.ExcelReader;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class PutSteps {

    private Response response;
    private User updatedUser;
    private UserAddress updatedAddress;
    private static final String EXCEL_PATH = "src/test/resources/userdata (1).xlsx";
    private static final String SHEET_NAME = "Sheet2";

    @Given("user prepares an update request for the created user")
    public void user_prepares_an_update_request_for_the_created_user() {
        // Fetch userId from POST step
        String userId = PostSteps.getCreatedUserId();

        // Read second row (index 1) for update data
        ExcelReader reader = new ExcelReader(EXCEL_PATH);
        List<Map<String, String>> allData = reader.getData(SHEET_NAME);
        Map<String, String> updatedData = allData.get(0);  // second row

        updatedAddress = new UserAddress();
        updatedAddress.setPlotNumber(updatedData.get("plotNumber"));
        updatedAddress.setStreet(updatedData.get("street"));
        updatedAddress.setState(updatedData.get("state"));
        updatedAddress.setCountry(updatedData.get("country"));
        updatedAddress.setZipCode(Long.parseLong(updatedData.get("zipCode")));

        updatedUser = new User();
        updatedUser.setUserFirstName(updatedData.get("userFirstName"));
        updatedUser.setUserLastName(updatedData.get("userLastName"));
        updatedUser.setUserContactNumber(Long.parseLong(updatedData.get("userContactNumber")));
        updatedUser.setUserEmailId(updatedData.get("userEmailId"));
        updatedUser.setUserAddress(updatedAddress);

        // Send PUT request
        response = RestAssured
                .given()
                .auth().preemptive().basic(
                        ConfigReader.getProperty("username"),
                        ConfigReader.getProperty("password"))
                .contentType("application/json")
                .pathParam("userId", userId)
                .body(updatedUser)
                .put(ConfigReader.getProperty("update.user.endpoint")); // Should be /uap/updateuser/{userId}

        System.out.println("PUT Response:");
        System.out.println(response.getBody().asPrettyString());
    }

    @When("user submits the update request")
    public void user_submits_the_update_request() {
        // Already sent in Given
    }

    @Then("API should return status code 200 for successful update")
    public void api_should_return_status_code_200_for_successful_update() {
        assertEquals(response.getStatusCode(), 200);
    }
}
