# 🏢 Smart Meeting Room Booking System

**Deployment Status:** ✅ Live
**Hosting Platform:** Render (Docker – Free Tier)

> ⏳ **Important:** The application is deployed on **Render Free Tier**.
> If the app is idle, the first request may take **30–60 seconds** due to cold start. This is expected behavior.

---

## 📌 Overview

The **Smart Meeting Room Booking System** is a Java web application built using **Servlets and JSP**, designed to manage and view meeting room bookings efficiently.

The application is:

* Built on **Jakarta Servlet 6**
* Deployed on **Apache Tomcat 10.1**
* Connected to a **PostgreSQL database**
* **Dockerized** for cloud and local deployment
* Hosted on **Render**

---

## 🌐 Live Endpoints

| Endpoint        | Description                              |
| --------------- | ---------------------------------------- |
| `/`             | Welcome / Home page                      |
| `/bookRoom.jsp` | Book a meeting room                      |
| `/viewBookings` | View all bookings (optional date filter) |

---

## 🧰 Tech Stack

* **Java:** JDK 25
* **Server:** Apache Tomcat 10.1.28
* **Framework:** Jakarta Servlet 6, JSP
* **Build Tool:** Maven (WAR packaging)
* **Database:** PostgreSQL (Neon / Railway / Local)
* **Containerization:** Docker
* **Cloud Platform:** Render

### Key Dependencies

* `jakarta.servlet-api`
* `jakarta.servlet.jsp-api`
* `org.postgresql`

---

## 🔐 Environment Variables

The application uses environment variables for secure database access:

```text
DB_URL  = jdbc:postgresql://<host>:5432/<db>?sslmode=require
DB_USER = <database_username>
DB_PASS = <database_password>
```

✔ No hardcoded credentials
✔ Cloud-ready configuration

---

## 🚀 Render Deployment Notes

* Connect the GitHub repository to Render
* Select **Docker** as the service type
* Configure environment variables:

  * `DB_URL`
  * `DB_USER`
  * `DB_PASS`
* Health check path:

  * `/` or `/bookRoom.jsp`

### Free Tier Behavior

* Service may **sleep when inactive**
* First request may be slow (cold start)
* Automatic restarts are normal

---

## 🖥️ Local Deployment (Step-by-Step)

You can run the application locally using **Docker** or **Tomcat**.

---

### ✅ Prerequisites

Make sure the following are installed:

* JDK 25
* Maven 3.9+
* Docker
* PostgreSQL

Verify:

```bash
java -version
mvn -version
docker --version
```

---

## 🔧 Option 1: Local Deployment Using Docker (Recommended)

### 1️⃣ Build the WAR File

```bash
mvn clean package -DskipTests
```

WAR output:

```
target/smart-meeting-room.war
```

---

### 2️⃣ Build Docker Image

```bash
docker build -t smart-meeting-room:latest .
```

---

### 3️⃣ Run Docker Container

```bash
docker run -p 8080:8080 \
  -e DB_URL="jdbc:postgresql://<host>:5432/<db>?sslmode=require" \
  -e DB_USER="<username>" \
  -e DB_PASS="<password>" \
  smart-meeting-room:latest
```

---

### 4️⃣ Access the Application

```
http://localhost:8080
```

---

## 🔧 Option 2: Local Deployment Using Tomcat (Without Docker)

### 1️⃣ Build WAR File

```bash
mvn clean package -DskipTests
```

---

### 2️⃣ Deploy WAR to Tomcat

Copy:

```
target/smart-meeting-room.war
```

to:

```
<TOMCAT_HOME>/webapps/
```

---

### 3️⃣ Set Environment Variables

#### Windows (PowerShell)

```powershell
setx DB_URL "jdbc:postgresql://<host>:5432/<db>?sslmode=require"
setx DB_USER "<username>"
setx DB_PASS "<password>"
```

#### Linux / macOS

```bash
export DB_URL="jdbc:postgresql://<host>:5432/<db>?sslmode=require"
export DB_USER="<username>"
export DB_PASS="<password>"
```

---

### 4️⃣ Start Tomcat

**Windows**

```bash
startup.bat
```

**Linux / macOS**

```bash
./startup.sh
```

---

### 5️⃣ Access the Application

```
http://localhost:8080/smart-meeting-room/
```

---

## 🗄️ Database Setup (PostgreSQL)

### Create Database

```sql
CREATE DATABASE meeting_db;
```

### Create Table

```sql
CREATE TABLE IF NOT EXISTS room_bookings (
    id SERIAL PRIMARY KEY,
    room_name VARCHAR(100) NOT NULL,
    booking_date DATE NOT NULL,
    time_slot VARCHAR(20) NOT NULL,
    booked_by VARCHAR(100) NOT NULL
);
```

---

## 🛠 Troubleshooting

### ❗ Tomcat Shutdown Warning

```
shutdown command is disabled
```

✔ Expected (disabled in `server.xml` with port = -1)

---

### ❗ 404 Error on `/`

* Ensure latest commit is deployed
* Confirm WAR is deployed successfully
* Restart the service if needed

---

### ❗ Database Connection Errors

* Verify `DB_URL`, `DB_USER`, `DB_PASS`
* Ensure database is running
* Confirm schema exists

---

### ❗ Port 8080 Already in Use

```bash
netstat -ano | findstr :8080
```

Stop conflicting service or change Tomcat port.

---

## 📁 Project Structure

```text
src/
 └── main/
     ├── java/com/example/meeting/   # Servlets & DB logic
     └── webapp/                     # JSP views, index.jsp, web.xml
Dockerfile                           # Tomcat + JDK 25
pom.xml                              # Maven configuration
```

---

## 📜 License

This is an **internal learning and demonstration project**.
No external license is declared.

---

## ⭐ Key Highlights

* Dockerized Java Servlet application
* Environment-variable-based configuration
* PostgreSQL integration using JDBC
* Cloud deployment on Render
* Clean separation of concerns (Servlets + JSP)

