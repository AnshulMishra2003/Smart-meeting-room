package com.example.meeting;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bookRoom")
public class BookRoomServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String room = req.getParameter("room");
        String date = req.getParameter("date");
        String slot = req.getParameter("slot");
        String bookedBy = req.getParameter("bookedBy");

        try (Connection con = DBConnection.getConnection()) {

            // Server-side validation: prevent booking for past dates
            try {
                LocalDate selected = LocalDate.parse(date);
                if (selected.isBefore(LocalDate.now())) {
                    req.setAttribute("error", "Cannot book a past date. Please choose today or a future date.");
                    req.getRequestDispatcher("bookRoom.jsp").forward(req, res);
                    return;
                }
                
                // Validate time slot for today's bookings
                if (selected.isEqual(LocalDate.now())) {
                    int slotHour = parseSlotHour(slot);
                    int currentHour = LocalTime.now().getHour();
                    
                    if (slotHour < currentHour) {
                        req.setAttribute("error", "Cannot book a past time slot. Please choose a current or future time.");
                        req.getRequestDispatcher("bookRoom.jsp").forward(req, res);
                        return;
                    }
                }
            } catch (Exception pe) {
                req.setAttribute("error", "Invalid date or time slot format.");
                req.getRequestDispatcher("bookRoom.jsp").forward(req, res);
                return;
            }

            String sql =
                "INSERT INTO room_bookings (room_name, booking_date, time_slot, booked_by) " +
                "VALUES (?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, room);
            // Bind as SQL Date to avoid implicit cast issues
            ps.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.parse(date)));
            ps.setString(3, slot);
            ps.setString(4, bookedBy);

            ps.executeUpdate();
            res.sendRedirect("success.jsp");

        } catch (SQLIntegrityConstraintViolationException e) {
            req.setAttribute("error", "Room already booked for this slot!");
            req.getRequestDispatcher("bookRoom.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("Server Error");
        }
    }
    
    // Helper: extract 24h start hour from slot label, e.g. "1PM-2PM" -> 13
    private int parseSlotHour(String slot) {
        if (slot == null || slot.isEmpty()) {
            throw new IllegalArgumentException("Invalid slot");
        }
        String start = slot.split("-")[0].trim().toUpperCase();
        // Expect formats like "9AM", "12PM"
        java.util.regex.Matcher m = java.util.regex.Pattern
                .compile("^(1[0-2]|0?[1-9])(AM|PM)$")
                .matcher(start);
        if (!m.find()) {
            throw new IllegalArgumentException("Unrecognized slot format: " + slot);
        }
        int hour = Integer.parseInt(m.group(1));
        String meridiem = m.group(2);
        if ("AM".equals(meridiem)) {
            if (hour == 12) hour = 0; // 12AM -> 0
        } else { // PM
            if (hour != 12) hour += 12; // 1PM..11PM -> 13..23; 12PM stays 12
        }
        return hour;
    }
}
