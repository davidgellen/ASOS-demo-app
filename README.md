# ASOS-demo-app

How to setup project locally (in Intellij IDEA):
1. clone the project
2. download jdk 17+ (Intellij will suggest if no appropriate SDK present)
3. download postgres driver (https://www.postgresql.org/download/), created user postgres with password postgres
4. create database 'reserv' 
5. add postgres as datasource via Database in right sidebar, user: postgres, password postgres, database reserv (test connection to see whether you can connect)
6. if asked enable annotation processing (due to lombok dependency)

## SQL vs. KEY-VALUE NoSQL

To correctly setup the project on this branch, beside the instructions above, you need to setup local redis server. Since Redis is open-source, the installer is free to download on [Redis](https://redis.io/download/). Installation steps differ by operating system.
