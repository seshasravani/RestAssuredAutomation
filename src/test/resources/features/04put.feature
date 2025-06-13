        Feature: Update created user
    
     Background:
    Given base URI is set

  Scenario: Modify user details using userId from POST response
    Given user prepares an update request for the created user
    When user submits the update request
    Then API should return status code 200 for successful update
    