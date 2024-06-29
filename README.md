# product-api

Demo application build on Spring boot 3.2.1. This application provides following api endpoints:

1. POST /products - Add a new product.
o Accepts a JSON payload with name, category, description, price, and imageUrl.
2. GET /products - Retrieve all products.
o Returns a paginated list of products.
3. GET /search - Search for products by name.
o Accepts a query string parameter for the search term.
o Implements a fuzzy search algorithm that can return relevant results, even if the
search term has typos or is an approximate string. The implementation can eg. be
done using edit distance with Damerau-Levenshtein distance. Don’t use a ready
made fuzzy search library.

4. GET /products/:id - Get details for a specific product.


# Running the application locally

Execute the main method in the ProductApiApplication class from your IDE.

Alternatively run the following command:

`mvn spring-boot:run`

We can also run and deploy the jar file using following commands
1. `./mvnw package`
2. `java -jar target/product-0.0.1-SNAPSHOT.jar`

Once the application is up and running you can view the Swagger ui for application apis at http://localhost:8080
