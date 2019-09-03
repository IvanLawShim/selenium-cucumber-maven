Feature: Login Feature
  Verify if user is able to Login in to the admin site

Scenario: Login as an Admin
    Given user is on homepage
    When user navigates to Login Page
    And user enters username "demo_admin"
    And user enters password "HCW1Pe1Y3C8/eSN"
    Then success message is displayed "demo_admin"

Scenario: Login with a non-existing account
    Given user is on homepage
    When user navigates to Login Page
    And user enters username "test"
    And user enters password "tespass"
    Then user is prompted for an error	