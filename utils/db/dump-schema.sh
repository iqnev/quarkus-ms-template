#!/bin/bash
[ "$1" = -x ] && shift && set -x
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

source "${DIR}/.env"

DB_CONTEXT=$1
if [[ "${DB_CONTEXT}" == "" ]]; then
  DB_CONTEXT=boilerplate
fi

"${DEV_HUB_PATH}/db/db-docker-dump-schema.sh" \
  "${DB_CONTAINER}" boilerplate localdev "${DB_CONTEXT}"
