package exercise6.server;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysql.jdbc.JDBCManager;
import java.io.IOException;

public class ListItemServlet extends HttpServlet {

    private final JDBCManager jdbcManager;

    public ListItemServlet(JDBCManager jdbcManager) {
        this.jdbcManager = jdbcManager;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //Use DB instead to find item
        int entries = jdbcManager.countAllEntriesInDatabase();

        //Lecture 6 - JSON
        JsonArrayBuilder result = Json.createArrayBuilder();
        for (int i = 1; i <= entries; i++) {
            Item tempItem = jdbcManager.getItemFromDatabase(i);

            result.add(Json.createObjectBuilder()
                    .add("name", tempItem.getName())
                    .add("price", tempItem.getPrice())
                    .add("category", tempItem.getCategory())
            );
        }
        resp.getWriter().write(result.build().toString());
    }
}
