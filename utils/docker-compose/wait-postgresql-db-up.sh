#!/bin/bash
[ "$1" = -x ] && shift && set -x
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

command -v pg_isready > /dev/null 2>&1 || {
  echo >&2 -e "This script needs pg_isready as a dependency package, please install it\n"
  exit 2
}

export PGPORT=3333
export PGHOST=localhost
export POSTGRESQL_WAIT_CONNECT_SECONDS=120

COUNTER=1
while ! pg_isready -h "${PGHOST}" -p "${PGPORT}";
do
  sleep 1
  COUNTER=$(expr ${COUNTER} + 1)

  if [ ${COUNTER} -gt ${POSTGRESQL_WAIT_CONNECT_SECONDS} ]; then
    echo >&2 "Connection wait failed"
    exit 1
  fi
done
