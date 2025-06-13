package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import pojos.User;
import pojos.UserAddress;
import utils.ExcelReader;
import utils.ConfigReader;

import java.util.List;
import java.util.Map;

public class PostSteps {

    private Response response;
    private static final String EXCEL_PATH = "src/test/resources/userdata (1).xlsx";
    private static final String SHEET_NAME = "Sheet1";
    private static Map<String, String> currentData;

    private User user;               // Reusable User object
    private UserAddress address;     // Reusable UserAddress object

    private static String userId;           // Captured after successful POST
    private static String userFirstName;    // Captured after successful POST

    // ===================== Reusable Builder =====================
    private void prepareUserFromExcel() {
        ExcelReader reader = new ExcelReader(EXCEL_PATH);
        List<Map<String, String>> allData = reader.getData(SHEET_NAME);
        currentData = allData.get(0);

        address = new UserAddress();
        address.setPlotNumber(currentData.get("plotNumber"));
        address.setStreet(currentData.get("street"));
        address.setState(currentData.get("state"));
        address.setCountry(currentData.get("country"));
        address.setZipCode(Long.parseLong(currentData.get("zipCode")));

        user = new User();
        user.setUserFirstName(currentData.get("userFirstName"));
        user.setUserLastName(currentData.get("userLastName"));
        user.setUserContactNumber(Long.parseLong(currentData.get("userContactNumber")));
        user.setUserEmailId(currentData.get("userEmailId"));
        user.setUserAddress(address);
    }

    // ===================== Positive Scenario =====================
    @Given("user sends request with all required fields from Excel")
    public void user_sends_request_with_all_required_fields_from_excel() {
        RestAssured.baseURI = ConfigReader.getProperty("base.url");
        prepareUserFromExcel();

        response = RestAssured
                .given()
                .auth().preemptive()
                .basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
                .header("Content-Type", "application/json")
                .body(user)
                .post(ConfigReader.getProperty("create.user.endpoint"));

        System.out.println("Positive Scenario Response:");
        System.out.println(response.getBody().asPrettyString());

        // Capture userId and first name for reuse in GET steps
        userId = response.jsonPath().getString("userId");
        userFirstName = response.jsonPath().getString("userFirstName");
    }

    @When("request is submitted to create user")
    public void request_is_submitted_to_create_user() {
        // Request already sent in Given
    }

    @Then("API should return status code 201")
    public void api_should_return_status_code_201() {
        Assert.assertEquals(response.getStatusCode(), 201);
    }

    // ===================== Negative Scenario: No Auth =====================
    @Given("user sends POST request without authentication")
    public void user_sends_post_request_without_authentication() {
        RestAssured.baseURI = ConfigReader.getProperty("base.url");
        prepareUserFromExcel();

        response = RestAssured
                .given()
                // no auth here
                .header("Content-Type", "application/json")
                .body(user)
                .post(ConfigReader.getProperty("create.user.endpoint"));

        System.out.println("Negative Scenario (No Auth) Response:");
        System.out.println(response.getBody().asPrettyString());
    }

    @Then("API should return status code 401 for unauthorized")
    public void api_should_return_status_code_401_for_unauthorized() {
        Assert.assertEquals(response.getStatusCode(), 401);
    }

    // ===================== Getters for Sharing Data =====================
    public static String getCreatedUserId() {
        return userId;
    }

    public static String getCreatedUserFirstName() {
        return userFirstName;
    }
    
   
}