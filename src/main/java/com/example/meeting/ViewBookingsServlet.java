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

        try (Connection con = DBConnection.getConnection()) {

            // Cleanup: remove bookings older than 7 days from today
            try (PreparedStatement clean = con.prepareStatement(
                    "DELETE FROM room_bookings WHERE booking_date < DATE_SUB(CURDATE(), INTERVAL 7 DAY)")) {
                clean.executeUpdate();
            }

            String sql;
            PreparedStatement ps;

            if (selectedDate != null && !selectedDate.isEmpty()) {
                sql = "SELECT booking_date, room_name, time_slot, booked_by " +
                      "FROM room_bookings WHERE booking_date = ? AND booking_date >= CURDATE() " +
                      "ORDER BY booking_date, time_slot";
                ps = con.prepareStatement(sql);
                ps.setString(1, selectedDate);
            } else {
                sql = "SELECT booking_date, room_name, time_slot, booked_by " +
                      "FROM room_bookings WHERE booking_date >= CURDATE() " +
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
            res.getWriter().println("Error loading bookings");
        }
    }
}
