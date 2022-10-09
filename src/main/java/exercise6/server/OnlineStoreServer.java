package exercise6.server;

import mysql.jdbc.JDBCManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class OnlineStoreServer {

    private final Server server;
    private final Logger logger = LoggerFactory.getLogger(OnlineStoreServer.class);
    private final JDBCManager jdbcManager = new JDBCManager();

    public OnlineStoreServer(int port) throws Exception {
        this.server = new Server(port);
        server.setHandler(createWebApp());
    }


    private WebAppContext createWebApp() throws Exception {
        WebAppContext webContext = new WebAppContext();
        webContext.setContextPath("/");

        var resources = Resource.newClassPathResource("/webapp");
        var sourceDirectory = new File(resources.getFile()
                .getAbsoluteFile()
                .toString()
                .replace("target\\classes", "src\\main\\resources")
        );

        if (sourceDirectory.isDirectory()) {
            webContext.setBaseResource(Resource.newResource(sourceDirectory));    // Change the default path to src/main/resources, if exist.
            webContext.setInitParameter(DefaultServlet.CONTEXT_INIT + "useFileMappedBuffer", "false");  //To avoid Jetty to lock files for changes.
        } else {
            webContext.setBaseResource(resources);
        }

        webContext.addServlet(new ServletHolder(new AddItemServlet(jdbcManager)), "/api/addItem");
        webContext.addServlet(new ServletHolder(new ListItemServlet(jdbcManager)), "/api/listItems/*");

        logger.info("Server started on {}", server.getURI());
        return webContext;
    }

    public URL getURL() throws MalformedURLException {
        return server.getURI().toURL();
    }

    public void start() throws Exception {
        server.start();
    }

    public static void main(String[] args) throws Exception {
        var server = new OnlineStoreServer(8080);
        server.start();
        System.out.println(server.getURL());
    }
}
