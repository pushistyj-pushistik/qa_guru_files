package guru.qa;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonParsingTest {
    static ClassLoader cl = JsonParsingTest.class.getClassLoader();

    @Test
    void jsonTest() throws Exception {
        Gson gson = new Gson();
        try (InputStream is = cl.getResourceAsStream("files/simple.json")) {
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
            Assertions.assertThat(jsonObject.get("name").getAsString()).isEqualTo("Ivan");
            Assertions.assertThat(jsonObject.get("address").getAsJsonObject().get("house").getAsString()).isEqualTo("23");
        }
    }
}
