# Motorcycle Spare Parts E-commerce Website

A complete e-commerce solution for buying motorcycle spare parts, both brand new and used.

## Features

- User authentication and authorization (Admin and Customer roles)
- Product browsing and searching
- Product categorization (Brand new/Used, Engine, Body, Brake, Electric, Carburetor, Cooling System, etc.)
- Shopping cart functionality
- Wishlist management
- Online payment processing
- Order management
- Admin dashboard for product and order management
- Responsive design for all devices

## Technology Stack

- **Backend**: Spring Boot 3.2.3
- **Frontend**: Angular 17
- **Database**: MySQL
- **Payment Gateway**: Stripe

## Project Structure

The project is divided into two main parts:

### Backend (Spring Boot)

- RESTful API endpoints
- JWT-based authentication
- Database integration with JPA/Hibernate
- Email notifications
- Payment processing

### Frontend (Angular)

- Responsive UI with Bootstrap 5
- State management with RxJS
- Form validation
- Payment integration

## Getting Started

### Prerequisites

- Java 17
- Node.js and npm
- MySQL
- Angular CLI

### Backend Setup

1. Navigate to the backend directory:
   ```
   cd motorcycle-parts-ecommerce/backend
   ```

2. Build the project:
   ```
   ./mvnw clean install
   ```

3. Run the application:
   ```
   ./mvnw spring-boot:run
   ```

The backend server will start on http://localhost:8080.

### Frontend Setup

1. Navigate to the frontend directory:
   ```
   cd motorcycle-parts-ecommerce/frontend
   ```

2. Install dependencies:
   ```
   npm install
   ```

3. Run the development server:
   ```
   ng serve
   ```

The frontend application will be available at http://localhost:4200.

## API Documentation

API documentation is available at http://localhost:8080/swagger-ui.html when the backend is running.

## License

This project is licensed under the MIT License. 