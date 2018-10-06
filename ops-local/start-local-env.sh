#!/usr/bin/env bash
set -e

# Build wfeservices
pushd ../ztm-services
mvn clean package -DskipTests

popd
cp ../ztm-services/target/ztmtimetable*.jar ./wfeservices/wfeservices.jar

# Build wfespa
rm -r -f ./ztm-spa/dist

pushd ../ztm-spa && npm install && bower install && gulp
popd
cp -r ../ztm-spa/dist ./wfespa

# Start docker-compose
docker-compose up --build
