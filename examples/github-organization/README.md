# Basic Jenkins that scans a GitHub Organization
This example deploys a very basic Jenkins instance, extending our [base configuration](../../base) to scan a GitHub Organization for Jenkinsfiles.

To deploy and use this example, perform the following steps:
 * Create a new namespace for the example `kubectl create ns jenkins-on-k8s-github-example`
 * Set an admin password file `echo JENKINS_ADMIN_PASSWORD=examplePassword > jenkins-admin-password.env`
 * Set your GitHub Organization name `echo GITHUB_ORGANIZATION_NAME=Awesomeware-ACME > github-organization-name.env`
 * Create a [Personal Access Token](https://support.cloudbees.com/hc/en-us/articles/234710368-GitHub-Permissions-and-API-token-Scopes-for-Jenkins). For use with your real GitHub Organization, it is recommended to do this on a separate "Jenkins" GitHub user, so you can restrict what organizations and repositories Jenkins can see and manipulate. The minimum access token scopes needed are `admin:repo_hook` and `repo`. If you want to authenticate users in Jenkins via their GitHub usernames, you will also want `read:org` and `user:email`
 * Set your access token details for Jenkins by copying `secrets.yaml.example` to `secrets.yaml` and adjusting for your details
 * Deploy Jenkins `kustomize build | kubectl apply -f -`
 * Wait for the deployment to complete `kubectl rollout status deployment/jenkins-master -n jenkins-on-k8s-github-example`
 * Port forward to Jenkins to see the UI `kubectl port-forward deployment/jenkins-master -n jenkins-on-k8s-github-example 8080`
 * Visit the UI on [https://127.0.0.1:8080](https://127.0.0.1:8080) and confirm you can see Jenkins
 * Use the login link and login with username of `admin` and the password you set earlier
 * Confirm you can now see a manage jenkins link
 * Confirm that the GitHub Organization you configured shows up in the build list.
