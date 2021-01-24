import jenkins.model.Jenkins
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.plugins.credentials.impl.*
import jenkins.branch.OrganizationFolder
import org.jenkinsci.plugins.github_branch_source.*
import org.jenkinsci.plugins.*

def orgName = System.getenv("GITHUB_ORGANIZATION_NAME")
def orgCred = System.getenv("GITHUB_ORGANIZATION_CREDENTIAL_ID")
if (orgCred == null) {
    orgCred = 'github-organization-access'
}
println "Setting up a GitHub organization named ${orgName} with an auth secret named ${orgCred}"
if (orgName != null) {
    def folder = Jenkins.instance.items.isEmpty() ? Jenkins.instance.createProject(OrganizationFolder, orgName) : Jenkins.instance.items[0]
    def navigator = new GitHubSCMNavigator(orgName)
    navigator.credentialsId = orgCred
    navigator.traits = [
    new jenkins.scm.impl.trait.WildcardSCMSourceFilterTrait('*', ''),
    new jenkins.scm.impl.trait.RegexSCMHeadFilterTrait('.*'),
    new BranchDiscoveryTrait(1), // Exclude branches that are also filed as PRs.
    new OriginPullRequestDiscoveryTrait(1), // Merging the pull request with the current target branch revision.
    new ForkPullRequestDiscoveryTrait(1, new ForkPullRequestDiscoveryTrait.TrustPermission()),  // Allow people in the organisation to update Jenkinsfiles in forks
    ]
    folder.navigators.replace(navigator)
    Jenkins.instance.save()
    navigator.afterSave(folder)
}