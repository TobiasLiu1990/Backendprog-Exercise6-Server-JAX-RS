package exercise6.server;

import jakarta.json.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mysql.jdbc.JDBCManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

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
    public Response addItem(String body) throws IOException {
        JSONArray jsonArray = new JSONArray("[" + body + "]");

        //Bad fix, body.length() is always 36 in this case.
        if (body.length() > 36) {
            String itemName = "";
            int price = 0;
            String category = "0";

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                itemName = obj.getString("item");
                price = obj.getInt("price");            //Need to always receive a number.
                category = obj.getString("category");   //Need to change to menu that shows options.
            }

            var item = new Item(itemName, price, category);
            System.out.println(item);
//            jdbcManager.addItemToDatabase(item);
        } else {
            return null;
        }
        return Response.ok().build();
    }
}
