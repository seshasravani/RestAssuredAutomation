
        package stepdefinitions;

    import io.cucumber.java.en.*;
    import io.restassured.RestAssured;
    import io.restassured.response.Response;
    import org.testng.Assert;
    import utils.ConfigReader;

    public class GetSteps {

        private Response response;

        // Positive scenario - with authorization

        @Given("base URI is set for GET all users with auth")
        public void base_uri_is_set_for_get_all_users_with_auth() {
            //RestAssured.baseURI = ConfigReader.getProperty("base.url");
        }

        @When("user sends a GET request with valid credentials to fetch all users")
        public void user_sends_a_get_request_with_valid_credentials_to_fetch_all_users() {
            response = RestAssured
                    .given()
                    .auth().preemptive()
                    .basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
                    .header("Content-Type", "application/json")
                    .get(ConfigReader.getProperty("get.all.users.endpoint"));

//            System.out.println("==== GET All Users Response (With Auth) ====");
//            System.out.println(response.getBody().asPrettyString());
        }

        @Then("API should return status code 200 with users list")
        public void api_should_return_status_code_200_with_users_list() {
            Assert.assertEquals(response.getStatusCode(), 200);
            String responseBody = response.getBody().asString();
            Assert.assertTrue(responseBody.contains("userId"), "Response does not contain userId");
        }
       
        // userid getsteps
        
        
        @Given("user sends GET request with created userId")
        public void user_sends_get_request_with_created_user_id() {
            String userId = PostSteps.getCreatedUserId();
            String endpoint = ConfigReader.getProperty("get.user.by.id.endpoint") + "/" + userId;
            
            System.out.println("created user id is:"+userId);

            response = RestAssured
                    .given()
                    .auth().preemptive()
                    .basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
                    .get(endpoint);

            System.out.println("GET by ID Response:");
            System.out.println(response.getBody().asPrettyString());
        }

        @Then("API should return user details with status code 200")
        public void api_should_return_user_details_with_status_code_200() {
            Assert.assertEquals(response.getStatusCode(), 200);
        }
//get by first name
        
        @Given("user sends GET request with created userFirstName")
        public void user_sends_get_request_with_created_user_first_name() {
        	String userFirstName = PostSteps.getCreatedUserFirstName();
            String endpoint = ConfigReader.getProperty("get.user.by.firstname.endpoint") + "/" + userFirstName;
          //  setCreatedUserFirstName(createdUserFirstName); // You should have a setter
            
            System.out.println("created user name is:"+userFirstName);

            response = RestAssured
                    .given()
                    .auth().preemptive()
                    .basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
                    .get(endpoint);

            System.out.println("GET by user first name Response:");
            System.out.println(response.getBody().asPrettyString());
        	
        	
        	
           // throw new io.cucumber.java.PendingException();
        }
        
        
        
        
        

        // Negative scenario - no auth

        @Given("base URI is set for GET all users without auth")
        public void base_uri_is_set_for_get_all_users_without_auth() {
            //RestAssured.baseURI = ConfigReader.getProperty("base.url");
        }

        @When("user sends a GET request without credentials to fetch all users")
        public void user_sends_a_get_request_without_credentials_to_fetch_all_users() {
            response = RestAssured
                    .given()
                    .header("Content-Type", "application/json")
                    .get(ConfigReader.getProperty("get.all.users.endpoint"));

            System.out.println("==== GET All Users Response (Without Auth) ====");
            System.out.println(response.getBody().asPrettyString());
        }

        @Then("API should return status code 401 unauthorized")
        public void api_should_return_status_code_401_unauthorized() {
            Assert.assertEquals(response.getStatusCode(), 401);
        }
    }


