Feature: Get all users with valid authentication

Background:
    Given base URI is set

  Scenario: Successfully fetch all users with valid auth
    Given base URI is set for GET all users with auth
    When user sends a GET request with valid credentials to fetch all users
    Then API should return status code 200 with users list

    Given user sends GET request with created userId
    Then API should return user details with status code 200
    
    Given user sends GET request with created userFirstName
    Then API should return user details with status code 200