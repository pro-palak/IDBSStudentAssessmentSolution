/*
 * Copyright (C) 1993-2020 ID Business Solutions Limited
 * All rights reserved
 */
package com.idbs.devassessment.solution;

import java.io.StringReader;
import java.util.*;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.idbs.devassessment.core.IDBSSolutionException;
import com.idbs.devassessment.core.DifficultyLevel;
import com.idbs.devassessment.harness.DigitalTaxTracker;
import com.idbs.devassessment.solution.model.Terms;

/**
 * Example solution for the example question
 */

public class CandidateSolution extends AssessmentSolutionBase {
    @Override
    public DifficultyLevel getDifficultyLevel() {
        /*
         *
         * CHANGE this return type to YOUR selected choice of difficulty level to which you will code an answer to.
         *
         */

        return DifficultyLevel.LEVEL_2;
    }

    @Override
    public String getAnswer() throws IDBSSolutionException {
        /*
         * This is the default solution and provides some example code on how to extract data from Json in java.
         *
         * As an initial start we suggest you comment ALL the code below and return a null value from the method. Run
         * this in the assessment application and you'll see many examples of the Json that question produces.
         */

        // first get Json as a String for the question using the inherited method...
        String json = getDataForQuestion();

        try {
            return String.valueOf(Double.valueOf(evaluateLevel1(json)).longValue());
        } catch (Exception e) {
            return String.valueOf(Double.valueOf(evaluateLevel2(json)).longValue());
        }
    }

    private double evaluateLevel1(String json) {
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject jsonObject = reader.readObject();
        reader.close();

        int startValue = jsonObject.getInt("xValue");

        JsonArray jsonArray = jsonObject.getJsonArray("terms");

        List<Terms> terms = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObj = jsonArray.getJsonObject(i);
            int power = jsonObj.getInt("power");
            int multiplier = jsonObj.getInt("multiplier");
            String action = jsonObj.getString("action");
            terms.add(new Terms(power, multiplier, action));
        }

        double answer = 0;
        for (Terms term : terms) {
            double value = term.getMultiplier() * Math.pow(startValue, term.getPower());
            if (term.getAction().equals("subtract")) {
                answer = DigitalTaxTracker.subtract(answer, value);
            } else {
                answer = DigitalTaxTracker.add(answer, value);
            }
        }
        return answer;
    }

    // evaluate the expression for the given x value
    private double evaluateLevel2(String expression) {
        double answer = 0;
        //numeric:x = 46; y = +8.x^0+4.x^1+1.x^2+7.x^3

        String[] parts = expression.split("; ");

        String[] xSubParts = parts[0].split(":")[1].split("=");
        String xExpression = xSubParts[1].trim();
        long x = Long.parseLong(xExpression);

        String[] ySubParts = parts[1].split("=");
        String yExpression = ySubParts[1].trim().replace("x", Long.toString(x));

        String[] terms = yExpression.split("(?=[+-])");
        for (String term : terms) { //+8.46^0
            String[] factorParts = term.split("\\.");
            int multiplier = Integer.parseInt(factorParts[0]);
            int power = Integer.parseInt(factorParts[1].split("\\^")[1]);
            double value = multiplier * Math.pow(x, power);
            answer = DigitalTaxTracker.add(answer, value);
        }
        return answer;
    }
}

