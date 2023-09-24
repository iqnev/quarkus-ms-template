#!/bin/bash
[ "$1" = -x ] && shift && set -x
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

cd "${DIR}"

set -e

./mvnw --s "${DIR}/.mvn/settings.xml" clean install -Pnative -DskipTests -Dquarkus.profile=dev
