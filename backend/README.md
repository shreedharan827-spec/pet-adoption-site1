# PawsAdopt Backend API

A comprehensive Spring Boot REST API for a Pet Adoption System. This backend provides complete user management, pet catalog, and adoption tracking functionality.

## Features

✅ **User Management**
- User registration with validation
- User profile management
- Security questions for account recovery
- Email, phone number, and Aadhar verification

✅ **Pet Management**
- Complete pet catalog with multiple pet types (Dogs, Cats, Rabbits, Birds, etc.)
- Pet filtering by type and adoption status
- Pet health information tracking
- Pet availability status

✅ **Adoption System**
- Adoption request/application management
- Status tracking (Pending, Approved, Rejected, Cancelled)
- Adoption history

✅ **Security**
- Password encryption using BCrypt
- CORS configuration
- Input validation
- Exception handling
- Logging and monitoring

✅ **REST API**
- Comprehensive API endpoints
- Standard response format
- Error handling
- API documentation

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Spring Security**
- **MySQL/H2 Database**
- **Maven**
- **Lombok**
- **JWT Authentication** (ready to implement)

## Project Structure

```
backend/
├── src/main/java/com/petadoption/
│   ├── controller/          # REST Controllers
│   ├── service/             # Business Logic
│   ├── model/               # Entity Models
│   ├── repository/          # Data Access Layer
│   ├── dto/                 # Data Transfer Objects
│   ├── exception/           # Custom Exceptions
│   ├── config/              # Configuration Classes
│   └── PawsAdoptApplication.java  # Main Application
├── src/main/resources/
│   └── application.properties     # Application Configuration
└── pom.xml                  # Maven Configuration

```

## Installation & Setup

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+ (optional - uses H2 by default for development)

### Steps

1. **Navigate to backend directory**
```bash
cd backend
```

2. **Install dependencies**
```bash
mvn clean install
```

3. **Run the application**
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080/api`

## API Endpoints

### User Management

#### Register User
```
POST /api/users/register
Content-Type: application/json

{
  "fullName": "John Doe",
  "email": "john@example.com",
  "phoneNumber": "9876543210",
  "aadharNumber": "123456789012",
  "password": "SecurePass@123",
  "securityQuestion1": "What was your first pet's name?",
  "securityAnswer1": "Fluffy",
  "securityQuestion2": "What city were you born in?",
  "securityAnswer2": "New York"
}
```

#### Get User by ID
```
GET /api/users/{userId}
```

#### Get User by Email
```
GET /api/users/email/{email}
```

#### Update User
```
PUT /api/users/{userId}
Content-Type: application/json
```

#### Verify User
```
POST /api/users/{userId}/verify
```

#### Delete User
```
DELETE /api/users/{userId}
```

### Pet Management

#### Get All Available Pets
```
GET /api/pets
```

#### Get Pets by Type
```
GET /api/pets/type/{type}
```

Supported types: DOG, CAT, RABBIT, BIRD, HAMSTER, GUINEA_PIG, OTHER

#### Get Pet by ID
```
GET /api/pets/{petId}
```

#### Create Pet (Admin)
```
POST /api/pets
Content-Type: application/json

{
  "name": "Max",
  "type": "DOG",
  "breed": "Golden Retriever",
  "description": "Friendly and energetic",
  "age": 3,
  "gender": "MALE",
  "isVaccinated": true,
  "isNeutered": false,
  "healthNotes": "No health issues"
}
```

#### Update Pet (Admin)
```
PUT /api/pets/{petId}
Content-Type: application/json
```

#### Delete Pet (Admin)
```
DELETE /api/pets/{petId}
```

#### Get Available Pets Count
```
GET /api/pets/stats/count
```

## Database

### H2 (Development - Default)
- Console: `http://localhost:8080/api/h2-console`
- URL: `jdbc:h2:mem:pawsadopt`
- Username: `sa`
- Password: (empty)

### MySQL (Production)
Update `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pawsadopt
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=create-drop
```

## Response Format

### Success Response
```json
{
  "success": true,
  "message": "Operation successful",
  "data": { ... },
  "statusCode": 200,
  "timestamp": 1634567890123
}
```

### Error Response
```json
{
  "success": false,
  "message": "Error message",
  "data": null,
  "statusCode": 400,
  "timestamp": 1634567890123
}
```

## Configuration

Edit `src/main/resources/application.properties`:

```properties
# Server
server.port=8080
server.servlet.context-path=/api

# Database
spring.datasource.url=jdbc:h2:mem:pawsadopt
spring.jpa.hibernate.ddl-auto=create-drop

# JWT
jwt.secret=your_secret_key
jwt.expiration=86400000

# Logging
logging.level.com.petadoption=DEBUG
```

## Security Features

- ✅ Password encryption with BCrypt
- ✅ CORS enabled for frontend integration
- ✅ Input validation
- ✅ Exception handling
- ✅ Logging of all operations
- ✅ Security questions for account recovery
- ✅ Email/Phone/Aadhar duplicate checking

## Testing with cURL

### Register a user:
```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "John Doe",
    "email": "john@example.com",
    "phoneNumber": "9876543210",
    "aadharNumber": "123456789012",
    "password": "SecurePass@123",
    "securityQuestion1": "pet",
    "securityAnswer1": "Fluffy",
    "securityQuestion2": "city",
    "securityAnswer2": "New York"
  }'
```

### Get all pets:
```bash
curl -X GET http://localhost:8080/api/pets
```

## Development

### Build
```bash
mvn clean build
```

### Run Tests
```bash
mvn test
```

### Generate JAR
```bash
mvn package
```

Output: `target/paws-adopt-backend-1.0.0.jar`

## Future Enhancements

- [ ] JWT Authentication
- [ ] Role-based Access Control (RBAC)
- [ ] Email notifications
- [ ] SMS OTP verification
- [ ] Payment integration
- [ ] Admin dashboard
- [ ] Advanced search and filtering
- [ ] Adoption statistics and reports
- [ ] User reviews and ratings
- [ ] Adoption tips and guides

## Troubleshooting

**Port Already in Use:**
```bash
# Change port in application.properties
server.port=8081
```

**Database Connection Error:**
```bash
# Check MySQL is running
# Update connection credentials in application.properties
```

**Validation Errors:**
```bash
# Ensure all required fields are provided
# Check phone number format: 10 digits starting with 6-9
# Check Aadhar format: 12 digits
```

## Contributors

- Development Team: PawsAdopt Team

## License

MIT License - Feel free to use this project for personal and commercial purposes.

## Support

For issues, questions, or suggestions:
- Email: support@pawsadopt.com
- GitHub Issues: [Create an issue]

---

**Happy coding! 🐾**
