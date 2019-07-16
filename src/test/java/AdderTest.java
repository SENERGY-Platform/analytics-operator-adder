import org.infai.seits.sepl.operators.Message;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class AdderTest {
    @Test
    public void fromSingleStream() throws Exception {
        Adder adder = new Adder();
        List<Message> messages = TestMessageProvider.getTestMesssagesSet();
        for (int i = 0; i < messages.size(); i++) {
            Message m = messages.get(i);
            adder.config(m);
            adder.run(m);

            m.addInput("value");
            m.addInput("timestamp");

            double valueActual = m.getInput("value").getValue();
            double valueExpected = Double.parseDouble(m.getMessageString().split("value\":")[1].split("}")[0]);
            String timestampExpected = m.getInput("timestamp").getString();
            String timestampActual = m.getMessageString().split("timestamp\":\"")[1].split("\"")[0];
            Assert.assertEquals(valueExpected, valueActual, 0.01);
            Assert.assertEquals(timestampExpected, timestampActual);
        }
    }
}
