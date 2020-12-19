import jenkins.model.Jenkins

Jenkins.instance.agentProtocols = ["JNLP4-connect", "Ping"] as Set
Jenkins.instance.crumbIssuer = new hudson.security.csrf.DefaultCrumbIssuer(true)
Jenkins.instance.injector.getInstance(jenkins.security.s2m.AdminWhitelistRule.class).setMasterKillSwitch(false)
Jenkins.instance.setNumExecutors(0)
