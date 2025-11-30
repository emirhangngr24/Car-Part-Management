#  CAR PART MANAGEMENT

A REST API developed for vehicle spare parts and inventory management with JWT authentication and role-based access control.

## ✨ Features

- **Brand Management** - Create, list, update, delete brands
- **Parts Management** - Manage vehicle parts with CRUD operations
- **Contact Form** - Manage customer messages
- **File Management** - Upload images/videos via Cloudinary
- **JWT Authentication** - Secure token-based login
- **Role-Based Access** - Admin and general user roles

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.x, Spring Security 6.x
- **Database**: PostgreSQL, Hibernate ORM, JsPA
- **Authentication**: JWT (JSON Web Tokens)
- **API Documentation**: Springdoc-OpenAPI (Swagger 3.0)
- **Cloud Storage**: Cloudinary
- **Build Tool**: Maven

## 🚀 Getting Started

### Prerequisites
- Java 17+
- PostgreSQL 12+
- Maven 3.6+
- Cloudinary Account (free)

### Installation

#### 1. **Clone the Repository**
```bash
git clone https://github.com/emirhangngr24/car-track-management.git
cd car-track-management
```

#### 2. **Create a Cloudinary Account**

**IMPORTANT:** Cloudinary API keys are required for the file upload feature to work!

**Step 1: Go to Cloudinary**
- Go to https://cloudinary.com
- Click on "Sign Up for Free" button
- Register with email (or Google/GitHub)

**Step 2: Get API Keys**
- Enter your Dashboard
- Select "Settings" from the left sidebar
- Click on the "API Keys" tab
- Copy the following information:
  ```
  Cloud Name: (your_cloud_name)
  API Key: (your_api_key)
  API Secret: (your_api_secret)
  ```

#### 3. **Add to application.properties**

Open the `src/main/resources/application.properties` file:

```properties
# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/car
spring.datasource.username=postgres
spring.datasource.password=your_password

# Cloudinary API Keys
cloudinary.cloud.name=your_cloud_name
cloudinary.api.key=your_api_key
cloudinary.api.secret=your_api_secret
```

#### 4. **Configure the Database**

Create a database in PostgreSQL:

```bash
# Log into PostgreSQL
psql -U postgres

# Create database
CREATE DATABASE car;

# Exit
\q
```

#### 5. **Build and Run**

```bash
mvn clean install
mvn spring-boot:run
```

The API will run at: `http://localhost:8080`

---

## 📚 Swagger API Documentation

You can test all endpoints through Swagger UI:
```
http://localhost:8080/swagger-ui.html
```

- ✅ Automatic listing of all endpoints
- ✅ JWT authentication
- ✅ Test APIs in the browser

## 🧪 Testing

### Using Swagger UI
1. Open `http://localhost:8080/swagger-ui.html`
2. Click on [Authorize] button
3. Enter your JWT token
4. Select an endpoint and click "Try it out" button
5. Send the request
<img width="987" height="850" alt="Screenshot 2025-11-30 174047" src="https://github.com/user-attachments/assets/0936abe1-a8fe-49db-82c5-37e56a7328c3" />

### Example cURL Request
```bash
curl -X GET http://localhost:8080/api/public/marcalar \
  -H "Authorization: Bearer paste_token_here"
```

### File Upload Testing (with Cloudinary)
```bash
# Login with admin token
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password"}'

# Copy the token

# Upload file
curl -X POST http://localhost:8080/api/admin/parcalar/1/upload \
  -H "Authorization: Bearer paste_token_here" \
  -F "file=@image.jpg"
```

## 📊 API Endpoints

### Parts Management (11 endpoints)
```
GET    /api/admin/parcalar/{id}                - Get part details (Admin)
PUT    /api/admin/parcalar/{id}                - Update part (Admin)
DELETE /api/admin/parcalar/{id}                - Delete part (Admin)
GET    /api/admin/parcalar                     - List all parts (Admin)
POST   /api/admin/parcalar                     - Create new part (Admin)
GET    /api/public/parcalar                    - Public parts list
GET    /api/public/parcalar/{id}               - Get part details (Public)
GET    /api/public/parcalar/marka/{markaId}    - List parts by brand
GET    /api/admin/parcalar/marka/{markaId}     - List parts by brand (Admin)
DELETE /api/admin/parcalar/{id}/video          - Delete part video (Admin)
DELETE /api/admin/parcalar/{id}/fotograf       - Delete part photo (Admin)
```

### Brand Management (6 endpoints)
```
GET    /api/admin/marcalar/{id}                - Get brand details (Admin)
PUT    /api/admin/marcalar/{id}                - Update brand (Admin)
DELETE /api/admin/marcalar/{id}                - Delete brand (Admin)
GET    /api/admin/marcalar                     - List all brands (Admin)
POST   /api/admin/marcalar                     - Create new brand (Admin)
GET    /api/public/marcalar                    - Public brands list
```

### Contact Form (6 endpoints)
```
PUT    /api/contact/{id}/mark-read             - Mark message as read (Admin)
POST   /api/contact/send                       - Send message (Public)
GET    /api/contact/unread                     - Get unread messages (Admin)
GET    /api/contact/unread-count               - Get unread message count (Admin)
GET    /api/contact/all                        - Get all messages (Admin)
DELETE /api/contact/{id}                       - Delete message (Admin)
```

### Site Content Management (8 endpoints)
```
PUT    /api/admin/site-content/{id}            - Update content (Admin)
PUT    /api/admin/site-content/key/{key}       - Update content by key (Admin)
PUT    /api/admin/site-content/bulk            - Bulk update (Admin)
GET    /api/public/site-content                - Public content list
GET    /api/public/site-content/{key}          - Get content by key (Public)
GET    /api/public/site-content/category/{category} - Get by category (Public)
GET    /api/admin/site-content                 - List all content (Admin)
```

### Authentication (3 endpoints)
```
POST   /api/auth/register                      - Register new user
POST   /api/auth/login                         - User login
GET    /api/auth/test                          - Test endpoint
```

---

## 🔒 Security

✅ Password encryption (BCrypt)
✅ JWT token expiration
✅ CORS configuration
✅ Input validation
✅ SQL injection prevention (JPA)
✅ Role-based access control
✅ Cloudinary API Secret protection

## 📋 Database Relationships
```
Brand (1) ↔ (Many) Part
- One brand can have many parts
- Delete brand → Parts automatically deleted (cascade)
```

## 👤 Author

**Emirhan Güngör**
- Email: emirhangungor2424@hotmail.com
- GitHub: [emirhangngr24](https://github.com/emirhangngr24)
- LinkedIn: [Emirhan-GÜNGÖR](https://www.linkedin.com/in/emirhan-g%C3%BCng%C3%B6r-b6b186326/)

---

**Last Updated**: November 2025 | Status: Active Development ✨
