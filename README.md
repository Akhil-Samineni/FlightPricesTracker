# Springflighttracker
To start the app and deploy to kubernetes 
first login to azure from cli using az login
once logged in
az aks get-credentials --name your-aks-cluster-name --resource-group your-aks-resource-group
And then run startup.sh file with arguments of docker username and password
.\startup.sh dockerUserName dockerPassword

docker build -f Dockerfile -t dockerhubusername/appname:latest .
docker push dockerhubusername/appname:latest
kubectl apply -f deployment.yaml
kubectl get services
kubectl get pods
kubectl logs -f podname
kubectl delete service kubernetes (but his keeps on recreating we need to delete deployment itself)
kubectl get deployments
kubectl delete deployment deploymnetname

Once deployed into cluster:

ERROR: Caused by: com.azure.core.exception.HttpResponseException: Status code 403, "{"error":{"code":"Forbidden","message":"Caller is not authorized to perform actio
n on resource.

For this we need to go to Virtual machine scale sets and then click on the pool where your service is running
and enable managed identity and then click on azure role assignments and give Key Vault Secrets User role
Doing this reflects on key vault side as well
I need to delete deployment and then restart it 

