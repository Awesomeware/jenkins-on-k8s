import jenkins.model.Jenkins
import org.csanchez.jenkins.plugins.kubernetes.KubernetesCloud

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
