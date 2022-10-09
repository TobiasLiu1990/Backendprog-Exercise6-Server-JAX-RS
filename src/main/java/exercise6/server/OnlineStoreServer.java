package exercise6.server;

import mysql.jdbc.JDBCManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnlineStoreServer {

    private final Server server = new Server(8080);
    private final Logger logger = LoggerFactory.getLogger(OnlineStoreServer.class);
    private final JDBCManager jdbcManager = new JDBCManager();


    public OnlineStoreServer() throws Exception {
        startServer();
    }

    public void startServer() throws Exception {
        //Instantiate a WebAbbContext to set a path to where to work from. Refers to target/classes by default.
        var targetResource = Resource.newClassPathResource("/webapp");
        var webApp = new WebAppContext(targetResource, "/");


        //Sets a different path to work from: src/main/resources.
        var sourceResources = Resource.newResource(targetResource.getFile().toString()
                .replace("target\\classes", "src\\main\\resources")
        );
        if (sourceResources.exists()) {
            webApp.setBaseResource(sourceResources);    // Change the default path to src/main/resources, if exist.
            webApp.setInitParameter(DefaultServlet.CONTEXT_INIT + "useFileMappedBuffer", "false");  //To avoid Jetty to lock files for changes.
        }


        //Adding Servlet - To handle the actual page.
        var addItemServlet = new ServletHolder(new AddItemServlet(jdbcManager)); //Send itemRepository to AddItemServlet to save inputs
        webApp.addServlet(addItemServlet, "/api/addItem");
        //Adding Servlet - To handle listing all items.
        var listItemServlet = new ServletHolder(new ListItemServlet(jdbcManager));
        webApp.addServlet(listItemServlet, "/api/listItems/*");

        //JSP
//        webApp.addServletContainerInitializer(new JettyJasperInitializer());    //Sets so all .jsp files are handled by jetty


        server.setHandler(webApp);


        server.start();
        logger.info("Server started on {}", server.getURI());
    }

    public static void main(String[] args) throws Exception {
        var server = new OnlineStoreServer();

    }

}
