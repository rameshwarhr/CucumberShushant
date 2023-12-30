@46147
Feature: 46147

  @46147_1
  @46147_3
  Scenario: To verify that the cd icon is greyed out and tool tip displays a message as expired
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    And I navigate to "Media" tab
    And I search patient "OLSON ELI" under media tab
    Then the the CD icon should be be greyed out
    And the tool tip should display the message as "Expired"

  @46147_2
  Scenario: To verify that download iso image displays an appropriate error message when clicked for expired cd
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    And I navigate to "Media" tab
    And I search patient "OLSON ELI" under media tab
    And I click on download ISO image icon
    Then pop-up should be displayed with error message as "This media has expired. Please burn again."

  @46147_4
  @46147_5
  @RevertToDefaultClinicParameterValue
  Scenario: To verify that media retention period can be modified and it does not affects the other clinic parameter value
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    And I navigate to "System Settings" tab
    And I click on the "Clinic parameters" option in the settings panel
    And type "AuditRepositoryManager.logExportLimit" in the search clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb.rest.AuditRepositoryManager.logExportLimit" in the filtered clinic parameter result
    And I set the parameter value to "500" and click on Save
    And type "mediaRetentionPeriod" in the search clinic parameter textbox
    And I click on "com.vidistar.dicom.ejb.rest.ExportedMediaManager.mediaRetentionPeriod" in the filtered clinic parameter result
    And I set the parameter value to "2" and click on Save
    Then the value of "AuditRepositoryManager.logExportLimit" "should not" change to "2"
    And  the value of "mediaRetentionPeriod" "should" change to "2"

  @46147_8
  @DeleteBurnedPatients
  Scenario: To verify that CD can be burned at study level
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I enter "CAROTID^CEREBROVASCULAR" in "Patient Name" filter under study list tab
    And I navigate to "Study Tree" tab
    And I expand studies for first patient
    And select all the studies of 1st patient
    And click on "Burn selected Entities to CD/DVD ROM" button
    And click on Yes
    And I navigate to "Media" tab
    And I search patient "CAROTID CEREBROVASCULAR" under media tab
    Then the patient should have green check CD icon
    When I expand the cd burned patient in media tab
    And I expand the patient to view its studies
    Then all the studies of that patient should be burned to media

  @46147_14
  Scenario: To verify that media creation status dropdown have expired value
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "Media" tab
    And I click on media creation status dropdown
    Then dropdown should contain "Expired" option

  @46147_9
  Scenario: To verify that expired iso gets added to the audit repository
    Given vidistar application is launched
    And I have logged in the application as "FaxLogAuditor" user
    When I click on the "Audit Repository" option in the settings panel
    And I select "Audit Event" as "ISODeleted"
    And I search "OLSON^ELI" patient under audit repository
    And I clear the timestamp value textbox field
    Then the searched patient should be present under information column