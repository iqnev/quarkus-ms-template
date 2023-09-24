#!/bin/bash
[ "$1" = -x ] && shift && set -x
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

cd "${DIR}"

./init-docker-secrets.sh

./init-data-volumes.sh

DOCKER_COMPOSE_FILE="${DIR}/docker-compose.yaml"

echo -e "\nUsing ${DOCKER_COMPOSE_FILE}"
docker-compose -p boilerplate -f "${DOCKER_COMPOSE_FILE}" $*
