import org.infai.ses.senergy.operators.Builder;
import org.infai.ses.senergy.operators.Config;
import org.infai.ses.senergy.operators.Message;
import org.infai.ses.senergy.utils.ConfigProvider;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestMessageProvider {

    public static List<Message> getTestMesssagesSet() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/test/resources/sample-data-small.json"));
        Builder builder = new Builder("1", "1");
        List<Message> messageSet = new ArrayList<>();
        Config config = new Config(getConfig());
        ConfigProvider.setConfig(config);


        String line;
        Message m;
        JSONObject jsonObjectRead, jsonObject;
        while ((line = br.readLine()) != null) {
            jsonObjectRead = new JSONObject(line);
            jsonObject = new JSONObject().put("device_id", messageSet.size() % 2 == 0 ? "1": "2").put("value", new JSONObject().put("reading", jsonObjectRead));
            m = new Message(builder.formatMessage(jsonObject.toString()));
            messageSet.add(m);
        }
        return messageSet;
    }

    public static String getConfig() {
        return "{\n" +
                "  \"inputTopics\": [\n" +
                "    {\n" +
                "      \"name\": \"iot_bc59400c-405c-4c84-9862-a791daa82b60\",\n" +
                "      \"filterType\": \"DeviceId\",\n" +
                "      \"filterValue\": \"1,2\",\n" +
                "      \"mappings\": [\n" +
                "        {\n" +
                "          \"dest\": \"value1\",\n" +
                "          \"source\": \"value.reading.value1\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"dest\": \"value2\",\n" +
                "          \"source\": \"value.reading.value2\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"dest\": \"value3\",\n" +
                "          \"source\": \"value.reading.value3\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"dest\": \"timestamp\",\n" +
                "          \"source\": \"value.reading.timestamp\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }
}
