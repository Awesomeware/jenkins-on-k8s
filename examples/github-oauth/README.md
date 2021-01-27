# Basic Jenkins with GitHub organization scanning & auth
This example deploys a very basic Jenkins instance, extending our [base configuration](../../base) to:

* Authenticate the security realm via GitHub OAuth.
* Authorize user permissions based on GitHub repository permissions.
* Scan for repositories with Jenkinsfiles in your GitHub organization.

Prior to the below setup, create a new namespace for the example `kubectl create ns jenkins-on-k8s-github-example`

## Set up Github Organization scanning
 * Set your GitHub Organization name `echo GITHUB_ORGANIZATION_NAME=Awesomeware-ACME > github-organization.env`
 * Create a [Personal Access Token](https://support.cloudbees.com/hc/en-us/articles/234710368-GitHub-Permissions-and-API-token-Scopes-for-Jenkins). For use with your real GitHub Organization, it is recommended to do this on a separate "Jenkins" GitHub user, so you can restrict what organizations and repositories Jenkins can see and manipulate. The minimum access token scopes needed are `admin:repo_hook` and `repo`. If you want to authenticate users in Jenkins via their GitHub usernames, you will also want `read:org` and `user:email`
 * Set your access token details for Jenkins by copying `secrets.yaml.example` to `secrets.yaml` and adjusting for your details

## Set up GitHub OAuth
 * Create a [GitHub app](https://plugins.jenkins.io/github-oauth/), see the steps under "Setup"
   and note down the `clientId` and `clientSecret`.
 * Set the clientID to use `echo GITHUB_CLIENT_ID=<clientId> >> github-oauth.env`
 * Set the clientSecret to use `echo GITHUB_CLIENT_SECRET=<clientSecret> >> github-oauth.env`
 * Set a list of GitHub usernames to make admins, separated by comma `echo GITHUB_ADMINS=person1,person2,person3 >> github-oauth.env`
 * Set a list of Organization names to allow, separated by comma `echo GITHUB_ORG_NAMES=yourOrg >> github-oauth.env`

## Set up Jenkins ingress & URL
 * Deploy an ingress into the example namespace to reach your Jenkins instance, see e.g., `ingress.yaml.example`
 * Edit `configuration/groovy/set-url.groovy` to point to the URL
 you've set up an ingress for.

## Deploy
 * Deploy Jenkins `kustomize build | kubectl apply -f -`
 * Wait for the deployment to complete `kubectl rollout status deployment/jenkins-master -n jenkins-on-k8s-github-example`
 * Visit the UI at your URL and confirm you can see Jenkins.
 * Try to Log In, and confirm that you are directed to GitHub, asked
 to approve access to the GitHub app you set up, and then directed back
 to Jenkins.
 * If you added your GitHub username as one of the admins, you should
 now see "Manage Jenkins" in the left-side menu.
 * Confirm that the GitHub Organization you configured shows up in the build list.
 * Trigger an Organization scan by hitting 'Scan', and confirm it works.
