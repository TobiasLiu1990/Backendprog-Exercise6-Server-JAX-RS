package exercise6.server;

import jakarta.json.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mysql.jdbc.JDBCManager;

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
                    .add("itemName", tempItem.getItemName())
                    .add("price", tempItem.getPrice())
                    .add("category", tempItem.getCategory())
            );
        }
        return Response.ok(result.build().toString()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addItem(String body) {

        var item = new Item();

        //Need to find the 3 inputs/parse JSON to String/int then add to db.


        jdbcManager.addItemToDatabase(item);


        return Response.ok().build();
    }
}
