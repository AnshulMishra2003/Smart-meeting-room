# Smart Meeting Room Booking

Deployment is completed and the app is live.

**Overview**
- Servlet/JSP app on Tomcat 10.1 (Jakarta Servlet 6)
- PostgreSQL database (Neon/Railway). JDBC via env vars
- Dockerized service, suitable for Render deployment

**Live Endpoints**
- `/` – Welcome page
- `/bookRoom.jsp` – Book a room
- `/viewBookings` – View bookings (optionally filter by `date`)

**Environment Variables**
- `DB_URL`: `jdbc:postgresql://<host>:5432/<db>?sslmode=require`
- `DB_USER`: Database username
- `DB_PASS`: Database password

**Local Build & Run**
```bash
mvn clean package -DskipTests
docker build -t smart-meeting-room:latest .
docker run -p 8080:8080 \
	-e DB_URL="jdbc:postgresql://<host>:5432/<db>?sslmode=require" \
	-e DB_USER="<user>" -e DB_PASS="<pass>" \
	smart-meeting-room:latest
```

**Render Deployment Notes**
- Connect repo and choose “Docker” for the service
- Set env vars (`DB_URL`, `DB_USER`, `DB_PASS`)
- Health check path can be `/` or `/bookRoom.jsp`

**Tech Stack**
- JDK 25, Tomcat 10.1.28
- Maven WAR packaging
- Dependencies: `jakarta.servlet-api`, `jakarta.servlet.jsp-api`, `org.postgresql`

**Troubleshooting**
- If logs show shutdown command warnings, it’s disabled via server.xml (`port=-1`)
- 404 on `/` usually means redeploy pending; ensure latest commit is deployed
- Database connectivity errors: verify `DB_URL`, `DB_USER`, `DB_PASS` and that schema exists

**Schema**
```sql
CREATE TABLE IF NOT EXISTS room_bookings (
	id SERIAL PRIMARY KEY,
	room_name VARCHAR(100) NOT NULL,
	booking_date DATE NOT NULL,
	time_slot VARCHAR(20) NOT NULL,
	booked_by VARCHAR(100) NOT NULL
);
```

**Project Structure**
- `src/main/java/com/example/meeting/` – Servlets + DB connection
- `src/main/webapp/` – JSP views, index, web.xml
- `Dockerfile` – Tomcat on JDK 25; deploys WAR
- `pom.xml` – Maven config and dependencies

**License**
- Internal project. No external license declared.