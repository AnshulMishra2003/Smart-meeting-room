<%@ page import="java.time.LocalDate, java.time.LocalTime, java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book Meeting Room</title>
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <script>
        function updateTimeSlots() {
            const dateInput = document.querySelector('input[name="date"]');
            const slotSelect = document.querySelector('select[name="slot"]');
            const selectedDate = dateInput.value;
            const today = '<%= LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE) %>';
            // Use browser time to reflect user's local current hour
            const currentHour = new Date().getHours();
            
            if (selectedDate === today) {
                // Filter slots: disable past time slots for today
                Array.from(slotSelect.options).forEach(option => {
                    const slotHour = parseInt(option.getAttribute('data-hour'));
                    if (slotHour < currentHour) {
                        option.disabled = true;
                        option.style.color = '#ccc';
                    } else {
                        option.disabled = false;
                        option.style.color = '';
                    }
                });
                // Select first available slot
                for (let i = 0; i < slotSelect.options.length; i++) {
                    if (!slotSelect.options[i].disabled) {
                        slotSelect.selectedIndex = i;
                        break;
                    }
                }
            } else {
                // Enable all slots for future dates
                Array.from(slotSelect.options).forEach(option => {
                    option.disabled = false;
                    option.style.color = '';
                });
            }
        }
    </script>
</head>
<body>

<div class="app">
    <div class="header">
        <div class="title">Meeting Room Booking</div>
        <div class="nav">
            <a href="viewBookings">View Bookings</a>
            <!-- <a href="success.jsp">Success Page</a> -->
        </div>
    </div>
    <hr class="sep"/>

<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
    <p style="color:red;"><%= error %></p>
<%
    }
    
    String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
%>

<form action="bookRoom" method="post" class="form">
        <div>
            <div class="label">Name</div>
            <input type="text" name="bookedBy" class="input" required>
        </div>

        <div>
            <div class="label">Room</div>
            <select name="room" class="select">
        <option>Keymaker</option>
        <option>Trinity</option>
        <option>Agent Smith</option>
        <option>Morpheus</option>
        <option>Charvis</option>
            </select>
        </div>

        <div>
            <div class="label">Date</div>
            <input type="text" name="date" id="bookingDate" class="date" required>
        </div>

        <div>
            <div class="label">Time Slot</div>
            <select name="slot" id="timeSlot" class="select" required>
        <%
            // Generate slots from 9 AM to 7 PM
            String[] slotLabels = {
                "9AM-10AM", "10AM-11AM", "11AM-12PM", "12PM-1PM", 
                "1PM-2PM", "2PM-3PM", "3PM-4PM", "4PM-5PM", 
                "5PM-6PM", "6PM-7PM"
            };
            
            for (int i = 0; i < slotLabels.length; i++) {
                int slotHour = 9 + i;
                String disabled = ""; // initial render leaves all enabled; JS will disable past slots for today
        %>
                <option value="<%= slotLabels[i] %>" data-hour="<%= slotHour %>" <%= disabled %>><%= slotLabels[i] %></option>
        <%
            }
        %>
            </select>
        </div>

        <div>
            <button type="submit" class="button">Book Room</button>
            <a href="viewBookings" class="button secondary" style="margin-left:8px;">View Bookings</a>
        </div>
</form>

    <div class="footer">Smart Meeting Room v1.0</div>
</div>

<script>
    // Initialize after DOM ready
        document.addEventListener('DOMContentLoaded', updateTimeSlots);
</script>

<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script>
    // Initialize Flatpickr calendar for booking date
    document.addEventListener('DOMContentLoaded', function() {
        flatpickr('#bookingDate', {
            dateFormat: 'Y-m-d',
            minDate: 'today',
            allowInput: false,
            defaultDate: '<%= today %>',
            onReady: updateTimeSlots,
            onChange: updateTimeSlots
        });
    });
</script>

</body>
</html>
