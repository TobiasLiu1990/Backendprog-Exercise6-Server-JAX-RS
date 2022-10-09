package exercise6.server;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysql.jdbc.JDBCManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AddItemServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(AddItemServlet.class);
    private final JDBCManager jdbcManager;

    public AddItemServlet(JDBCManager jdbcManager) {
        this.jdbcManager = jdbcManager;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Get input from web
        String itemName = req.getParameter("itemName");
        int itemPrice = Integer.parseInt(req.getParameter("itemPrice"));
        String itemCategory = req.getParameter("itemCategory");

        //Save the input to item object.
        Item item = new Item(itemName, itemPrice, itemCategory);


        //DB (Can remove itemRepository if DB is added?)
        jdbcManager.addItemToDatabase(item);


        logger.info("\nAdded Item:" + item);
    }
}
