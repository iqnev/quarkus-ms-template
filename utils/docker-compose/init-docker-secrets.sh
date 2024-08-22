#!/bin/bash
[ "$1" = -x ] && shift && set -x
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

cd "${DIR}"

if [[ -f "./docker-secrets/db-password" ]]; then
 exit
fi

echo "Init docker-secrets"

mkdir docker-secrets
echo "db-test-pass" > ./docker-secrets/db-password
