@insprint
Feature: Nested Filters functionality

  @NestedFilters-1
  Scenario Outline: Verify nested filters functionality for Match All condition on study list
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I click on Nested filter under "StudyList" tab
    And I click on match filter dropdown
    And I select "Match All" from the match filter dropdown
    And I set nested filter as below
      | FilterColumn | FilterCondition | FilterValue |
      | <Criteria1>  | <Condition>     | <Value1>    |
      | <Criteria2>  | <Condition>     | <Value2>    |
      | <Criteria3>  | <Condition>     | <Value3>    |
    And click on search button
    Then studies should get listed as per the filters applied

    Examples:
      | Condition                        | Criteria1      | Value1                | Criteria2       | Value2    | Criteria3           | Value3        |
      | equals                           | Patient Name   | MACDONALD^BENJAMIN^^^ | Patient ID      | 5625561   | Referring Physician | ZUFELT^^^^    |
      | not equal                        | Patient Name   | MACDONALD^BENJAMIN^^^ | Patient ID      | 5625561   | Referring Physician | ZUFELT^^^^    |
      | equals (disregard case)          | Patient Name   | MACDONALD^BENJAMIN^^^ | Patient ID      | 5625561   | Referring Physician | ZUFELT^^^^    |
      | not equal (disregard case)       | Patient Name   | MACDONALD^BENJAMIN^^^ | Patient ID      | 5625561   | Referring Physician | ZUFELT^^^^    |
      | contains                         | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | does not contain                 | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | contains (match case)            | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | does not contain (match case)    | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | starts with                      | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | starts with (match case)         | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | does not start with              | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | does not start with (match case) | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | equals                           | Worksheet      | COMPLETED             | Final Report    | COMPLETED | Patient Sex         | Male          |
      | not equal                        | Worksheet      | COMPLETED             | Final Report    | COMPLETED | Patient Sex         | Male          |
      | is one of                        | Assigned Users | AutoPhysician         | Assigned Groups | AAA       | Service Location    | VidiStar, LLC |

  @NestedFilters-2
  Scenario Outline: Verify nested filters functionality for Match None condition on Study list
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I click on Nested filter under "StudyList" tab
    And I click on match filter dropdown
    And I select "Match None" from the match filter dropdown
    And I set nested filter as below
      | FilterColumn | FilterCondition | FilterValue |
      | <Criteria1>  | <Condition>     | <Value1>    |
      | <Criteria2>  | <Condition>     | <Value2>    |
      | <Criteria3>  | <Condition>     | <Value3>    |
    And click on search button
    Then studies should get listed as per the filters applied

    Examples:
      | Condition                        | Criteria1      | Value1                | Criteria2       | Value2    | Criteria3           | Value3        |
      | equals                           | Patient Name   | MACDONALD^BENJAMIN^^^ | Patient ID      | 5625561   | Referring Physician | ZUFELT^^^^    |
      | not equal                        | Patient Name   | MACDONALD^BENJAMIN^^^ | Patient ID      | 5625561   | Referring Physician | ZUFELT^^^^    |
      | equals (disregard case)          | Patient Name   | MACDONALD^BENJAMIN^^^ | Patient ID      | 5625561   | Referring Physician | ZUFELT^^^^    |
      | not equal (disregard case)       | Patient Name   | MACDONALD^BENJAMIN^^^ | Patient ID      | 5625561   | Referring Physician | ZUFELT^^^^    |
      | contains                         | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | does not contain                 | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | contains (match case)            | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | does not contain (match case)    | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | starts with                      | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | starts with (match case)         | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | does not start with              | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | does not start with (match case) | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | equals                           | Worksheet      | COMPLETED             | Final Report    | COMPLETED | Patient Sex         | Male          |
      | not equal                        | Worksheet      | COMPLETED             | Final Report    | COMPLETED | Patient Sex         | Male          |
      | is one of                        | Assigned Users | AutoPhysician         | Assigned Groups | AAA       | Service Location    | VidiStar, LLC |

  @NestedFilters-3
  Scenario Outline: Verify nested filters functionality for Match Any condition on Study list
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I click on Nested filter under "StudyList" tab
    And I click on match filter dropdown
    And I select "Match Any" from the match filter dropdown
    And I set nested filter as below
      | FilterColumn | FilterCondition | FilterValue |
      | <Criteria1>  | <Condition>     | <Value1>    |
      | <Criteria2>  | <Condition>     | <Value2>    |
      | <Criteria3>  | <Condition>     | <Value3>    |
    And click on search button
    Then studies should get listed as per the filters applied

    Examples:
      | Condition                        | Criteria1      | Value1                | Criteria2       | Value2    | Criteria3           | Value3        |
      | equals                           | Patient Name   | MACDONALD^BENJAMIN^^^ | Patient ID      | 5625561   | Referring Physician | ZUFELT^^^^    |
      | not equal                        | Patient Name   | MACDONALD^BENJAMIN^^^ | Patient ID      | 5625561   | Referring Physician | ZUFELT^^^^    |
      | equals (disregard case)          | Patient Name   | MACDONALD^BENJAMIN^^^ | Patient ID      | 5625561   | Referring Physician | ZUFELT^^^^    |
      | not equal (disregard case)       | Patient Name   | MACDONALD^BENJAMIN^^^ | Patient ID      | 5625561   | Referring Physician | ZUFELT^^^^    |
      | contains                         | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | does not contain                 | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | contains (match case)            | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | does not contain (match case)    | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | starts with                      | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | starts with (match case)         | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | does not start with              | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | does not start with (match case) | Patient Name   | MACDONALD^BENJAMIN    | Patient ID      | 5625561   | Referring Physician | ZUFELT        |
      | equals                           | Worksheet      | COMPLETED             | Final Report    | COMPLETED | Patient Sex         | Male          |
      | not equal                        | Worksheet      | COMPLETED             | Final Report    | COMPLETED | Patient Sex         | Male          |
      | is one of                        | Assigned Users | AutoPhysician         | Assigned Groups | AAA       | Service Location    | VidiStar, LLC |

  @NestedFilters-4
  Scenario Outline: Verify nested filters functionality for Match All(null/not null) condition on Study list
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I click on Nested filter under "StudyList" tab
    And I click on match filter dropdown
    And I select "Match All" from the match filter dropdown
    And I set nested filter as below
      | FilterColumn | FilterCondition |
      | <Criteria1>  | <Condition>     |
      | <Criteria2>  | <Condition>     |
      | <Criteria3>  | <Condition>     |
    And click on search button
    Then studies should get listed as per the filters applied

    Examples:
      | Condition   | Criteria1 | Criteria2   | Criteria3    |
      | is null     | Worksheet | Patient Sex | Final Report |
      | is not null | Worksheet | Patient Sex | Final Report |

  @NestedFilters-5
  Scenario Outline: Verify nested filters functionality for Match None(null/not null) condition on Study list
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I click on Nested filter under "StudyList" tab
    And I click on match filter dropdown
    And I select "Match None" from the match filter dropdown
    And I set nested filter as below
      | FilterColumn | FilterCondition |
      | <Criteria1>  | <Condition>     |
      | <Criteria2>  | <Condition>     |
      | <Criteria3>  | <Condition>     |
    And click on search button
    Then studies should get listed as per the filters applied

    Examples:
      | Condition   | Criteria1 | Criteria2   | Criteria3    |
      | is null     | Worksheet | Patient Sex | Final Report |
      | is not null | Worksheet | Patient Sex | Final Report |

  @NestedFilters-6
  Scenario Outline: Verify nested filters functionality for Match Any(null/not null) condition on Study list
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I click on Nested filter under "StudyList" tab
    And I click on match filter dropdown
    And I select "Match Any" from the match filter dropdown
    And I set nested filter as below
      | FilterColumn | FilterCondition |
      | <Criteria1>  | <Condition>     |
      | <Criteria2>  | <Condition>     |
      | <Criteria3>  | <Condition>     |
    And click on search button
    Then studies should get listed as per the filters applied

    Examples:
      | Condition   | Criteria1 | Criteria2   | Criteria3    |
      | is null     | Worksheet | Patient Sex | Final Report |
      | is not null | Worksheet | Patient Sex | Final Report |

  @NestedFilters-7
  Scenario Outline: Verify nested filters functionality for Match All condition on Study Tree(patients)
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "Study Tree" tab
    And I click on Nested filter under "StudyTree" tab
    And I click on match filter dropdown
    And I select "Match All" from the match filter dropdown
    And I set nested filter as below
      | FilterColumn | FilterCondition | FilterValue |
      | <Criteria1>  | <Condition>     | <Value1>    |
      | <Criteria2>  | <Condition>     | <Value2>    |
      | <Criteria3>  | <Condition>     | <Value3>    |
    And I click on search button
    Then "patients" should get listed as per the filters applied for Study Tree

    Examples:
      | Condition | Criteria1    | Value1                | Criteria2  | Value2  | Criteria3   | Value3 |
      | equals    | Patient Name | MACDONALD^BENJAMIN^^^ | Patient ID | 5625561 | Patient Sex | Male   |
      | not equal | Patient Name | MACDONALD^BENJAMIN^^^ | Patient ID | 5625561 | Patient Sex | Male   |

  @NestedFilters-8
  Scenario Outline: Verify nested filters functionality for Match None condition on Study Tree(patients)
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "Study Tree" tab
    And I click on Nested filter under "StudyTree" tab
    And I click on match filter dropdown
    And I select "Match None" from the match filter dropdown
    And I set nested filter as below
      | FilterColumn | FilterCondition | FilterValue |
      | <Criteria1>  | <Condition>     | <Value1>    |
      | <Criteria2>  | <Condition>     | <Value2>    |
      | <Criteria3>  | <Condition>     | <Value3>    |
    And I click on search button
    Then "patients" should get listed as per the filters applied for Study Tree

    Examples:
      | Condition | Criteria1    | Value1                | Criteria2  | Value2  | Criteria3   | Value3 |
      | equals    | Patient Name | MACDONALD^BENJAMIN^^^ | Patient ID | 5625561 | Patient Sex | Male   |
      | not equal | Patient Name | MACDONALD^BENJAMIN^^^ | Patient ID | 5625561 | Patient Sex | Male   |

  @NestedFilters-9
  Scenario Outline: Verify nested filters functionality for Match Any condition on Study Tree(patients)
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "Study Tree" tab
    And I click on Nested filter under "StudyTree" tab
    And I click on match filter dropdown
    And I select "Match Any" from the match filter dropdown
    And I set nested filter as below
      | FilterColumn | FilterCondition | FilterValue |
      | <Criteria1>  | <Condition>     | <Value1>    |
      | <Criteria2>  | <Condition>     | <Value2>    |
      | <Criteria3>  | <Condition>     | <Value3>    |
    And I click on search button
    Then "patients" should get listed as per the filters applied for Study Tree

    Examples:
      | Condition | Criteria1    | Value1                | Criteria2  | Value2  | Criteria3   | Value3 |
      | equals    | Patient Name | MACDONALD^BENJAMIN^^^ | Patient ID | 5625561 | Patient Sex | Male   |
      | not equal | Patient Name | MACDONALD^BENJAMIN^^^ | Patient ID | 5625561 | Patient Sex | Male   |

  @NestedFilters-10
  Scenario Outline: Verify nested filters functionality for Match All(null/not null) condition on Study Tree(patients)
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "Study Tree" tab
    And I click on Nested filter under "StudyTree" tab
    And I click on match filter dropdown
    And I select "Match All" from the match filter dropdown
    And I set nested filter as below
      | FilterColumn | FilterCondition |
      | <Criteria1>  | <Condition>     |
      | <Criteria2>  | <Condition>     |
      | <Criteria3>  | <Condition>     |
    And I click on search button
    Then "patients" should get listed as per the filters applied for Study Tree

    Examples:
      | Condition   | Criteria1    | Criteria2  | Criteria3   |
      | is null     | Patient Name | Patient ID | Patient Sex |
      | is not null | Patient Name | Patient ID | Patient Sex |

  @NestedFilters-11
  Scenario Outline: Verify nested filters functionality for Match None(null/not null) condition on Study Tree(patients)
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "Study Tree" tab
    And I click on Nested filter under "StudyTree" tab
    And I click on match filter dropdown
    And I select "Match None" from the match filter dropdown
    And I set nested filter as below
      | FilterColumn | FilterCondition |
      | <Criteria1>  | <Condition>     |
      | <Criteria2>  | <Condition>     |
      | <Criteria3>  | <Condition>     |
    And I click on search button
    Then "patients" should get listed as per the filters applied for Study Tree

    Examples:
      | Condition   | Criteria1    | Criteria2  | Criteria3   |
      | is null     | Patient Name | Patient ID | Patient Sex |
      | is not null | Patient Name | Patient ID | Patient Sex |

  @NestedFilters-12
  Scenario Outline: Verify nested filters functionality for Match Any(null/not null) condition on Study Tree(patients)
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "Study Tree" tab
    And I click on Nested filter under "StudyTree" tab
    And I click on match filter dropdown
    And I select "Match Any" from the match filter dropdown
    And I set nested filter as below
      | FilterColumn | FilterCondition |
      | <Criteria1>  | <Condition>     |
      | <Criteria2>  | <Condition>     |
      | <Criteria3>  | <Condition>     |
    And I click on search button
    Then "patients" should get listed as per the filters applied for Study Tree

    Examples:
      | Condition   | Criteria1    | Criteria2  | Criteria3   |
      | is null     | Patient Name | Patient ID | Patient Sex |
      | is not null | Patient Name | Patient ID | Patient Sex |

  @NestedFilters-13
  Scenario Outline: Verify nested filters functionality for Match All condition on Study Tree(patients)
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "Study Tree" tab
    And I click on Nested filter under "StudyTree" tab
    And I click on match filter dropdown
    And I select "Match All" from the match filter dropdown
    And I set nested filter as below
      | FilterColumn | FilterCondition | FilterValue |
      | <Criteria1>  | <Condition>     | <Value1>    |
      | <Criteria2>  | <Condition>     | <Value2>    |
    And I click on search button
    Then "patients" should get listed as per the filters applied for Study Tree

    Examples:
      | Condition                        | Criteria1    | Value1                | Criteria2  | Value2  |
      | equals (disregard case)          | Patient Name | MACDONALD^BENJAMIN^^^ | Patient ID | 5625561 |
      | not equal (disregard case)       | Patient Name | MACDONALD^BENJAMIN^^^ | Patient ID | 5625561 |
      | contains                         | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |
      | does not contain                 | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |
      | contains (match case)            | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |
      | does not contain (match case)    | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |
      | starts with                      | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |
      | starts with (match case)         | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |
      | does not start with              | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |
      | does not start with (match case) | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |

  @NestedFilters-14
  Scenario Outline: Verify nested filters functionality for Match None condition on Study Tree(patients)
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "Study Tree" tab
    And I click on Nested filter under "StudyTree" tab
    And I click on match filter dropdown
    And I select "Match None" from the match filter dropdown
    And I set nested filter as below
      | FilterColumn | FilterCondition | FilterValue |
      | <Criteria1>  | <Condition>     | <Value1>    |
      | <Criteria2>  | <Condition>     | <Value2>    |
    And I click on search button
    Then "patients" should get listed as per the filters applied for Study Tree

    Examples:
      | Condition                        | Criteria1    | Value1                | Criteria2  | Value2  |
      | equals (disregard case)          | Patient Name | MACDONALD^BENJAMIN^^^ | Patient ID | 5625561 |
      | not equal (disregard case)       | Patient Name | MACDONALD^BENJAMIN^^^ | Patient ID | 5625561 |
      | contains                         | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |
      | does not contain                 | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |
      | contains (match case)            | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |
      | does not contain (match case)    | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |
      | starts with                      | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |
      | starts with (match case)         | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |
      | does not start with              | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |
      | does not start with (match case) | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |

  @NestedFilters-15
  Scenario Outline: Verify nested filters functionality for Match Any condition on Study Tree(patients)
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "Study Tree" tab
    And I click on Nested filter under "StudyTree" tab
    And I click on match filter dropdown
    And I select "Match Any" from the match filter dropdown
    And I set nested filter as below
      | FilterColumn | FilterCondition | FilterValue |
      | <Criteria1>  | <Condition>     | <Value1>    |
      | <Criteria2>  | <Condition>     | <Value2>    |
    And I click on search button
    Then "patients" should get listed as per the filters applied for Study Tree

    Examples:
      | Condition                        | Criteria1    | Value1                | Criteria2  | Value2  |
      | equals (disregard case)          | Patient Name | MACDONALD^BENJAMIN^^^ | Patient ID | 5625561 |
      | not equal (disregard case)       | Patient Name | MACDONALD^BENJAMIN^^^ | Patient ID | 5625561 |
      | contains                         | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |
      | does not contain                 | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |
      | contains (match case)            | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |
      | does not contain (match case)    | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |
      | starts with                      | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |
      | starts with (match case)         | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |
      | does not start with              | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |
      | does not start with (match case) | Patient Name | MACDONALD^BENJAMIN    | Patient ID | 5625561 |

  @NestedFilters-16
  Scenario Outline: Verify nested filters functionality for Match All condition on Study Tree(studies)
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "Study Tree" tab
    And I click on Nested filter under "StudyTree" tab
    And I click on match filter dropdown
    And I select "Match All" from the match filter dropdown
    And I set nested filter as below
      | FilterColumn | FilterCondition | FilterValue |
      | <Criteria1>  | <Condition>     | <Value1>    |
      | <Criteria2>  | <Condition>     | <Value2>    |
    And I click on search button
    And I expand first patient
    Then "studies" should get listed as per the filters applied for Study Tree

    Examples:
      | Condition | Criteria1       | Value1    | Criteria2        | Value2        |
      | equals    | Worksheet       | COMPLETED | Final Report     | COMPLETED     |
      | not equal | Worksheet       | COMPLETED | Final Report     | COMPLETED     |
      | is one of | Assigned Groups | AAA       | Service Location | VidiStar, LLC |

  @NestedFilters-17
  Scenario Outline: Verify nested filters functionality for Match None condition on Study Tree(studies)
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "Study Tree" tab
    And I click on Nested filter under "StudyTree" tab
    And I click on match filter dropdown
    And I select "Match None" from the match filter dropdown
    And I set nested filter as below
      | FilterColumn | FilterCondition | FilterValue |
      | <Criteria1>  | <Condition>     | <Value1>    |
      | <Criteria2>  | <Condition>     | <Value2>    |
    And I click on search button
    And I expand first patient
    Then "studies" should get listed as per the filters applied for Study Tree

    Examples:
      | Condition | Criteria1       | Value1    | Criteria2        | Value2        |
      | equals    | Worksheet       | COMPLETED | Final Report     | COMPLETED     |
      | not equal | Worksheet       | COMPLETED | Final Report     | COMPLETED     |
      | is one of | Assigned Groups | AAA       | Service Location | VidiStar, LLC |

  @NestedFilters-18
  Scenario Outline: Verify nested filters functionality for Match Any condition on Study Tree(studies)
    Given vidistar application is launched
    And I have logged in the application as "Admin" user
    When I navigate to "Study Tree" tab
    And I click on Nested filter under "StudyTree" tab
    And I click on match filter dropdown
    And I select "Match Any" from the match filter dropdown
    And I set nested filter as below
      | FilterColumn | FilterCondition | FilterValue |
      | <Criteria1>  | <Condition>     | <Value1>    |
      | <Criteria2>  | <Condition>     | <Value2>    |
    And I click on search button
    And I expand first patient
    Then "studies" should get listed as per the filters applied for Study Tree

    Examples:
      | Condition | Criteria1       | Value1    | Criteria2        | Value2        |
      | equals    | Worksheet       | COMPLETED | Final Report     | COMPLETED     |
      | not equal | Worksheet       | COMPLETED | Final Report     | COMPLETED     |
      | is one of | Assigned Groups | AAA       | Service Location | VidiStar, LLC |