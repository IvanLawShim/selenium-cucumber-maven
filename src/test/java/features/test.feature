Feature: Login Test Case
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
	    
	Scenario: Forgot password situation
			Given user is on homepage
	    When user navigates to Login Page
	    And user enters username "syncjohn.qa4@gmail.com"
	    And user enters password "123N"
	    And user forgot password "test_merchant"
	    And user fixes password "merch@123"
	    When user navigates to Login Page
	    And user enters username "syncjohn.qa4@gmail.com"
	    And user enters password "merch@123"
	    Then success message is displayed "Azura"

	Scenario: Customer logs in
			Given user is on homepage
			When user navigates to Login Register Page
			And user enters username "test_user" and password "merch@123"
			And user logs in and wait for confirmation
			Then user is confirm to arrived in the user profile
			