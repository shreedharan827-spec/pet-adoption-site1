# Pet Adoption Site - Deployment Guide

## Overview
This guide explains how to deploy the Pet Adoption application to a global server accessible via browser.

## Architecture
- **Backend**: Spring Boot REST API (Java 17)
- **Frontend**: Static HTML/CSS/JS served via web server
- **Database**: MySQL

## Prerequisites
- Docker and Docker Compose
- Cloud platform account (AWS, Google Cloud, DigitalOcean, etc.)
- Domain name (optional)

## Local Development with Docker

1. **Start services**:
   ```bash
   cd backend
   docker-compose up --build
   ```

2. **Access**:
   - Backend API: http://localhost:8080/api
   - Database: localhost:3306

## Production Deployment

### Option 1: Docker on Cloud Server

1. **Build and push Docker image**:
   ```bash
   cd backend
   docker build -t your-registry/pet-adoption-backend:latest .
   docker push your-registry/pet-adoption-backend:latest
   ```

2. **Deploy to cloud**:
   - Use AWS ECS, Google Cloud Run, or DigitalOcean App Platform
   - Set environment variables:
     - `SPRING_PROFILES_ACTIVE=prod`
     - `DATABASE_URL` (production MySQL)
     - `DB_USERNAME`
     - `DB_PASSWORD`
     - `JWT_SECRET`
     - `PORT=8080`

### Option 2: Heroku Deployment

1. **Prepare for Heroku**:
   ```bash
   # Add Procfile
   echo "web: java -jar target/paws-adopt-backend-1.0.0.jar --server.port=\$PORT" > Procfile
   ```

2. **Deploy**:
   ```bash
   heroku create your-app-name
   heroku config:set SPRING_PROFILES_ACTIVE=prod
   heroku config:set DATABASE_URL=your_mysql_url
   heroku config:set JWT_SECRET=your_secret
   git push heroku main
   ```

### Option 3: Railway or Render

- Use their Docker-based deployment
- Connect to cloud database
- Set environment variables as above

## Frontend Deployment

### Option 1: Netlify/Vercel (Static Hosting)

1. **Build and deploy**:
   - Upload frontend/ folder to Netlify
   - Set build command: none (static)
   - Publish directory: /

2. **Update API URL**:
   - In `registration.js`, change `API_BASE_URL` to your backend URL
   - Example: `const API_BASE_URL = 'https://your-backend.herokuapp.com/api';`

### Option 2: Serve from Backend

- Copy frontend files to `backend/src/main/resources/static/`
- Update API calls to relative: `const API_BASE_URL = '/api';`

## Database Setup

### Production MySQL
- Use AWS RDS, Google Cloud SQL, or PlanetScale
- Create database: `pet_adoption_prod`
- Set connection URL in environment variables

### Environment Variables
```bash
DATABASE_URL=jdbc:mysql://host:port/db?sslmode=require
DB_USERNAME=your_db_user
DB_PASSWORD=your_db_password
JWT_SECRET=your_secure_jwt_secret
SPRING_PROFILES_ACTIVE=prod
```

## Security Checklist

- [ ] Change default passwords
- [ ] Use strong JWT secret
- [ ] Enable SSL/HTTPS
- [ ] Configure CORS for production domain
- [ ] Set up proper authentication (currently basic auth for testing)
- [ ] Enable database SSL
- [ ] Set up monitoring/logging

## Testing Deployment

1. **API Health**: `GET https://your-domain/api/actuator/health`
2. **User Registration**: Test signup flow
3. **Database**: Verify data persistence
4. **Frontend**: Check API calls work

## Troubleshooting

- **Port issues**: Ensure PORT environment variable is used
- **Database connection**: Check DATABASE_URL format
- **CORS errors**: Update allowed origins in AppConfig.java
- **Static files**: Ensure correct paths for frontend assets

## Cost Estimation

- **Backend**: ~$5-15/month (cloud hosting)
- **Database**: ~$10-30/month (managed MySQL)
- **Frontend**: Free (Netlify) or ~$5/month
- **Domain**: ~$10-20/year

## Support

For issues, check logs and ensure all environment variables are set correctly.