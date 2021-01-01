import jenkins.model.Jenkins
import org.csanchez.jenkins.plugins.kubernetes.KubernetesCloud

// To run Jenkins builds within our kubernetes cluster, we need an appropriately configured
// Cloud in Jenkins. This file sets up that local cloud, naming it "kubernetes" by default.
// Downstream configurations (such as GitHub Organizations) can rely on this Cloud being
// configured when deciding where to run builds by default.

if (System.getenv("DONT_INSTALL_LOCAL_CLOUD") == null) {
    def localName = System.getenv("LOCAL_CLOUD_NAME")
    if (localName == null) {
        localName = "kubernetes"
    }

    def existingCloud = Jenkins.instance.clouds.getByName(localName)
    def localCloud = new KubernetesCloud(localName)
    localCloud.setMaxRequestsPerHost(KubernetesCloud.DEFAULT_MAX_REQUESTS_PER_HOST)
    localCloud.setDirectConnection(true)

    if (existingCloud == null) {
        Jenkins.instance.clouds.add(localCloud)
    } else {
        Jenkins.instance.clouds.replace(existingCloud, localCloud)
    }

    Jenkins.instance.save()
}
