#!/bin/bash
[ "$1" = -x ] && shift && set -x
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

source ${DIR}/utils/db/.env

DB_PASSWORD=$(cat ${DIR}/utils/docker-compose/docker-secrets/db-password)

cd ${DIR}

docker build -t boilerplate-flyway:latest -f ./utils/docker/Docker-flyway .

docker run --rm --network="host" \
  -e FLYWAY_USER="${DB_USER}" \
  -e FLYWAY_URL=jdbc:postgresql://localhost:3333/local-test \
  -e FLYWAY_PASSWORD="${DB_PASSWORD}" \
  -e FLYWAY_SCHEMAS=public \
  -e FLYWAY_LOCATIONS=filesystem:/db/ \
  boilerplate-flyway:latest
