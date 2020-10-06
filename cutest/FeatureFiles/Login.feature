Feature: Login module
Scenario: Verify user is able to login with valid credentials
Given user is on home page
When Enter username and password
Then verify logout link and close the browser