## Summary

Project: Ecom-Platform 

Team: Order&Promotion 

Target: Migration project from Monolithic to Microservice. 

Attention:

for new schema & tables, Please use Flyway to finish sync and migration.

1. create migration script in resources/db/migration 
2. run project to sync script into your local databse 
3. check database modification to make sure all changes sync
4. run JOOQ-CodeGen to create DAO into project 5. Leverage created DAO finishing business reqirements
