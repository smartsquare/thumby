# Deployment

The `/kubernetes` folder contains a pure k8s deployment while the `/istio` folder make use of some additional istio features.

## ISTIO

### Tracing

Enable port-forwarding for Jaeger
```bash
kubectl port-forward -n istio-system \
    $(kubectl get pod -n istio-system -l app=jaeger -o jsonpath='{.items[0].metadata.name}') \
    16686:16686
```
