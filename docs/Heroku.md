# Heroku deployment
The goal here was to deploy a demo online, so that you don't have to run the code locally and also have a showcase available.

I used [Heroku](https://www.heroku.com/home) once before with GitHub so I choose that as my cloud platform again.

## How to set it up
The setup is based on [this guide](https://dev.to/heroku/deploying-to-heroku-from-github-actions-29ej)

### Preparing Heroku
At first: You need to [setup an account](https://signup.heroku.com/)

TL;DR<br/>
Web applications on Heroku are called [web dynos](https://www.heroku.com/dynos).<br>
A [free tier account on Heroku](https://www.heroku.com/pricing) grants you 550 dyno hours (and additional 450 hours if you add your credit card).<br>
Your dyno will automatically sleep/shut down if there is no activity in the last 30 minutes.

After the account is set up, create a new app:
![Pic](https://user-images.githubusercontent.com/80211953/111872102-e4fb4a80-898d-11eb-8151-ab41ca7a23b7.png)
Choose your region, add a name (the app will be available under that ``https://<name>.herokuapp.com``) and then click create app.

### Deploying the project using GitHub
Great, now we have a new app on heroku... but there isn't actually anything running, because nothing was deployed.

So how can we deploy code (from GitHub)?<br>
There are 2 ways:
* Setup a deployment pipeline directly in Heroku
* Write a [GitHub actions](https://github.com/features/actions) workflow that builds and deploys the code

I wanted to stay independent from Heroku and - through the fact that I already had experience with GitHub Actions - I ultimately choose those.

#### Setting up secrets
There have to be some [secrets stored for the coming GitHub Actions workflow](https://docs.github.com/en/actions/reference/encrypted-secrets#creating-encrypted-secrets-for-a-repository):
* ``HEROKU_APP_NAME``<br>
The app-name that is used on heroku e.g. ``hackathon-ms-fuel-filling``
* ``HEROKU_API_KEY``<br>
The API-Key so that the app can be deployed to heroku.<br>
The key can be generated via multiple methods.<br>
I recommend adding a new "authorization" (API-Key) using https://dashboard.heroku.com/account/applications#authorizations

#### Writing the workflow
Setup a new workflow and name it e.g. [deploy.yml](../.github/workflows/deploy.yml)

The workflow has to meet the following requirements:
* Executed the workflow when a new release is created or manually
* Build tha app (as jar)
* Deploy the app to Heroku

The first two parts are pretty easy doable if you know a bit about GitHub Actions.<br>
The last part is a little bit more tricky:
* GitHub Actions own virtual (linux) machines are by default equipped with the Heroku CLI.<br>More details are available [here](https://github.com/actions/virtual-environments#available-environments)
* At first install [Heroku's Java plugin](https://github.com/heroku/plugin-java): ``heroku plugins:install java``
* Deploy the jar with the deploy command ``heroku deploy:jar``
  * Select the jar using ``target/fuel-filling-service.jar``
  * Set the JDK explicitly to Java 11 using ``--jdk 11``.<br> Heroku trys to deploy by default with JDK 8.
  * The libs folder must be included: ``--includes target/libs/``
  * Select the app that you want to deploy to with ``--app ${{ secrets.HEROKU_APP_NAME }}``
  * Of course Heroku also needs some type of authentication. This is done using ``HEROKU_API_KEY: ${{ secrets.HEROKU_API_KEY }}``

In the last 2 steps you noted the use of ``${{ secret.XXX }}`` this is the usage of the GitHub secrets we created before.

#### Using a Procfile
There is one special case when you want to use heroku: You have to either expose your app on Port 80 or you have to bind to Herokus ``PORT`` environment variable.

This can be done easily using a [Procfile](/Procfile) in the repository root which contains ``-Dserver.port=$PORT``.

#### Deploying it
Great - now when everything was done correctly - you should be able to deploy it.<br>
Go to the ``Actions`` tab of your repository and run the Deployment workflow:
![Pic](https://user-images.githubusercontent.com/80211953/111875065-137e2300-8998-11eb-9819-f83c0e04cdbc.png)

After some time you should see that your app was deployed on Heroku.<br>
It is also useful to check the logs for problems and have a quick look at your app.
![Pic](https://user-images.githubusercontent.com/80211953/111875837-1418b880-899c-11eb-895e-81c62ba2e5d4.png)
