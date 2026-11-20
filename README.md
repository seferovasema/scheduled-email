# Scheduled Email System

A backend application built with **Java and Spring Boot** that allows users to schedule emails for future delivery. The system automatically processes scheduled emails, sends them using SMTP, and manages delivery statuses and retry attempts.

## Overview

The Scheduled Email System is designed to automate email delivery at predefined times.

Instead of sending emails immediately, users can schedule them for a specific date and time. A background scheduler periodically checks for pending emails and processes them automatically.

The application also includes retry handling and email processing management to improve reliability.

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Gradle
- JavaMailSender
- Jakarta Validation
- Lombok
- REST API
- Maven-compatible Gradle Wrapper

## Features

- Schedule emails for future delivery.
- Automatically process scheduled emails using Spring Scheduler.
- Send emails through Gmail SMTP.
- Manage email delivery statuses.
- Track email processing information.
- Retry failed email deliveries.
- Configure maximum retry attempts.
- Handle email processing timeouts.
- Validate incoming requests.
- Handle exceptions with a global exception handler.
- Persist email information in PostgreSQL.

## Email Statuses

The application uses the following email statuses:

| Status | Description |
|---|---|
| PENDING | Email is waiting to be processed. |
| PROCESSING | Email is currently being processed. |
| SENT | Email was successfully sent. |
| FAILED | Email delivery failed. |

## Project Structure

```text
src/main/java/com/sema/scheduledemail
│
├── controller
│   └── EmailController.java
│
├── dto
│   ├── request
│   │   └── EmailRequest.java
│   └── response
│       └── EmailResponse.java
│
├── entity
│   └── Email.java
│
├── enums
│   └── EmailStatus.java
│
├── exception
│   ├── EmailNotFoundException.java
│   ├── ErrorResponse.java
│   └── GlobalExceptionHandler.java
│
├── mapper
│   └── EmailMapper.java
│
├── repository
│   └── EmailRepository.java
│
├── scheduler
│   └── EmailScheduler.java
│
└── service
    ├── EmailClaimService.java
    ├── EmailService.java
    └── impl
        └── EmailServiceImpl.java
```

## Configuration

The application uses environment variables to keep database and email credentials outside the application configuration file.

Configure the following environment variables before running the application:

| Variable | Description |
|---|---|
| DB_URL | PostgreSQL database URL |
| DB_USERNAME | Database username |
| DB_PASSWORD | Database password |
| MAIL_USERNAME | Gmail email address |
| MAIL_PASSWORD | Gmail App Password |

### Gmail Configuration

To send emails through Gmail SMTP:

1. Enable 2-Step Verification in your Google Account.
2. Generate a Google App Password.
3. Use your Gmail address as `MAIL_USERNAME`.
4. Use the generated App Password as `MAIL_PASSWORD`.

Never commit real credentials or passwords to GitHub.

## Running the Application

### Prerequisites

- Java 17 or compatible version required by the project.
- PostgreSQL.
- Gmail account with an App Password.

### 1. Clone the Repository

```bash
git clone https://github.com/seferovasema/scheduled-email.git
```

### 2. Navigate to the Project

```bash
cd scheduled-email
```

### 3. Configure Environment Variables

For Windows PowerShell:

```powershell
$env:DB_URL="jdbc:postgresql://127.0.0.1:5433/scheduled_email"
$env:DB_USERNAME="email_user"
$env:DB_PASSWORD="your_database_password"

$env:MAIL_USERNAME="your_email@gmail.com"
$env:MAIL_PASSWORD="your_gmail_app_password"
```

Make sure PostgreSQL is running and the database exists.

### 4. Run the Application

```bash
.\gradlew bootRun
```

The application will start on:

```text
http://localhost:8080
```

## Scheduler Configuration

The email scheduler is configurable through application properties.

| Property | Default Value | Description |
|---|---|---|
| cron | `*/10 * * * * *` | Scheduler runs every 10 seconds. |
| processing-timeout-minutes | `15` | Processing timeout. |
| max-retries | `3` | Maximum retry attempts. |
| batch-size | `10` | Maximum emails processed per batch. |

## Database

PostgreSQL is used to persist scheduled email information, including:

- Recipient
- Subject
- Email body
- Scheduled date and time
- Delivery status
- Sent date and time
- Retry count
- Error message
- Processing information

## Future Improvements

- JWT-based authentication and authorization.
- Email templates.
- Redis integration.
- Message queue integration with RabbitMQ or Kafka.
- Monitoring and metrics with Spring Boot Actuator.
- Unit and integration testing.

## Author

**Sema Seferova**

GitHub: [@seferovasema](https://github.com/seferovasema)

---

*Built as a backend development project to practice Spring Boot, REST APIs, database management, scheduling, and email automation.*
