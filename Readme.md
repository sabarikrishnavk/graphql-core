
Gradle setup
----
Install gradle 7.0 version]

gradle wrapper --gradle-version 7.0 

Codegen
---
gradle catalog:generateJava

Docker for elastic and mysql
---- 
    create index based on es-mapping and load  data into index
  
1. cd setup
2. Elastic instance:
   docker-compose -f docker-product-es.yml up
3. npm install 
4. npm run setup:product
5. npm run setup:inventory
6. http://localhost:9200/product/_search
7. http://localhost:9200/inventory/_search