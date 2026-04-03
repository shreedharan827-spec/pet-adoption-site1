# Quick Start Guide - PawsAdopt Backend

## 🚀 Getting Started in 5 Minutes

### Step 1: Prerequisites Check
```bash
java -version  # Should be Java 17+
mvn -version   # Should be Maven 3.6+
```

### Step 2: Navigate to Backend Directory
```bash
cd /home/sri_usernot/Documents/Pet-Adoption-site/backend
```

### Step 3: Build Project
```bash
mvn clean install
```

### Step 4: Run Application
```bash
mvn spring-boot:run
```

You should see:
```
🐾 PawsAdopt Backend API Started Successfully!
📚 API Documentation: http://localhost:8080/api/swagger-ui.html
🗄️ H2 Console: http://localhost:8080/api/h2-console
```

### Step 5: Test an Endpoint
```bash
curl http://localhost:8080/api/pets
```

## 📁 Project Structure

```
backend/
├── pom.xml                                 # Maven dependencies
├── README.md                               # Full documentation
├── .gitignore                              # Git ignore file
└── src/
    └── main/
        ├── java/com/petadoption/
        │   ├── PawsAdoptApplication.java   # Main Spring Boot app
        │   ├── controller/                 # REST endpoints
        │   │   ├── UserController.java
        │   │   ├── PetController.java
        │   │   └── AdoptionController.java
        │   ├── service/                    # Business logic
        │   │   ├── UserService.java
        │   │   ├── PetService.java
        │   │   └── AdoptionService.java
        │   ├── model/                      # Entity models
        │   │   ├── User.java
        │   │   ├── Pet.java
        │   │   └── Adoption.java
        │   ├── repository/                 # Data access
        │   │   ├── UserRepository.java
        │   │   ├── PetRepository.java
        │   │   └── AdoptionRepository.java
        │   ├── dto/                        # Data transfer objects
        │   │   ├── ApiResponse.java
        │   │   ├── RegistrationRequest.java
        │   │   └── UserResponse.java
        │   ├── exception/                  # Exception handling
        │   │   ├── GlobalExceptionHandler.java
        │   │   ├── UserNotFoundException.java
        │   │   └── UserAlreadyExistsException.java
        │   └── config/                     # Configuration
        │       └── AppConfig.java
        └── resources/
            └── application.properties      # App configuration
```

## 🔑 Key Features

✅ **User Management**
- Registration with email/phone/Aadhar validation
- User profile management
- Security questions for recovery

✅ **Pet Management**
- Complete pet catalog
- Filter by type and status
- Health tracking

✅ **Adoption System**
- Adoption requests
- Status tracking (Pending, Approved, Rejected)
- Admin approval workflow

✅ **Security**
- Password encryption (BCrypt)
- CORS enabled
- Input validation
- Exception handling

## 📡 API Endpoints Summary

| Method | Endpoint | Purpose |
|--------|----------|---------|
| POST | `/api/users/register` | Register new user |
| GET | `/api/users/{id}` | Get user details |
| GET | `/api/pets` | Get all available pets |
| GET | `/api/pets/{id}` | Get pet details |
| POST | `/api/adoptions/request/{userId}/{petId}` | Create adoption request |
| GET | `/api/adoptions/user/{userId}` | Get user's adoption requests |

## 🗄️ Database

**Default (Development): H2 In-Memory**
- Console: http://localhost:8080/api/h2-console
- Auto-created tables on startup
- Data cleared on shutdown

**For Production (MySQL):**
1. Install MySQL
2. Create database:
```sql
CREATE DATABASE pawsadopt CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
3. Update `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pawsadopt
spring.datasource.username=root
spring.datasource.password=your_password
```

## 🧪 Testing

### Register a User
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

### Get All Pets
```bash
curl http://localhost:8080/api/pets
```

### Create Adoption Request
```bash
curl -X POST "http://localhost:8080/api/adoptions/request/1/1?adoptionReason=Want%20a%20pet"
```

## 🛠️ Development Commands

```bash
# Clean build
mvn clean

# Compile only
mvn compile

# Run tests
mvn test

# Create JAR package
mvn package

# Run from JAR
java -jar target/paws-adopt-backend-1.0.0.jar

# Skip tests during build
mvn clean package -DskipTests

# Check dependencies
mvn dependency:tree
```

## 📋 Required Fields

### User Registration
- `fullName` - 3-50 characters, letters and spaces only
- `email` - Valid email format
- `phoneNumber` - 10 digits, starts with 6-9
- `aadharNumber` - 12 digits
- `password` - Min 8 chars, uppercase, lowercase, number, special char
- `securityQuestion1` & `securityQuestion2` - From predefined list
- `securityAnswer1` & `securityAnswer2` - Custom answers

### Pet Creation
- `name` - Pet's name
- `type` - DOG, CAT, RABBIT, BIRD, etc.
- `breed` - Breed name
- `description` - Pet description
- `age` - Age in years
- `gender` - MALE, FEMALE, UNKNOWN
- `isVaccinated` - Boolean
- `isNeutered` - Boolean

## ⚠️ Common Issues

**Port 8080 already in use:**
```properties
# Edit application.properties
server.port=8081
```

**Build fails:**
```bash
# Clear Maven cache
rm -rf ~/.m2/repository
mvn clean install
```

**Database connection error:**
- Make sure MySQL is running (if using MySQL)
- Check connection string in application.properties

**Validation errors:**
- Check field formats - Phone: 10 digits starting with 6-9
- Aadhar: Exactly 12 digits
- Password: Must meet all requirements

## 📚 Next Steps

1. **Test all endpoints** using the curl commands above
2. **Connect frontend** by updating CORS origins if needed
3. **Review database** using H2 console
4. **Add authentication** using JWT (commented code available)
5. **Deploy** to production server

## 🔐 Security Notes

- Change JWT secret in production
- Use HTTPS in production
- Store sensitive config in environment variables
- Add API rate limiting
- Implement RBAC (Role-Based Access Control)
- Add API authentication middleware

## 📞 Troubleshooting

**Application won't start:**
1. Check Java version: `java -version`
2. Check Maven: `mvn -version`
3. Check logs for errors
4. Ensure port 8080 is free

**Getting 404 errors:**
- Check endpoint path and HTTP method
- Verify CORS configuration
- Check request body format

**Database issues:**
- View H2 console: http://localhost:8080/api/h2-console
- Check application.properties
- Review tables and data

## 🎯 API Response Format

All responses follow this format:
```json
{
  "success": true/false,
  "message": "Operation result message",
  "data": { ... result data ... },
  "statusCode": 200,
  "timestamp": 1634567890123
}
```

## 💡 Tips

- Use Postman or Insomnia for API testing
- Check logs for debugging: `mvn spring-boot:run`
- H2 Console is great for database exploration
- Keep application.properties for different environments
- Use meaningful variable names for better code readability

---

**Happy coding! 🐾**

For full documentation, see [README.md](README.md)
