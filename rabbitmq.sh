docker run -p 5672:5672 -p 15672:15672 -p 15692:15692 --hostname localhost -d --name rabbit rabbitmq:3-management
docker exec -it rabbit rabbitmq-plugins enable rabbitmq_consistent_hash_exchange
docker exec -it rabbit rabbitmq-plugins enable rabbitmq_prometheus

