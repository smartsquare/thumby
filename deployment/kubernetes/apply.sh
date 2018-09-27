#!/usr/bin/env bash
kubectl apply -f upload-service.yaml
kubectl apply -f generator-service.yaml
kubectl apply -f gallery-service.yaml

kubectl apply -f ingress.yaml

kubectl get pods
kubectl get ingress