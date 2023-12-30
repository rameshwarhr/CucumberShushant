Feature: 46532

  @46532_1
  Scenario Outline: To verify tab turns yellow when subtab "Codes" are added in prelim and final report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "LYNDA" and last name as "SMART"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<reports>" option
    Then the Report Editor window should load
    When I click on "FitWidth" icon
    Then report dynamic preview should be stretched to "fit the width" of the screen
    When I click on "Procedure" under left panel
    And select an "Treadmill/ Stress Test" from the "Echo CPT Codes" category
    Then Report Dynamic Preview should get updated with "Treadmill/ Stress"
    When I click on "Indications" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    When I click on "Interventions" under left panel
    And select a "Interventions" from the "Interventions codes" category
    Then Report Dynamic Preview should get updated with "Interventions"
    When I click on "Diagnosis" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    And below tabs should be highlighted in yellow
      | Procedure     |
      | Indications   |
      | Interventions |
      | Diagnosis     |
    Examples:
      | reports                                     |
      | Pediatric TTE Preliminary Report (Standard) |
      | Pediatric TTE Final Report (Standard)       |

  @46532_2
  Scenario Outline: To verify tab turns yellow when subtab "Favourites" are added in prelim and final report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "LYNDA" and last name as "SMART"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<reports>" option
    Then the Report Editor window should load
    When I click on "FitWidth" icon
    Then report dynamic preview should be stretched to "fit the width" of the screen
    When I click on "Procedure" under left panel
    And I switch to "Favourites" subtab
    And provide "TestPhrase" under the "Favourites Text Area"
    And I "Save" it as "TestPhrase"
    Then "template" should be available in the Template box
    When I click on "Indications" under left panel
    And I switch to "Favourites" subtab
    And provide "AutoPhrase" under the "Favourites Text Area"
    And I "Save" it as "AutoPhrase"
    Then "template" should be available in the Template box
    When I click on "Interventions" under left panel
    And I switch to "Favourites" subtab
    And provide "AutoTest" under the "Favourites Text Area"
    And I "Save" it as "AutoTest"
    Then "template" should be available in the Template box
    When I click on "Diagnosis" under left panel
    And I switch to "Favourites" subtab
    And provide "TestAuto" under the "Favourites Text Area"
    And I "Save" it as "TestAuto"
    Then "template" should be available in the Template box
    And below tabs should be highlighted in yellow
      | Procedure     |
      | Indications   |
      | Interventions |
      | Diagnosis     |
    Examples:
      | reports                                     |
      | Pediatric TTE Preliminary Report (Standard) |
      | Pediatric TTE Final Report (Standard)       |

  @46532_3
  Scenario Outline: To verify tab turns yellow when other subtabs are added in the prelim and final report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "LYNDA" and last name as "SMART"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<reports>" option
    Then the Report Editor window should load
    When I click on "FitWidth" icon
    Then report dynamic preview should be stretched to "fit the width" of the screen
    When I click on "Procedure" under left panel
    When I select below checkbox on "Saline Contrast" subtab
      | Saline Contrast |
    When I click on "Indications" under left panel
    And I click on "FreeText" subtab
    And type "This is a CT Test" in "Free Text Area" on report editor
    When I click on "Diagnosis" under left panel
    And I click on "FreeText" subtab
    And type "This is a CT Test" in "Free Text Area" on report editor
    Then below tabs should be highlighted in yellow
      | Procedure     |
      | Indications   |
      | Interventions |
      | Diagnosis     |
    Examples:
      | reports                                     |
      | Pediatric TTE Preliminary Report (Standard) |
      | Pediatric TTE Final Report (Standard)       |

  @46532_4
  Scenario Outline: To verify tab turns yellow for different templates and patients
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "<firstname>" and last name as "<lastname>"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "<reportoption>" option
    And select the "<reporttype>" option
    Then the Report Editor window should load
    When I click on "FitWidth" icon
    Then report dynamic preview should be stretched to "fit the width" of the screen
    When I click on "Indications/History" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    When I click on "Diagnosis" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    When I click on "Procedure" under left panel
    And select an "Treadmill/ Stress Test" from the "Echo CPT Codes" category
    Then Report Dynamic Preview should get updated with "Treadmill/ Stress"
    And below tabs should be highlighted in yellow
      | Indications/History |
      | Diagnosis           |
      | Procedure           |
    Examples:
      | firstname | lastname | reportoption  | reporttype                           |
      | SANSA     | STARK    | Echo & Stress | Echo Final Report                    |
      | BENJEN    | STARK    | MRI           | MRI Report                           |
      | ROBERT    | STARK    | Nuclear       | New Myocardial Perfusion Scan Report |

  @46532_5
  Scenario Outline: To verify color changes from yellow to white after removing the data entered in the subtabs
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "LYNDA" and last name as "SMART"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<reports>" option
    Then the Report Editor window should load
    When I click on "FitWidth" icon
    Then report dynamic preview should be stretched to "fit the width" of the screen
    When I click on "Procedure" under left panel
    And I switch to "Saline Contrast" subtab
    When I select below checkbox on "Saline Contrast" subtab
      | Saline Contrast |
    And I uncheck below checkbox on "Saline Contrast" subtab
      | Saline Contrast |
    And I switch to "Other" subtab
    And type "Auto Other Test" in "Other Text Area" on report editor
    And I clear the Other Text Area
    When I click on "Indications" under left panel
    And I switch to "FreeText" subtab
    And type "This is a CT Test" in "Free Text Area" on report editor
    And I clear the FreeText Text Area
    And I switch to "Favorites" subtab
    And provide "AutoPhrase" under the "Favourites Text Area"
    And I clear the Favourites Text Area
    When I click on "Interventions" under left panel
    And I switch to "Favourites" subtab
    And provide "AutoTest" under the "Favourites Text Area"
    And I clear the Favourites Text Area
    When I click on "Diagnosis" under left panel
    And I switch to "FreeText" subtab
    And type "This is a CT Test" in "Free Text Area" on report editor
    And I clear the FreeText Text Area
    And I switch to "Favourites" subtab
    And provide "TestAuto" under the "Favourites Text Area"
    And I clear the Favourites Text Area
    Then below tabs should be in white
      | Procedure     |
      | Indications   |
      | Interventions |
      | Diagnosis     |
    Examples:
      | reports                                     |
      | Pediatric TTE Preliminary Report (Standard) |
      | Pediatric TTE Final Report (Standard)       |

  @46532_6
  Scenario Outline: To verify that tabs and subtabs are yellow when we amend a particular report after signing the prelim and final report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "LYNDA" and last name as "SMART"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I unselect "Send report to referring physician automatically after signing" checkbox
    And save the preferences
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<reports>" option
    Then the Report Editor window should load
    When I click on "FitWidth" icon
    Then report dynamic preview should be stretched to "fit the width" of the screen
    When I click on "Procedure" under left panel
    And select an "Treadmill/ Stress Test" from the "Echo CPT Codes" category
    Then Report Dynamic Preview should get updated with "Treadmill/ Stress"
    When I click on "Indications" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    When I click on "Interventions" under left panel
    And select a "Interventions" from the "Interventions codes" category
    Then Report Dynamic Preview should get updated with "Interventions"
    When I click on "Diagnosis" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    And below tabs should be highlighted in yellow
      | Procedure     |
      | Indications   |
      | Interventions |
      | Diagnosis     |
    When I sign the report
    And I close report viewer
    Then signed report should be displayed under Reports tab
    When I select amend report
    Then the Report Editor window should load
    And below tabs should be highlighted in yellow
      | Procedure     |
      | Indications   |
      | Interventions |
      | Diagnosis     |
    Examples:
      | reports                                     |
      | Pediatric TTE Preliminary Report (Standard) |
      | Pediatric TTE Final Report (Standard)       |

  @46532_7
  Scenario: To verify the data is present in final report when prelim report is signed with data in all four tabs
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "LYNDA" and last name as "SMART"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I unselect "Send report to referring physician automatically after signing" checkbox
    And save the preferences
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    Then the Report Editor window should load
    When I click on "FitWidth" icon
    Then report dynamic preview should be stretched to "fit the width" of the screen
    When I click on "Procedure" under left panel
    And select an "Treadmill/ Stress Test" from the "Echo CPT Codes" category
    Then Report Dynamic Preview should get updated with "Treadmill/ Stress"
    When I click on "Indications" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    When I click on "Interventions" under left panel
    And select a "Interventions" from the "Interventions codes" category
    Then Report Dynamic Preview should get updated with "Interventions"
    When I click on "Diagnosis" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    And below tabs should be highlighted in yellow
      | Procedure     |
      | Indications   |
      | Interventions |
      | Diagnosis     |
    When I sign the report
    And I close report viewer
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Final Report (Standard)" option
    Then the Report Editor window should load
    And below tabs should be highlighted in yellow
      | Procedure     |
      | Indications   |
      | Interventions |
      | Diagnosis     |

  @46532_8
  Scenario: To verify tabs and subtabs are yellow when we amend final report having data in 2 different tabs from prelim and final report each
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "LYNDA" and last name as "SMART"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I unselect "Send report to referring physician automatically after signing" checkbox
    And save the preferences
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    Then the Report Editor window should load
    When I click on "FitWidth" icon
    Then report dynamic preview should be stretched to "fit the width" of the screen
    When I click on "Procedure" under left panel
    And select an "Treadmill/ Stress Test" from the "Echo CPT Codes" category
    Then Report Dynamic Preview should get updated with "Treadmill/ Stress"
    When I click on "Indications" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    And below tabs should be highlighted in yellow
      | Procedure   |
      | Indications |
    When I sign the report
    And I close report viewer
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Final Report (Standard)" option
    Then the Report Editor window should load
    When I click on "Interventions" under left panel
    And select a "Interventions" from the "Interventions codes" category
    Then Report Dynamic Preview should get updated with "Interventions"
    When I click on "Diagnosis" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    And below tabs should be highlighted in yellow
      | Interventions |
      | Diagnosis     |
    When I sign the report
    And I close report viewer
    Then signed report should be displayed under Reports tab
    When I select amend report
    Then the Report Editor window should load
    And below tabs should be highlighted in yellow
      | Procedure     |
      | Indications   |
      | Interventions |
      | Diagnosis     |

  @46532_9
  Scenario: To verify tab turns yellow when subtab "Codes" are added in prelim and final report after unfinalizing the study
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "LYNDA" and last name as "SMART"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Final Report (Standard)" option
    Then the Report Editor window should load
    When I click on "FitWidth" icon
    Then report dynamic preview should be stretched to "fit the width" of the screen
    When I click on "Procedure" under left panel
    And select an "Treadmill/ Stress Test" from the "Echo CPT Codes" category
    Then Report Dynamic Preview should get updated with "Treadmill/ Stress"
    When I click on "Indications" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    When I click on "Interventions" under left panel
    And select a "Interventions" from the "Interventions codes" category
    Then Report Dynamic Preview should get updated with "Interventions"
    When I click on "Diagnosis" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    And below tabs should be highlighted in yellow
      | Procedure     |
      | Indications   |
      | Interventions |
      | Diagnosis     |
    When I sign the report
    And I close report viewer
    And close the dicom viewer
    And right click on the study from study list
    And I select "Edit Study Attributes" option from study list right click menu
    And I change the status of the final report to "reverted"
    Then the final report status should change to "reverted"
    When I open a study for patient with first name as "LYNDA" and last name as "SMART"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Final Report (Standard)" option
    Then the Report Editor window should load
    When I click on "FitWidth" icon
    Then report dynamic preview should be stretched to "fit the width" of the screen
    When I click on "Procedure" under left panel
    And select an "Treadmill/ Stress Test" from the "Echo CPT Codes" category
    Then Report Dynamic Preview should get updated with "Treadmill/ Stress"
    When I click on "Indications" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    When I click on "Interventions" under left panel
    And select a "Interventions" from the "Interventions codes" category
    Then Report Dynamic Preview should get updated with "Interventions"
    When I click on "Diagnosis" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    And below tabs should be highlighted in yellow
      | Procedure     |
      | Indications   |
      | Interventions |
      | Diagnosis     |

  @46532_10
  Scenario: To verify the combinations of different tabs i.e the one tab which is to be checked and the any other tab except to be checked
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "LYNDA" and last name as "SMART"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I unselect "Send report to referring physician automatically after signing" checkbox
    And save the preferences
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    Then the Report Editor window should load
    When I click on "FitWidth" icon
    Then report dynamic preview should be stretched to "fit the width" of the screen
    When I click on "Procedure" under left panel
    And select an "Treadmill/ Stress Test" from the "Echo CPT Codes" category
    Then Report Dynamic Preview should get updated with "Treadmill/ Stress"
    When I click on "Indications" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    When I click on "Atria" under left panel
    And I click on "FreeText" subtab
    And type "This is a CT Test" in "Free Text Area" on report editor
    When I click on "Atrial Septum" under left panel
    And I click on "FreeText" subtab
    And type "This is a CT Test" in "Free Text Area" on report editor
    Then below tabs should be highlighted in yellow
      | Procedure     |
      | Indications   |
      | Atria         |
      | Atrial Septum |

  @46532_12
  Scenario: To verify any 2 tabs turn yellow and do baseline comparison with self
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "LYNDA" and last name as "SMART"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    Then the Report Editor window should load
    When I click on "FitWidth" icon
    Then report dynamic preview should be stretched to "fit the width" of the screen
    When I click on "Procedure" under left panel
    And select an "Treadmill/ Stress Test" from the "Echo CPT Codes" category
    Then Report Dynamic Preview should get updated with "Treadmill/ Stress"
    When I click on "Indications" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    And below tabs should be highlighted in yellow
      | Procedure   |
      | Indications |
    When I sign the report
    And I click on "Set report comparison baseline" icon
    And right click on the "Pediatric Echo Preliminary Report"
    And select "Set comparison baseline"
    Then below tabs and subtabs should have asterisk and codes should not be selected
      | tabs        | subtabs | codes                     |
      | Procedure   | Codes   | Treadmill/ Stress Test    |
      | Indications | Codes   | Typhoid fever,unspecified |

  @46532_13
  Scenario: To verify yellowing of tabs on entering data works when we add some codes and then do baseline comparison
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "LYNDA" and last name as "SMART"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    Then the Report Editor window should load
    When I click on "FitWidth" icon
    Then report dynamic preview should be stretched to "fit the width" of the screen
    When I click on "Procedure" under left panel
    And select an "Treadmill/ Stress Test" from the "Echo CPT Codes" category
    Then Report Dynamic Preview should get updated with "Treadmill/ Stress"
    When I click on "Indications" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    And below tabs should be highlighted in yellow
      | Procedure   |
      | Indications |
    When I sign the report
    And close report viewer
    And I select amend report
    Then the Report Editor window should load
    When I click on "Set report comparison baseline" icon
    And right click on the "Pediatric Echo Preliminary Report"
    And select "Set comparison baseline"
    Then below tabs should be highlighted in yellow having asterisk
    And below tabs and subtabs should have asterisk and codes should not be selected
      | tabs        | subtabs | codes                     |
      | Procedure   | Codes   | Treadmill/ Stress Test    |
      | Indications | Codes   | Typhoid fever,unspecified |

  @46532_14
  Scenario: To verify yellowing of tabs on entering data works when we do baseline comparison and then add codes
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "LYNDA" and last name as "SMART"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    And I sign the report
    And close report viewer
    And I select amend report
    Then the Report Editor window should load
    When I click on "Set report comparison baseline" icon
    And right click on the "Pediatric Echo Preliminary Report"
    And select "Set comparison baseline"
    When I click on "FitWidth" icon
    Then report dynamic preview should be stretched to "fit the width" of the screen
    When I click on "Procedure" under left panel
    And select an "Treadmill/ Stress Test" from the "Echo CPT Codes" category
    Then Report Dynamic Preview should get updated with "Treadmill/ Stress"
    When I click on "Indications" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    And below tabs and subtabs should have asterisk and codes should not be selected
      | tabs        | subtabs | codes                     |
      | Procedure   | Codes   | Treadmill/ Stress Test    |
      | Indications | Codes   | Typhoid fever,unspecified |

  @46532_15
  Scenario: To verify yellowing of tabs on entering data works when we do baseline comparison between 2 reports having less and more number of codes respectively
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "LYNDA" and last name as "SMART"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    When I click on "Procedure" under left panel
    And select an "Treadmill/ Stress Test" from the "Echo CPT Codes" category
    Then Report Dynamic Preview should get updated with "Treadmill/ Stress"
    And I sign the report
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    Then report editor should open within the same window
    When I click on "Indications" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    When I click on "Diagnosis" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    And I sign the report
    And close report viewer
    And I select amend report for the first report
    Then the Report Editor window should load
    When I click on "Set report comparison baseline" icon
    And right click on the "Pediatric Echo Preliminary Report" for the second report
    And select "Set comparison baseline"
    And below tabs and subtabs should have asterisk and codes should not be selected
      | tabs        | subtabs | codes                     |
      | Procedure   | Codes   | Treadmill/ Stress Test    |
      | Indications | Codes   | Typhoid fever,unspecified |

  @46532_16
  Scenario: To verify yellowing of tabs on entering data works when we do baseline comparison between 2 reports having more and less number of codes respectively
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "LYNDA" and last name as "SMART"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    When I click on "Procedure" under left panel
    And select an "Treadmill/ Stress Test" from the "Echo CPT Codes" category
    Then Report Dynamic Preview should get updated with "Treadmill/ Stress"
    When I click on "Indications" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    When I sign the report
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    Then report editor should open within the same window
    When I click on "Diagnosis" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    When I sign the report
    And close report viewer
    And I select amend report for the second report
    Then the Report Editor window should load
    When I click on "Set report comparison baseline" icon
    And right click on the "Pediatric Echo Preliminary Report" for the first report
    And select "Set comparison baseline"
    And below tabs and subtabs should be yellow with asterisk and codes should not be selected
      | tabs        | subtabs | codes                     |
      | Procedure   | Codes   | Treadmill/ Stress Test    |
      | Indications | Codes   | Typhoid fever,unspecified |

  @46532_17
  Scenario: To verify yellowing of tabs on entering data works when we do baseline comparison between 2 reports having few and 0 number of codes respectively
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "LYNDA" and last name as "SMART"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    When I click on "Procedure" under left panel
    And select an "Treadmill/ Stress Test" from the "Echo CPT Codes" category
    Then Report Dynamic Preview should get updated with "Treadmill/ Stress"
    When I click on "Indications" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    When I sign the report
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    Then report editor should open within the same window
    When I sign the report
    And close report viewer
    And I select amend report for the first report
    Then the Report Editor window should load
    When I click on "Set report comparison baseline" icon
    And right click on the "Pediatric Echo Preliminary Report" for the second report
    And select "Set comparison baseline"
    And below tabs and subtabs should be yellow with asterisk and codes should not be selected
      | tabs        | subtabs | codes                     |
      | Procedure   | Codes   | Treadmill/ Stress Test    |
      | Indications | Codes   | Typhoid fever,unspecified |

  @46532_18
  Scenario: To verify yellowing of tabs on entering data works when we do baseline comparison between 2 reports having 0 and few number of codes respectively
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "LYNDA" and last name as "SMART"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    And I sign the report
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    Then report editor should open within the same window
    And I click on "Procedure" under left panel
    And select an "Treadmill/ Stress Test" from the "Echo CPT Codes" category
    Then Report Dynamic Preview should get updated with "Treadmill/ Stress"
    When I click on "Indications" under left panel
    And select a "Typhoid fever,unspecified" from the "ICD-10 diagnosis codes" category
    Then Report Dynamic Preview should get updated with "Typhoid fever"
    When I sign the report
    And close report viewer
    And I select amend report for the first report
    Then the Report Editor window should load
    When I click on "Set report comparison baseline" icon
    And right click on the "Pediatric Echo Preliminary Report" for the second report
    And select "Set comparison baseline"
    And below tabs and subtabs should have asterisk and codes should not be selected
      | tabs        | subtabs | codes                     |
      | Procedure   | Codes   | Treadmill/ Stress Test    |
      | Indications | Codes   | Typhoid fever,unspecified |

  @46532_19
  Scenario: To verify yellowing of tabs on entering data works between two report for same number of codes but with different sequence
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "LYNDA" and last name as "SMART"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    And I sign the report
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    Then report editor should open within the same window
    When I click on "Procedure" under left panel
    And select below codes from the "Echo CPT Codes" category
      | Treadmill/ Stress Test                        |
      | TTE Congenital Cardiac Anomalies Complete     |
      | TTE Congenital Anomalies Follow-up or Limited |
    Then Report Dynamic Preview should get updated with "Echo CPT Codes"
    When I sign the report
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    Then report editor should open within the same window
    When I click on "Procedure" under left panel
    And select below codes from the "Echo CPT Codes" category
      | TTE Congenital Anomalies Follow-up or Limited |
      | TTE Congenital Cardiac Anomalies Complete     |
      | Treadmill/ Stress Test                        |
    Then Report Dynamic Preview should get updated with "Echo CPT Codes"
    And close report viewer
    And I select amend report for the first report
    Then the Report Editor window should load
    When I click on "Set report comparison baseline" icon
    And right click on the "Pediatric Echo Preliminary Report" for the second report
    And select "Set comparison baseline"
    Then "Procedure" and subtab "Codes" should be yellow with asterisk and below "CPT Codes" should not be selected
      | TTE Congenital Anomalies Follow-up or Limited |
      | TTE Congenital Cardiac Anomalies Complete     |
      | Treadmill/ Stress Test                        |

  @46532_20
  Scenario: To verify yellowing of tabs on entering data works between two report for same number but with different codes
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "LYNDA" and last name as "SMART"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    And I sign the report
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    Then report editor should open within the same window
    When I click on "Procedure" under left panel
    And select below codes from the "Echo CPT Codes" category
      | Treadmill/ Stress Test                    |
      | TTE Congenital Cardiac Anomalies Complete |
    Then Report Dynamic Preview should get updated with "Echo CPT Codes"
    When I sign the report
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric TTE Preliminary Report (Standard)" option
    Then report editor should open within the same window
    When I click on "Procedure" under left panel
    And select below codes from the "Echo CPT Codes" category
      | TTE Congenital Anomalies Follow-up or Limited |
      | TTE - 2D w/ or w/o M-Mode Complete            |
    Then Report Dynamic Preview should get updated with "Echo CPT Codes"
    And close report viewer
    And I select amend report for the first report
    Then the Report Editor window should load
    When I click on "Set report comparison baseline" icon
    And right click on the "Pediatric Echo Preliminary Report" for the second report
    And select "Set comparison baseline"
    Then "Procedure" and subtab "Codes" should be yellow with asterisk and below "CPT Codes" should not be selected
      | TTE Congenital Anomalies Follow-up or Limited |
      | TTE - 2D w/ or w/o M-Mode Complete            |