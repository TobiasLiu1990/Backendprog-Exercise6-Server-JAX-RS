package exercise6.server;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysql.jdbc.JDBCManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ListItemServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ListItemServlet.class);
    private final JDBCManager jdbcManager;

    public ListItemServlet(JDBCManager jdbcManager) {
        this.jdbcManager = jdbcManager;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.getWriter().write("Here are the books at path: " + req.getPathInfo());

        //Use DB instead to find item
        int allItems = jdbcManager.countAllEntriesInDatabase();


        //Lecture 6 - JSON
        JsonArrayBuilder result = Json.createArrayBuilder();
        for (int i = 1; i <= allItems; i++) {
            Item tempItem = jdbcManager.getItemFromDatabase(i);

            result.add(Json.createObjectBuilder()
                    .add("name", tempItem.getName())
                    .add("price", tempItem.getPrice())
                    .add("category", tempItem.getCategory())
            );
        }

        if ("application/json".equals(req.getHeader("Accept"))) {
            resp.getWriter().write(result.build().toString());
        } else {
            resp.getWriter().write("<ul>");
            for (int i = 1; i <= allItems; i++) {
                Item tempItem = jdbcManager.getItemFromDatabase(i);

                resp.getWriter().write("<li>" + tempItem + "</li>");
                resp.getWriter().write("<hr>");
            }
            resp.getWriter().write("</ul>");
        }

        logger.info("Should list all items");
    }
}
