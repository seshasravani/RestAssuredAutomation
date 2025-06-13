package stepdefinitions;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import utils.ConfigReader;

public class BackgroundSteps {

    @Given("base URI is set")
    public void base_uri_is_set() {
        RestAssured.baseURI = ConfigReader.getProperty("base.url");
        System.out.println("Base URI set to: " + RestAssured.baseURI);
    }
}
