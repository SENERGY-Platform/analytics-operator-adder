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

package org.infai.ses.senergy.operators.adder;

import org.infai.ses.senergy.operators.BaseOperator;
import org.infai.ses.senergy.operators.FlexInput;
import org.infai.ses.senergy.operators.Helper;
import org.infai.ses.senergy.operators.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Adder extends BaseOperator {

    private Map<String, Double> map;
    private boolean debug;
    private long highTs;

    public Adder(){
        map = new HashMap<>();
        debug = Boolean.parseBoolean(Helper.getEnv("DEBUG", "false"));
    }

    @Override
    public void run(Message message) {
        FlexInput valueInput = message.getFlexInput("value");
        
        Set<Map.Entry<String, Double>> entries = valueInput.getFilterIdValueMap(Double.class).entrySet();
        for (Map.Entry<String, Double> entr: entries) {
            map.put(entr.getKey(), entr.getValue());
        }

        double sum = map.values().stream().mapToDouble(v -> v).sum();

        if (debug) {
            for (Map.Entry<String, Double> entr: map.entrySet()) {
                System.out.println(entr.getKey() + ": " + entr.getValue());
            }
        }

        long currentTs = message.getMessage().getKafkaTimestamp();
        if (currentTs > highTs) {
            highTs = currentTs;
        }
        message.getMessage().setKafkaTimestamp(highTs);
        message.output("sum", sum);
    }

    @Override
    public Message configMessage(Message message) {
        message.addFlexInput("value");
        return message;
    }
}
