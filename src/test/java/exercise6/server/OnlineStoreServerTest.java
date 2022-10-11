package exercise6.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class OnlineStoreServerTest {

    private OnlineStoreServer server;

    @BeforeEach
    void setUp() throws Exception {
        server = new OnlineStoreServer(0);
        server.start();
    }

    @Test
    void shouldReturnResponseCode200() throws IOException {
        var connection = openConnection("/");
        assertThat(connection.getResponseCode()).isEqualTo(200);
    }

    @Test
    void shouldShowFrontPage() throws IOException {
        var connection = openConnection("/");
        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("<title>Online store</title>");
    }

    @Test
    void shouldConnectToApiItems() throws IOException {
        var connection = openConnection("/api/items");
        assertThat(connection.getResponseCode()).isEqualTo(200);
    }

    @Test
    void shouldShowDynamicContent() throws IOException {
        var connection = openConnection("/api/items");
        assertThat(connection.getHeaderField("Content-Type")).isEqualTo("application/json");

        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("{\"item\":\"Laptop x500\"");
    }


    private HttpURLConnection openConnection(String path) throws IOException {
        return (HttpURLConnection) new URL(server.getURL(), path).openConnection();
    }

}














