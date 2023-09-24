## Project Structure

* `api-dtos`: This module houses all REST API Data Transfer Objects (DTOs), ensuring a clean
  separation of data objects used for API communication.

* `commons`: The "commons" module contains shared functionalities specific to this microservice. It
  provides reusable code snippets, utilities, and shared components that can be leveraged throughout
  the project. Additionally, this module includes common test resources and test cases, ensuring
  consistent and comprehensive testing.

* `configuration`: This module contains configuration classes and resources tailored for the
  service. Configuration is managed using the .properties format, and there should be only one
  configuration file for clarity and simplicity.

* `db-entities`: In this module, you'll find the database entities for the microservice. These
  entities encapsulate the structure and relationships of the data stored in the database.
  Organizing entities in a dedicated module promotes separation of concerns and facilitates easier
  management and evolution of the data model.

* `db-migration`: This module stores Flyway database migration scripts, making it easy to manage and
  version database schema changes.

* `services`: The "services" module encompasses all the business logic and operations orchestration
  within the microservice. Each service encapsulates specific functionality, promoting modularity
  and reusability for easier maintenance and testing.

* `resources`: The "resources" module contains implementations of the REST API endpoints, providing
  the actual HTTP interfaces for interacting with the microservice.

* `application`: This module serves as an aggregation module, bringing together the various
  components of the project.

* `utils`: In the "utils" module, infrastructure scripts and utilities are stored to support the
  development and deployment of the microservice.


## Quick Start

First need to set all the necessary variables. You can use the following script.

```bash
 source ./start-quarkus-env.sh
 ```

Also you need to have **PostgreSQL** DB docker container and start **flyway** migration:

```bash
utils/docker-compose/docker-compose.sh up -d
```

And

```bash
./start-flyway.sh
```

### Building

Execute:

```bash
./mvnw clean install
```

or

```bash
./build-dev.sh
```

To build quarkus native:

```bash
./mvnw clean install -Pnative -DskipTests
```

or

```bash
./build-native.sh
```

### Starting ITs

You can run the ITs

```bash 
./mvnw clean verify
```


### Starting Quarkus

Execute to start PostgreSQL DB docker container:

```bash
utils/docker-compose/docker-compose.sh up -d
```

Execute to start Quarkus in dev mode:

```bash
./start-quarkus-dev.sh
```

Execute to start Quarkus native mode:

```bash
./start-quarkus-native.sh
```

> The native build should be produced before that

The start script has the same settings configured in the
`utils/docker-compose/docker-compose.yaml`

