package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.ConfigReader;
import utils.ExcelReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class PatchSteps {

    private Response response;
    private static final String EXCEL_PATH = "src/test/resources/userdata (1).xlsx";
    private static final String SHEET_NAME = "Patch";

  
    @When("user sends PATCH request to update specific fields from Excel")
    public void user_sends_patch_request_to_update_specific_fields_from_excel() {
        // Get created userId from earlier POST step
        String userId = PostSteps.getCreatedUserId();

        // Read Excel data
        ExcelReader reader = new ExcelReader(EXCEL_PATH);
        List<Map<String, String>> patchDataList = reader.getData(SHEET_NAME);
        Map<String, String> patchData = patchDataList.get(0);  // Use first row for PATCH

        Map<String, Object> partialUpdateMap = new HashMap<>();

        // Dynamically add only available fields
        patchData.forEach((key, value) -> {
            if (value != null && !value.trim().isEmpty()) {
                if (key.equals("userContactNumber") || key.equals("zipCode")) {
                    partialUpdateMap.put(key, Long.parseLong(value));
                } else {
                    partialUpdateMap.put(key, value);
                }
            }
        });

        // Send PATCH request
        response = RestAssured
                .given()
                .auth().preemptive().basic(
                        ConfigReader.getProperty("username"),
                        ConfigReader.getProperty("password"))
                .contentType("application/json")
                .pathParam("userId", userId)
                .body(partialUpdateMap)
                .patch(ConfigReader.getProperty("patch.user.endpoint")); // /uap/updateuserfields/{userId}

        System.out.println("PATCH Response:");
        System.out.println(response.getBody().asPrettyString());
    }

    @Then("API should return status code {int} for successful patch update")
    public void api_should_return_status_code_for_successful_patch_update(Integer expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        System.out.println("Actual PATCH status code: " + actualStatusCode);
        assertEquals(actualStatusCode, expectedStatusCode.intValue());
    }

}
