Feature: VID-485

  @DontSaveReport
  @485_1
  @485_2
  @485_3
  @485_4
  Scenario: To verify baseline comparison does not copy value in the current report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "COLT" and last name as "BISHOP"
    Then viewer should load the study for patient
    When I click on previous "echo pediatric" study from the study list tab
    And I select "Echo Final Report" with date "05_05_2020" and time "5_13_55" for amending
    Then the Report Editor window should load
    When I click on "comparison baseline" icon
    And select "Echo Final Report" with date "05_14_2020" and time "15_35" for comparison
    And I maximize report editor
    And I click on "Impressions" under left panel
    Then all "Impressions" subtabs should have asterisk
    When I switch to "General" subtab
    Then "Urgent" field under Classification section is unselected and highlighted with red rectangle
    And below fields should be highlighted with red rectangle
      | Abnormal Echo  |
      | Cannot exclude |
    When I switch to "Favorites" subtab
    Then "Testingggg" text "should not" be present in Free Text
    When I switch to "Free text Comparison" subtab
    Then below fields should be highlighted with red rectangle
      | Other     |
      | MR worse  |
      | MR better |
      | MS worse  |
    When I switch to "Mass Pericardium Shunts & Vessels" subtab
    Then below fields should be highlighted with red rectangle
      | No Shunt         |
      | Possible PFO ASD |
      | Dilated Ao Root  |
    When I switch to "Chambers & Valves" subtab
    Then below fields should be highlighted with red rectangle
      | No WMA |