Feature: create an issue

  Scenario Outline: create 5 stories using example table
    Given the user sends create a story in JIRA with "<summary>", "<description>", "<issueType>"
    Then the user validates story creation through UI and "<summary>", "<description>", "<issueType>"

    Examples:
      | summary                   | description                                                          | issueType |
      | Validate story creation 1 | Create a story through API call using Java code and verify with UI 1 | Story     |
      | Validate story creation 2 | Create a story through API call using Java code and verify with UI 2 | Story     |
      | Validate story creation 3 | Create a story through API call using Java code and verify with UI 3 | Story     |
      | Validate story creation 4 | Create a story through API call using Java code and verify with UI 4 | Story     |
      | Validate story creation 5 | Create a story through API call using Java code and verify with UI 5 | Story     |
      | Validate bug creation 1   | Create a bug through API call using Java code and verify with UI 1   | Bug       |
      | Validate bug creation 2   | Create a bug through API call using Java code and verify with UI 2   | Bug       |
      | Validate bug creation 3   | Create a bug through API call using Java code and verify with UI 3   | Bug       |
      | Validate bug creation 4   | Create a bug through API call using Java code and verify with UI 4   | Bug       |
      | Validate bug creation 5   | Create a bug through API call using Java code and verify with UI 5   | Bug       |



