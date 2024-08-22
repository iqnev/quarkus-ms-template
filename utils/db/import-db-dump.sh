#!/bin/bash
[ "$1" = -x ] && shift && set -x
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

source "${DIR}/.env"

DB_CONTAINER=local-test-db

DATABASE_TYPE=postgresql "${DEV_HUB_PATH}/db/db-docker-execute-sql.sh" \
  "${DB_CONTAINER}" boilerplate local-test "$1"
