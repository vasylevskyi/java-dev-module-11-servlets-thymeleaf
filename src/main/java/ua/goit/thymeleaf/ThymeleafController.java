package ua.goit.thymeleaf;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import ua.goit.TimeZoneConverter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/thymeleaf")
public class ThymeleafController extends HttpServlet {
    private TemplateEngine engine;

    public void init() throws ServletException {
        engine = new TemplateEngine();

        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("D:/GoIT/GitHub/java-dev-module-11-servlets-thymeleaf/src/main/resources/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);

        engine.addTemplateResolver(resolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String timeZone = req.getParameter("timezone");
        String convertedDateTime;
        String correctTimeZone;
        String lastTimeZone;
        Context context;
        resp.setContentType("text/html; charset=utf-8");

        try {
            Map<String, String> cookieMap =
                    Arrays.stream(req.getCookies())
                            .collect(Collectors.toMap(Cookie::getName, Cookie::getValue));

            lastTimeZone = cookieMap.get("lastTimezone").toString();
        } catch (NullPointerException ex) {
            lastTimeZone = null;
        }

        if (timeZone == null && lastTimeZone  == null) {
            convertedDateTime = new TimeZoneConverter().formatDateTime("UTC");
            context = new Context(
                req.getLocale(),
                Map.of("time", convertedDateTime)
            );
            engine.process("time", context, resp.getWriter());
        } else if (timeZone == null && lastTimeZone  != null) {
            convertedDateTime = new TimeZoneConverter().formatDateTime(lastTimeZone);
            context = new Context(
                    req.getLocale(),
                    Map.of("timeZone", lastTimeZone, "timeWithZone", convertedDateTime)
            );
            engine.process("timezone", context, resp.getWriter());
        } else {
            correctTimeZone= timeZone.replace(" ", "+");
            convertedDateTime = new TimeZoneConverter().formatDateTime(correctTimeZone);
            context = new Context(
                    req.getLocale(),
                    Map.of("timeZone", correctTimeZone, "timeWithZone", convertedDateTime)
            );
            resp.setHeader("Set-Cookie", "lastTimezone=" + correctTimeZone);
            engine.process("timezone", context, resp.getWriter());
        }
        resp.getWriter().close();
    }
}
