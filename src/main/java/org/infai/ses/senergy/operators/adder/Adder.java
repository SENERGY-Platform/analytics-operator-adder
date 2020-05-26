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

import org.infai.ses.senergy.operators.Message;
import org.infai.ses.senergy.operators.OperatorInterface;
import java.util.HashMap;
import java.util.Map;


public class Adder implements OperatorInterface {

    private Map<String, Double> map;

    public Adder(){
        map = new HashMap<>();
    }

    @Override
    public void run(Message message) {
        double value = message.getInput("value").getValue();
        String timestamp = message.getInput("timestamp").getString();

        map.put(message.getMessageEntityId(), value);

        double sum = map.values().stream().mapToDouble(v -> v).sum();

        message.output("lastTimestamp", timestamp);
        message.output("sum", sum);
    }

    @Override
    public void configMessage(Message message) {
        message.addInput("value");
        message.addInput("timestamp");
    }
}