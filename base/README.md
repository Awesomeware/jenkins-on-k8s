# Base configuration
This folder provides the bare minimum configuration required to set up Jenkins within a Kubernetes cluster, and use that cluster as a cloud provider to run builds with.

## Usage
This configuration can be used to deploy Jenkins directly if you want, although the configured Jenkins instance is not especially useful without further plugins. By default this Jenkins instance is open, and requires no login credentials to access.

### Examples of usage
An example of setting up basic authorization and an admin account can be found [here](../examples/admin-password/README.md).
