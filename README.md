# C9 Laboration Warehouse

Here’s a detailed documentation of the REST endpoints in this API, describing the HTTP methods, paths, parameters, and expected responses:

---

### 1. **Get All Products**
   - **Endpoint**: `/my-web-app/api/warehouse/products`
   - **HTTP Method**: `GET`
   - **Description**: Retrieves a paginated list of all products in the warehouse.
   - **Parameters**:
     - **Query Parameters**: `PaginationQuery` class with validation for pagination details (like product ID and page size).
     - **Usage**: Select from what product ID you want to start from and choose how many products are to be shown after that product.
   - **Produces**: `application/json`
   - **Response**:
     - **200 OK**: Returns a list of `ProductRecord` in JSON format.

   Example:
   ```
   GET /my-web-app/api/warehouse/products?productId=1&pageSize=10
   ```

---

### 2. **Get Product by ID**
   - **Endpoint**: `/my-web-app/api/warehouse/products/{id}`
   - **HTTP Method**: `GET`
   - **Description**: Retrieves a product by its unique ID.
   - **Path Parameters**:
     - `id` (String): The unique identifier of the product.
   - **Produces**: `application/json`
   - **Response**:
     - **200 OK**: Returns the `ProductRecord` with the specified ID in JSON format.
     - **404 Not Found**: If no product is found with the provided ID.

   Example:
   ```
   GET /my-web-app/api/warehouse/products/12345
   ```

---

### 3. **Search Products by Category**
   - **Endpoint**: `/my-web-app/api/warehouse/products/category/{category}`
   - **HTTP Method**: `GET`
   - **Description**: Retrieves a list of products based on their category.
   - **Path Parameters**:
     - `category` (String): The category of the products to filter, validated by `@ValidCategory`.
   - **Produces**: `application/json`
   - **Response**:
     - **200 OK**: Returns a list of products belonging to the specified category.
     - **404 Not Found**: If no products are found in the specified category.

   Example:
   ```
   GET /my-web-app/api/warehouse/products/category/electronics
   ```

---

### 4. **Add a New Product**
   - **Endpoint**: `/my-web-app/api/warehouse/products`
   - **HTTP Method**: `POST`
   - **Description**: Adds a new product to the warehouse.
   - **Consumes**: `application/json`
   - **Request Body**:
     - A `Product` object, validated by `@Valid`.
   - **Header Parameters**:
     - `X-Forwarded-Proto`: The protocol used in the request (optional).
   - **Response**:
     - **201 Created**: The product is successfully added, and the response includes the URI to the newly created product.
     - **400 Bad Request**: If the request body fails validation.

   Example:
   ```
   POST /my-web-app/api/warehouse/products
   Content-Type: application/json
   {
     "name": "New Product",
     "category": "FURNITURE",
     "rating": 5
   }
   ```

---

### 5. **Populate Products**
   - **Endpoint**: `/my-web-app/api/warehouse/populate`
   - **HTTP Method**: `POST`
   - **Description**: Populates the warehouse with predefined products for testing or demo purposes.
   - **Response**:
     - **200 OK**: Products are successfully populated.

   Example:
   ```
   POST /my-web-app/api/warehouse/populate
   ```

---

### Summary:
- These endpoints expose operations to interact with a product warehouse, allowing users to retrieve, add, and filter products.
- The API is designed with pagination, validation, and standardized responses, making it accessible and easy to use.
  
