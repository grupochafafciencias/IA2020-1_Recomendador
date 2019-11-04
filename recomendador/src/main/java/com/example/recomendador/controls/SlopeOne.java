package com.example.recomendador.controls;

import com.example.recomendador.models.Series;
import com.example.recomendador.models.User;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Map;

public class SlopeOne {

    private static Map<Series, Map<Series, Double>> diff = new HashMap<>();
    private static Map<Series, Map<Series, Integer>> freq = new HashMap<>();
    private static Map<User, HashMap<Series, Double>> inputData;
    private static Map<User, HashMap<Series, Double>> outputData = new HashMap<>();

    public static void slopeOne(List<User> numberOfUsers, List<Series> series) {
        inputData = InputData.initializeData(numberOfUsers,series);
        System.out.println("Slope One - Before the Prediction\n");
        buildDifferencesMatrix(inputData);
        System.out.println("\nSlope One - With Predictions\n");
        predict(inputData);
    }

    /**
     * Based on the available data, calculate the relationships between the
     * items and number of occurences
     *
     * @param data
     *            existing user data and their items' ratings
     */
    private static void buildDifferencesMatrix(Map<User, HashMap<Series, Double>> data) {
        for (HashMap<Series, Double> user : data.values()) {
            for (Entry<Series, Double> e : user.entrySet()) {
                if (!diff.containsKey(e.getKey())) {
                    diff.put(e.getKey(), new HashMap<Series, Double>());
                    freq.put(e.getKey(), new HashMap<Series, Integer>());
                }
                for (Entry<Series, Double> e2 : user.entrySet()) {
                    int oldCount = 0;
                    if (freq.get(e.getKey()).containsKey(e2.getKey())) {
                        oldCount = freq.get(e.getKey()).get(e2.getKey()).intValue();
                    }
                    double oldDiff = 0.0;
                    if (diff.get(e.getKey()).containsKey(e2.getKey())) {
                        oldDiff = diff.get(e.getKey()).get(e2.getKey()).doubleValue();
                    }
                    double observedDiff = e.getValue() - e2.getValue();
                    freq.get(e.getKey()).put(e2.getKey(), oldCount + 1);
                    diff.get(e.getKey()).put(e2.getKey(), oldDiff + observedDiff);
                }
            }
        }
        for (Series j : diff.keySet()) {
            for (Series i : diff.get(j).keySet()) {
                double oldValue = diff.get(j).get(i).doubleValue();
                int count = freq.get(j).get(i).intValue();
                diff.get(j).put(i, oldValue / count);
            }
        }
        printData(data);
    }

    /**
     * Based on existing data predict all missing ratings. If prediction is not
     * possible, the value will be equal to -1
     *
     * @param data
     *            existing user data and their items' ratings
     */
    private static void predict(Map<User, HashMap<Series, Double>> data) {
        HashMap<Series, Double> uPred = new HashMap<Series, Double>();
        HashMap<Series, Integer> uFreq = new HashMap<Series, Integer>();
        for (Series j : diff.keySet()) {
            uFreq.put(j, 0);
            uPred.put(j, 0.0);
        }
        for (Entry<User, HashMap<Series, Double>> e : data.entrySet()) {
            for (Series j : e.getValue().keySet()) {
                for (Series k : diff.keySet()) {
                    try {
                        double predictedValue = diff.get(k).get(j).doubleValue() + e.getValue().get(j).doubleValue();
                        double finalValue = predictedValue * freq.get(k).get(j).intValue();
                        uPred.put(k, uPred.get(k) + finalValue);
                        uFreq.put(k, uFreq.get(k) + freq.get(k).get(j).intValue());
                    } catch (NullPointerException e1) {
                    }
                }
            }
            HashMap<Series, Double> clean = new HashMap<Series, Double>();
            for (Series j : uPred.keySet()) {
                if (uFreq.get(j) > 0) {
                    clean.put(j, uPred.get(j).doubleValue() / uFreq.get(j).intValue());
                }
            }
            for (Series j : InputData.items) {
                if (e.getValue().containsKey(j)) {
                    clean.put(j, e.getValue().get(j));
                } else {
                    clean.put(j, -1.0);
                }
            }
            outputData.put(e.getKey(), clean);
        }
        printData(outputData);
    }

    private static void printData(Map<User, HashMap<Series, Double>> data) {
        for (User user : data.keySet()) {
            System.out.println(user.getUsername() + ":");
            print(data.get(user));
        }
    }

    private static void print(HashMap<Series, Double> hashMap) {
        NumberFormat formatter = new DecimalFormat("#0.000");
        for (Series j : hashMap.keySet()) {
            System.out.println(" " + j.getTitle() + " --> " + formatter.format(hashMap.get(j).doubleValue()));
        }
    }

}

