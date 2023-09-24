#!/bin/bash

if [ "$1" = "-x" ]; then
  shift
  set -x
fi

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "${DIR}/start-quarkus-env.sh"

cd "${DIR}"
set -e

DOCKER_CONTAINER_NAME="boilerplate-db"

if [[ "$(docker inspect -f '{{.State.Running}}' $DOCKER_CONTAINER_NAME 2>/dev/null)" != "true" ]]; then
  echo "Docker container $DOCKER_CONTAINER_NAME is not running. Starting it now..."
  "${DIR}/utils/docker-compose/docker-compose.sh" up -d
fi

./start-flyway.sh

# Check if JVM_DEBUG_PORT parameter is provided, if not use the default value from start-quarkus-env.sh
if [ -z "$2" ]; then
  JVM_DEBUG_PORT=${JVM_DEBUG_PORT}
else
  JVM_DEBUG_PORT="$2"
fi

./mvnw --s "${DIR}/.mvn/settings.xml" clean compile quarkus:dev -Ddebug=${JVM_DEBUG_PORT} -Dmaven.plugin.validation=VERBOSE
