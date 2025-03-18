# Secure Notes API
 - A RESTful API for managing personal notes with JWT authentication.
# Features
 - User registration and authentication
 - JWT-based security
 - CRUD operations for notes
 - User-specific note access control

# Folder Structure

```
src
├── main
│   ├── java
│   │   └── com.example.spring_boot_training
│   │        ├── config               
│   │        │   └── SecurityConfig.java
│   │        ├── controller           
             │   ├── AuthController.java
│   │        │   └── NoteController.java
│   │        ├── dto                   
│   │        │   ├── AuthRequest.java
│   │        │   ├── AuthResponse.java
│   │        │   ├── CreateNoteDto.java
             │   ├── GetNoteDto.java
│   │        │   ├── UpdateNoteDto.java
│   │        │   └── UserDto.java
│   │        ├── entities             
│   │        │   ├── Note.java
│   │        │   └── User.java
│   │        ├── exception             
│   │        │   └── ErrorException.java
             │   └── GlobalExceptionHandler.java
             │   └── NoteNotFoundException.java
│   │        ├── repository           
│   │        │   ├── NoteRepository.java
│   │        │   └── UserRepository.java
│   │        ├── security              
│   │        │   ├── JwtAuthenticationFilter.java
│   │        │   └── JwtService.java
│   │        ├── services            
│            │   ├── AuthService.java
│            │   ├── NoteService.java
│   │        │   ├── NoteServiceImpl.java
│   │        │   └── UserService.java
             └── SpringBootTrainingApplication.java      
│   └── resources
│       └── application.properties         
└── test
    └── java
    └── com.example.spring_boot_training
        └── NoteServiceImplTest.java
└───── docker-compose.yml
└───── Dockerfile


```


# Installation
1) Clone the repository:
```

https://github.com/rajatk3566/Spring_boot_Note_App.git

```
2) Configure database connection in application.properties:
```

spring.datasource.url=jdbc:mysql://localhost:3306/notesdb
spring.datasource.username=root
spring.datasource.password=password

```

3) Build the application:
```

mvn clean install

```

4) Run the application:
```

mvn spring-boot:run

```


# API Endpoints

- AuthController.java – Handles authentication endpoints
  - POST /auth/register – Register a new user
  - POST /auth/login – Authenticate user and generate JWT
- NoteController.java – Handles note CRUD endpoints
   - POST /api/notes – Create a new note
   - GET /api/notes/{id} – Get note by ID
   - GET /api/notes – Get all notes
   - PUT /api/notes/{id} – Update note by ID
   - PATCH /api/notes/{id} – Partially update a note 
   - DELETE /api/notes/{id} – Delete note by ID