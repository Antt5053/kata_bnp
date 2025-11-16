Feature: Test booking endpoint

  Scenario: Get booking ids
    Given the API endpoint is "/booking"
    When I send a GET request
    Then the response status code should be 200

  Scenario: Get booking by id
    Given a booking already exist
    When I retrieve the booking by ID
    Then the booking details should match booking

  Scenario: Update booking
    Given a booking already exist
    When I update the booking with new data
    Then the booking details should reflect the update
    And the response status code should be 200

  Scenario: Delete a booking
    Given a booking already exist
    When I delete the booking
    Then the booking should no longer exist

  Scenario: Create a booking
    Given The firstname of the person booking is "Wilson"
    Then The lastname of the person booking is "Doudou"
    Then The price of the booking is 100
    Then The the deposit of the booking is payed
    Then the booking date start the "2023-01-01"
    Then the booking date end the "2023-01-05"
    Then The additional needs is "Cake"
    Then I create the booking
    Then the response status code should be 200
    And the response should match the booking schema

  Scenario: Create a booking without firstname
    Given The lastname of the person booking is "Doudou"
    Then The price of the booking is 100
    Then The the deposit of the booking is payed
    Then the booking date start the "2023-01-01"
    Then the booking date end the "2023-01-05"
    Then The additional needs is "Cake"
    Then I create the booking
    Then the response status code should be 500

  Scenario: Create a booking without lastname
    Given The firstname of the person booking is "Wilson"
    Then The price of the booking is 100
    Then The the deposit of the booking is payed
    Then the booking date start the "2023-01-01"
    Then the booking date end the "2023-01-05"
    Then The additional needs is "Cake"
    Then I create the booking
    Then the response status code should be 500

  Scenario: Create a booking without price
    Given The firstname of the person booking is "Wilson"
    Then The lastname of the person booking is "Doudou"
    Then The the deposit of the booking is payed
    Then the booking date start the "2023-01-01"
    Then the booking date end the "2023-01-05"
    Then The additional needs is "Cake"
    Then I create the booking
    Then the response status code should be 500

  Scenario: Create a booking the payed field
    Given The firstname of the person booking is "Wilson"
    Then The lastname of the person booking is "Doudou"
    Then The price of the booking is 100
    Then the booking date start the "2023-01-01"
    Then the booking date end the "2023-01-05"
    Then The additional needs is "Cake"
    Then I create the booking
    Then the response status code should be 500

  Scenario: Create a booking without start date
    Given The firstname of the person booking is "Wilson"
    Then The lastname of the person booking is "Doudou"
    Then The price of the booking is 100
    Then The the deposit of the booking is payed
    Then the booking date end the "2023-01-05"
    Then The additional needs is "Cake"
    Then I create the booking
    Then the response status code should be 500

  Scenario: Create a booking without end date
    Given The firstname of the person booking is "Wilson"
    Then The lastname of the person booking is "Doudou"
    Then The price of the booking is 100
    Then The the deposit of the booking is payed
    Then the booking date start the "2023-01-01"
    Then The additional needs is "Cake"
    Then I create the booking
    Then the response status code should be 500

  Scenario: Create a booking without date
    Given The firstname of the person booking is "Wilson"
    Then The lastname of the person booking is "Doudou"
    Then The price of the booking is 100
    Then The the deposit of the booking is payed
    Then The additional needs is "Cake"
    Then I create the booking
    Then the response status code should be 500

  Scenario: Create a booking without additional needs
    Given The firstname of the person booking is "Wilson"
    Then The lastname of the person booking is "Doudou"
    Then The price of the booking is 100
    Then The the deposit of the booking is payed
    Then the booking date start the "2023-01-01"
    Then the booking date end the "2023-01-05"
    Then I create the booking
    Then the response status code should be 200

  Scenario: Create a booking price is negative
    Given The firstname of the person booking is "Wilson"
    Then The lastname of the person booking is "Doudou"
    Then The price of the booking is -100
    Then The the deposit of the booking is payed
    Then the booking date start the "2023-01-01"
    Then the booking date end the "2023-01-05"
    Then The additional needs is "Cake"
    Then I create the booking
    Then the response status code should be 500

  Scenario: Create a booking with wrong values
    Given I create a new booking with wrong values
    Then the response status code should be 500

  Scenario: Create a booking with checkout before checkin
    Given I create a new booking with checking before checkout
    Then the response status code should be 400

  Scenario: Create, retrieve, update, and delete a booking
    Given I create a new booking
    Then the response should match the booking schema
    And the response status code should be 200
    And the booking details should match the created data
    When I retrieve the booking by ID
    Then the booking details should match
    When I update the booking with new data
    Then the booking details should reflect the update
    When I partialy update the booking with new data
    Then the booking details should reflect the partial update
    When I delete the booking
    Then the booking should no longer exist