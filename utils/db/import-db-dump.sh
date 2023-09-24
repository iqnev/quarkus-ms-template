#!/bin/bash
[ "$1" = -x ] && shift && set -x
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

source "${DIR}/.env"

DB_CONTAINER=boilerplate-db

DATABASE_TYPE=postgresql "${DEV_HUB_PATH}/db/db-docker-execute-sql.sh" \
  "${DB_CONTAINER}" boilerplate localdev "$1"
