#!/bin/bash
[ "$1" = -x ] && shift && set -x
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

cd "${DIR}"

DB_VOLUME_NAME=local-test-db-data

for volume_name in $DB_VOLUME_NAME
do
  docker volume inspect "${volume_name}" > /dev/null 2>&1

  if [ $? -eq 0 ]; then
    echo -e "\nAlready exists volume: ${volume_name}"
  else
    echo -e "\nCreating volume:" &&
    docker volume create "${volume_name}"
  fi
done