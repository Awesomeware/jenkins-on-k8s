# Jenkins on Kubernetes
More power for your CI on Kubernetes!

## What is it?
This repository provides a set of convenient definitions and scripts to help you easily set up, maintain and extend Jenkins as a service within a Kubernetes cluster. The aim is to make use of Jenkins power and flexibility to provide you with a feature rich CI / CD solution that is able to deploy multiple software components within your Kubernetes cluster, and depending upon your configuration, also promote those same software components into other environments. The solution offered here is opinioniated, in the sense that it aims to provide a good solution to companies that deploy software-as-a-service and consist of one or more development teams and one or more DevOps engineers that have responsibility across all of those teams.

## How do I use it?
Fork this repository, and then add your own folder next to the folders within this repository. We use [kustomize](https://github.com/kubernetes-sigs/kustomize) to deploy Jenkins, and so you can place your own configuration into a folder as an [overlay](https://kubectl.docs.kubernetes.io/references/kustomize/glossary/#overlay). You can either extend from the [base configuration](/base), or one of the [overlays](/overlays) in this repository. We also provide a number of [components](/components) that you can mixin to your Jenkins instance, allowing you to mix and match functionality your organization needs. For more descriptive examples, check out the [examples](/examples) in this repository.

### Adding plugins
To add a plugin to the Jenkins instance, you can define the plugin in a `plugins.txt` file within a ConfigMap, and mount it under the `/jenkins-plugins` path. For example:

```yaml
volumeMounts:
  - name: jenkins-base-plugins
    mountPath: /jenkins-plugins/base
  - name: jenkins-my-plugins
    mountPath: /jenkins-plugins/mine
```

These plugin lists will get concatenated, and installed on startup. You are not currently able to override plugin versions from the upstream kustomization you are extending, so we try to keep plugin lists for upstream kustomizations to the minimum required for some given functionality or example. In general though, the `base` configuration has the bare minimum for any Jenkins instance, and `overlays` in this repository get more and more opinionated about plugins and configuration.

### Adding configuration
To add your own configuration to execute on startup, you can define a number of groovy files within a ConfigMap, and mount them under the `` path. For example:

```yaml
volumeMounts:
  - name: jenkins-base-customization
    mountPath: /jenkins-customization/base
  - name: jenkins-my-customization
    mountPath: /jenkins-customization/mine
```

The groovy files within these ConfigMaps are executed in alphabetical order by Jenkins. To ensure better control by your own configuration, the groovy file name are prefixed with the folder they are mounted under. For example `set-best-practice-settings.groovy` in `jenkins-base-customization` would be named `base-set-best-practice-settings.groovy` in the example above. This allows you to execute both before and after any upstream configuration as you require, by mounting your own ConfigMaps with particular names. In the example above, all configuration groovy in `jenkins-my-customization` would execute after configuration groovy in `jenkins-base-customization` and so could depend upon that prior configuration having already happened.
