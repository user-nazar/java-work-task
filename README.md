# java-work-task
Develop a REST service for library management using Java Spring Boot, JPA, Spring Validator, H2/Postgres database, JUnit and Mockito

# Description
### 📘 Swagger UI Preview

![image](https://github.com/user-attachments/assets/ec64fa7a-e6d1-4b05-8993-7b45274f1f6d)

The screenshot shows the **Swagger UI** of the Library REST API available at `http://localhost:8081/swagger-ui/index.html`

- **API Title**: Library REST API  
- **Version**: 1.0.0  
- **OpenAPI Spec**: `/v3/api-docs`  
- **Base URL**: `http://localhost:8081`

This interface is automatically generated using Springdoc OpenAPI and allows you to explore and test endpoints directly from the browser

From the screenshot, the `book-controller` provides:

- `GET /api/books/{id}` to fetch a book by ID  
- `PUT /api/books/{id}` to update book details by ID  
- `DELETE /api/books/{id}` to remove a book by ID

Other available endpoints include:

- List all books  
- Create new books  
- Borrow or return books  
- Manage members  
- View borrowed book reports and statistics

![image](https://github.com/user-attachments/assets/8abb161f-67a6-4819-bc1b-9b6fb7392c6e)
![image](https://github.com/user-attachments/assets/d218ea8d-e158-49ba-93ab-4dbd8cacc5c6)

The image shows a successful response (201 Created) from the POST /api/books endpoint.
It confirms that a book titled "Harry Potter" by "Joanne Rowling" with an initial amount of 10 copies was created.
The book was assigned an ID of 1, and currently no members have borrowed it (borrowedBy: []).

## Main requests:

### GET /api/books
![image](https://github.com/user-attachments/assets/4a25331a-4a63-45fb-8648-76f766a7d33e)

### POST /api/members
![image](https://github.com/user-attachments/assets/f38ca723-a050-406a-8ab4-65d464821624)

### POST /api/members/{memberId}/borrow/{bookId}
![image](https://github.com/user-attachments/assets/81a84c78-3b95-40f8-a6e5-ca6638fac07f)

### GET /api/books/borrowed/names
![image](https://github.com/user-attachments/assets/9d9d92b3-ec27-4897-94b4-85f43564b7f4)

### DELETE /api/books/{id}
![image](https://github.com/user-attachments/assets/e43c262d-2b27-4b8c-b94e-4f2b6f5838ad)
![image](https://github.com/user-attachments/assets/a773fedb-c8ee-44e3-a94c-483d7defad0a)

# Requirements:
#### №1 

### 1.2 Book Attributes
- Each book must have:
  - `ID`
  - `title`
  - `author`
  - `amount`

### 1.3 Duplicate Book Handling
- If a book with the same `title` and `author` exists, increment its `amount` by 1  
- If either the `title` or `author` is different, create a new book entry

### 1.4 Delete Restriction
- A book cannot be deleted if at least one copy is borrowed

### 1.5 Book Validation
- `title`: required, must start with a capital letter, minimum 3 characters
- `author`: required, must be two capitalized words (e.g. `George Orwell`)

### 1.6 Custom Endpoints
- Retrieve all **distinct borrowed book titles**
- Retrieve all **distinct borrowed book titles** and the **count of borrowed copies**

#### №2

## 👤 2. Member Management

### 2.1 CRUD Operations
- Implement Create, Read, Update, Delete for members

### 2.2 Member Attributes
- Each member must have:
  - `ID`
  - `name`
  - `membership date` (set automatically at creation)

### 2.3 Borrowing Rules
- A member can borrow up to **10 books** (limit comes from property file or env variable)
- On borrow:
  - The book’s `amount` is decreased by 1
  - If `amount` is 0, borrowing is not allowed
- On return:
  - The book’s `amount` is increased by 1

### 2.4 Delete Restriction
- A member cannot be deleted if they have borrowed books

### 2.5 Member Validation
- `name`: required

### 2.6 Custom Endpoints
- Retrieve all books borrowed by a specific member by `name`

All endpoints can be tested interactively through Swagger without needing external tools
