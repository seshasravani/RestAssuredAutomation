@post
Feature: Create a user
Background:
    Given base URI is set


  Scenario: Create user using data from Excel
    Given user sends request with all required fields from Excel
    When request is submitted to create user
    Then API should return status code 201
