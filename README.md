# ASOS-demo-app

## About
This project was developed as assignment for Software architecture course on FEI STU, 2022/2023. The project demonstrates the differences between SQL databases and NoSQL databases. Comparison and measurement of each type of NoSQL DB with SQL DB is implemented in its own branch.

How to setup project locally (in Intellij IDEA):
1. clone the project
2. download jdk 17+ (Intellij will suggest if no appropriate SDK present)
3. download postgres driver (https://www.postgresql.org/download/), created user postgres with password postgres
4. create database 'reserv' 
5. add postgres as datasource via Database in right sidebar, user: postgres, password postgres, database reserv (test connection to see whether you can connect)
6. if asked enable annotation processing (due to lombok dependency)
