#!/usr/bin/env bash
kubectl apply -f gcloud-storage-egress.yaml
kubectl apply -f upload-service.yaml
kubectl apply -f generator-service.yaml
kubectl apply -f gallery-service.yaml

kubectl apply -f gateway.yaml

kubectl get pods


