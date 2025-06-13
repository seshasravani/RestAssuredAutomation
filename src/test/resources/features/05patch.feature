Feature: Patch User

  Background:
    Given base URI is set

  Scenario: Partially update user details using userId
    When user sends PATCH request to update specific fields from Excel
    Then API should return status code 200 for successful patch update
