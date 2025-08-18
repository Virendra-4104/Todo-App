# To-Do List Application

A full-stack to-do list application with a Spring framework for backend and React.js for frontend.  
Users can create an account, log in securely, and manage their personal to-do list.

## Table of Contents
- Features
- Tech Stack
- Backend Project Structure
- API Endpoints
- Frontend Libraries

## Features
- **User Authentication**: Users can sign up and log in securely.
- **Personal To-Do List**: Each user’s tasks are private and tied to their account.
- **CRUD Operations**: Create, Read, Update, and Delete tasks.
- **Status Tracking**: Mark tasks as complete or incomplete.

## Tech Stack
- **Backend:** Java, Spring Boot, Spring Security ,Spring Data MongoDb, Maven.
- **Frontend:** React.js, React Router DOM, Axios, React Hot Toast.
- **Database:** MongoDB.
- **Tools:** Git, VS Code, Postman.

## Backend Project Structure
```
todoAppBackend/
├── src/
│ └── main/
│ ├── java/com/example/todoAppBackend/
│ │ ├── config/
│ │ │ └── SecurityConfig.java
│ │ ├── controller/
│ │ │ ├── TaskController.java
│ │ │ └── UserAuthController.java
│ │ ├── entity/
│ │ │ ├── Task.java
│ │ │ └── User.java
│ │ ├── filter/
│ │ │ └── JwtFilter.java
│ │ ├── repository/
│ │ │ ├── TaskRepository.java
│ │ │ └── UserRepository.java
│ │ ├── service/
│ │ │ ├── CustomUserDetailsService.java
│ │ │ ├── TaskService.java
│ │ │ └── UserService.java
│ │ ├── util/
│ │ │ └── JwtUtil.java
│ │ └── TodoApplication.java
│ └── resources/
│   └── application.properties
├── target/
└── pom.xml
```


## Backend API Endpoints

| Method   | Endpoint                                    | Description               |
|----------|---------------------------------------------|---------------------------|
| `POST`   | `/todo/user/sign-up`                        | Create account            |
| `POST`   | `/todo/user/log-in`                         | Login account             |
| `PUT`    | `/todo/user/{username}`                     | Update account            |
| `DELETE` | `/todo/user/{username}`                     | Delete account            |
| `POST`   | `/todo/task/{username}`                     | Create new task           |
| `GET`    | `/todo/task/{username}`                     | Get all tasks             |
| `PUT`    | `/todo/task/{username}/{taskId}`            | Update task               |
| `DELETE` | `/todo/task/{username}/{taskId}`            | Delete task               |

> 💡 **Note:**  
> Replace `{username}` with the actual username.  
> Example: `/todo/task/virendra`  
>  
> Replace `{taskId}` with the specific task's ID.  
> Example: `/todo/task/virendra/689f19b52f1070edc95989f1`

## Frontend Libraries
- **React.js** → Core library for building the user interface.
- **React Router DOM** → For navigation between pages.  
- **Axios** → For making HTTP requests to the backend APIs.  
- **React Hot Toast** → For showing success/error notifications.  

> 💡 **Note:**
>the frontend create with the help of ai. or say I only create this simple design or fix some code. and all code generate by chatgpt.