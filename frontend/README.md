# 🐾 PawsAdopt - Pet Adoption Platform

Welcome to **PawsAdopt**, a comprehensive full-stack web application for pet adoption. This project consists of a modern **frontend** built with HTML/CSS/JavaScript and a robust **Java Spring Boot backend** API.

## 📋 Project Overview

PawsAdopt is a complete pet adoption system that connects pet lovers with adoptable pets. Users can register, browse available pets, and submit adoption requests with a secure registration process.

### Features

✨ **Frontend (HTML/CSS/JavaScript)**
- Responsive pet adoption website
- Dynamic hover animations and transitions
- Multi-step registration form with validation
- Beautiful UI with modern color scheme
- Real-time form validation feedback
- OTP verification system
- Success/error handling with notifications

✨ **Backend (Java Spring Boot)**
- RESTful API for all operations
- User management with security
- Complete pet catalog system
- Adoption request tracking
- Database persistence
- Exception handling and logging
- CORS enabled for frontend integration

## 📁 Project Structure

```
Pet-Adoption-site/
├── frontend/                          # Frontend Application
│   ├── index.html                    # Main adoption website
│   ├── registration.html             # Registration page
│   ├── styles.css                    # Main styling
│   ├── registration.css              # Registration styling
│   ├── script.js                     # Main page logic
│   └── registration.js               # Registration logic
│
└── backend/                           # Backend Application
    ├── pom.xml                       # Maven configuration
    ├── README.md                     # Backend documentation
    ├── QUICKSTART.md                 # Backend quick start
    ├── .gitignore                    # Git ignore rules
    └── src/
        └── main/
            ├── java/com/petadoption/
            │   ├── PawsAdoptApplication.java        # Main app
            │   ├── controller/                      # REST endpoints
            │   │   ├── UserController.java
            │   │   ├── PetController.java
            │   │   └── AdoptionController.java
            │   ├── service/                         # Business logic
            │   │   ├── UserService.java
            │   │   ├── PetService.java
            │   │   └── AdoptionService.java
            │   ├── model/                           # Entity models
            │   │   ├── User.java
            │   │   ├── Pet.java
            │   │   └── Adoption.java
            │   ├── repository/                      # Data access
            │   │   ├── UserRepository.java
            │   │   ├── PetRepository.java
            │   │   └── AdoptionRepository.java
            │   ├── dto/                             # DTOs
            │   │   ├── ApiResponse.java
            │   │   ├── RegistrationRequest.java
            │   │   └── UserResponse.java
            │   ├── exception/                       # Exception handling
            │   │   ├── GlobalExceptionHandler.java
            │   │   ├── UserNotFoundException.java
            │   │   └── UserAlreadyExistsException.java
            │   └── config/                          # Configuration
            │       └── AppConfig.java
            └── resources/
                └── application.properties  # App config
```

## 🚀 Quick Start

### Frontend Setup

1. **Navigate to frontend**
```bash
cd frontend/
```

2. **Open in browser**
- Double-click `index.html` to open main site
- Or use a local server:
```bash
python3 -m http.server 8000
# Visit: http://localhost:8000
```

### Backend Setup

1. **Navigate to backend**
```bash
cd backend/
```

2. **Build and run**
```bash
mvn clean install
mvn spring-boot:run
```

3. **Access API**
```
http://localhost:8080/api
```

## 🌐 Frontend Features

### Pages

**🏠 Home/Adoption Page** (`index.html`)
- Pet listing with dynamic cards
- Filter by pet type (Dogs, Cats, Rabbits, Birds)
- Smooth hover animations
- Adoption request buttons
- Beautiful gradient background
- Fully responsive design

**📝 Registration Page** (`registration.html`)
- 3-step registration process with progress bar
- Step 1: Personal Information
  - Name validation
  - Email verification
  - Phone number (10 digits)
  - Aadhar number (12 digits)

- Step 2: Security Setup
  - Strong password requirements
  - Password strength indicator
  - Security questions
  - Show/hide password toggle

- Step 3: Verification
  - OTP verification (6 digits)
  - Resend OTP with countdown
  - Data confirmation

- Success/Error pages with feedback

### Technologies Used
- **HTML5** - Semantic markup
- **CSS3** - Modern styling with gradients, animations
- **JavaScript ES6+** - Form validation, interactions
- **Animations** - Smooth transitions and hover effects
- **Responsive Design** - Mobile, tablet, desktop

## 🔧 Backend Features

### User Management API

```
POST   /api/users/register              # Register new user
GET    /api/users/{id}                  # Get user details
GET    /api/users/email/{email}         # Get user by email
PUT    /api/users/{id}                  # Update user
DELETE /api/users/{id}                  # Delete user
POST   /api/users/{id}/verify           # Verify user
```

### Pet Management API

```
GET    /api/pets                        # Get all available pets
GET    /api/pets/type/{type}            # Get pets by type
GET    /api/pets/{id}                   # Get pet details
POST   /api/pets                        # Create pet (Admin)
PUT    /api/pets/{id}                   # Update pet (Admin)
DELETE /api/pets/{id}                   # Delete pet (Admin)
GET    /api/pets/stats/count            # Get pet statistics
```

### Adoption Management API

```
POST   /api/adoptions/request/{userId}/{petId}    # Create request
GET    /api/adoptions/user/{userId}               # Get user requests
GET    /api/adoptions/pet/{petId}                 # Get pet requests
GET    /api/adoptions/{id}                        # Get request details
PUT    /api/adoptions/{id}/approve                # Approve (Admin)
PUT    /api/adoptions/{id}/reject                 # Reject (Admin)
PUT    /api/adoptions/{id}/cancel                 # Cancel
GET    /api/adoptions/pending/all                 # Get pending (Admin)
GET    /api/adoptions/stats                       # Get statistics (Admin)
```

### Technologies Used
- **Java 17** - Programming language
- **Spring Boot 3.2.0** - Framework
- **Spring Data JPA** - ORM
- **Spring Security** - Security
- **MySQL/H2** - Database
- **Maven** - Build tool
- **Lombok** - Code generation
- **JWT** - Authentication ready

## ⚙️ Configuration

### Frontend HTML/CSS/JavaScript
All configuration is in the respective files:
- Colors and styling: `styles.css`, `registration.css`
- Animations: Defined in CSS `@keyframes`
- Validation rules: `script.js`, `registration.js`

### Backend Configuration

Edit `backend/src/main/resources/application.properties`:

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

## 🗄️ Database Models

### User Entity
```
- id (Long, Primary Key)
- email (String, Unique)
- fullName (String)
- phoneNumber (String, Unique)
- aadharNumber (String, Unique)
- password (String, Encrypted)
- securityQuestion1 (String)
- securityAnswer1 (String)
- securityQuestion2 (String)
- securityAnswer2 (String)
- isVerified (Boolean)
- isActive (Boolean)
- createdAt (LocalDateTime)
- updatedAt (LocalDateTime)
```

### Pet Entity
```
- id (Long, Primary Key)
- name (String)
- type (Enum: DOG, CAT, RABBIT, BIRD...)
- breed (String)
- description (String)
- age (Integer)
- gender (Enum: MALE, FEMALE, UNKNOWN)
- isVaccinated (Boolean)
- isNeutered (Boolean)
- status (Enum: AVAILABLE, ADOPTED, RESERVED)
- createdAt (LocalDateTime)
- updatedAt (LocalDateTime)
```

### Adoption Entity
```
- id (Long, Primary Key)
- userId (Long, Foreign Key)
- petId (Long, Foreign Key)
- status (Enum: PENDING, APPROVED, REJECTED, CANCELLED)
- adoptionReason (String)
- requestedAt (LocalDateTime)
- completedAt (LocalDateTime)
```

## 🔐 Security Features

### Frontend
- ✅ Client-side form validation
- ✅ Password strength indicator
- ✅ Secure OTP verification
- ✅ Error message handling
- ✅ Input sanitization

### Backend
- ✅ BCrypt password encryption
- ✅ Email/Phone/Aadhar duplicate checking
- ✅ CORS configuration
- ✅ Exception handling
- ✅ SQL injection prevention (JPA)
- ✅ Request validation
- ✅ Logging for audit trail
- ✅ JWT ready (for API authentication)

## 🧪 Testing

### Frontend Testing
1. Open `http://localhost:8000/` in browser
2. Test registration flow
3. Test pet filtering
4. Test adoption requests
5. Test responsive design

### Backend Testing

**Register User:**
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

**Get Pets:**
```bash
curl http://localhost:8080/api/pets
```

## 📱 Responsive Breakpoints

Frontend is optimized for:
- 📱 Mobile: 320px - 480px
- 📱 Tablet: 481px - 768px
- 🖥️ Desktop: 769px+

## 🎨 Color Palette

- **Primary**: #FF6B6B (Coral Red)
- **Secondary**: #4ECDC4 (Teal)
- **Accent**: #FFE66D (Warm Yellow)
- **Dark**: #2C3E50 (Charcoal)
- **Light**: #F7F9FC (Off-white)

## 📚 Documentation

- **Frontend**: See `frontend/` files for inline comments
- **Backend**: 
  - [README.md](backend/README.md) - Full documentation
  - [QUICKSTART.md](backend/QUICKSTART.md) - Quick start guide

## 🚀 Deployment

### Frontend
- Upload `frontend/` folder to any static hosting (GitHub Pages, Netlify, etc.)
- Or serve with any web server

### Backend
```bash
# Create JAR
mvn clean package

# Run JAR
java -jar target/paws-adopt-backend-1.0.0.jar

# Or deploy to cloud (AWS, GCP, Azure, Heroku, etc.)
```

## 🔲 API Response Format

All API responses follow this standard format:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": { ... },
  "statusCode": 200,
  "timestamp": 1634567890123
}
```

## 📝 Common Workflows

### User Registration Flow
1. User fills registration form (frontend)
2. Form validates on client-side
3. Submit to backend `/users/register`
4. Backend validates and creates user
5. Returns success response
6. Frontend shows success page

### Pet Adoption Flow
1. User browses pets (frontend)
2. Clicks "Adopt Pet" button
3. Frontend sends request to `/adoptions/request/{userId}/{petId}`
4. Backend creates adoption record
5. Admin reviews pending requests
6. Admin approves/rejects adoption
7. User notified of status

## 🔗 Frontend-Backend Integration

Update environment variables in `frontend/` JavaScript files:

```javascript
// Current (Local)
const API_BASE = 'http://localhost:8080/api';

// Production
const API_BASE = 'https://api.pawsadopt.com/api';
```

## ⚡ Performance Optimizations

### Frontend
- Minimized CSS/JS
- Smooth animations using CSS
- Lazy loading for images
- Efficient DOM manipulation

### Backend
- Database indexing on primary keys
- Connection pooling
- JPA query optimization
- Lombok for code generation
- Spring Security caching

## 🐛 Troubleshooting

### Frontend Issues
- **Styles not loading**: Clear browser cache (Ctrl+Shift+Delete)
- **Form not submitting**: Check browser console for errors
- **Animations not smooth**: Check browser performance tab

### Backend Issues
- **Port 8080 in use**: Change port in application.properties
- **Database errors**: Check MySQL connection
- **Validation errors**: Review required fields

## 📈 Future Enhancements

- [ ] Advanced pet filtering (age, size, breed)
- [ ] User dashboard
- [ ] Pet adoption history
- [ ] Reviews and ratings
- [ ] Email notifications
- [ ] SMS alerts
- [ ] Payment integration
- [ ] Admin panel
- [ ] Mobile app (iOS/Android)
- [ ] Machine learning recommendations

## 👥 Team & Contributing

This project is developed and maintained by the PawsAdopt team.

### Contributing
1. Fork the repository
2. Create feature branch
3. Make your changes
4. Submit pull request

## 📄 License

MIT License - Free for personal and commercial use

## 📞 Support & Contact

- 📧 Email: support@pawsadopt.com
- 🌐 Website: https://pawsadopt.com
- 📱 Phone: +1-800-ADOPT-ME

---

## 🎉 Getting Started Now

### Start Frontend
```bash
cd frontend
python3 -m http.server 8000
# Visit http://localhost:8000
```

### Start Backend
```bash
cd backend
mvn spring-boot:run
# Backend at http://localhost:8080/api
```

### Enjoy!
```
🐾 Welcome to PawsAdopt - Find Your Perfect Pet! 🐾
```

---

**Made with ❤️ for pet lovers everywhere**
