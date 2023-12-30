@VID-387
Feature: VID-387

  @387_1
    @387_2
    @DontSaveReport
  Scenario Outline: Verify phrases related to rest imaging are populated to the report preview
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "ECHO" and last name as "TEST"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Nuclear" option
    And select the "<reportType>" option
    Then the Report Editor window should load
    When I click on "Procedure&Symptoms" in report editor left panel
    And I maximize report editor
    And select "Rest imaging" on report editor
    And I enter below details in the "Myocardial Perfusion Scan Report" fields
      | field        | type | value |
      | Rest imaging | text | 20    |
    And I restore report editor
    Then phrases related to rest imaging should be displayed on the preview
    When I maximize report editor
    And unselect "Rest imaging(Yellow)" on report editor
    And I enter below details in the "Myocardial Perfusion Scan Report" fields
      | field        | type | value |
      | Rest imaging | text | 20    |
    And I restore report editor
    Then phrases related to rest imaging should be displayed on the preview
    Examples:
      | reportType                                       |
      | New Myocardial Perfusion Scan Preliminary Report |
      | New Myocardial Perfusion Scan Report             |