package exercise6.server;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import mysql.jdbc.JDBCManager;

import java.io.StringReader;

@Path("/items")
public class ItemEndPoint {

    private final JDBCManager jdbcManager = new JDBCManager();

    @GET
    public Response listItems() {
        JsonArrayBuilder result = Json.createArrayBuilder();
        int entries = jdbcManager.countAllEntriesInDatabase();

        for (int i = 1; i <= entries; i++) {
            Item tempItem = jdbcManager.getItemFromDatabase(i);

            result.add(Json.createObjectBuilder()
                    .add("name", tempItem.getName())
                    .add("price", tempItem.getPrice())
                    .add("category", tempItem.getCategory())
            );
        }
        return Response.ok(result.build().toString()).build();
    }

    @POST
    public Response addItem() {
        return null;
    }
}
