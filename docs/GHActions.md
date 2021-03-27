# GitHub Actions

The following plans are used:
| Plan | Runs when | Description |
| --- | --- | --- |
| [checkBuild.yml](../.github/workflows/checkBuild.yml) | Push or PullRequest on develop | Checks if the project is buildable |
| [release.yml](../.github/workflows/release.yml) | Push on master | Builds a release jar and creates a new release draft on GitHub, where the jar is uploaded |
| [deploy.yml](../.github/workflows/deploy.yml) | Push on master | Deploys the current code to [Heroku](Heroku.md) |
| [gh-pages.yml](../.github/workflows/gh-pages.yml) | Push on master | Creates a [project-info-report about the dependencies](https://maven.apache.org/plugins/maven-project-info-reports-plugin/dependencies-mojo.html) and publishes it with [GitHub pages](https://pages.github.com/) |
