# Kafka Setup in AKS

This repository is for building customer environments for testing using kafka integration. We assume you are already connected a AKS cluster.

## Setup Kafka in AKS

1. Creating namespace

`kubectl apply -f kafka-namespace.yaml`

2. Installing kafka Heml Chart 

`helm install --namespace kafka-ns kafka-testlab -f kafka-helm-values.yaml azure-marketplace/kafka --set rbac.create=true `

Response:

```
NAME: kafka-testlab
LAST DEPLOYED: Mon Nov 29 16:05:40 2021
NAMESPACE: kafka-ns
STATUS: deployed
REVISION: 1
TEST SUITE: None
NOTES:
CHART NAME: kafka
CHART VERSION: 14.4.1
APP VERSION: 2.8.1
---------------------------------------------------------------------------------------------
 WARNING

    By specifying "serviceType=LoadBalancer" and not configuring the authentication
    you have most likely exposed the Kafka service externally without any
    authentication mechanism.

    For security reasons, we strongly suggest that you switch to "ClusterIP" or
    "NodePort". As alternative, you can also configure the Kafka authentication.

---------------------------------------------------------------------------------------------

** Please be patient while the chart is being deployed **

Kafka can be accessed by consumers via port 9092 on the following DNS name from within your cluster:

    kafka-testlab.kafka-ns.svc.cluster.local

Each Kafka broker can be accessed by producers via port 9092 on the following DNS name(s) from within your cluster:

    kafka-testlab-0.kafka-testlab-headless.kafka-ns.svc.cluster.local:9092
    kafka-testlab-1.kafka-testlab-headless.kafka-ns.svc.cluster.local:9092

To create a pod that you can use as a Kafka client run the following commands:

    kubectl run kafka-testlab-client --restart='Never' --image marketplace.azurecr.io/bitnami/kafka:2.8.1-debian-10-r31 --namespace kafka-ns --command -- sleep infinity
    kubectl exec --tty -i kafka-testlab-client --namespace kafka-ns -- bash

    PRODUCER:
        kafka-console-producer.sh \

            --broker-list kafka-testlab-0.kafka-testlab-headless.kafka-ns.svc.cluster.local:9092,kafka-testlab-1.kafka-testlab-headless.kafka-ns.svc.cluster.local:9092 \
            --topic test

    CONSUMER:
        kafka-console-consumer.sh \

            --bootstrap-server kafka-testlab.kafka-ns.svc.cluster.local:9092 \
            --topic test \
            --from-beginning

To connect to your Kafka server from outside the cluster, follow the instructions below:

  NOTE: It may take a few minutes for the LoadBalancer IPs to be available.
        Watch the status with: 'kubectl get svc --namespace kafka-ns -l "app.kubernetes.io/name=kafka,app.kubernetes.io/instance=kafka-testlab,app.kubernetes.io/component=kafka,pod" -w'

    Kafka Brokers domain: You will have a different external IP for each Kafka broker. You can get the list of external IPs using the command below:

        echo "$(kubectl get svc --namespace kafka-ns -l "app.kubernetes.io/name=kafka,app.kubernetes.io/instance=kafka-testlab,app.kubernetes.io/component=kafka,pod" -o jsonpath='{.items[*].status.loadBalancer.ingress[0].ip}' | tr ' ' '\n')"

    Kafka Brokers port: 9094

```

3. Validating Kafka is up & running

`kubectl get all -n kafka-ns`

Response:

```
NAME                            READY   STATUS                  RESTARTS   AGE
pod/kafka-testlab-0             1/1     Running                 0          15h
pod/kafka-testlab-zookeeper-0   1/1     Running                 0          15h

NAME                                       TYPE           CLUSTER-IP    EXTERNAL-IP    PORT(S)                      AGE
service/kafka-testlab                      ClusterIP      10.0.178.68   <none>         9092/TCP                     17h
service/kafka-testlab-external             LoadBalancer   10.0.184.30   20.120.43.32   9094:32291/TCP               17h
service/kafka-testlab-headless             ClusterIP      None          <none>         9092/TCP,9093/TCP            17h
service/kafka-testlab-zookeeper            ClusterIP      10.0.4.2      <none>         2181/TCP,2888/TCP,3888/TCP   17h
service/kafka-testlab-zookeeper-headless   ClusterIP      None          <none>         2181/TCP,2888/TCP,3888/TCP   17h

NAME                                       READY   AGE
statefulset.apps/kafka-testlab             1/1     17h
statefulset.apps/kafka-testlab-zookeeper   1/1     17h
```

## Test Kafka using client producer/consumer

### Kafka Bootstrap Server:
    
   > testlabkafka.eastus.cloudapp.azure.com:9094 
   

1.  Create a Pod for running

`kubectl run kafka-testlab-client --restart='Never' --image marketplace.azurecr.io/bitnami/kafka:2.8.1-debian-10-r31 --namespace kafka-ns --command -- sleep infinity`

2. Run a producer.

`kubectl exec --tty -i kafka-testlab-client --namespace kafka-ns -- kafka-console-producer.sh --broker-list testlabkafka.eastus.cloudapp.azure.com:9094 --topic test`

3. Run a consumer in other shell windows.

`kubectl exec --tty -i kafka-testlab-client --namespace kafka-ns -- kafka-console-consumer.sh --bootstrap-server testlabkafka.eastus.cloudapp.azure.com:9094  --topic test  --from-beginning`


### Remove installed objects.

1. Remove Kafka Helm Chart.

`helm uninstall kafka-testlab --namespace kafka-ns`

2. Remove Namespace.

`kubectl delete namespace  kafka-ns`

### More about set up kafka Documentation

* [Apache Kafka packaged by Bitnami](https://bitnami.com/stack/kafka/helm)


## Setup the configuration.

1. Update the application.properties file with your kafka properties located at:
`./kafka-streams-demo/src/main/application.properties`

2. Update the ApplicationContext.xml file with your Stardog properties located at:
 `./kafka-streams-demo/src/main/ApplicationContext.xml`

3. Run it as Spring Boot Application.


