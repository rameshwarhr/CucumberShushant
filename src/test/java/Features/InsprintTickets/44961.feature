@insprint
Feature: 44961

  @44961-1
  @44961-2
  Scenario: Verify that Audit repository and Fax log tab contains export and tooltip functionality
    Given vidistar application is launched
    And I have logged in the application as "FAXLOGAUDITOR" user
    When I navigate to "System Settings" tab
    And I click on the "Audit Repository" option in the settings panel
    Then "Export Audit Repository to CSV" button should be present
    When I "hover" the "Export Audit Repository to CSV" button
    Then "Export Audit Repository to CSV" tooltip should be displayed
    When I click on the "Fax Log" option in the settings panel
    Then "Export Fax Logs to CSV" button should be present
    When I "hover" the "Export Fax Logs to CSV" button
    Then "Export Fax Logs to CSV" tooltip should be displayed

  @DeleteExportedFiles
  @44961-3
  @44961-4
  @44961-5
  Scenario: Verify that audit repository file is downloaded at given location in csv format and contains the following fields
    Given vidistar application is launched
    And I have logged in the application as "FAXLOGAUDITOR" user
    When I navigate to "System Settings" tab
    And I click on the "Audit Repository" option in the settings panel
    And I select "Audit Event" as "SecurityAlert"
    And I enter "Timestamp from" as "4 weeks ago"
    And "click" the "Export Audit Repository to CSV" button
    And save the "Audit Repository" exported data
    And wait for the download to complete
    Then exported data should get downloaded in the csv file format
    And csv file "should" contain the following fields
      | Audit Event       |
      | Host              |
      | TimeStamp (local) |
      | Patient Name      |
      | Patient ID        |
      | Local User        |
      | AET               |
      | ConfigType        |
      | Action            |
      | Object Action     |
      | IP                |
      | HName             |
      | AlertType         |
      | SUID              |
      | CUID              |
      | NumberOfInstances |
      | Description       |
    And csv file "should not" contain the following fields
      | Information |

  @DeleteExportedFiles
  @44961-3
  @44961-4
  @44961-6
  Scenario: Verify that faxlog file is downloaded at given location in csv format and contains the following fields
    Given vidistar application is launched
    And I have logged in the application as "FAXLOGAUDITOR" user
    When I navigate to "System Settings" tab
    And I click on the "Fax Log" option in the settings panel
    And I enter "Timestamp from" as "4 weeks ago"
    And "click" the "Export Fax Logs to CSV" button
    And save the "Fax Logs" exported data
    And wait for the download to complete
    Then exported data should get downloaded in the csv file format
    And csv file "should" contain the following fields
      | TimeStamp       |
      | Patient Name    |
      | Patient ID      |
      | Study Date/time |
      | Chart #         |
      | Document Title  |
      | Document Date   |
      | Sender ID       |
      | Recipient Name  |
      | Recipient Phone |
      | Fax Status Name |
      | Fax Message     |

  @DeleteExportedFiles
  @44961-7-1
  Scenario: Verify that exported audit repository file contains filtered logs and count should match
    Given vidistar application is launched
    And I have logged in the application as "FAXLOGAUDITOR" user
    When I navigate to "System Settings" tab
    And I click on the "Audit Repository" option in the settings panel
    And I select "Audit Event" as "SecurityAlert"
    And I enter "Timestamp from" as "4 weeks ago"
    And get the "Audit" logs count
    And "click" the "Export Audit Repository to CSV" button
    And save the "Audit Repository" exported data
    And wait for the download to complete
    Then exported logs count should match with UI logs
    And exported logs should contain the filtered results

  @DeleteExportedFiles
  @44961-7-2
  Scenario: Verify that faxlog exported file contains filtered logs and count should match
    Given vidistar application is launched
    And I have logged in the application as "FAXLOGAUDITOR" user
    When I navigate to "System Settings" tab
    And I click on the "Fax Log" option in the settings panel
    And I enter "Document Title" as "Echo"
    And I enter "Timestamp from" as "4 weeks ago"
    And get the "Fax" logs count
    And "click" the "Export Fax Logs to CSV" button
    And save the "Fax Logs" exported data
    And wait for the download to complete
    Then exported logs count should match with UI logs
    And exported logs should contain the filtered results

  @44961-8
  Scenario: Verify that export button is not present for tabs apart from FaxLog and Audit Repository
    Given vidistar application is launched
    And I have logged in the application as "FAXLOGAUDITOR" user
    When I navigate to "System Settings" tab
    Then following Settings "should" be available in the left panel
      | Edit Account |
    And export button for this settings should not be visible

  @44961-9
  Scenario: Verify export functionality upon removal of appropriate roles
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    And I navigate to "System Settings" tab
    And I remove the "faxlog" role for the "FAXLOGAUDITOR" user
    And I remove the "auditor" role for the "FAXLOGAUDITOR" user
    And I log out of the application
    And log back in the application as "FAXLOGAUDITOR" user
    And I navigate to "System Settings" tab
    Then following Settings "should not" be available in the left panel
      | Fax Log          |
      | Audit Repository |
    When I log out of the application
    And log back in the application as "Admin" user
    And I navigate to "System Settings" tab
    And add the "faxlog" role for the "FAXLOGAUDITOR" user
    And add the "auditor" role for the "FAXLOGAUDITOR" user
    And I log out of the application
    And log back in the application as "FAXLOGAUDITOR" user
    And I navigate to "System Settings" tab
    And I click on the "Audit Repository" option in the settings panel
    Then "Export Audit Repository to CSV" button should be present
    When I click on the "Fax Log" option in the settings panel
    Then "Export Fax Logs to CSV" button should be present

  @44961-13-1
  Scenario: Verify export functionality for maximum logs export limit for audit repository
    Given vidistar application is launched
    And I have logged in the application as "FAXLOGAUDITOR" user
    When I navigate to "System Settings" tab
    And I click on the "Audit Repository" option in the settings panel
    And "click" the "Export Audit Repository to CSV" button
    Then export error message should be displayed

  @44961-13-2
  Scenario: Verify export functionality for maximum logs export limit for faxlog
    Given vidistar application is launched
    And I have logged in the application as "FAXLOGAUDITOR" user
    When I navigate to "System Settings" tab
    And I click on the "Audit Repository" option in the settings panel
    And "click" the "Export Audit Repository to CSV" button
    Then export error message should be displayed

  @DeleteExportedFiles
  @44961-15-1
  Scenario: Verify if user is able to download audit repository logs multiple times with similar search criteria
    Given vidistar application is launched
    And I have logged in the application as "FAXLOGAUDITOR" user
    When I navigate to "System Settings" tab
    And I click on the "Audit Repository" option in the settings panel
    And I select "Audit Event" as "SecurityAlert"
    And I enter "Timestamp from" as "4 weeks ago"
    And "click" the "Export Audit Repository to CSV" button
    And save the "Audit Repository" exported data
    And wait for the download to complete
    And "click" the "Export Audit Repository to CSV" button
    Then save file window should appear

  @DeleteExportedFiles
  @44961-15-2
  Scenario: Verify if user is able to download fax logs multiple times with similar search criteria
    Given vidistar application is launched
    And I have logged in the application as "FAXLOGAUDITOR" user
    When I navigate to "System Settings" tab
    And I click on the "Fax Log" option in the settings panel
    And I enter "Document Title" as "Echo"
    And I enter "Timestamp from" as "4 weeks ago"
    And "click" the "Export Fax Logs to CSV" button
    And save the "Fax Logs" exported data
    And wait for the download to complete
    And "click" the "Export Fax Logs to CSV" button
    Then save file window should appear

  @DeleteExportedFiles
  @RevertAssignedFaxlogAuditorRole
  @44961-10-1
  Scenario: Verify audit repository export functionality for different user
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    And I navigate to "System Settings" tab
    And add the "faxlog" role for the "Physician" user
    And add the "auditor" role for the "Physician" user
    And I log out of the application
    And log back in the application as "Physician" user
    And I navigate to "System Settings" tab
    And I click on the "Audit Repository" option in the settings panel
    Then "Export Audit Repository to CSV" button should be present
    When I "hover" the "Export Audit Repository to CSV" button
    Then "Export Audit Repository to CSV" tooltip should be displayed
    When I select "Audit Event" as "SecurityAlert"
    And I enter "Timestamp from" as "4 weeks ago"
    And "click" the "Export Audit Repository to CSV" button
    And save the "Audit Repository" exported data
    And wait for the download to complete
    Then exported data should get downloaded in the csv file format

  @DeleteExportedFiles
  @RevertAssignedFaxlogAuditorRole
  @44961-10-2
  Scenario: Verify faxlog export functionality for different user
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    And I navigate to "System Settings" tab
    And add the "faxlog" role for the "Physician" user
    And add the "auditor" role for the "Physician" user
    And I log out of the application
    And log back in the application as "Physician" user
    And I navigate to "System Settings" tab
    And I click on the "Fax Log" option in the settings panel
    Then "Export Fax Logs to CSV" button should be present
    When I "hover" the "Export Fax Logs to CSV" button
    Then "Export Fax Logs to CSV" tooltip should be displayed
    When I enter "Timestamp from" as "4 weeks ago"
    And I "click" the "Export Fax Logs to CSV" button
    And save the "Fax Logs" exported data
    And wait for the download to complete
    Then exported data should get downloaded in the csv file format

  @DeleteExportedFiles
  @44961-11-1
  Scenario: Verify that exported audit repository file is sorted as per UI
    Given vidistar application is launched
    And I have logged in the application as "FAXLOGAUDITOR" user
    When I navigate to "System Settings" tab
    And I click on the "Audit Repository" option in the settings panel
    And I select "Audit Event" as "SecurityAlert"
    And I enter "Timestamp from" as "4 weeks ago"
    And I sort "Timestamp (local)" column in "descending" order
    Then "AuditRepository" grid is sorted by "Timestamp (local)" in "descending" order
    When I "click" the "Export Audit Repository to CSV" button
    And save the "Audit Repository" exported data
    And wait for the download to complete
    Then exported excel should be sorted in "descending" order

  @DeleteExportedFiles
  @44961-11-2
  Scenario: Verify that exported faxlog file is sorted as per UI
    Given vidistar application is launched
    And I have logged in the application as "FAXLOGAUDITOR" user
    When I navigate to "System Settings" tab
    And I click on the "Fax Log" option in the settings panel
    And I enter "Document Title" as "Echo"
    And I enter "Timestamp from" as "4 weeks ago"
    And I sort "Timestamp" column in "descending" order
    Then "FaxLog" grid is sorted by "Timestamp" in "descending" order
    When "click" the "Export Fax Logs to CSV" button
    And save the "Fax Logs" exported data
    And wait for the download to complete
    Then exported excel should be sorted in "descending" order