/*
 * Copyright 2018 InfAI (CC SES)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.infai.ses.senergy.operators.adder3;

import org.infai.ses.senergy.exceptions.NoValueException;
import org.infai.ses.senergy.operators.BaseOperator;
import org.infai.ses.senergy.operators.FlexInput;
import org.infai.ses.senergy.operators.Helper;
import org.infai.ses.senergy.operators.Message;

import java.util.HashMap;
import java.util.Map;


public class Adder extends BaseOperator {

    private Map<String, Double> map;
    private boolean debug;

    public Adder(){
        map = new HashMap<>();
        debug = Boolean.parseBoolean(Helper.getEnv("DEBUG", "false"));
    }

    @Override
    public void run(Message message) {
        double value1, value2, value3;
        FlexInput valueInput1 = message.getFlexInput("value1");
        FlexInput valueInput2 = message.getFlexInput("value2");
        FlexInput valueInput3 = message.getFlexInput("value3");
        try {
            value1 = valueInput1.getValue();
            value2 = valueInput2.getValue();
            value3 = valueInput3.getValue();
        } catch (NoValueException e) {
            e.printStackTrace();
            return;
        }
        double value = value1 + value2 + value3;
        String timestamp;
        try {
            timestamp = message.getFlexInput("timestamp").getString();
        } catch (NoValueException e) {
            e.printStackTrace();
            return;
        }

        map.put(valueInput1.getCurrentFilterId(), value);

        double sum = map.values().stream().mapToDouble(v -> v).sum();

        if (debug) {
            for (Map.Entry<String, Double> entr: map.entrySet()) {
                System.out.println(entr.getKey() + ": " + entr.getValue());
            }
        }

        message.output("lastTimestamp", timestamp);
        message.output("sum", sum);
    }

    @Override
    public Message configMessage(Message message) {
        message.addFlexInput("value1");
        message.addFlexInput("value2");
        message.addFlexInput("value3");
        message.addFlexInput("timestamp");
        return message;
    }
}
