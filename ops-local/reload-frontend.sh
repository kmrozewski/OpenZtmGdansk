# Build wfespa
rm -r -f ./wfespa/dist

pushd ../ztm-spa && npm install && bower install --allow-root && gulp
popd
cp -r ../ztm-spa/dist ./wfespa

docker-compose up --build -d --no-deps wfespa