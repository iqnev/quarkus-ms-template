#!/bin/bash
[ "$1" = -x ] && shift && set -x
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

cd "${DIR}"

set -e

# The db/user password are needed when running the integration tests
export BOILERPLATE_DB_USER=localdev
export BOILERPLATE_DB_PASSWORD=$(cat ${DIR}/utils/docker-compose/docker-secrets/db-password)
./mvnw --s "${DIR}/.mvn/settings.xml" clean install "$@"
