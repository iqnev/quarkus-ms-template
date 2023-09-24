DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

export JVM_DEBUG_PORT=7000
export DB_USER=localdev
export DB_PASSWORD=$(cat ${DIR}/utils/docker-compose/docker-secrets/db-password)
