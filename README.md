# Online Tech Store

Online store with implemented basic functionality
Two user roles with the corresponding functions are implemented.
User capabilities:
- register;
- log in/out;
- choosing the necessary product;
- shopping cart managing;
- placing orders;
- view orders history;

Admin capabilities:
- managing users;
- managing products;
- view orders and manage it;

## Technologies Used

 - Java 11
 - Maven 4.0.0
 - Maven Checkstyle Plugin
 - Apache Tomcat
 - Servlet API
 - Mysql Connector Java 8.0.21
 - JDBC
 - JSTL 1.2
 - JSP

## Project structure

### Web part
 A set of pages that display the result of the application to the user

##### The following web filters have been implemented:

- Authentication filter - is responsible for admitting only 
  registered users to the system.

- Authorization filter - ensures that the user cannot access 
  information that does not correspond to his role.

### Business logic part 
##### DAO and Service layers have been implemented here:

- The DAO layer is responsible for retrieving information 
  from the database and updating it.

- The Service layer is responsible for processing information and modifying it 
  in the process of user interaction with the application.

## Project launching

1. To run the project on your local machine, clone this repository. 

2. Make sure you have configured Tomcat Server and set up the MySQL and some 
RDBMS on your machine. 

3. Perform the initialization of the data scheme (sample here -> init_dbscheme.sql). 

4. Set up your schema's password and user's login in the ConnectionUtil class. 

5. If you have performed all the steps correctly, and your server is active, 
the application is working.

6. To login as admin use login: admin and password: admin. 

## Author

[Artem Vorobiov](https://github.com/SetMyDream)