import jenkins.model.Jenkins

// The false here prevents sign-ups https://javadoc.jenkins.io/hudson/security/HudsonPrivateSecurityRealm.html
def hudsonRealm = new hudson.security.HudsonPrivateSecurityRealm(false)
def adminUsername = System.getenv('JENKINS_ADMIN_USERNAME') ?: 'admin'
def adminPassword = System.getenv('JENKINS_ADMIN_PASSWORD')
if (adminPassword != null) {
    hudsonRealm.createAccount(adminUsername, adminPassword)
    Jenkins.instance.setSecurityRealm(hudsonRealm)
    Jenkins.instance.save()
}