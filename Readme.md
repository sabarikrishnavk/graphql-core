
Gradle setup
----
Install gradle 7.0 version]

gradle wrapper --gradle-version 7.0 

Codegen
---
gradle catalog:generateJava

Docker for elastic and postgresql
---- 
    create index based on es-mapping and load  data into index
  
1. cd setup
2. Elastic instance:
   docker-compose -f docker-db-es-redis.yml up

Product/Inventory setup
--
Set up the environment
> npm install

Load data to elastic product index 
> npm run setup:product 

Verify the product index
> http://localhost:9200/product/_search

Load data to elastic inventory index
> npm run setup:inventory
   
Verify the inventory index
> http://localhost:9200/inventory/_search


Redis instance
---
Connect to setup redis instance

> docker exec -it setup_redis_1 sh

Use redis client 
> redis-cli

Sample commands in redis-cli
   > 127.0.0.1:6379> set name test
   
   > OK

   > 127.0.0.1:6379> get name

   > "test"
> 
> 

Docker build
---

Catalog API

> gradle catalog:dockerBuildImage

> docker run -d -p 8081:8081 --name galaxy-catalog-docker galaxy-catalog:latest


Auth API

> gradle auth:dockerBuildImage

> docker run -d -p 8082:8082 --name galaxy-catalog-docker galaxy-catalog:latest



