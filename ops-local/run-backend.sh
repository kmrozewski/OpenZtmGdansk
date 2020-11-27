#!/usr/bin/env bash
set -e

# Build wfeservices
pushd ../ztm-services
mvn clean package -DskipTests

popd
cp ../ztm-services/target/ztm-services*.jar ./wfeservices/wfeservices.jar

docker-compose up --build -d wfeservices