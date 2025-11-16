Feature: Auth token flow

  Scenario: Generate a token
    When I create an auth token
    Then the token should not be empty