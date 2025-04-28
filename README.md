# Overview

This is the **REST API** backend for the Expenses tracker app.
Built with **Spring Boot**, it handles:
* User Authentication (JWT-based)
* Expense CRUD operations
* User-specific expense separation

This project was crated using Spring Boot 3.4.4 (Java 17)

## Setup Instructions

1. Clone the repository: **git clone 'backend-repo-url'**,
**cd 'backend-folder'**

3. Import the project from your IDE (e.g Intellij, Eclipse)
4. Configure Database (example: H2, MySQL, PostgreSQL). Update your application.properties

   (# Example)
   
     spring.datasource.url=jdbc:postgresql://localhost:5432/payments_management
     
     spring.datasource.username=postgres
     
     spring.datasource.password=your_password
     
     spring.datasource.driver-class-name=org.postgresql.Driver

5. Run the application.
   The server will start on _http://localhost:8080_
  
