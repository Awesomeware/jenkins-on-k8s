import jenkins.model.Jenkins
import hudson.security.FullControlOnceLoggedInAuthorizationStrategy

// Lets make it so if you aren't logged in, you can see jobs, but cannot go to 'Manage Jenkins'
def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
strategy.setAllowAnonymousRead(true)
Jenkins.instance.setAuthorizationStrategy(strategy)
Jenkins.instance.save()
