**Database Connection done easily**
open gitbash -> you should be located in the folder of the project
* docker-compose up -d    
now the container is running to check:
+ docker-compose ps
+ or: docker ps  
okay now to see the tables use a gui tool like myaql desk 
or with these commands:
+ docker exec -it boxing-fever-db bash   
then to enter sql:
+ docker exec -it boxing-fever-db mysql -u user -p
+ after p enter the password  




*To shutdown:*  
docker-compose down
