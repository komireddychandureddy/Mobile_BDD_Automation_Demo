Feature: Iagree Page
  #@MobileTest
  Scenario: Iagree page validation
  Given I launched the application and click on Join button
  And I Accept the privacy policy
  And I click on continue button
  Then I should navigate to Screen asking to enter email address
  
   # @MobileTest
  Scenario Outline: Email adress Validation
  Given I launched the application and click on Join button
  And I Accept the privacy policy
  And I click on continue button
  And I enter a valid "Email" address
  And I tap on next
  Then I should navigate to Screen asking to set your password
  Examples:
  |Email|
  |abc@gmail.com|
  
   # @MobileTest
  Scenario Outline: Password screen validation
   Given I launched the application and click on Join button
  And I Accept the privacy policy
  And I click on continue button
  And I enter a valid "Email" address
  And I tap on next
  And I enter a valid "password"
  And I tap on next
  Then I should navgiate to the page asking to enter memorable word.
  Examples:
  |Email        |password|
  |abc@gmail.com|abdcd123A@|
  
  #@MobileTest
 Scenario: memorable word screen validation at Registration
  Given I launched the application and click on Join button
 And I Accept the privacy policy
 And I click on continue button
 And I enter a valid "Email" address
 And I tap on next
 And I enter a valid "password"
 And I tap on next
 When I navigate to the page asking to enter memorable word.
 And I enter memorable "word"
 Then next button should be activated
 
  

  