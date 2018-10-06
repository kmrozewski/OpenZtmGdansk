
### What the `start-local-environment.sh` does?
* builds optimized version of angularjs application and moves it to lightweight nginx-alpine container,
* builds dependencies and then jar of `wfeservices` and moves it to lightweight java8-alpine docker image,
* builds and runs docker-compose containers described in `docker-compose.yml`.