import org.infai.ses.senergy.operators.Message;
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
            adder.configMessage(m);
            adder.run(m);

            double valueActual = Double.parseDouble(m.getMessageString().split("sum\":")[1].split("}")[0]);
            double valueExpected = Double.parseDouble(m.getMessageString().split("expectValue\":")[1].split(",")[0]);
            String timestampExpected = m.getMessageString().split("lastTimestamp\":")[1].split(",")[0];
            String timestampActual = m.getMessageString().split("expectTS\":")[1].split(",")[0];
            Assert.assertEquals(valueExpected, valueActual, 0.01);
            Assert.assertEquals(timestampExpected, timestampActual);
        }
    }
}
