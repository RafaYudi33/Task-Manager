
<h1 align="center" style="font-weight: bold;">Task Manager API 💻</h1>



<div align="center" style="display: flex; justify-content: center;">
  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="java">
  <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="spring">
  <img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white" alt="mysql">
  <img src="https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white" alt="AWS">
</div>

<br> 
<p align="center">
 <a href="#started">Getting Started</a> • 
  <a href="#routes">API Endpoints</a> •
 <a href="#email">Simple Email Service</a>
</p>

<br>

<p align="center">
  <b>A REST API for managing personal tasks.</b>
</p>

<h2 id="started">🚀 Getting started</h2>

Here you describe how to run your project locally

### Prerequisites

Ensure you have the following installed:

- Java 17 or higher
- Maven for dependency management

### Cloning

Clone the repository:

```bash
git clone your-project-url-in-github
```

### Environment Variables

Before running the application, make sure to set up the necessary environment variables. Use the provided `application.properties.example` file as a reference to create your configuration file named `application.properties` with your AWS credentials.

```yaml
aws.credentials.accessKey= ${YOUR_AWS_ACESS_KEY}
aws.credentials.secretKey= ${YOUR_AWS_SECRET_KEY}
```

### Starting

Start your project:

```bash
cd /path/to/your/project
mvn spring-boot:run
```

<br>

<h2 id="routes">📍 API Endpoints</h2>
<p style="font-style: italic; color: #666666;">Before making any requests to an endpoint, it is necessary to run the application and consult the documentation generated by Swagger. This can be done through the browser by accessing the link <a href="http://localhost:8080/swagger-ui/index.html#" style="color: #007bff; text-decoration: none;">http://localhost:8080/swagger-ui/index.html#</a>.</p>

<p style="color: #333333;">The documentation provides important information, including details on which authorization header to use for requests.</p>

<div>
<h3>Users Endpoints:</h3>

  | Endpoint             | Description                                         
  |----------------------|-----------------------------------------------------
  | <kbd>POST /users/v1/register</kbd>     | <kbd>Register a user by passing in JSON or XML</kbd>
  | <kbd>DELETE /users/v1/id</kbd> | <kbd>Delete a user by passing user ID </kbd>
  | <kbd>DELETE /users/v1/login</kbd> | <kbd>Login a user by passing your credentials </kbd>

</div>



<div>
<h3>Tasks Endpoints:</h3>
  
| Endpoint                       | Description                                          
|--------------------------------|-----------------------------------------------------
| <kbd>GET /tasks/v1/:id</kbd>   | <kbd>Find a task by ID</kbd>                       
| <kbd>PUT /tasks/v1/:id</kbd>   | <kbd>Update a task by passing changes in JSON or XML (only the fields to be updated)</kbd>
| <kbd>DELETE /tasks/v1/:id</kbd>| <kbd>Delete a task by passing task ID</kbd>
| <kbd>GET /tasks/v1/</kbd>      | <kbd>Find all tasks from a specific user (user is retrieved through the authentication header)</kbd>
| <kbd>POST /tasks/v1/</kbd>     | <kbd>Create a task by passing JSON or XML</kbd>

</div>
<br>

<h2 id="email">📧 Simple Email Service</h2>

The application uses Amazon's Simple Email Service (SES) for sending emails. Since this is a personal project, I used the free tier of SES. In this version, it's necessary to verify the email addresses that will be used to send or receive emails.

The application is configured to invoke the service every day at 18:00. However, to allow users to test immediately, I have enabled an endpoint <kbd>POST /email</kbd>.

