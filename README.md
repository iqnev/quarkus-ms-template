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

* `services`: The "services" module encompasses all the business logic and operations orchestration
  within the microservice. Each service encapsulates specific functionality, promoting modularity
  and reusability for easier maintenance and testing.

* `resources`: The "resources" module contains implementations of the REST API endpoints, providing
  the actual HTTP interfaces for interacting with the microservice.

* `application`: This module serves as an aggregation module, bringing together the various
  components of the project.


## Quick Start

Here is the database schema:

```sql
CREATE TABLE states
(
  id            BIGSERIAL PRIMARY KEY       NOT NULL,
  name          VARCHAR(255)                NOT NULL,
  region        VARCHAR(255),
  public_id     VARCHAR(128)                NOT NULL,
  created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE cities
(
  id            BIGSERIAL PRIMARY KEY       NOT NULL,
  name          VARCHAR(255)                NOT NULL,
  public_id     VARCHAR(128)                NOT NULL,
  state_id      BIGINT REFERENCES states (id),
  created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified TIMESTAMP WITHOUT TIME ZONE NOT NULL
);
```

and I will give you the simple data to insert:

```sql

-- Insert data into the 'states' table
INSERT INTO states (name, region, public_id, created_at, last_modified)
VALUES ('California', 'West', '4C1BQ4ESCRVBVZQYVVADQR8L', NOW(), NOW()),
       ('New York', 'Northeast', '5QQDQKV64QXF8P9R2ECVPYQJ', NOW(), NOW()),
       ('Texas', 'South', '5B4KYWFP28S9H8S7SWS2TJ33', NOW(), NOW()),
       ('Florida', 'South', '5VVPE42YRBKAZZ2EF4DSXT6F', NOW(), NOW());

-- Insert data into the 'cities' table
INSERT INTO cities (name, state_id, public_id, created_at, last_modified)
VALUES ('Los Angeles', 1, '57V6VA7KT7RWZB1PSBCRVPZR', NOW(), NOW()),
       ('San Francisco', 1, '4ED12E9P9EV4HR4ZS2XSVJY1', NOW(), NOW()),
       ('New York City', 2, '4K42AP78K7BPB2H8RBWKEZ55', NOW(), NOW()),
       ('Buffalo', 2, '5XC2HZZCJ34W3MQAABZT5W17', NOW(), NOW()),
       ('Austin', 3, '5S5Q7XTW3WSS9S5RAQXZT42R', NOW(), NOW()),
       ('Houston', 3, '47EJS8C25HZAQZ1K28VCSRVW', NOW(), NOW()),
       ('Miami', 4, '4XH7HWW2WKZ84JRA4HJP9J2K', NOW(), NOW());
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


