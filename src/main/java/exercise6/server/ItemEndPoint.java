package exercise6.server;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mysql.jdbc.JDBCManager;

import java.io.StringReader;

@Path("/items")
public class ItemEndPoint {

//    @Inject
    private final JDBCManager jdbcManager = new JDBCManager();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listItems() {
        JsonArrayBuilder result = Json.createArrayBuilder();
        int entries = jdbcManager.countAllEntriesInDatabase();

        for (int i = 1; i <= entries; i++) {
            Item tempItem = jdbcManager.getItemFromDatabase(i);

            result.add(Json.createObjectBuilder()
                    .add("item", tempItem.getName())
                    .add("price", tempItem.getPrice())
                    .add("category", tempItem.getCategory())
            );
        }
        return Response.ok(result.build().toString()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addItem(String body) {
        System.out.println(body);
        JsonObject jsonItem = Json.createReader(new StringReader(body)).readObject();

        var item = new Item();

        //To put item in db first.
        String[] parts = body.split(":");
        int secondPart = 0;

        switch (secondPart) {
            case 0 -> item.setName(parts[0]);
            case 2 -> item.setPrice(Integer.parseInt(parts[2]));
            case 4 -> item.setCategory(parts[4]);
        }

//        switch (secondPart) {
//            case 0 -> System.out.println((parts[0]));
//            case 2 -> System.out.println((parts[2]));
//            case 4 -> System.out.println((parts[4]));
//        }

//        jdbcManager.addItemToDatabase(item);

        item.setName(jsonItem.getString("item"));
        item.setPrice(jsonItem.getInt("price"));
        item.setCategory(jsonItem.getString("category"));

        return Response.ok().build();
    }
}
