Feature: Validating Place API's
@AddPlace @Regression
Scenario Outline: Verify if place is being succesfully added using AddplaceAPI
  Given Add Place Payload with "<name>" "<language>" "<address>"
  When user calls "AddPlaceAPI" with "POST" http method
  Then the API call is sucess with status code 200
  And "status" in response body is "OK"
  And "scope" in response body is "APP"
  And verify place_Id created maps to "<name>" using "getPlaceAPI"

Examples:
    |name    |language |address          |
    |AAhouse |English  |World Cross cnter|
#    |BBhouse |Spanish  |Sea Cross cnter|

@DeletePlace @Regression
Scenario: Verify if Delete Place functionality is working

    Given Delete Paylod
    When user calls "deletePlaceAPI" with "POST" http method
    Then the API call is sucess with status code 200
    And "status" in response body is "OK"
    
    