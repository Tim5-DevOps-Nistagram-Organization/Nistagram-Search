#!/bin/bash

token=${1}
branch=${2}
pullKey=${3:-0}
base=${4:-main}

source ./docker-config/.env.test

if [ "$pullKey" = "0" ]; then
  SONAR_TOKEN=${token} BRANCH_NAME=${branch} COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose -f ./docker-compose.test.yml  --env-file ./docker-config/.env.test up --build -d
else
  SONAR_TOKEN=${token} PULL_KEY=${pullKey} PULL_REQUEST_BRANCH=${branch} BASE=${base} COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose -f ./docker-compose.test.yml  --env-file ./docker-config/.env.test up --build -d
fi

is_finished() {
    service="$1"
    container_id="$(docker-compose -f ./docker-compose.test.yml ps -q "$service")"
    health_status="$(docker inspect -f "{{.State.Status}}" "$container_id")"

    echo "STATUS: $health_status, CONTAINER: $service"

    if [ "$health_status" = "exited" ]; then
        return 0
    else
        return 1
    fi
}

while ! is_finished nistagram-search-microservice; do sleep 20; done
# provera Quality Gate-a i da li je neki od testova pao
servers_logs=$(docker logs nistagram-search --tail 20)
python ./sonar-maven-breaker.py --testLogs "${servers_logs}" --projectKey ${SONAR_PROJ_KEY_SVC}  --pullRequestNumber ${pullKey} --branch ${branch}




