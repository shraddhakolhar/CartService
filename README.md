Cart Service

The Cart Service is responsible for managing the shopping cart functionality in the e-commerce platform. It allows authenticated users to add products to their cart, update the quantity of items, view the contents of their cart, and clear the cart when an order is placed.

The service uses Spring Security with JWT authentication to ensure that only authenticated users can access cart operations. It also uses Spring Data JPA with MySQL for persistence and Flyway for managing database migrations.

Configuration values such as database credentials, service port, and JWT secrets are externalized using environment variables instead of being hardcoded in the application. This allows the service to run in different environments (development, testing, or production) without modifying the code.

To run the service, first ensure that the Eureka Server and MySQL database are running. Then build the project using Maven and start the application using Spring Boot.

mvn clean install
mvn spring-boot:run

By default, the service runs on port 8383, but the port can be changed using the SERVER_PORT environment variable.

The Cart Service exposes several REST endpoints for cart operations. Users can add items to their cart, update item quantities, retrieve their current cart, and clear the cart when an order is completed.

Main endpoints include:

POST /cart/add – Adds a product to the user's cart

PUT /cart/update – Updates the quantity of a cart item

GET /cart – Retrieves the current user's cart

DELETE /cart/delete – Clears the cart

Flyway is used to manage database schema migrations, and migration scripts should be placed inside the src/main/resources/db/migration directory.
