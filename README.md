# Boilerplate


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

