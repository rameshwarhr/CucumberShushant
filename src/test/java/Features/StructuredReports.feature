@StructuredReports
@Regression
Feature: Reports

  @Stable
  @RevertSignedReportCloseAutomatically
  @AT-219
  Scenario: Verify report & Dicom viewer window is closed after signing the reports
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    And select "Close the viewer automatically after signing" checkbox
    And save the preferences
    And I navigate to "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Consultation Final Report" option
    Then the Report Editor window should load
    When I sign the report
    Then the Report Editor window should close
    And I should return to the study list page

  @Stable
  @RevertReportPreferences
  @AT-213
  Scenario: Verify single monitor view functionality for report
    Given vidistar application is launched
    And I have logged in the application as "ADMIN" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    And select "Use just one window (may be useful when having just one screen)" checkbox
    And save the preferences
    And click on 'Restart later' button on the Restart required modal
    And I open the "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Consultation Final Report" option
    Then Report should open within the same window as the images and thumbnails

  @Stable
  @DontSaveReport
  @AT-98
  Scenario: Verify user is able to adjust the presentation diagram and same is reflected in dynamic preview
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "OB-GYN" option
    And select the "First Trimester Final Report" option
    Then the Report Editor window should load
    When I click on "Presentation diagram" in report editor
    Then report dynamic preview "should not" display diagram
    When I click on "FitWidth" icon
    And I select "Include diagram" in report editor
    Then report dynamic preview "should" display diagram
    When I adjust the presentation diagram on report editor
    Then report dynamic preview should display adjusted diagram

  @Stable
  @RevertSignedReportPrintAutomatically
  @AT-215
  Scenario: Verify the auto print functionality for Final report
    Given vidistar application is launched
    And I have logged in the application as "PHYSICIAN" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    And select "Print report automatically after signing" checkbox
    And save the preferences
    And open the "Reports" menu
    And go to the "OB-GYN" option
    And select the "First Trimester Final Report" option
    Then the Report Editor window should load
    When I sign the report
    Then print report window should be displayed

  @Stable
  @DeleteTemplate
  @AT-220
  Scenario: Verify user is able to save Normal Template
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open a study for patient with first name as "BENJAMIN" and last name as "MACDONALD"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Consultation Final Report" option
    Then the Report Editor window should load
    When I click on "Labs" in report editor left panel
    And type "This is a CT Automation Test Fax" in "Free Text Area" on report editor
    And I click on "SAVE AS TEMPLATE" icon
    Then Save Report Template window is opened
    When I enter name "AutomationTemplate" for normal template
    And I click OK
    And I close report viewer
    And click on Don't Save
    And I open the "Reports" menu
    And select the "AutomationTemplate" option
    And I click on "FITWIDTH" icon
    Then lab updates should be reflected on the Report

  @Stable
  @DontSaveReport
  @AT-64
  Scenario: Verify user is able to perform Zoom-in, Zoom-out, Fit to page & Fit Width options in report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And select the "Cath Final Report" option
    Then the Report Editor window should load
    And report should open in new window
    Then report dynamic preview should be stretched to "fit the width" of the screen
    When I click on "Zoom In" icon
    Then report should be "zoomed in"
    When I click on "Zoom Out" icon
    Then report should be "zoomed out"

  @Stable
  @UnselectLogo
  @AT-249
  Scenario:  Accreditation Logos - All On or All Off
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Service Locations" option in the settings panel
    And I set name as "Automation test SL" to add new service location
    And I select "Florida" from hubs dropdown
    And I "select" checkbox of accreditation logo
    And I save service location
    And I search "Automation test SL" in service location tab
    Then The new service location "Automation test SL" should be present
    When I log out of the application
    And log back in the application as "Operator" user
    And I enter "THOMAS^JAMES" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Edit Study Attributes" option from study list right click menu
    And I assign "Automation Test SL" from list of service location
    And I click save
    And I click on Yes
    And I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Vascular" option
    And select the "TCI Preliminary Report" option
    Then the Report Editor window should load
    When I double click on report dynamic preview
    And I click on "FitWidth" icon
    Then accreditation logo "should" be present in "TCI Preliminary" report
    When I close report viewer
    And click on Don't Save
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo & Exercise Stress Preliminary Report" option
    Then the Report Editor window should load
    When I double click on report dynamic preview
    And I click on "FitWidth" icon
    Then accreditation logo "should" be present in "Echo & Exercise Stress Preliminary" report
    When I close report viewer
    And click on Don't Save
    And I open the "Reports" menu
    And go to the "Nuclear" option
    And select the "New Myocardial Perfusion Scan Preliminary Report" option
    Then the Report Editor window should load
    When I double click on report dynamic preview
    And I click on "FitWidth" icon
    Then accreditation logo "should" be present in "New Myocardial Perfusion Scan Preliminary" report
    When I close report viewer
    And click on Don't Save
    And close the dicom viewer
    And I log out of the application
    And log back in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Service Locations" option in the settings panel
    And I search "Automation test" in service location tab
    And I edit the service location
    And I "deselect" checkbox of accreditation logo
    And I save service location
    And I log out of the application
    And log back in the application as "Operator" user
    And I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Vascular" option
    And select the "TCI Preliminary Report" option
    Then the Report Editor window should load
    And accreditation logo "should not" be present in "TCI Preliminary" report
    When I close report viewer
    And click on Don't Save
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo & Exercise Stress Preliminary Report" option
    Then the Report Editor window should load
    And accreditation logo "should not" be present in "Echo & Exercise Stress Preliminary" report
    When I close report viewer
    And click on Don't Save
    When I open the "Reports" menu
    And go to the "Nuclear" option
    And select the "New Myocardial Perfusion Scan Preliminary Report" option
    Then the Report Editor window should load
    And accreditation logo "should not" be present in "New Myocardial Perfusion Scan" report

  @Stable
  @DontSaveReport
  @AT-65
  Scenario: Verify user is able to fill the details in all the tabs and same is reflected in dynamic preview
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And select the "Cath Final Report" option
    Then the Report Editor window should load
    When I click on "FitWidth" icon
    Then report dynamic preview should be stretched to "fit the width" of the screen
    When I enter below details in the "Cath Final Report" fields
      | field                    | type | value |
      | Systolic blood pressure  | text | 120   |
      | Diastolic blood pressure | text | 80    |
    Then Report Dynamic Preview should get updated with "Patient Profile Data"
    When I click on "Indications History" under left panel
    And select an "ICD 10 Code" from the "ICD 10 Code" category
    Then Report Dynamic Preview should get updated with "ICD 10"
    When I click on "Technique and Protocols" under left panel
    And select "Mallampati score" under the "Technique And Protocol" tab
    Then Report Dynamic Preview should get updated with "Technique and Protocols content"
    When I click on "Coronary Arteries" under left panel
    And select "Arises from the left sinus of Valsalva" under the "Left Main" tab
    Then Report Dynamic Preview should get updated with "Coronary Arteries Content"
    When I click on "Ventriculography" under left panel
    And select "Left Ventriculography performed in the RAO shows normal chamber size" under the "Left Ventriculograpgy" tab
    Then Report Dynamic Preview should get updated with "Ventriculography Content"
    When I click on "Hemodynamics" under left panel
    And select "Serial saturations demonstrated no evidence for intercardiac shunting" under the "Hemodynamics" tab
    Then Report Dynamic Preview should get updated with "Hemodynamics Content"
    When I click on "Interventions" under left panel
    And select "Medication challenge" under the "Interventions" tab
    Then Report Dynamic Preview should get updated with "Intervention Content"
    When I click on "Impressions" under left panel
    And provide "Impressions" under the "Free Text Area"
    Then Report Dynamic Preview should get updated with "Impression Content"
    When I click on "Recommendations" under left panel
    And provide "Recommendations" under the "Recommendations Box"
    Then Report Dynamic Preview should get updated with "Recommendations Content"

  @Stable
  @AT-253
  Scenario: Verify validation of mandatory fields on report
    Given vidistar application is launched
    And I have logged in the application as "Operator" user
    When I open a study for patient with first name as "CLIFTON" and last name as "BOND"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I sign the report
    Then validation error should pop-up
    When I click on "OK" pop button
    And I close report viewer
    And click on Don't Save
    And close the dicom viewer
    And I log out of the application
    And log back in the application as "Physician" user
    When I open a study for patient with first name as "CLIFTON" and last name as "BOND"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Vascular" option
    And select the "Cerebrovascular Final Report" option
    Then the Report Editor window should load
    When I sign the report
    Then validation error should pop-up

  @Stable
  @AT-231
  @DontSaveReport
  Scenario: Verify user is able to customise the phrases on report
    Given vidistar application is launched
    And I have logged in the application as "Physician" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Final Report" option
    Then the Report Editor window should load
    When I press Ctrl+click on Heart rate in report
    Then phrase edit box should pop up
    When I type "Heart rate is zero" in phrase edit box
    And I click on OK
    Then new phrase should appear on report

  @Stable
  @DeleteLatestSeriesInstanceAndRevertFinalStatusReport
  @AT-245
  Scenario: Verify user is able to perform paging through report
    Given vidistar application is launched
    And I have logged in the application as "Physician" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And select the "Cath Final Report" option
    Then the Report Editor window should load
    When I click on "FitWidth" icon
    When I enter below details in the "Cath Final Report" fields
      | field                    | type | value |
      | Systolic blood pressure  | text | 120   |
      | Diastolic blood pressure | text | 80    |
    And I click on "Indications History" under left panel
    And select an "ICD 10 Code" from the "ICD 10 Code" category
    And I click on "Procedure" under left panel
    And select a "CPT code" from the "CPT Code" category
    And I click on "Technique and Protocols" under left panel
    And select "Mallampati score" under the "Technique And Protocol" tab
    And I click on "Coronary Arteries" under left panel
    And select "Arises from the left sinus of Valsalva" under the "Left Main" tab
    And I click on "Ventriculography" under left panel
    And select "Left Ventriculography performed in the RAO shows normal chamber size" under the "Left Ventriculograpgy" tab
    And I click on "Interventions" under left panel
    And select "Medication challenge" under the "Interventions" tab
    And I click on "Anatomy" under left panel
    And select "Include diagram in the report" on report editor
    And I click on "Next" page icon
    Then preview window should move "forward" by one page
    When I click on "Previous" page icon
    Then preview window should move "backward" by one page
    When I sign the report
    And I log out of the application
    And log back in the application as "Physician2" user
    And I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And select the "Cath Final Report" option
    Then the Report Editor window should load
    When I click on "FitWidth" icon
    And I click on "Next" page icon
    Then preview window should move "forward" by one page
    When I click on "Previous" page icon
    Then preview window should move "backward" by one page

  @Stable
  @RevertSignedReportPrintWithDefaultPrinter
  @AT-216
  Scenario: Verify user is able to print the final report to default printer
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    And select "Print to default printer" checkbox
    And select "Send report to referring physician automatically after signing" checkbox
    And save the preferences
    And I open the "Reports" menu
    And go to the "OB-GYN" option
    And select the "First Trimester Final Report" option
    Then the Report Editor window should load
    When I sign the report
    Then send fax and email window should be displayed

  @Stable
  @DontSaveReport
  @AT-227
  @731_1
  @731_2
  Scenario: Verify that user can adjust order of impressions Free Text
    Given vidistar application is launched
    And I have logged in the application as "Physician" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When I select "Report Editor" option under preferences
    And verify that "Copy right clicked findings to impressions free text box" checkbox is checked
    And save the preferences
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Final Report" option
    Then the Report Editor window should load
    When I right click on "Patient name" label
    Then "Patient name purple" should turn purple
    When I right click on "Sex" label
    Then "Sex purple" should turn purple
    When I right click on "Patient type" label
    Then "Patient type purple" should turn purple
    When I right click on "DOB" label
    Then "DOB purple" should turn purple
    When I click on Impressions tab
    And I click Free text subtab
    Then list should display all the labels that had been right clicked
    When I click on the hamburger on one of the impressions and move it down
    Then that line item should move down and relocate to the place where it was dropped
    And the order should also change in the report

  @Stable
  @DontSaveReport
  @AT-96
  Scenario: Verify vascular diagram reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Vascular" option
    And select the "Abdominal Aorta Preliminary Report" option
    Then the Report Editor window should load
    When I click on "FITPAGE" icon
    And I click on "Anatomy" in report editor
    Then preliminary report diagram "should not" be present in dynamic report
    When I select "Include diagram in the report" in report editor
    And I click on "Next" page icon
    Then preliminary report diagram "should" be present in dynamic report
    When I select "Lesion" option
    And I mark lesion on aorta in the report
    Then marked lesion should be visible in the prelim report dynamic preview
    When I close report viewer
    And click on Don't Save
    And I open the "Reports" menu
    And go to the "Vascular" option
    And select the "Abdominal Aorta Final Report" option
    Then the Report Editor window should load
    When I click on "FITPAGE" icon
    And I click on "Anatomy" in report editor
    Then final report diagram "should not" be present in dynamic report
    When I select "Include diagram in the report" in report editor
    And I click on "Next" page icon
    Then final report diagram "should" be present in dynamic report
    When I select "Lesion" option
    And I mark lesion on aorta in the report
    Then marked lesion should be visible in the final report dynamic preview

  @UnfinalizeStudy
  @AT-217
  @AT-11
  Scenario: Verify report is sent to referring physician after signing
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open a study for patient with first name as "ROBERT" and last name as "DASHON"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    And select "Send report to referring physician automatically after signing" checkbox
    And unselect "Do not prompt with the addressbook on automatic report sending" checkbox
    And save the preferences
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Final Report" option
    Then the Report Editor window should load
    When I click on the select Referring Physician Icon
    And select a referring physician from the list of physicians
    And I click on "Recommendations" in report editor left panel
    And type "This is a CT Automation Test Fax" in "Free Text Area" on report editor
    And I click on "FITWIDTH" icon
    Then selected physician should be displayed in Referred By field on dynamic preview
    When I sign the report
    Then send fax and email window should be displayed
    And assigned referring physician is listed under selected contacts
    When I click send button on fax email dialog
    And I close report viewer
    And close the dicom viewer
    Then black phone icon should be displayed for the patient study
    When I click on phone icon
    Then latest row should reflect the fax details correctly

  @Stable
  @DeleteTemplateAndFolder
  @AT-103
  Scenario: Verify tree favorites structure
    Given vidistar application is launched
    And I have logged in the application as "Physician" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Nuclear" option
    And select the "New Myocardial Perfusion Scan Report" option
    Then the Report Editor window should load
    When I click on "Impressions" under left panel
    And I switch to "Favourites" subtab
    And provide "Test Phrase" under the "Favourites Text Area"
    And I "Save" it as "Automation Test"
    Then "template" should be available in the Template box
    When I create a "Test folder" under Template Box
    Then "folder" should be available in the Template box
    When I select the created folder
    And I click on "Save" button
    Then the phrase should get saved to the created folder
    When I close report viewer
    And click on Save
    Then the Report Editor window should close
    When I open the "Reports" menu
    And go to the "Nuclear" option
    And select the "New Myocardial Perfusion Scan Report" option
    Then the Report Editor window should load
    When I click on "Impressions" under left panel
    And I switch to "Favourites" subtab
    Then "template" should be available in the Template box
    And "folder" should be available in the Template box

  @DeleteCorrectedReport
  @AT-148
  Scenario: Verify that user can make demographic changes without re-signing report
    Given vidistar application is launched
    And I have logged in the application as "Report Fixer" user
    When I open a study for patient with first name as "NOAH" and last name as "RYAN"
    Then viewer should load the study for patient
    When I right click a completed and signed "Pediatric Echo Report" report from reports list
    And click on "Correct" option for report
    Then report modification popup window should appear
    When I click on "Continue" button on report modification popup
    Then the Report Editor window should load
    When I click on "FITPAGE" icon
    Then report should be signed by "Admin"
    When I click on "Study Quality" under left panel
    And I click on "FreeText" subtab
    And type "This is a CT Test" in "Free Text Area" on report editor
    And I sign the report
    And I close report viewer
    And I right click corrected "Pediatric Echo Report" from reports list
    And click on "View" option for report
    Then the Report Editor window should load
    And the report contains the corrected information
    And the corrected report should be signed by "Admin"

  @Stable
  @AT-97
  Scenario Outline: Verify Wall motion abnormalities
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<reports>" option
    Then the Report Editor window should load
    When I click on "Left Ventricle" in report editor left panel
    And select "Wall Motion Analysis General" tab
    And select "ASE Standard-model" nomenclature
    And set below segment observations on report
      | Segment    | observation |
      | MALSegment | Normal      |
      | MISegment  | Hypokinesis |
      | BISSegment | Akinesis    |
    And I click on "FITWIDTH" icon
    Then "segment observations" should be reflected on the dynamic preview
    When I click on "Set all to normal" button on "Wall Motion Analysis" tab
    Then all segments should be displayed in red
    And "normal segment observations" should be reflected on the dynamic preview
    When I click on "Set all to no comment" button on "Wall Motion Analysis" tab
    Then all the segments observations should be cleared
    When I close report viewer
    And click on Don't Save
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<reports>" option
    Then the Report Editor window should load
    When I click on "Left Ventricle" in report editor left panel
    And select "Wall Motion Analysis General" tab
    And select "Fundamental-Text based" nomenclature
    When I click on "Akinesis" option under "Apical" on "Wall Motion Analysis" tab
    And I click on "Dyskinesis" option under "Lateral" on "Wall Motion Analysis" tab
    And I click on "FITWIDTH" icon
    Then "fundamental observations" should be reflected on the dynamic preview
    When I click on "no comment" option under "Apical" on "Wall Motion Analysis" tab
    Then only "fundamental basal apical" observations are displayed
    When I close report viewer
    And click on Don't Save
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<reports>" option
    Then the Report Editor window should load
    When I click on "Left Ventricle" in report editor left panel
    And select "Wall Motion Analysis General" tab
    And select "ASE Standard-Text" nomenclature
    And I click on "Akinesis" option under "Mid anteroseptal" on "Wall Motion Analysis" tab
    And I click on "Dyskinesis" option under "Basal inferolateral" on "Wall Motion Analysis" tab
    And I click on "FITWIDTH" icon
    Then "standard observations" should be reflected on the dynamic preview
    When I click on "no comment" option under "Mid anteroseptal" on "Wall Motion Analysis" tab
    Then only "standard basal inferolateral" observations are displayed
    Examples:
      | reports                 |
      | Echo Preliminary Report |
      | Echo Final Report       |

  @RevertSignedReport
  @342_3
  @AT-130
  Scenario: Verify user is able to amend report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Consultation Final Report" option
    Then the Report Editor window should load
    When I click on "FitWidth" icon
    And I click on "Tech comments" under left panel
    And provide "Tech comments" under the "Tech Comments Box"
    Then Report Dynamic Preview should get updated with "Tech Comments Content"
    When I sign the report
    And close report viewer
    And close the dicom viewer
    And I open the same study again
    Then viewer should load the study for patient
    And signed report should be displayed under Reports tab
    When I select "Signed Report" for amending
    Then the Report Editor window should load
    When I click on "FitWidth" icon
    And I click on "Clinical Summary" under left panel
    And provide "Amended Clinical Summary" under the "Clinical Summary Box"
    Then Report Dynamic Preview should get updated with "Amended Clinical Summary"
    When I sign the report
    And close report viewer
    Then signed report should be displayed under Reports tab
    When I view the amended report
    Then report should contain the amendments performed
    When I close report viewer
    And close the dicom viewer
    And view the final report Pdf
    Then pdf should contain the amendments

  @DontSaveReport
  @AT-107
  Scenario: Verify combination of all nuclear final reports
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open a study for patient with first name as "KAREN" and last name as "PRATT"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Nuclear" option
    And select the "New Myocardial Perfusion Scan Report" option
    Then the Report Editor window should load
    When I click on "ProcedureOrSymptoms" in report editor left panel
    Then "pharmacologic options" are available on report editor under "ProcedureOrSymptoms" tab
    And "exercise stress options" are available on report editor under "ProcedureOrSymptoms" tab
    When I click on "Recovery EKG" in report editor left panel
    And select below checkbox under "ST deviation" on "Recovery EKG" tab
      | No changes              |
      | Non-specific ST changes |
    And I click on "FitWidth" icon
    Then "Recovery EKG observations" should be reflected on the dynamic preview
    When I click on "Raw Data" in report editor left panel
    And select below checkbox on "Raw Data" tab
      | Diaphragm attenuation    |
      | Increased gut uptake     |
      | Motion artifact          |
      | Evidence for enlarged RV |
    Then "Raw Data observations" should be reflected on the dynamic preview

  @RevertReport
  @AT-120
  Scenario: Verify presence of Favorites Tab on Nuclear Report
    Given vidistar application is launched
    And I have logged in the application as "technician" user
    When I open a study for patient with first name as "CYRIL" and last name as "ROSS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Nuclear" option
    And select the "New Myocardial Perfusion Scan Preliminary Report" option
    Then the Report Editor window should load
    And "Favorites" subtab should be present under below structure tabs in report editor left panel
      | Procedure Data                  |
      | ProcedureOrSymptoms             |
      | Resting EKG Interpretation      |
      | Stress EKG Interpretation       |
      | Recovery EKG                    |
      | Stress SPECT Images             |
      | Rest SPECT Images               |
      | Gated Left Ventricular Function |
      | Impressions                     |
      | Recommendations                 |
    When I click on "Recovery EKG" in report editor left panel
    And select below checkbox under "ST deviation" on "Recovery EKG" tab
      | No changes              |
      | Non-specific ST changes |
    And I click on "FitWidth" icon
    Then "Recovery EKG observations" should be reflected on the dynamic preview
    When I sign the report
    And close report viewer
    And close the dicom viewer
    And I log out of the application
    And log back in the application as "physician" user
    And I open a study for patient with first name as "CYRIL" and last name as "ROSS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Nuclear" option
    And select the "New Myocardial Perfusion Scan Report" option
    Then the Report Editor window should load
    And "Favorites" subtab should be present under below structure tabs in report editor left panel
      | Procedure Data                  |
      | ProcedureOrSymptoms             |
      | Resting EKG Interpretation      |
      | Stress EKG Interpretation       |
      | Recovery EKG                    |
      | Stress SPECT Images             |
      | Rest SPECT Images               |
      | Gated Left Ventricular Function |
      | Impressions                     |
      | Recommendations                 |
    And I click on "FitWidth" icon
    And "Recovery EKG observations" should be reflected on the dynamic preview
    When I sign the report
    And close report viewer
    Then the Report Editor window should close

  @DontSaveReport
  @AT-150
  Scenario: Verify user is provided with spell check options on report editor
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And select the "Cath Final Report" option
    Then the Report Editor window should load
    When I click on "FitWidth" icon
    And I click on "Impressions" under left panel
    And I switch to "Favourites" subtab
    And type "Femoral" under the "Favourites Text Area"
    Then spelling checker mark "should not" be displayed
    When I clear the Favourites Text Area
    And I switch to "Free text" subtab
    And I switch to "Favourites" subtab
    When I type "Demoral " under the "Favourites Text Area"
    Then spelling checker mark "should" be displayed
    When I right click on incorrect word
    Then spelling suggestion window should be displayed
    When I select the correct word
    Then spelling checker mark "should not" be displayed

  @RevertSignedReportAndAutoSendFax
  @AT-218
  Scenario: Verify report is sent to referring physician on signing the report
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open a study for patient with first name as "ROBERT" and last name as "DASHON"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    And select "Send report to referring physician automatically after signing" checkbox
    And select "Do not prompt with the addressbook on automatic report sending" checkbox
    And save the preferences
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Final Report" option
    Then the Report Editor window should load
    When I click on the select Referring Physician Icon
    And select a referring physician from the list of physicians
    And I click on "Recommendations" in report editor left panel
    And type "This is CT Automation Test Fax" in "Free Text Area" on report editor
    And I click on "FITWIDTH" icon
    Then selected physician should be displayed in Referred By field on dynamic preview
    When I sign the report
    Then send fax and email window should not be displayed
    When I close report viewer
    And close the dicom viewer
    Then black phone icon should be displayed for the patient study
    When I click on phone icon
    Then latest row should reflect the fax details correctly

  @RevertAutomaticReportOpenOnStartup
  @AT-208
  Scenario: Verify report editor opens automatically on startup
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I open a study for patient with first name as "MARY" and last name as "PLAYER"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    And select "Automatically open editor window on viewer startup" checkbox
    And save the preferences
    And close the dicom viewer
    And I open the same study again
    Then the Report Editor window should load automatically

  @DontSaveReport
  @AT-243
  Scenario Outline: Verify format of Patient name on report
    Given vidistar application is launched
    And I have logged in the application as "technician" user
    When I open a study for patient with first name as "JEAN" and last name as "RUPARD"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "<report type>" option
    And select the "<report subtype>" option
    Then the Report Editor window should load
    And verify the patient name is in the format of LastName,FirstName MiddleName
    Examples:
      | report type   | report subtype                                   |
      | Echo & Stress | Echo Preliminary Report                          |
      | Vascular      | Cerebrovascular Preliminary Report               |
      | OB-GYN        | OB Consultation Preliminary Report               |
      | Nuclear       | New Myocardial Perfusion Scan Preliminary Report |

  @DontSaveReport
  @AT-198
  Scenario: Verify user is able to copy information from previous reports
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "MARY" and last name as "PLAYER"
    Then viewer should load the study for patient
    When I right click on "Echo report" and view the menu options
    Then "Create new preliminary report" option "should" be displayed
    And "Create new final report" option "should" be displayed
    When I select "Create new preliminary report" option for previous report
    Then previous report data should be available
    When I close report viewer
    And click on Don't Save
    And I right click on "Selected Echo report" and view the menu options
    And select "Create new final report" option for previous report
    Then previous report data should be available

  @RevertFinalReportViewingPreference
  @AT-104
  Scenario: To verify that user can bypass prelim prompt for final report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JULIA" and last name as "ROBERTS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I enter below details in the "Echo Preliminary Report" fields
      | field                   | type | value |
      | Systolic blood pressure | text | 120   |
    When I click on "Study Quality" under left panel
    And I click on "FreeText" subtab
    And type "This is message from Report 1" in "Free Text Area" on report editor
    And I sign the report
    When I close report viewer
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I enter below details in the "Echo Preliminary Report" fields
      | field                   | type | value |
      | Systolic blood pressure | text | 150   |
    When I click on "Study Quality" under left panel
    And I click on "FreeText" subtab
    And type "This is message from Report 2" in "Free Text Area" on report editor
    And I sign the report
    When I close report viewer
    And close the dicom viewer
    And I log out of the application
    And log back in the application as "Physician" user
    And I open a study for patient with first name as "JULIA" and last name as "ROBERTS"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    And select "Always use last signed preliminary report when creating final" checkbox
    And save the preferences
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Final Report" option
    Then the Report Editor window should load
    And report should contain information from last signed preliminary report

  @RevertToggleImageSelection
  @AT-8
  Scenario: Verify image printing functionality
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JULIA" and last name as "ROBERTS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And go to image number 3
    And I right click on viewport 0
    And I select "Toggle image selection (for export/print)" option from rich viewer right click menu
    Then printer icon should be displayed
    When I open the "Reports" menu
    And select the "Print Selected Images" option
    And preview the selected image
    Then the Report Editor window should load
    When I click on print button
    Then print report window should be displayed

  @DontSaveReport
  @AT-228
  Scenario: Verify free text boxes
    Given vidistar application is launched
    And I have logged in the application as "Operator" user
    When I open a study for patient with first name as "JOHN" and last name as "BAILLY"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And select the "Cath Preliminary Report" option
    Then the Report Editor window should load
    When I click on "FitWidth" icon
    And I click on "Impressions" under left panel
    And enter "No horizontal scrollbar should be displayed and only one line should appear in Free Text area" under the "Free Text Area"
    Then horizontal scroll bar should not be displayed
    When I close report viewer
    And click on Don't Save
    And I log out of the application
    And log back in the application as "Physician" user
    And I open a study for patient with first name as "JOHN" and last name as "BAILLY"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And select the "Cath Final Report" option
    Then the Report Editor window should load
    When I click on "FitWidth" icon
    And I click on "Impressions" under left panel
    And enter "No horizontal scrollbar should be displayed and only one line should appear in Free Text area" under the "Free Text Area"
    Then horizontal scroll bar should not be displayed

  @RevertNewAssignedPhysician
  @AT-214
  Scenario: Verify setting up referring physician from study list
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "STOWE^GLENDA^G^^" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Assign Study to Referring Physicians" option from study list right click menu
    Then assigned physician should be displayed on the right side of the screen
    When I "remove" the assigned physician "amoody"
    And I "add" the assigned physician "vidistarfax"
    Then assigned physician should be displayed on the right side of the screen
    When I click on Save button
    And I open a study for patient with first name as "GLENDA" and last name as "STOWE"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    And select "Setup referring physician list on the report from study list" checkbox
    And save the preferences
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    And assigned physician should be present in report editor window
    And assigned physician should be present in dynamic report preview

  @Stable
  @DontSaveReport
  @AT-248
  Scenario Outline: Verify that right clicking on any item in the Report Editor should automatically add that entry to Impressions
    Given vidistar application is launched
    And I have logged in the application as "Physician" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "<reports>" option
    Then the Report Editor window should load
    When I click on "Study Quality" in report editor left panel
    And I right click on "Average" label
    And I click on "Left Ventricle" in report editor left panel
    And I right click on "Normal systolic function" label
    And I click on "Right Ventricle" in report editor left panel
    And I right click on "Pacemaker" label
    And I click on "Impressions" in report editor left panel
    And I switch to "Free Text Comparison" subtab
    Then the right clicked labels should be present under impressions tab
    Examples:
      | reports                             |
      | Echo Final Report                   |
      | Echo & Exercise Stress Final Report |
      | Pediatric Echo Final Report         |

  @Stable
  @DontSaveReport
  @AT-238
  Scenario: Verify report measurements auto selection
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "JAXEN" and last name as "SKELTON"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Pediatric Echo Preliminary Report" option
    Then the Report Editor window should load
    When I click on "AortaOrPA" in report editor left panel
    And I switch to "Hemodynamics" subtab
    And I click on "FITWIDTH" icon
    And I enter below details in the "Pediatric Echo Preliminary Report" fields
      | field                     | type | value |
      | Ao Root Dia               | text | 80    |
      | Ascending Aortic Diameter | text | 120   |
    Then below measurements should be auto-checked
      | Ao Root Dia               |
      | Ascending Aortic Diameter |
    And "Aorta Findings" should be reflected on the dynamic preview
    When I click on "Tricuspid Valve" in report editor left panel
    And I enter below details in the "Pediatric Echo Preliminary Report" fields
      | field           | type | value |
      | Vegetation size | text | 40    |
    Then below measurements should be auto-checked
      | Vegetation size |
    And "Vegetation Findings" should be reflected on the dynamic preview

  @DeleteServerPdfAndRevertSignedReport
  @AT-232
  Scenario: Verify compare viewer report to server pdf
    Given vidistar application is launched
    And I have logged in the application as "Physician" user
    When I open a study for patient with first name as "JAXX" and last name as "COLLINS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "OB-GYN" option
    And select the "OB Consultation Final Report" option
    Then the Report Editor window should load
    When I click on "Tech comments" under left panel
    And provide "Server PDF Content Verification" under the "Tech Comments Box"
    And I sign the report
    And close report viewer
    And I right click on "OB Consultation Final Report" from reports list
    And export report as "PDF"
    And save the PDF report
    And close the dicom viewer
    And export the final report pdf from the study list
    And save the server PDF report
    And wait for the download to complete
    And unzip the downloaded file
    Then all entered details should be present in viewer report PDF and server PDF

  @Stable
  @DeleteTheCreatedStudyAndUncheckOptionInPreferences
  @AT-211
  Scenario: Verify that user can see History/Indications to new final report
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "ODIS" and last name as "HYATT"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Final Report" option
    Then the Report Editor window should load
    When I click on "Indications History" under left panel
    And I select the below codes
      | Typhoid fever                        |
      | Typhoid fever with heart involvement |
      | Typhoid arthritis                    |
    Then the selected codes should be present on dynamic preview
    When I sign the report
    And I close report viewer
    And I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    And select "Copy history  indications from previous final report" checkbox
    And save the preferences
    And close the dicom viewer
    Then study list should be displayed
    When I navigate to "Study Tree" tab
    And I expand studies for first patient
    And I click on new study option
    Then Add new study modal should be displayed
    When I enter study details
      | field            | value         |
      | Service Location | VidiStar, LLC |
    And click on save button
    Then new study should be created
    When I open created study in rich viewer
    Then viewer should be blank as there are no images
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    And the selected codes should be present on dynamic preview
    When I enter below details in the "Echo Preliminary Report" fields
      | field                   | type | value |
      | Systolic blood pressure | text | 120   |
    And I sign the report
    And I close report viewer
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Final Report" option
    Then the selected codes should be present on dynamic preview

  @DontSaveReport
  @AT-244
  Scenario: Verify that unit change in the report editor recalculates the measurement
    Given vidistar application is launched
    And I have logged in the application as "Operator" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I click on "Measurements" under left panel
    And I switch to "LVOT" subtab
    And I enter below details in the "Echo Preliminary Report" fields
      | field   | type | value |
      | LVOT PV | text | 80    |
    Then "LVOT PV" measurement value should be reflected on the dynamic preview
    And I enter below details in the "Echo Preliminary Report" fields
      | field            | type     | value |
      | meter per second | dropdown | cm/s  |
    Then "LVOT PV Updated" measurement value and unit should be reflected on the dynamic preview
    When I click on "Mitral Valve" under left panel
    And I switch to "Hemodynamics" subtab
    And I enter below details in the "Echo Preliminary Report" fields
      | field               | type | value |
      | PISA-Alias Velocity | text | 560   |
    Then "PISA-Alias Velocity" measurement value should be reflected on the dynamic preview
    And I enter below details in the "Echo Preliminary Report" fields
      | field                 | type     | value |
      | centimeter per second | dropdown | m/s   |
    Then "PISA-Alias Velocity Updated" measurement value and unit should be reflected on the dynamic preview

  @RevertSingleMonitorView
  @AT-242
  Scenario: Verify that if a measurement is added manually or using bullseye, it should automatically added to the report
    Given vidistar application is launched
    And I have logged in the application as "Operator" user
    When I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    Then Window Preferences dialog should open
    When select "Use just one window (may be useful when having just one screen)" checkbox
    And save the preferences
    And click on 'Restart later' button on the Restart required modal
    And close the dicom viewer
    And I open a study for patient with first name as "JAMES" and last name as "THOMAS"
    Then viewer should load the study for patient
    When I change the layout to "1:1"
    And I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then report editor should open within the same window
    When I click on "Aortic Valve" under left panel
    And I switch to "Hemodynamics" subtab
    And I click on the bullseye of "LVOT Diameter (2D)" measurement label
    Then "LVOT Diameter (2D)" measurement label should be auto-checked
    When I measure the value for "LVOT Diameter (2D)" using the bullseye on the image
    And I click on "Aorta PA" under left panel
    And I switch to "Hemodynamics" subtab
    And I click on the bullseye of "Ao Root Dia" measurement label
    Then "Ao Root Dia" measurement label should be auto-checked
    When I measure the value for "Ao Root Dia" using the bullseye on the image
    And I click on "Mitral Valve" under left panel
    And I switch to "Hemodynamics" subtab
    And I click on the bullseye of "PISA-Radius" measurement label
    Then "PISA-Radius" measurement label should be auto-checked
    When I measure the value for "PISA-Radius" using the bullseye on the image
    And I click on "Show report preview" button in a single monitor view
    Then the Report Editor window should load
    And the "Measurement Value Calculated By Bullseye" should be present under findings section in dynamic preview
    When I click on "Show images" button in a single monitor view
    Then measurement tab should turn yellow
    When I click on "Yellow Measurements" under left panel
    Then "LVOT Diameter (2D) & Ao Root Dia" should be auto-checked under measurements tab
    When I switch to "MV Regurg" subtab
    Then "MR PISA-Radius" should be auto-checked under measurements tab
    When I click on "Yellow Aortic Valve" under left panel
    And I enter below details in the "Echo Preliminary Report" fields
      | field                     | type | value |
      | Yellow LVOT Diameter (2D) | text | 8.5   |
    And I click on "Yellow Aorta PA" under left panel
    And I enter below details in the "Echo Preliminary Report" fields
      | field              | type | value |
      | Yellow Ao Root Dia | text | 5.8   |
    And I click on "Yellow Mitral Valve" under left panel
    And I enter below details in the "Echo Preliminary Report" fields
      | field              | type | value |
      | Yellow PISA-Radius | text | 6.6   |
    And I click on "Show report preview" button in a single monitor view
    Then the Report Editor window should load
    And the "Measurement Value Calculated Manually" should be present under findings section in dynamic preview
    When I click on "Show images" button in a single monitor view
    And I click on "Yellow Measurements" under left panel
    Then "Updated MR PISA-Radius" should be auto-checked under measurements tab
    When I switch to "Ao&LA&RA" subtab
    Then "Updated LVOT Diameter (2D) & Ao Root Dia" should be auto-checked under measurements tab

  @AT-240
  Scenario Outline: Verify measurements calculated through bullseye are reflected correctly on report Report Measurements linked to Viewer
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "MEASUREMENT" and last name as "TEST"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Fetal Echo Final Report" option
    Then the Report Editor window should load
    When I click on "Measurements" in report editor left panel
    And select "Aorta" tab
    And hover on "<Measurement Field>" measurement
    And select the bullseye
    And I switch to the viewer window
    And I change the layout to "1:1"
    And I go to image number <Image Number>
    And draw a measurement on the image
    Then the distance and the label should appear on the image
    When I switch to the report window
    Then bullseye captured value should appear in the text field of the selected measurement in the report editor
    Examples:
      | Measurement Field   | Image Number |
      | Ao Root Diam        | 6            |
      | Ao Asc Diam         | 7            |
      | Ao Arch distal Diam | 6            |
      | Aortic Isthmus Diam | 7            |