import jenkins.model.Jenkins

Jenkins.instance.agentProtocols = ["JNLP4-connect", "Ping"] as Set
Jenkins.instance.crumbIssuer = new hudson.security.csrf.DefaultCrumbIssuer(true)
Jenkins.instance.injector.getInstance(jenkins.security.s2m.AdminWhitelistRule.class).setMasterKillSwitch(false)
Jenkins.instance.setNumExecutors(0)
Jenkins.instance.setInstallState(jenkins.install.InstallState.INITIAL_SETUP_COMPLETED)
Jenkins.instance.save()