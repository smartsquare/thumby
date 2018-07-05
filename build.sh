#!/bin/bash

cd upload

docker build -t gcr.io/carbide-parser-205909/image-upload:1 .

cd ../gallery
docker build -t gcr.io/carbide-parser-205909/image-gallery:1 .

cd ../thumb-generator
docker build -t gcr.io/carbide-parser-205909/thumb-generator:1 .

cd ..


