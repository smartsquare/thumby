# Deployment

The `/kubernetes` folder contains a pure k8s deployment while the `/istio` folder make use of some additional istio features.

## Kubernetes
on gcloud cluster

Simple service, deployment and ingress definition to deploy on a GKE cluster

## ISTIO

### Install
on gcloud cluster

```bash
curl -L https://git.io/getLatestIstio | sh -
cd istio-1.0.1
```

Grant the cluster admin permissions to the current user. 
You need these permissions to create the necessary role based access control (RBAC) rules for Istio:
```bash
kubectl create clusterrolebinding cluster-admin-binding \
  --clusterrole=cluster-admin \
  --user="$(gcloud config get-value core/account)"
```

```bash
kubectl apply -f install/kubernetes/helm/istio/templates/crds.yaml
kubectl apply -f install/kubernetes/istio-demo-auth.yaml

kubectl label namespace default istio-injection=enabled

```

### Tracing

Enable port-forwarding for Jaeger
```bash
kubectl port-forward -n istio-system \
    $(kubectl get pod -n istio-system -l app=jaeger -o jsonpath='{.items[0].metadata.name}') \
    16686:16686
```
