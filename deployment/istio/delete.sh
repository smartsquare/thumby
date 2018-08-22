#!/usr/bin/env bash
kubectl delete -f gcloud-storage-egress.yaml
kubectl delete -f upload-service.yaml
kubectl delete -f generator-service.yaml
kubectl delete -f gallery-service.yaml

kubectl delete -f gateway.yaml

kubectl delete virtualservice --all

