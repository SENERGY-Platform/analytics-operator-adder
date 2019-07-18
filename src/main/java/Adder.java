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


import org.infai.seits.sepl.operators.Helper;
import org.infai.seits.sepl.operators.Message;
import org.infai.seits.sepl.operators.OperatorInterface;

import java.time.format.DateTimeParseException;


public class Adder implements OperatorInterface {

    private boolean debug;

    public Adder(){
        debug = Helper.getEnv("DEBUG", "false").equalsIgnoreCase("true");
    }

    @Override
    public void run(Message message) {
        double value1 = message.getInput("value1").getValue();
        double value2 = message.getInput("value2").getValue();
        String timestamp1 = message.getInput("timestamp1").getString();
        String timestamp2 = message.getInput("timestamp2").getString();

        if(debug){
            System.out.println("Got values:\n\tvalue1: " + value1 + "\n\ttimestamp1: " + timestamp1
                    + "\n\tvalue2: " + value2 + "\n\ttimestamp2" + timestamp2);
        }

        long long1 = 0, long2 = 0;

        try{
            long1 = DateParser.parseDateMills(timestamp1);
        } catch (DateTimeParseException e) {
            System.err.println("Could not parse timestamp1, assume 0");
        }
        try{
            long2 = DateParser.parseDateMills(timestamp1);
        } catch (DateTimeParseException e) {
            System.err.println("Could not parse timestamp2, assume 0");
        }

        message.output("timestamp", long1 > long2 ? timestamp1 : timestamp2); //Outputs latest timestamp
        message.output("value", value1 + value2);
    }

    @Override
    public void config(Message message) {
        message.addInput("value1");
        message.addInput("value2");
        message.addInput("timestamp1");
        message.addInput("timestamp2");
    }
}
