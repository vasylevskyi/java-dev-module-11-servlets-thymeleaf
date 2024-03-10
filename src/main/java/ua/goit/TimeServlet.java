package ua.goit;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String timeZone = req.getParameter("timezone");
        String convertedDateTime;
        resp.setContentType("text/html; charset=utf-8");

        if (timeZone == null) {
            convertedDateTime = new TimeZoneConverter().formatDateTime("UTC");
            resp.getWriter().write("<h1>Current time in UTC:</h1>");
        } else {
            String correctTimeZone = timeZone.replace(" ", "+");
            convertedDateTime = new TimeZoneConverter().formatDateTime(correctTimeZone);
            resp.getWriter().write("<h1>Current time in ${timeZone} zone:</h1>"
                    .replace("${timeZone}", correctTimeZone));
        }

        resp.getWriter().write("<br>");
        resp.getWriter().write(convertedDateTime);
        resp.getWriter().close();
    }
}
