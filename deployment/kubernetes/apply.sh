#!/usr/bin/env bash
kubectl apply -f upload-service.yaml
kubectl apply -f generator-service.yaml
kubectl apply -f gallery-service.yaml

kubectl apply -f ingress.yaml

echo "kubectl get pods"
kubectl get pods

echo "kubectl get ingress"
kubectl get ingress
