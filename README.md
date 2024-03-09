
<h1 align="center" style="font-weight: bold;">Task Manager API 💻</h1>

<div align="center" style="display: flex; justify-content: center;">
  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="java">
  <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="spring">
  <img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white" alt="mysql">
  <img src="https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white" alt="AWS">
</div>


<p align="center">
 <a href="#started">Getting Started</a> • 
 <a href="#doc">Endpoints Documentation</a> •
  <a href="#routes">API Endpoints</a> •
 <a href="#contribute">Contribute</a>
</p>

<p align="center">
  <b>A REST API for managing personal tasks.</b>
</p>

## 🚀 Getting started

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

Use the `application.properties.example` as reference to create your configuration file `application.properties` with your AWS Credentials. 

```yaml
aws.credentials.accessKey= ${YOUR_AWS_KEY}
aws.credentials.secretKey= ${YOUR_AWS_SECRET}
```

### Starting

Start your project:

```bash
cd /path/to/your/project
mvn spring-boot:run

<h2 id="doc">📍 API Endpoints</h2>
