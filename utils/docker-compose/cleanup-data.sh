#!/bin/bash
set -euo pipefail

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "${DIR}"

DB_VOLUME_NAME=local-test-db-data

if docker volume inspect "${DB_VOLUME_NAME}" > /dev/null 2>&1; then
  printf "\nDelete DB volume:\n"
  docker volume rm "${DB_VOLUME_NAME}"
else
  printf "\nNot exists DB volume: %s\n" "${DB_VOLUME_NAME}"
fi