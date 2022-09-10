Feature: It should be possible to parse JSON post content with JSONPath.

  Scenario: JSONPath is used to extract values from post content

    When received post content is:
    """
    {
      "ref": "refs/heads/develop",
      "head_commit": {
        "committer": {
          "name": "baxterthehacker",
          "username": "Baxter the Hacker"
        }
      }
    }
    """


    Given the following generic variables are configured:
      | variable            | expression                        | expressionType  | defaultValue | regexpFilter  |
      | committer_name      | $.head_commit.committer.name      | JSONPath        |              |               |
      | committer_username  | $.head_commit.committer.username  | JSONPath        |              |               |
      | ref                 | $.ref                             | JSONPath        |              |               |

    Then variables are resolved to:
      | variable            | value              |
      | committer_name      | baxterthehacker    |
      | committer_username  | Baxter the Hacker  |
      | ref                 | refs/heads/develop |


    Given the following generic variables are configured:
      | variable            | expression                        | expressionType  | defaultValue | regexpFilter  |
      | everything          | $                                 | JSONPath        |              |               |

    Then variable everything is resolved to:
    """
    {
      "ref": "refs/heads/develop",
      "head_commit": {
        "committer": {
          "name": "baxterthehacker",
          "username": "Baxter the Hacker"
        }
      }
    }
    """