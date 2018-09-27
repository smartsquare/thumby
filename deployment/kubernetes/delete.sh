#!/usr/bin/env bash
kubectl delete -f upload-service.yaml
kubectl delete -f generator-service.yaml
kubectl delete -f gallery-service.yaml

kubectl delete -f ingress.yaml

kubectl get pods