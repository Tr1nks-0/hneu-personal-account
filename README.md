# Students personal account
The web application is developed to make it possible to store all students profiles in one place and move paper accounting of students marks to electronic.

## Setup development environemnt 
Install the dependencies: 
* Java 8;
* IDEA (your choice)
* Gradle (optional);
* Node & npm;
* Mysql Server (as an alternative you can use docker to quickly manage your database instances - use instruction below).

### Quickly create database locally using Docker
```
docker run --name stud_portal_db -p 3306:3306 \
 -e MYSQL_ROOT_PASSWORD=root \
 -e MYSQL_DATABASE=stud_portal \
 -e MYSQL_USER=stud_portal_admin \
 -e MYSQL_PASSWORD=qwerty \
 -v /tmp/mysql/data:/var/lib/mysql \
 -d mysql --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
 ```
 
 ## Run the project 
 First, build the project: 
 ```
 gradle clean build
 ```
 Then, run spring boot aplication:
 ```
 gradle bootRun
 ```
 or it is also possible via running `Application.groovy` in your IDEA.
 
 The site will be available via url: <http://localhost:8080>
