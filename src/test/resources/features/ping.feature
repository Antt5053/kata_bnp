Feature: Test ping

  Scenario: Get health check
    Given the API endpoint is "/ping"
    When I send a GET request
    Then the response status code should be 201




