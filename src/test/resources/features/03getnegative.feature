Feature: Get all users without authentication

Background:
    Given base URI is set

  Scenario: Fail to fetch all users without auth
    Given base URI is set for GET all users without auth
    When user sends a GET request without credentials to fetch all users
    Then API should return status code 401 unauthorized
