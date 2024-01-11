package com.example.couch.function;

import java.util.Random;

public class RandomResult {

    public String getUserCode() {

        Random random = new Random();
        String result = "";
        int type;
        char randomChar;
        int randomInt;

        for (int i = 0; i < 12; i++) {

            type = random.nextInt(2);

            if (type == 0) {

                randomInt = 0;

                while (randomInt < 65) {

                    randomInt = random.nextInt(91);

                }

                randomChar = (char) randomInt;
                result += randomChar;

            } else {

                randomInt = random.nextInt(9);

                result += String.valueOf(randomInt);

            }

        }

        return result;

    } // getUserCode()

}
