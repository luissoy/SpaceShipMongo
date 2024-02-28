# SpaceShipMongo API

## Project Information

### Project Structure
The project is a Spring Boot application that provides CRUD operations for managing spaceships from series and movies. It uses MongoDB as the database.

### Project Dependencies
- **Spring Boot Version:** 3.2.3
- **Java Version:** 21

### Swagger Documentation
The application includes Swagger documentation for the API. It is accessible at [/swagger](/swaggerl).

### Modules
- **Controller Module:** Handles HTTP requests and manages API endpoints.
- **Service Module:** Implements business logic and interacts with the database.
- **Model Module:** Defines the data model for spaceships.
- **Exception Controller Module:** Provides exception handling for the API.
- **Dockerfile:** Defines the Docker image for the application.
- **Docker-compose:** Sets up the MongoDB and MongoDB Express containers for development.

## How to Build and Run

### Prerequisites
- Java 21
- Docker

### Build and Run Steps

1. Clone the repository.
2. Navigate to the project root directory.

#### Build the Application
```bash
./mvnw clean install
```
The API will be accessible at http://localhost:8080/api/v1/spaceships.
#### Build and Run with Docker
```bash
docker-compose up
```
This command builds the Docker image and starts the application along with MongoDB and MongoDB Express.

The API will be accessible at http://localhost:10001/api/v1/spaceships.

## API Endpoints

### Spaceships

#### Get All Spaceships
- **URL:** `/api/v1/spaceships`
- **Method:** GET
- **Parameters:** None
- **Response:** List of spaceships.

#### Get Spaceship by ID
- **URL:** `/api/v1/spaceships/{id}`
- **Method:** GET
- **Parameters:** `id` - ID of the spaceship
- **Response:** Details of the specified spaceship.

#### Get Spaceships by Name
- **URL:** `/api/v1/spaceships/names/{name}`
- **Method:** GET
- **Parameters:** `name` - Name of the spaceship
- **Response:** List of spaceships with the specified name.

#### Save Spaceship
- **URL:** `/api/v1/spaceships`
- **Method:** POST
- **Request Body:** SpaceshipDto (JSON)
- **Response:** Details of the saved spaceship.

#### Update Spaceship
- **URL:** `/api/v1/spaceships/{id}`
- **Method:** PUT
- **Parameters:** `id` - ID of the spaceship
- **Request Body:** SpaceshipDto (JSON)
- **Response:** Details of the updated spaceship.

#### Delete Spaceship
- **URL:** `/api/v1/spaceships/{id}`
- **Method:** DELETE
- **Parameters:** `id` - ID of the spaceship
- **Response:** No content if successful.

### Exception Handling
The application includes exception handling for the following scenarios:
- DataNotFoundException: Returns a 404 status code.
- DataAutoIdException: Returns a 500 status code for auto-generated ID exceptions.
- General Exception: Returns a 500 status code for other unhandled exceptions.

### MongoDB Configuration
The application uses MongoDB as the database. It is configured with the following parameters:
- **Host:** `mongo`
- **Port:** `27017`
- **Database:** `spaceship_mongo_github`
- **Username:** `luis`
- **Password:** `luissoy`

### Docker-compose Configuration
The Docker-compose file sets up three containers: MongoDB, MongoDB Express, and the application.
- **MongoDB:**
    - Container Name: `mongodb`
    - Port: `27017`
    - Username: `luis`
    - Password: `luissoy`
- **MongoDB Express:**
    - Container Name: `mongo-express`
    - Port: `10000` ([http://localhost:10000](http://localhost:10000))
    - Username: `luis`
    - Password: `luissoy`
- **Application:**
    - Container Name: `app`
    - Port: `10001` ([http://localhost:10001](http://localhost:10001))
    - MongoDB Connection: `mongo:27017`
    - Database: `spaceship_mongo_github`
    - Username: `luis`
    - Password: `luissoy`
