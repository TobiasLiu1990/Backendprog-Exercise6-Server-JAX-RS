package exercise6.server;

import mysql.jdbc.JDBCManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
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

        setSourceDirectory(webContext);

        //Set up JAX-RS (jerseyServlet)
        var jerseyServlet = webContext.addServlet(ServletContainer.class, "/api/*");
        //Tell jersey where to look - in jersey specific packages - local packages
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "exercise6.server");


//        webContext.addServlet(new ServletHolder(new AddItemServlet(jdbcManager)), "/api/addItem");
//        webContext.addServlet(new ServletHolder(new ListItemServlet(jdbcManager)), "/api/listItems/*");

        logger.info("Server started on {}", server.getURI());
        return webContext;
    }

    //Sets the path to work/build to
    private void setSourceDirectory(WebAppContext webContext) throws IOException {
        var resources = Resource.newClassPathResource("/webapp");
        var sourceDirectory = getSourceDirectory(resources);

        if (sourceDirectory != null) {
            webContext.setBaseResource(Resource.newResource(sourceDirectory));    // Change the default path to src/main/resources, if exist.
            webContext.setInitParameter(DefaultServlet.CONTEXT_INIT + "useFileMappedBuffer", "false");  //To avoid Jetty to lock files for changes.
        } else {
            webContext.setBaseResource(resources);
        }
    }

    //This check is added because getFile() does not work for a .jar file.
    private File getSourceDirectory(Resource resources) throws IOException {
        if (resources.getFile() == null) {
            return null;
        } else {
            var sourceDirectory = new File(resources.getFile()
                    .getAbsolutePath()
                    .replace("target\\classes", "src\\main\\resources"));
            return sourceDirectory.exists() ? sourceDirectory : null;
        }
    }

    public URL getURL() throws MalformedURLException {
        return server.getURI().toURL();
    }

    public void start() throws Exception {
        server.start();
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        String portAzure = System.getenv("HTTP_PLATFORM_PORT");

        if (portAzure != null) {
            port = Integer.parseInt(portAzure);
        }

        var server = new OnlineStoreServer(port);
        server.start();
        System.out.println(server.getURL());
    }
}
