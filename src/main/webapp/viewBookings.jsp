<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bookings</title>
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
</head>
<body>

<%
    String selectedDate = (String) request.getAttribute("selectedDate");
%>

<div class="app">
    <div class="header">
        <div class="title"><%= (selectedDate != null && !selectedDate.isEmpty()) ? "Bookings for " + selectedDate : "Upcoming Meeting Room Bookings" %></div>
        <div class="nav">
            <a href="bookRoom.jsp">Book Room</a>
            <!-- <a href="success.jsp">Success Page</a> -->
        </div>
    </div>
    <hr class="sep"/>

    <form action="viewBookings" method="get" class="form" style="grid-template-columns: 1fr auto auto; align-items:center;">
        <div>
            <div class="label">Choose a date</div>
            <input type="text" id="filterDate" name="date" class="date" value="<%= selectedDate != null ? selectedDate : "" %>">
        </div>
        <button type="submit" class="button">Filter</button>
        <a href="viewBookings" class="button secondary">Show All</a>
    </form>

    <br>

    <table class="table">
    <thead>
        <tr>
            <th>Date</th>
            <th>Room</th>
            <th>Time Slot</th>
            <th>Booked By</th>
        </tr>
    </thead>
    <tbody>

<%
    List<String[]> list = (List<String[]>) request.getAttribute("bookings");
    if (list != null && !list.isEmpty()) {
        for (String[] b : list) {
%>
<tr>
    <td><%= b[0] %></td>
    <td><%= b[1] %></td>
    <td><%= b[2] %></td>
    <td><%= b[3] %></td>
</tr>
<%
        }
    } else {
%>
<tr>
    <td colspan="4">No bookings found</td>
</tr>
<%
    }
%>

    </tbody>
    </table>

    <div class="footer">Smart Meeting Room v1.0</div>
</div>

<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script>
  // Initialize Flatpickr for filter date
  document.addEventListener('DOMContentLoaded', function() {
    flatpickr('#filterDate', {
      dateFormat: 'Y-m-d',
      minDate: 'today',
      allowInput: false
    });
  });
</script>
</body>
</html>
