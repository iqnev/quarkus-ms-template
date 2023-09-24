# Generate new FlyWay Migration Scripts

### FlyWay Migration Scripts

Flyway migration scripts must be located in `db-migration/resources/db.migration`, they are loaded 
when starting up the project via `start-quarkus-dev.sh` or `start-quarkus-native.sh`

The FlyWay migration script requires the .env file located in `./utils/db/` to be setup.
Use the `./utils/db/.env-template` and replace the needed variables.

### Dump the DB schema

Execute this:

```bash
./utils/db/dump-schema.sh
```

