@VID-477
Feature: VID-477

@RevertSingleMonitorView
@477_1_Part1
@477_2_Part1
@477_3_Part1
Scenario Outline: To verify endocarditis section is visible when "Use just one window" preference is enabled
Given vidistar application is launched
And I have logged in the application as "physician" user
When I open a study for patient with first name as "MARTHA" and last name as "BORDEN"
Then viewer should load the study for patient
When I navigate to "Window" menu
And select the "Preferences" option
When select "Use just one window (may be useful when having just one screen)" checkbox
And save the preferences
And click on 'Restart later' button on the Restart required modal
And close the dicom viewer
And I open a study for patient with first name as "MARTHA" and last name as "BORDEN"
Then viewer should load the study for patient
When I open the "Reports" menu
And go to the "Echo & Stress" option
And select the "<reporttype>" option
Then report editor should open within the same window
When I click on "Impressions" in report editor left panel
And I switch to "Chambers and Valves" subtab
Then "Endocarditis" section should be visible on report editor
When I enter below details in the "<reporttype>" fields
|field					   |type			|value	       |
|Aortic endocarditis	   |dropdown		|Aortic Mitral |
|Aortic vegetation		   |dropdown		|Aortic Mitral |
And I select below checkbox on "Chambers & Valves" subtab
|Valvular Disease IE       |
|No vegetation		   	   |
|Cannot Exclude Vegetation |
|Embolic potential         |
And I click on "Show report preview" button on viewer
Then "Endocarditis observation" should be reflected on the dynamic preview
Examples:
|reporttype        		   |
|TEE Final Report		   |
|Pediatric TTE Final Report|

@ExitReportAndViewer
@477_1_Part2
@477_2_Part2
@477_3_Part2
Scenario Outline: To verify endocarditis section is visible when "Use just one window" preference is disabled
Given vidistar application is launched
And I have logged in the application as "physician" user
When I open a study for patient with first name as "MARTHA" and last name as "BORDEN"
Then viewer should load the study for patient
When I open the "Reports" menu
And go to the "Echo & Stress" option
And select the "<reporttype>" option
Then the Report Editor window should load
When I click on "Impressions" in report editor left panel
And I switch to "Chambers & Valves" subtab
Then "Endocarditis" section should be visible on report editor
When I enter below details in the "<reporttype>" fields
|field					   |type			|value	       |
|Aortic endocarditis	   |dropdown		|Aortic Mitral |
|Aortic vegetation		   |dropdown		|Aortic Mitral |
And I select below checkbox on "Chambers & Valves" subtab
|Valvular Disease IE       |
|No vegetation		   	   |
|Cannot Exclude Vegetation |
|Embolic potential         |
Then "Endocarditis observations" should be reflected on the dynamic preview
Examples:
|reporttype        		   |
|TEE Final Report		   |
|Pediatric TTE Final Report|

@DeleteLatestSeriesInstanceAndRevertFinalStatusReport
@477_4
Scenario Outline: To verify endocarditis section is visible when report is amended
Given vidistar application is launched
And I have logged in the application as "physician" user
When I open a study for patient with first name as "MARTHA" and last name as "BORDEN"
Then viewer should load the study for patient
When I open the "Reports" menu
And go to the "Echo & Stress" option
And select the "<reporttype>" option
Then the Report Editor window should load
When I sign the report
And close report viewer
And I select "<amendreport>" for amending
Then the Report Editor window should load
When I click on "Impressions" in report editor left panel
And I switch to "Chambers & Valves" subtab
Then "Endocarditis" section should be visible on report editor
When I enter below details in the "<reporttype>" fields
|field					   |type			|value	       |
|Aortic endocarditis	   |dropdown		|Aortic Mitral |
|Aortic vegetation		   |dropdown		|Aortic Mitral |
And I select below checkbox on "Chambers & Valves" subtab
|Valvular Disease IE       |
|No vegetation		   	   |
|Cannot Exclude Vegetation |
|Embolic potential         |
Then "Endocarditis observations" should be reflected on the dynamic preview
Examples:
|reporttype        		   |amendreport				       |
|TEE Final Report		   |Transesophageal Echo Report	   |
|Pediatric TTE Final Report|Pediatric Echo Final Report	   |

@477_5
Scenario: To verify scrollbar is not visible when report content is less
Given vidistar application is launched
And I have logged in the application as "physician" user
When I open a study for patient with first name as "MARTHA" and last name as "BORDEN"
Then viewer should load the study for patient
When I open the "Reports" menu
And go to the "Echo & Stress" option
And select the "Pediatric TTE Final Report" option
Then the Report Editor window should load
When I click on "Impressions" in report editor left panel
Then "Normal Cardiac Abnormal" section should be visible on report editor
And vertical scroll bar should not be displayed

@477_6
@477_8
Scenario: To verify 2D-Mode and Other section is visible when "Use just one window" preference is disabled
Given vidistar application is launched
And I have logged in the application as "physician" user
When I open a study for patient with first name as "MARTHA" and last name as "BORDEN"
Then viewer should load the study for patient
When I open the "Reports" menu
And go to the "Echo & Stress" option
And select the "Pediatric TTE Final Report" option
Then the Report Editor window should load
When I click on "Ventricle-Left Ventricle" in report editor left panel
And I switch to "Systolic Function" subtab
Then "2D-Mode Other" section should be visible on report editor
When I resize report editor pane
And I close report viewer
And click on Don't Save
And I open the "Reports" menu
And go to the "Echo & Stress" option
And select the "Pediatric TTE Final Report" option
Then the Report Editor window should load
When I click on "Ventricle-Left Ventricle" in report editor left panel
And I switch to "Systolic Function" subtab
Then "2D-Mode Other" section should be visible on report editor

@RevertSingleMonitorView
@477_7
Scenario: To verify 2D-Mode and Other section is visible when "Use just one window" preference is enabled
Given vidistar application is launched
And I have logged in the application as "physician" user
When I open a study for patient with first name as "MARTHA" and last name as "BORDEN"
Then viewer should load the study for patient
When I navigate to "Window" menu
And select the "Preferences" option
When select "Use just one window (may be useful when having just one screen)" checkbox
And save the preferences
And click on 'Restart later' button on the Restart required modal
And close the dicom viewer
And I open a study for patient with first name as "MARTHA" and last name as "BORDEN"
Then viewer should load the study for patient
When I open the "Reports" menu
And go to the "Echo & Stress" option
And select the "Pediatric TTE Final Report" option
Then report editor should open within the same window
When I click on "Ventricle-Left Ventricle" in report editor left panel
And I switch to "Systolic Function" subtab
Then "2D-Mode Other" section should be visible on report editor