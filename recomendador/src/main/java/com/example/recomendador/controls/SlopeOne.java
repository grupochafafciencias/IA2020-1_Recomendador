package com.example.recomendador.controls;

import com.example.recomendador.models.Series;
import com.example.recomendador.models.User;
import com.example.recomendador.models.UserSeries;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SlopeOne {

    private static Map<Series, Map<Series, Double>> diff = new HashMap<>();
    private static Map<Series, Map<Series, Integer>> freq = new HashMap<>();
    private static Map<User, HashMap<Series, Double>> inputData;
    private static Map<User, HashMap<Series, Double>> outputData = new HashMap<>();

    public static Set<User> slopeOne(List<User> numberOfUsers, List<Series> series) {
        inputData = InputData.initializeData(numberOfUsers,series);
        System.out.println("Slope One - Before the Prediction\n");
        buildDifferencesMatrix(inputData);
        System.out.println("\nSlope One - With Predictions\n");
        return predict(inputData);
    }

    private static void buildDifferencesMatrix(Map<User, HashMap<Series, Double>> data) {
        for (HashMap<Series, Double> user : data.values()) {
            for (Entry<Series, Double> e : user.entrySet()) {
                if (!diff.containsKey(e.getKey())) {
                    diff.put(e.getKey(), new HashMap<Series, Double>());
                    freq.put(e.getKey(), new HashMap<Series, Integer>());
                }
                for (User usr : data.keySet()) {
                    for(UserSeries e2: usr.getWatched()){
                    int oldCount = 0;
                    if (freq.get(e.getKey()).containsKey(e2.getSeries())) {
                        oldCount = freq.get(e.getKey()).get(e2.getSeries());
                    }
                    double oldDiff = 0.0;
                    if (diff.get(e.getKey()).containsKey(e2.getSeries())) {
                        oldDiff = diff.get(e.getKey()).get(e2.getSeries());
                    }
                    double observedDiff = e.getValue() - e2.getRating();
                    freq.get(e.getKey()).put(e2.getSeries(), oldCount + 1);
                    diff.get(e.getKey()).put(e2.getSeries(), oldDiff + observedDiff);}
                }
            }
        }
        for (Series j : diff.keySet()) {
            for (Series i : diff.get(j).keySet()) {
                double oldValue = diff.get(j).get(i);
                int count = freq.get(j).get(i);
                diff.get(j).put(i, oldValue / count);
            }
        }
    }

    private static Set<User> predict(Map<User, HashMap<Series, Double>> data) {
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
                        double predictedValue = diff.get(k).get(j) + e.getValue().get(j);
                        double finalValue = predictedValue * freq.get(k).get(j);
                        uPred.put(k, uPred.get(k) + finalValue);
                        uFreq.put(k, uFreq.get(k) + freq.get(k).get(j));
                    } catch (NullPointerException e1) {
                    }
                }
            }
            System.out.println(uFreq);
            System.out.println(uPred);
            HashMap<Series, Double> clean = new HashMap<Series, Double>();
            for (Series j : uPred.keySet()) {
                if (uFreq.get(j) > 0) {
                    clean.put(j, uPred.get(j)/ uFreq.get(j));
                }
            }
            System.out.println(clean);
            outputData.put(e.getKey(), clean);
        }
        return printData(outputData);
    }

    private static Set<User> printData(Map<User, HashMap<Series, Double>> data) {
        Set<User> foo=data.keySet();
        for (User user : foo) {
            System.out.println(user.getUsername() + ":");
            HashMap<Series,Double> hashMap=data.get(user);
            NumberFormat formatter = new DecimalFormat("#0.000");
            int len=hashMap.keySet().size();
            UserSeries[] userSeries=new UserSeries[len];
            int counter=0;
            for (Series j : hashMap.keySet()) {
                System.out.println(" " + j.getTitle() + " --> " + formatter.format(hashMap.get(j).doubleValue()));
                userSeries[counter]=new UserSeries(j,hashMap.get(j));
                counter++;
            }
            user.setWatched(userSeries);
        }
        return foo;
    }
}

