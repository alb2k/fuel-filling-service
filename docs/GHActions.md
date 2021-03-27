# GitHub Actions

The following plans are used:
| Plan | Runs when | Description |
| --- | --- | --- |
| [checkBuild.yml](../.github/workflows/checkBuild.yml) | Manually<br>Push or PullRequest on develop | Checks if the project is buildable |
| [release.yml](../.github/workflows/release.yml) | Push on master | Builds a release jar and creates a new release draft on GitHub, where the jar is uploaded |
| [deploy.yml](../.github/workflows/deploy.yml) | Push on master | Deploys the current code to [Heroku](Heroku.md) |
