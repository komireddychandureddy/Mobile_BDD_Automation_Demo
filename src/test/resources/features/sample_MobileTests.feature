Feature: Sample test
  #@MobileTest
  Scenario Outline:  Sample test end to end scenario
  Given I launched the application and click on login button
  And I enter "Emailid" and click on next
  And I also enter "password" and click on next
  And I enter the "memorableWord" and click on next
  When I click on Your money
  And continue for finacical status
  And I select a "currency" for depositing in account
  And I enter "amount" of deposit every year
  And I select the income "mainsource"
  And I select the employment "status"
  And I tap on what is the "accountfor"
  And I select yes for Us "taxpayer"
  Then I should get a message "congratulations" and redirect to User home page
  Examples:
  |Emailid       |password     |memorableWord|currency|amount|mainsource|status    |accountfor|taxpayer|
  |abcc@gmail.com|samplepasswrd|mem          |USD     |421   |salary    |Employed  |saving    |Yes     |
  
  @MobileTest
  Scenario: Sample test
  Given I launched the application
  And I click on login Button.
  Then I should be able to see textfiled emailid
  