package com.example.meeting;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/viewBookings")
public class ViewBookingsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        List<String[]> bookings = new ArrayList<>();
        String selectedDate = req.getParameter("date");
        java.time.LocalDate filterDate = null;
        if (selectedDate != null && !selectedDate.isEmpty()) {
            try {
                filterDate = java.time.LocalDate.parse(selectedDate);
            } catch (Exception pe) {
                req.setAttribute("error", "Invalid date format. Please use YYYY-MM-DD.");
                req.setAttribute("bookings", bookings);
                req.setAttribute("selectedDate", selectedDate);
                req.getRequestDispatcher("viewBookings.jsp").forward(req, res);
                return;
            }
        }

        try (Connection con = DBConnection.getConnection()) {

            // Cleanup: remove bookings older than 7 days from today
                try (PreparedStatement clean = con.prepareStatement(
                    "DELETE FROM room_bookings WHERE booking_date < CURRENT_DATE - INTERVAL '7 days'")) {
                clean.executeUpdate();
            }

            String sql;
            PreparedStatement ps;

            if (filterDate != null) {
                sql = "SELECT booking_date, room_name, time_slot, booked_by " +
                        "FROM room_bookings WHERE booking_date = ? " +
                        "ORDER BY booking_date, time_slot";
                ps = con.prepareStatement(sql);
                ps.setDate(1, java.sql.Date.valueOf(filterDate));
            } else {
                sql = "SELECT booking_date, room_name, time_slot, booked_by " +
                        "FROM room_bookings WHERE booking_date >= CURRENT_DATE " +
                        "ORDER BY booking_date ASC, time_slot";
                ps = con.prepareStatement(sql);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                bookings.add(new String[]{
                    rs.getString("booking_date"),
                    rs.getString("room_name"),
                    rs.getString("time_slot"),
                    rs.getString("booked_by")
                });
            }

            req.setAttribute("bookings", bookings);
            req.setAttribute("selectedDate", selectedDate);
            req.getRequestDispatcher("viewBookings.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Error loading bookings. Please try again.");
            req.setAttribute("bookings", bookings);
            req.setAttribute("selectedDate", selectedDate);
            req.getRequestDispatcher("viewBookings.jsp").forward(req, res);
        }
    }
}
