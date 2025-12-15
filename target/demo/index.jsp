<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Smart Meeting Room Booking</title>
    <link rel="stylesheet" href="css/styles.css">
    <style>
        .welcome-container {
            max-width: 600px;
            margin: 100px auto;
            text-align: center;
            padding: 40px;
        }
        .welcome-container h1 {
            color: #61dafb;
            margin-bottom: 20px;
            font-size: 2.5em;
        }
        .welcome-container p {
            color: #ccc;
            font-size: 1.2em;
            margin-bottom: 30px;
        }
        .action-buttons {
            display: flex;
            gap: 20px;
            justify-content: center;
            flex-wrap: wrap;
        }
        .action-btn {
            padding: 15px 30px;
            font-size: 1.1em;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            text-decoration: none;
            border-radius: 8px;
            transition: transform 0.2s, box-shadow 0.2s;
            display: inline-block;
        }
        .action-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 20px rgba(102, 126, 234, 0.4);
        }
        .action-btn.secondary {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
        }
    </style>
</head>
<body>
    <div class="welcome-container">
        <h1>🏢 Smart Meeting Room Booking</h1>
        <p>Welcome to the meeting room management system. Book rooms and manage your bookings efficiently.</p>
        <div class="action-buttons">
            <a href="bookRoom.jsp" class="action-btn">📅 Book a Room</a>
            <a href="viewBookings" class="action-btn secondary">👁️ View Bookings</a>
        </div>
    </div>
</body>
</html>
