#!groovy

import org.jenkinsci.plugins.GithubAuthorizationStrategy
import org.jenkinsci.plugins.GithubSecurityRealm
import jenkins.model.Jenkins

def jenkins = Jenkins.getInstance()

def githubSecurityRealm = new GithubSecurityRealm(
    GithubSecurityRealm.DEFAULT_WEB_URI,
    GithubSecurityRealm.DEFAULT_API_URI,
    System.getenv('JENKINS_GITHUB_CLIENT_ID'),
    System.getenv('JENKINS_GITHUB_CLIENT_SECRET'),
    GithubSecurityRealm.DEFAULT_OAUTH_SCOPES
)

def githubAuthorizationStrategy = new GithubAuthorizationStrategy(
    System.getenv('JENKINS_GITHUB_ADMINS'), /* adminUserNames */
    false,     /*authenticatedUserReadPermission*/
    true,      /*useRepositoryPermissions*/
    false,     /*authenticatedUserCreateJobPermission*/
    System.getenv('JENKINS_GITHUB_ORG_NAMES'), /*organizationNames*/
    true,      /*allowGithubWebHookPermission*/
    false,     /*allowCcTrayPermission*/
    false,     /*allowAnonymousReadPermission*/
    false      /*allowAnonymousJobStatusPermission*/
)

jenkins.setSecurityRealm(githubSecurityRealm)
jenkins.setAuthorizationStrategy(githubAuthorizationStrategy)
jenkins.save()
