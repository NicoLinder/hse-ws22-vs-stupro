# Service Discovery and Configuration with SmallRye

## Project Description
This project serves as a demonstration how SmallRye Stork can be used for service discovery. The demo application consists of a database, a main service that can access the database and process data and a client that connects to the data service and will serve as our "frontend". 

### SmallRye Stork
The client uses SmallRye Stork to discover available instances of the data service and select one of them to send requests to. We can register a number of instances for discovery using Consul and also configure how Stork should distribute the workload across the available instances.

### SmallRye Config
SmallRye Config is already used in the Quarkus framework to provide a way to configure applications, e.g. through and application properties file or environment variables. If we want to update multiple instances of a running service simultaneously, we must provide a central source for Config to read configuration values from. Consul, which is already used with Stork, can also be used for this.

## Running the project

### Prerequisites

To run the project, you need:
- Docker
- Maven
- JDK 17 or higher

### Running the project locally

1. Clone the repository
2. In the **client** directory, first run `mvnw package`, then `docker build -f src/main/docker/Dockerfile.jvm -t quarkus/client-jvm .` to build the client image
3. In the **service** directory, first run `mvnw package`, then `docker build -f src/main/docker/Dockerfile.jvm -t quarkus/service-jvm .` to build the service image
4. in the project root, run `docker-compose up -d`

TODO: Publish images to Docker Hub

### Check if Stork is working

The Consul instance running in docker provides a web UI that we can use to check whether our services have been registered correctly and can be discovered by Stork. To access it, open `http://localhost:8500` in a web browser. There should be a service named *order-service* with 2 instances running.

To test if service discovery is working, you can send a GET request to *localhost:8080/products* (e.g. with Postman or Insomnia) or run `curl http://localhost:8080/products`. A list of products available in the Stupro Shop should be returned.

The client uses Stork to transmit our request to one of the service instances. This process is transparent to the original caller, but by looking at the log output of the client container, we can see the actual address to which Stork has resolved the request.

In this example, Stork is configured to use a round-robin load balancing scheme, therefore the two instances will be selected alternately.

### Update configurations with Config/Consul

In order to update configuration values on all instances, we must set the desired values in Consul. Open the web UI at `http://localhost:8500` and go to the Key/Value section. Click *Create* and name the key *config/order-service*, then in the Value field below, enter *discount.percent=20* and click *Save*. After restarting the services, they use the updated config value provided by consul. When sending a GET to `curl http://localhost:8080/products`, you should see a discount was applied to the prices.

Note: With this method, it is required to restart the services to pick up configuration changes in consul. A live refresh feature is currently not part of the quarkus-config-consul extension. 




