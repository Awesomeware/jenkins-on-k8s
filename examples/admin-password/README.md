# Basic Jenkins with an admin account
This example deploys a very basic Jenkins instance, extending our [base configuration](../../base/README.md) to set an admin account. Additionally, we configure the authorization so if you are not logged in, you have a read-only view of our Jenkins instance.

To deploy and use this example, perform the following steps:
 * Create a new namespace for the example `kubectl create ns jenkins-on-k8s-admin-example`
 * Set an admin password file `echo JENKINS_ADMIN_PASSWORD=examplePassword > jenkins-admin-password.env`
 * Deploy Jenkins `kubectl apply -n jenkins-on-k8s-admin-example -k .`
 * Wait for the deployment to complete `kubectl rollout status deployment/jenkins-master -n jenkins-on-k8s-admin-example`
 * Port forward to Jenkins to see the UI `kubectl port-forward deployment/jenkins-master -n jenkins-on-k8s-admin-example 8080`
 * Visit the UI on [https://127.0.0.1:8080](https://127.0.0.1:8080) and confirm you can see Jenkins
 * Use the login link and login with username of `admin` and the password you set earlier
 * Confirm you can now see a manage jenkins link
