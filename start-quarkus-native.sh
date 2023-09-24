#!/bin/bash
[ "$1" = -x ] && shift && set -x
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

source ${DIR}/start-quarkus-env.sh

set -e

cd ${DIR}

./start-flyway.sh

./application/target/application-*-runner
