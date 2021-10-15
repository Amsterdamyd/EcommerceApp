# EcommerceApp
A demo of spring boot + rest api

In this challenge, you are part of team building product dashboard for internal use. One requirement is to create a REST API service using the Spring Boot framework. It will provide functionality to add, update and fetch products.

Example of a product JSON object:
{
    "id": 1,
    "name": "Waterproof",
    "category": "Coats and Jackets",
    "retail_price": 274.0,
    "discounted_price": 230.16,
    "availability": true
}

1. POST request to /products :
Creates a new product.
Expects a JSON product object with a unique ID. You can assume that the given object is always valid.
If a product with the same ID already exists, the response code is 400.  Otherwise the response code is 201.
<p align="center">
  <a target="_blank" rel="noopener noreferrer" href="https://github.com/Amsterdamyd/EcommerceApp/blob/main/pictures/create%20a%20product.jpg"><img src="https://github.com/Amsterdamyd/EcommerceApp/blob/main/pictures/create%20a%20product.jpg" style="max-width: 100%;"></a>
  <br>
</p>


2. PUT request to /products/{id}:
Updates the product with the given ID. The product JSON sent in the request body will have the keys retailPrice, discountedPrice, availability.
If the product with the requested ID does not exist, the response code is 400.  Otherwise the response code is 200.

3. GET request to /products/{id}:
Returns a record with the given ID and status code 200.
If there is no record in the database with the given ID, the response code is 404.

4. GET requests to /products?category={category}:
Returns all the products in the given category.
The response code should be 200.
Records should be sorted by the availability, i.e., in stock products must be listed before out of stock products. Products with same availability status must be sorted by the discounted price in ascending order. Finally, the products with same discounted price must be sorted by ID in descending order.

5. GET request to /products:
Returns all the records with status code 200.
Records should be sorted by ID in ascending order.
