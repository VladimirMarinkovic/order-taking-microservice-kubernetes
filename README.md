# order-taking-microservice-kubernetes


This project is based on project https://github.com/VladimirMarinkovic/order-taking-microservice and configured for kubernetes deployment with Istio service mesh integration.


 **Install Istio on kubernetes cluster** - https://istio.io/docs/setup/install/
 
 
* **MySql deployment** - kubectl apply -f mysql-deployment.yaml,mysql-policy.yaml

* **Create Configmap** - kubectl create configmap order-taking-service --from-literal=RDS_DB_NAME=ordertaking --from- literal=RDS_HOSTNAME=mysql --from-literal=RDS_PORT="3306" --from-literal=RDS_USERNAME=ordertaking_user

* **Create Secret** - kubectl create secret generic order-taking-service-secrets --from-literal=RDS_PASSWORD=ordertaking_password

* **Customer Details Deployment** - kubectl apply -f customer-details-deployment.yaml

* **Product Catalogue Deployment** - kubectl apply -f product-catalogue-deployment.yaml

* **Order Taking Deployment** - kubectl apply -f order-taking-deployment.yaml

* **Getway deployment** - kubectl apply -f http-gateway.yaml

* **Virtualservices deployment** - kubectl apply -f virtualservices.yaml


* **RabbitMq Deployment** - TODO ...
