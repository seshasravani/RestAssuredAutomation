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

public class DeleteByFirstNameSteps {

    private Response response;
    private String userFirstName;

    @Given("user is created for deletion by firstName")
    public void user_is_created_for_deletion_by_first_name() {
        RestAssured.baseURI = ConfigReader.getProperty("base.url");

        ExcelReader reader = new ExcelReader("src/test/resources/userdata (1).xlsx");
        List<Map<String, String>> data = reader.getData("Sheet1");
        Map<String, String> row = data.get(1);  // ✅ Pick second row

        UserAddress address = new UserAddress();
        address.setPlotNumber(row.get("plotNumber"));
        address.setStreet(row.get("street"));
        address.setState(row.get("state"));
        address.setCountry(row.get("country"));
        address.setZipCode(Long.parseLong(row.get("zipCode")));

        User user = new User();
        user.setUserFirstName(row.get("userFirstName"));
        user.setUserLastName(row.get("userLastName"));
        user.setUserContactNumber(Long.parseLong(row.get("userContactNumber")));
        user.setUserEmailId(row.get("userEmailId"));
        user.setUserAddress(address);

        response = RestAssured
                .given()
                .auth().preemptive()
                .basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
                .header("Content-Type", "application/json")
                .body(user)
                .post(ConfigReader.getProperty("create.user.endpoint"));

        Assert.assertEquals(response.getStatusCode(), 201);
        userFirstName = response.jsonPath().getString("userFirstName");
    }

    @When("user is deleted using userFirstName")
    public void user_is_deleted_using_user_first_name() {
        response = RestAssured
                .given()
                .auth().preemptive()
                .basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
                .delete("/uap/deleteuser/username/" + userFirstName);

        System.out.println("DELETE by userFirstName response:");
        System.out.println(response.getBody().asPrettyString());
    }

    @Then("API should return status code 200 for userFirstName deletion")
    public void api_should_return_status_code_200_for_user_first_name_deletion() {
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
