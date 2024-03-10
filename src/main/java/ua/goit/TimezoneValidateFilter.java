package ua.goit;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.ZoneId;
import java.time.zone.ZoneRulesException;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req,
                            HttpServletResponse res,
                            FilterChain chain) throws IOException, ServletException {
        String timeZone = req.getParameter("timezone");
        String correctTimeZone = "";

        if (timeZone != null) {
            correctTimeZone = timeZone.replace(" ", "+");
        } else {
            chain.doFilter(req, res);
        }

        try {
            ZoneId.of(correctTimeZone);
        } catch (Exception ex) {
            res.setStatus(400);
            res.setContentType("text/html; charset=utf-8");
            res.getWriter().write("Invalid timezone");
        }
        chain.doFilter(req, res);
    }
}
//ZoneRulesException

