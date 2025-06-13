#Feature: Delete user by first name
#
  #Background:
    #Given base URI is set
#
  #Scenario: Delete user using userFirstName
    #Given user sends DELETE request with created userFirstName
    #When request is submitted to delete the user
    #Then API should return status code 200 for successful deletion

    
    
    Feature: Delete user by userId

  Background:
    Given base URI is set

  Scenario: Delete user using userId
    Given user sends DELETE request with created userId
    When request is submitted to delete user by userId
    Then API should return status code 200 for userId deletion
    