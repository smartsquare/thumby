# smart image thumbnail services

[![Build Status](https://travis-ci.org/smartsquare/thumby.svg?branch=master)](https://travis-ci.org/smartsquare/thumby)

## Build

### build local

build and push docker images to local repository

`$ ./gradlew clean build jibDockerBuild`

### build remote 

build and push docker images to remote repository

`$ ./gradlew clean build jibDockerBuild`


### run local

`$ docker-compose up`

## Configuration

The `.env` file contains the gcloud repository and bucket name and is used by the gradle build and docker.
