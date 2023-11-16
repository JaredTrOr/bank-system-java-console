package com.validations;

import java.util.HashMap;
import java.util.Map;

public class InputValidations {

    public InputValidations() {}

    public Map<String, Object> checkDoubleInput (String input) {

        Map<String, Object> isDouble = new HashMap<>();

        try {
            isDouble.put("isNumber", true);
            isDouble.put("number", Double.parseDouble(input));
            return isDouble;
        } catch(Exception e) {
            isDouble.put("isNumber", false);
            return isDouble;
        }
    }


}
