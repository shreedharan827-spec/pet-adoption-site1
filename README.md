# 🐾 Pet Adoption Site

A full-stack web application for pet adoption, featuring user registration, pet browsing, and adoption management.

## 🚀 Quick Start

### Prerequisites
- Java 17
- MySQL 8.0
- Node.js (for frontend development)
- Docker (optional)

### Local Development

1. **Start MySQL Database**:
   ```bash
   # Using Docker
   docker run --name mysql-pet -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=pet_adoption_site -e MYSQL_USER=pet_user -e MYSQL_PASSWORD=Shree@1650! -p 3306:3306 -d mysql:8.0
   ```

2. **Run Backend**:
   ```bash
   cd backend
   mvn spring-boot:run
   ```
   API available at: http://localhost:8080/api

3. **Run Frontend**:
   ```bash
   cd frontend
   python3 -m http.server 3000
   ```
   Frontend available at: http://localhost:3000

## 🏗️ Architecture

- **Backend**: Spring Boot REST API
- **Frontend**: Vanilla HTML/CSS/JavaScript
- **Database**: MySQL
- **Deployment**: Docker-ready

## 📁 Project Structure

```
pet-adoption-site/
├── backend/                 # Spring Boot API
│   ├── src/
│   ├── pom.xml
│   ├── Dockerfile
│   └── docker-compose.yml
├── frontend/               # Static web app
│   ├── index.html
│   ├── registration.html
│   ├── script.js
│   └── styles.css
├── DEPLOYMENT.md          # Deployment guide
└── nginx.conf            # Nginx config
```

## 🌐 Production Deployment

See [DEPLOYMENT.md](DEPLOYMENT.md) for detailed deployment instructions.

### Quick Deploy Options

- **Heroku**: `git push heroku main`
- **Docker**: `docker-compose up --build`
- **Railway/Render**: Connect GitHub repo
- **AWS/Google Cloud**: Use provided Docker setup

### Environment Variables

```bash
# Database
DATABASE_URL=jdbc:mysql://host:port/db
DB_USERNAME=user
DB_PASSWORD=password

# Security
JWT_SECRET=your_secret_key
SPRING_PROFILES_ACTIVE=prod

# Server
PORT=8080
```

## 🔧 API Endpoints

### User Management
- `POST /api/users/register` - Register new user
- `POST /api/users/send-otp` - Send OTP
- `POST /api/users/verify-otp` - Verify OTP
- `GET /api/users/{id}` - Get user by ID

### Pet Management
- `GET /api/pets` - Get all pets
- `GET /api/pets/{id}` - Get pet by ID
- `POST /api/pets` - Add new pet (admin)

### Adoption
- `POST /api/adoptions` - Request adoption
- `GET /api/adoptions/user/{userId}` - Get user adoptions

## 🧪 Testing

```bash
# Backend tests
cd backend
mvn test

# API testing with curl
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{"fullName":"Test User","email":"test@example.com",...}'
```

## 🤝 Contributing

1. Fork the repository
2. Create feature branch
3. Commit changes
4. Push to branch
5. Create Pull Request

## 📄 License

MIT License - see LICENSE file for details.

## 📞 Support

For issues and questions:
- Check [DEPLOYMENT.md](DEPLOYMENT.md) for common problems
- Review application logs
- Ensure environment variables are set correctly