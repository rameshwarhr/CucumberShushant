@Web
@Regression
Feature: Web Application

  @Stable
  @DeleteCreatedPatient
  @AT-25
  Scenario: Verify basic filters functionality on Study list page
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "Study Tree" tab
    And I add a new patient
    Then Add new study modal should be displayed
    When I enter study details
      | field             | value         |
      | Study Description | TESTSTUDY     |
      | Service Location  | VidiStar, LLC |
    And click on save button
    And I navigate to "Study List" tab
    When I enter "NAME" in "Patient Name" filter under study list tab
    Then study list "should" display studies only which are having "Patient Name" as "NAME"
    When I clear the "Patient Name" filter
    And I enter "12345678" in "Patient ID" filter under study list tab
    Then study list "should" display studies only which are having "Patient ID" as "12345678"
    When I clear the "Patient Id" filter
    And I enter "45 days ago" in "Study Start Date" filter under study list tab
    Then study list "should" display studies only which are having "Study Start Date" as "45 days ago"

  @Stable
  @AT-37
  Scenario: Verify patient studies are sorted according to columns in ascending and descending order
    Given vidistar application is launched
    And I have logged in the application as "Global Admin" user
    When I sort "Patient Name" in "ascending" order
    Then studies are sorted by "Patient Name" in "ascending" order
    When I sort "Patient Name" in "descending" order
    Then studies are sorted by "Patient Name" in "descending" order
    When I sort "Patient ID" in "ascending" order
    Then studies are sorted by "Patient ID" in "ascending" order
    When I sort "Patient ID" in "descending" order
    Then studies are sorted by "Patient ID" in "descending" order
    When I sort "Study Date/Time" in "ascending" order
    Then studies are sorted by "Study Date/Time" in "ascending" order
    When I sort "Study Date/Time" in "descending" order
    Then studies are sorted by "Study Date/Time" in "descending" order

  @Stable
  @AT-114
  Scenario: Verify Global Admin Permissions
    Given vidistar application is launched
    And I have logged in the application as "Global Admin" user
    When I navigate to "System Settings" tab
    And edit the first user from users list
    And open Roles tab
    Then the following roles should be "displayed" in the Available Roles list
      | JBossAdmin       |
      | anonymizer-admin |
      | global-admin     |
    When I close Edit User window
    And I click on Add New User button
    And open Roles tab
    Then the following roles should be "displayed" in the Available Roles list
      | JBossAdmin       |
      | anonymizer-admin |
      | global-admin     |
    When I close Add New User window
    And I log out of the application
    And log back in the application as "admin" user
    And I navigate to "System Settings" tab
    And edit the first user from users list
    And open Roles tab
    Then the following roles should be "not displayed" in the Available Roles list
      | JBossAdmin       |
      | anonymizer-admin |
      | global-admin     |
    When I close Edit User window
    And I click on Add New User button
    And open Roles tab
    Then the following roles should be "not displayed" in the Available Roles list
      | JBossAdmin       |
      | anonymizer-admin |
      | global-admin     |

  @Stable
  @AT-210
  Scenario: Vidiadm-ctrl Access
    Given vidistar application is launched
    And I have logged in the application as "JBOSSADMIN" user
    When I open vidistar admin control access
    Then JMX Agent View should open

  @Stable
  @RevertAssignedRole
  @AT-223
  Scenario: Verify Audit-Repository role for admin user
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I navigate to "System Settings" tab
    Then "Audit Repository" is "not present" in the settings panel
    When I add "auditor" role to user
    And I log out of the application
    And log back in the application as "admin" user
    And I navigate to "System Settings" tab
    Then "Audit Repository" is "present" in the settings panel
    When I click on the "Audit Repository" option in the settings panel
    Then "Audit Events" should be displayed on system settings tab

  @Stable
  @RevertAssignedRole
  @AT-224
  Scenario: Verify Admin is able to add Faxlog-Auditor role to a user
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I navigate to "System Settings" tab
    Then "Fax Log" is "not present" in the settings panel
    When I add "faxlog-auditor" role to user
    And wait for the data to load
    And I log out of the application
    And log back in the application as "admin" user
    And I navigate to "System Settings" tab
    Then "Fax Log" is "present" in the settings panel
    When I click on the "Fax Log" option in the settings panel
    Then "Fax Status Events" should be displayed on system settings tab

  @Stable 
  @SearchAdvanced
  @AT-33
  Scenario: Verify advanced filters functionality on Study list page
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I click on Advanced filter
    Then Advanced filter section should get unfolded
    When I select "124test" from "Assigned Users" filter
    Then study list "should" display studies only which are having "Assigned Users" as "124test"
    When I reset the "Assigned Users" filter
    And I select "SCHEDULED" in "Worksheet" filter
    Then study list "should" display studies only which are having "Worksheet" as "SCHEDULED"
    When I reset the "Worksheet" filter
    And I select "COMPLETED" in "Final Report" filter
    Then study list "should" display studies only which are having "Final Report" as "COMPLETED"
    When I reset the "Final Report" filter
    And I enter "US" in "Modality" filter
    Then study list "should" display studies only which are having "Modality" as "US"
    When I reset the "Modality" filter
    And I enter "123" in "Study ID" filter
    Then study list "should" display studies only which are having "Study ID" as "123"

  @Stable
  @AT-222
  Scenario: Verify Template Administration access upon removal of template-admin role for the user
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And remove the "templateadmin" role for the "PHYSICIAN" user if present
    And I log out of the application
    And log back in the application as "PHYSICIAN" user
    And I navigate to "System Settings" tab
    Then "Template Administration" is "not present" in the settings panel
    When I navigate to "Study List" tab
    And I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I open the "Reports" menu
    Then "Template Administration" should be disabled

  @Stable
  @AT-66
  @AT-19
  Scenario: Verify new user creation and EULA term and condition for new user
    Given vidistar application is launched
    And I have logged in the application as "Global Admin" user
    When I navigate to "System Settings" tab
    And create a new  user with below group & role
      | group           | role     |
      | American League | operator |
    Then new user should be created
    When I log out of the application
    And log back in the application with new user credentials
    Then The EULA should be presented for confirmation
    When I discard the Eula dialog box
    And log back in the application with new user credentials
    And accept EULA terms to proceed
    Then I am logged in as "new user"
    And below tabs are displayed according to assigned roles
      | Study List      |
      | Study Tree      |
      | Order List      |
      | Media           |
      | System Settings |

  @Stable
  @AT-185
  Scenario: Verify study list is displayed after login
    Given vidistar application is launched
    When I log in to the application as "Admin" user
    Then study list should be displayed

  @Stable
  @AT-186
  Scenario: Open the simple viewer
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "THOMAS^JAMES" in "Patient Name" filter under study list tab
    And right click on the study from study list
    Then "Open with Simple Viewer" option should be displayed in the study list right click menu
    When I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    And study should be loaded in simple viewer

  @Stable
  @AT-23
  Scenario: Editing patient/study/series information-authorization
    Given vidistar application is launched
    And I have logged in the application as "PHYSICIAN" user
    And hover on the first study
    Then floating "toolbar" is "present" on study list
    When I navigate to "Study Tree" tab
    And hover on the first patient
    Then edit floating "icon" is "not present" for patient list
    When I expand first patient
    And hover on the first study in the study tree tab
    Then floating "study toolbar" is "present" for study attributes
    When I expand study attributes
    And hover on the first series
    Then edit floating "series icon" is "not present" for series attributes
    When I log out of the application
    And log back in the application as "OPERATOR" user
    And I enter "BAILLY^JOHN" in "Patient Name" filter under study list tab
    And hover on the first study
    Then floating "toolbar" is "present" on study list
    When I navigate to "Study Tree" tab
    And hover on the first patient
    Then edit floating "icon" is "present" for patient list
    When I expand first patient
    And hover on the first study in the study tree tab
    Then floating "study toolbar" is "present" for study attributes
    When I expand study attributes
    And hover on the first series
    Then edit floating "series icon" is "present" for series attributes
    When I log out of the application
    And log back in the application as "MIDLEVEL" user
    When I enter "WAYNE^JOHN" in "Patient Name" filter under study list tab
    And hover on the first study
    Then floating "toolbar" is "not present" on study list

  @Stable
  @AT-21
  Scenario: Authorization - basic
    Given vidistar application is launched
    When I log in to the application as "Admin" user
    Then study list should be displayed
    When I log out of the application
    And I log in to the application as "INVALIDUSER1" user
    Then login should fail
    When I log in to the application as "INVALIDUSER2" user
    Then login should fail

  @Stable
  @RevertServiceLocation
  @AT-67
  Scenario: Create Service Location
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Service Locations" option in the settings panel
    And I set name as "Automation test" to add new service location
    And I select "Florida" from hubs dropdown
    And I save service location
    And I search "Automation test" in service location tab
    Then The new service location "Automation test" should be present

  @Stable
  @AT-225
  Scenario: Verify Accreditation Portal - Access
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And add the "qa" role for the "Admin" user
    And I log out of the application
    And log back in the application as "Admin" user
    And I navigate to "Accreditation" tab
    Then Documents Tree should be displayed on the left side of the screen under Settings
    And first document name under it should be displayed at the top of the document viewing panel
    When I navigate to "System Settings" tab
    And I remove the "qa" role for the "Admin" user
    And I log out of the application
    And log back in the application as "Admin" user
    Then "Accreditation" tab should not be present

  @Stable
  @AT-187
  Scenario: Verify simple viewer navigation
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "THOMAS^JAMES" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    And thumbnails should be "displayed"
    When I select "2:1" display
    Then images should be displayed in "2" viewports
    When I select "4:1" display
    Then images should be displayed in "4" viewports
    When I select "6:1" display
    Then images should be displayed in "6" viewports
    When I select "1:1" display
    And I click on Thumbnails section
    Then thumbnails should be "hidden"
    When I click on "forward" arrow button
    Then viewer should display next image
    When I open the first report under Reports section
    Then report should get opened in a new window
    When I close the report window
    Then screen should return to Simple Viewer main window

  @Stable
  @RevertHub
  @AT-68
  Scenario: Create Hub in System Settings
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Hubs" option in the settings panel
    And I create new hub with name "AutomationHub" & address "AutomationHubAddress"
    And I save hub
    And I search "AutomationHub" in Hubs tab
    Then the newly created hub should be present

  @Stable
  @RevertGroup
  @AT-69
  Scenario: Create Group in System Settings
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "System Settings" tab
    And I click on the "Groups" option in the settings panel
    And I create new group with id "AutomationGroup" & address "AutomationDescription"
    And I save group
    And I search "AutomationGroup" in Groups tab
    Then the newly created group should be present

  @RevertSignedSimpleViewerReport
  @AT-188
  Scenario: Verify ECG report creation in the Simple Viewer
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "TEST^MATHEW" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Open with Simple Viewer" option from study list right click menu
    Then simple viewer should open for patient study in new application tab
    When I select "show report preview" display view
    Then "report preview" view should be displayed
    When I select "ECG Interpretations"
    Then list of available findings categories should be displayed on the left side of the screen
    When I click on "expand" icon on any finding category
    Then list of statement abbreviations should be displayed
    When I hover on any abbreviation
    Then full statement should be displayed in popup
    When I select "show datasets" display view
    Then "datasets" view should be displayed
    When I select "show report preview" display view
    And I select an abbreviation
    Then it should get added to the report
    When I sign report
    Then "Final Report" status should be "COMPLETED"

  @Stable
  @RevertPatientStudySeries
  @AT-31
  Scenario: Verify Editing of patient/study/series information
    Given vidistar application is launched
    And I have logged in the application as "operator" user
    When I enter "BAILLY^JOHN" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Edit Study Attributes" option from study list right click menu
    Then Edit Study Attributes modal should open
    When I edit Description as "VASCULAR TEST 456 AUTOMATION" and save
    Then updated Description should be visible for the first study
    When I navigate to "Study Tree" tab
    And right click on the patient from study tree
    And I select "Edit Patient Attributes" option from study tree right click menu
    And I edit Patient Id as "140810520TEST" and save
    Then updated Patient Id should be visible for the patient
    When I expand first patient
    And I expand study attributes
    And hover on the first series
    And click on Edit Series icon
    And I edit Modality as "NM" and save
    Then updated Modality should be visible for the first series

  @Stable
  @AT-30
  Scenario: Verify Admin Access
    Given vidistar application is launched
    And I have logged in the application as "admin" user
    When I navigate to "System Settings" tab
    Then following Settings "should" be available in the left panel
      | Users                       |
      | Groups                      |
      | Password Complexity         |
      | Edit Account                |
      | My notifications            |
      | Notification administration |
      | Abbreviation dictionary     |
      | Service Locations           |
      | Hubs                        |
      | Clinic parameters           |
      | System Names and Numbers    |
    And respective panel for each settings should be visible

  @Stable
  @AT-26
  Scenario: Verify that tree level display appropriate set of data attributes
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "RYAN^NOAH" in "Patient Name" filter
    And I navigate to "Study Tree" tab
    Then below columns are displayed for Patient table
      | Patient Name       |
      | Patient ID         |
      | Patient Birth Date |
      | Last Study Date    |
      | Patient Sex        |
    When I expand patient "RYAN^NOAH" to view its studies
    Then below columns are displayed for Study table
      | Fax                 |
      | Chart #             |
      | Study Date/Time     |
      | Description         |
      | Worksheet           |
      | Final Report        |
      | Assigned Users      |
      | Assigned Groups     |
      | Service Location    |
      | Referring Physician |
    When I "expand" the 1st study
    Then below columns are displayed for Series table
      | Series Date         |
      | Modality            |
      | Description         |
      | Vendor              |
      | Model               |
      | Number Of Instances |
      | AE Title            |
    When I "expand" 1st series
    Then below columns are displayed for Instances table
      | Instance Date              |
      | Instance Number            |
      | Type                       |
      | Image Resolution           |
      | Photometric Interpretation |
      | bitsAllocated              |
      | Image                      |

  @Stable
  @AT-34
  Scenario: Verify global filter presets
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I wait for the study list data to load
    And I click on Presets dropdown
    Then below filter preset options "should" be available
      | CLEAR          |
      | All            |
      | All unread     |
      | Last 3 days    |
      | Today's unread |
    When I select "All" preset filter
    Then nothing should be filtered
    When I click on Presets dropdown
    And I select "All unread" preset filter
    Then study list "should" display studies only which are having "Final Report" as "SCHEDULED"
    When I click on Presets dropdown
    And I select "Last 3 days" preset filter
    Then "study start date" filter field should display "N days ago"
    And "start date N value" filter field should display "3"
    When I click on Presets dropdown
    And I select "Today's unread" preset filter
    Then "study start date" filter field should display "Today"
    And "study end date" filter field should display "Today"
    And "final report" filter field should display "SCHEDULED"

  @Stable
  @AT-24
  Scenario: Verify deleting authorization
    Given vidistar application is launched
    And I have logged in the application as "Physician" user
    When I navigate to "Study Tree" tab
    Then delete patient icon "should not" be visible
    When I log out of the application
    And I log in to the application as "Operator" user
    And I navigate to "Study Tree" tab
    Then delete patient icon "should" be visible

  @Stable
  @AT-82
  Scenario: Verify that user can see message banner at time of login
    Given vidistar application is launched
    Then message banner should be visible

  @Stable
  @DeletePreset
  @AT-35
  Scenario: Verify user is able to create preset
    Given vidistar application is launched
    And I have logged in the application as "PHYSICIAN" user
    When I wait for the study list data to load
    And I click on Advanced filter
    Then Advanced filter section should get unfolded
    When I enter "JAXX" in "Patient Name" filter under study list tab
    And I enter "5960346" in "Patient ID" filter under study list tab
    And I enter "7133119" in "Chart #" filter under study list tab
    And I select "AAA" from "Assigned Groups" filter
    And I select "COMPLETED" in "Worksheet" filter
    And I select "SCHEDULED" in "Final Report" filter
    And I save the preset as "Test Preset"
    And I click on Presets dropdown
    Then below filter preset options "should" be available
      | Test Preset |
    When I select "CLEAR" preset filter
    And I click on Presets dropdown
    And I select "Test Preset" preset filter
    Then "patient name" filter field should display "JAXX"
    And "chart #" filter field should display "7133119"
    And "assigned groups" filter field should display "AAA"
    And "worksheet" filter field should display "COMPLETED"
    And "final report" filter field should display "SCHEDULED"
    When I log out of the application
    And log back in the application as "PHYSICIAN2" user
    And I wait for the study list data to load
    And I click on Presets dropdown
    Then below filter preset options "should not" be available
      | Test Preset |

  @RemoveAssignedPhysician
  @AT-106
  Scenario: Verify referring physician access
    Given vidistar application is launched
    And I have logged in the application as "OPERATOR" user
    When I enter "WAYNE^JOHN" in "Patient Name" filter under study list tab
    And right click on the study from study list
    And I select "Assign Study to Referring Physicians" option from study list right click menu
    And assign physician "amoody" to the patient
    Then study "should" display "Assigned Users" as "amoody"
    When I open a study for patient with first name as "JOHN" and last name as "WAYNE"
    Then viewer should load the study for patient
    When I navigate to "Window" menu
    And select the "Preferences" option
    And select "Setup referring physician list on the report from study list" checkbox
    And save the preferences
    When I open the "Reports" menu
    And go to the "Echo & Stress" option
    And select the "Echo Preliminary Report" option
    Then the Report Editor window should load
    When I open the referring physician address book
    Then referred physician should be displayed under address book
    When I close report viewer
    And click on Don't Save
    And navigate to "Window" menu
    And select the "Preferences" option
    And unselect "Setup referring physician list on the report from study list" checkbox
    And save the preferences
    And close the dicom viewer

  @Stable
  @DeleteBurnedPatients
  @AT-100
  Scenario: Verify user is able to see burned patients of its service location only
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "Study Tree" tab
    And I click on Nested filter link
    And I click on match filter dropdown
    And I select "Match Any" from the match filter dropdown
    And I search for the below 1 patients
      | HYATT^ODIS  |
    And I click on search button
    And select both the patients from study tree for CD burning
    And click on "Burn selected Entities to CD/DVD ROM" button
    And click on Yes
    And I log out of the application
    And log back in the application as "Physician" user
    And I navigate to "Media" tab
    And I search patient "HYATT ODIS" under media tab
    Then searched patient should not be present

  @Stable
  @DeleteBurnedPatients
  @AT-44
  Scenario: Verify that user can burn the CD and download	ISO
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "HYATT^ODIS" in "Patient Name" filter under study list tab
    And I navigate to "Study Tree" tab
    And select the patient for CD burning
    And click on "Burn selected Entities to CD/DVD ROM" button
    And click on Yes
    And I navigate to "Media" tab
    And I search patient "HYATT ODIS" under media tab
    Then the patient should have green check CD icon

  @Stable
  @AT-22
  Scenario: Verify user can access only to the data which they are authorized to access
    Given vidistar application is launched
    And I have logged in the application as "Physician" user
    Then study list should be displayed
    When I enter "WAYNE^JOHN" in "Patient Name" filter under study list tab
    Then study list "should" display studies only which are having "Patient Name" as "WAYNE^JOHN"
    When I log out of the application
    And log back in the application as "Technician" user
    And I enter "DSR^CARDIAC" in "Patient Name" filter under study list tab
    Then searched patient should not be present
    When I enter "PATNAME12" in "Patient Name" filter under study list tab
    Then study list "should" display studies only which are having "Patient Name" as "PATNAME12"
    When I clear the "Patient Name" filter
    And I click on Advanced filter
    And I click on Service Location dropdown
    And search "Hogwarts" in the service location textbox
    Then searched service location should not be present
    When I close the service location dropdown
    And I log out of the application
    And log back in the application as "Admin" user
    And I navigate to "Study Tree" tab
    And I click on Nested filter link
    And I click on match filter dropdown
    And I select "Match Any" from the match filter dropdown
    And I click on "Add" button 2 time
    And I search for the below 3 patients
      | WAYNE^JOHN^^^  |
      | DSR^CARDIAC^^^ |
      | PATNAME12^^^^  |
    And I click on search button
    Then all searched patients should be present

  @Stable
  @AT-29
  Scenario: Verify user is able to authorize WADO service
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "THOMAS^JAMES" in "Patient Name" filter under study list tab
    And I navigate to "Study Tree" tab
    And I "expand" the 1st patient
    And I "expand" the 1st study
    And I "expand" 1st series
    And click on first thumbnail
    Then image "should" get opened
    When I switch back to Vidistar
    And I log out of the application
    And I have logged in the application as "Wadouser" user
    And switch to the other tab
    And I navigate to the image url
    Then image "should not" get opened
    When I switch back to Vidistar
    And I log out of the application
    And I navigate to the image url
    Then login alert should get displayed

  @Stable
  @UnmergeStudy
  @AT-73
  Scenario: Verify user is able to merge studies of two different patients
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "Study Tree" tab
    And I click on Nested filter link
    And I click on match filter dropdown
    And I select "Match Any" from the match filter dropdown
    And I click on "Add" button 1 time
    And I search for the below 2 patients
      | WHITTAKER^WESTON |
      | DUNCAN^KAY^^     |
    And I click on search button
    And I expand first patient
    And I select first study
    And I select second patient
    And I expand second patient
    And click Merge Patients option
    Then child patient study should be merged with master patient
    And merged study should not be present in the child patient

  @DeleteServerPdf
  @41083_11
  @AT-83
  Scenario: Verify only completed final reports are downloaded as part of server pdf
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I click on Advanced filter
    And I enter "NAME" in "Patient Name" filter under study list tab
    And export the final report pdf from the study list
    And save the server PDF report
    And wait for the download to complete
    And I select "COMPLETED" in "Final Report" filter
    And get the study count of the filter result
    Then downloaded folder reports count should match the study list count

  @AT-45772
  Scenario: Verify that ECG is opened in simple viewer when we single click on the study
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I open a study for patient with first name as "MATHEW" and last name as "TEST"
    Then simple viewer should open for patient study in new application tab

  @Stable
  @DeleteCreatedPatientWithAssignedPhysician
  @AT-1001
  Scenario: Verify whether physician is able to view newly created user with unassigned physician
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "Study Tree" tab
    And I add a new patient
    Then Add new study modal should be displayed
    When I enter study details
      | field             | value         |
      | Study Description | TESTSTUDY     |
      | Service Location  | VidiStar, LLC |
    And click on save button
    And I expand studies for first patient
    Then new study should be created
    When I log out of the application
    And log back in the application as "Physician" user
    And search the newly created user
    Then patient should be "unavailable"

  @Stable
  @DeleteCreatedPatientWithAssignedPhysician
  @AT-1002
  Scenario: Verify whether physician is able to view newly created user with assigned physician
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "Study Tree" tab
    And I add a new patient
    Then Add new study modal should be displayed
    When I enter study details
      | field             | value         |
      | Study Description | TESTSTUDY     |
      | Service Location  | VidiStar, LLC |
    And click on save button
    And I expand studies for first patient
    Then new study should be created
    And I navigate to "Study List" tab
    And search the newly created user
    And right click on the study from study list
    And I select "Assign Study to Physicians" option from study list right click menu
    And assign physician "AutoPhysician" to the patient
    Then study "should" display "Assigned Users" as "AutoPhysician"
    When I log out of the application
    And log back in the application as "Physician" user
    And search the newly created user
    Then patient should be "unavailable"

  @Stable
  @DeleteCreatedPatientWithAssignedPhysician
  @AT-1003
  Scenario Outline: Verify whether physician is able to view newly created user with assigned physician and various final report status
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    And I navigate to "Study Tree" tab
    And I add a new patient
    Then Add new study modal should be displayed
    When I enter study details
      | field             | value         |
      | Study Description | TESTSTUDY     |
      | Service Location  | VidiStar, LLC |
    And click on save button
    And I expand studies for first patient
    Then new study should be created
    When I navigate to "Study List" tab
    And search the newly created user
    And right click on the study from study list
    And I select "Assign Study to Physicians" option from study list right click menu
    And assign physician "AutoPhysician" to the patient
    Then study "should" display "Assigned Users" as "AutoPhysician"
    When I right click on the study from study list
    And I select "Edit Study Attributes" option from study list right click menu
    And I change the "Final Report" status of "study" to "<final report status>"
    When I log out of the application
    And log back in the application as "Physician" user
    And search the newly created user
    Then patient should be "available"
    Examples:
      | final report status |
      | scheduled           |
      | completed           |