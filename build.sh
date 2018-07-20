#!/bin/bash

./gradlew clean build

gcpRegistryHost=gcr.io
gcpProject=$(gcloud config get-value project)

echo "Pushing to repository project: "$gcpProject

cd upload
docker build -t $gcpRegistryHost/$gcpProject/upload-service:1 .

cd ../gallery
docker build -t $gcpRegistryHost/$gcpProject/gallery-service:1 .

cd ../thumb-generator
docker build -t $gcpRegistryHost/$gcpProject/thumb-generator-service:1 .

cd ..


